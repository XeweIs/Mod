package xewe.current.magic.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import xewe.current.magic.ability.Ability;
import xewe.current.magic.events.KeyPressed;
import xewe.current.magic.network.CPacketParticle;
import xewe.current.magic.network.CPacketSound;

public class Jump extends Ability {
    public static Jump ability = new Jump();

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Jump", "§f", 3, (short) 1, (short) 5);
        new CPacketSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, (float) player.posX, (float) player.posY, (float) player.posZ, 1f, 1f).sendToServer();
    }

    @Override
    protected boolean onUpdate() {
        player.motionY = KeyPressed.Shift ? 1.5 : 1;
        new CPacketParticle(EnumParticleTypes.CLOUD, (float) player.posX, (float) player.posY, (float) player.posZ).sendToServer();
        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
    }
}
