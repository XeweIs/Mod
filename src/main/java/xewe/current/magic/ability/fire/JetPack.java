package xewe.current.magic.ability.fire;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import xewe.current.magic.ability.Ability;
import xewe.current.magic.events.KeyPressed;
import xewe.current.magic.network.CPacketParticle;
import xewe.current.magic.network.CPacketSound;
import xewe.current.magic.network.SPacketEntityAction;
import xewe.current.magic.util.UtilVector;

public class JetPack extends Ability {
    public static JetPack ability = new JetPack();

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Jetpack", "§c", 10, (short) 2, (short) 45);
        player.capabilities.allowFlying = true;
        player.capabilities.isFlying = true;
        player.capabilities.setFlySpeed(0.007f);
    }

    @Override
    protected boolean onUpdate() {
        if (KeyPressed.Shift) return false;

        player.motionY = 0.2;
        Vec3d band = UtilVector.getVectorForRotation(0, player.rotationYaw).rotateYaw(1.5707963705062866f);
        for (byte i = -1; i < 2; i++) {
            if (i == 0) continue;

            Vec3d vec = UtilVector.getVectorForRotation(0, player.rotationYaw).scale(0).addVector(band.scale(i / 3f).x, -0.2f, band.scale(i / 3f).z).add(player.getPositionVector());
            new CPacketParticle(EnumParticleTypes.FLAME, (float) vec.x, (float) vec.y, (float) vec.z,
                    0, 4).sendToServer();
        }
        new CPacketSound(SoundEvents.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS, (float) player.posX, (float) player.posY, (float) player.posZ, 1f, 0.5f).sendToServer();
        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
        if (!player.isCreative())
            player.capabilities.allowFlying = false;
        player.capabilities.setFlySpeed(0.05f);
        player.capabilities.isFlying = false;

        SPacketEntityAction packet = new SPacketEntityAction(player.getEntityId());
        packet.notDamageFall();
        packet.sendToServer();
    }
}
