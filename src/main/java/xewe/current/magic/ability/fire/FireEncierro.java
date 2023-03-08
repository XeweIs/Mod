package xewe.current.magic.ability.fire;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xewe.current.magic.ability.Ability;
import xewe.current.magic.network.CPacketSound;
import xewe.current.magic.network.SPacketEntityAction;
import xewe.current.magic.network.SPacketWorldAction;
import xewe.current.magic.util.UtilVector;

public class FireEncierro extends Ability {
    public static FireEncierro ability = new FireEncierro();

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active) || !player.onGround) return;

        super.start(player, "Fire Encierro", "§c", 10, (short) 5, (short) 5);
    }

    @Override
    protected boolean onUpdate() {
        player.motionX = UtilVector.getVectorForRotation(0, player.rotationYaw).scale(1).x;
        player.motionZ = UtilVector.getVectorForRotation(0, player.rotationYaw).scale(1).z;
        SPacketWorldAction packet = new SPacketWorldAction();
        packet.explosion(new BlockPos(player.posX, player.posY, player.posZ), 0.4f);
        packet.sendToServer();
        return true;
    }

    @SubscribeEvent
    public void Jump(LivingEvent.LivingJumpEvent event) {
        if (statusAbility.equals(Status.active)) player.motionY = 0;
    }

    @Override
    protected void onExit() {
        super.onExit();
    }
}
