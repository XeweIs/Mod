package xewe.current.magic.ability.air;

import xewe.current.magic.ability.Ability;
import xewe.current.magic.events.KeyPressed;
import xewe.current.magic.network.CPacketParticle;
import xewe.current.magic.network.CPacketSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import xewe.current.magic.network.SPacketEntityAction;

public class AirDash extends Ability {
    public static AirDash ability = new AirDash();

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Air Dash", "§f", 4, (short) 1, (short) 1);

        //Направление рывка будет зависеть от движения игрока по сёрфу. Движется влево - рывок влево.
        if (player.moveStrafing != 0) {
            this.playerVec = player.getLookVec().rotateYaw(Math.signum(player.moveStrafing) * (float) Math.PI / 2f);
        }

        SPacketEntityAction packet = new SPacketEntityAction(player.getEntityId());
        packet.notDamageFall();
        packet.sendToServer();

        new CPacketSound(SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, (float) player.posX, (float) player.posY, (float) player.posZ,
                1f, 3f).sendToServer();
        for (byte i = 0; i <= 10; i++)
            new CPacketParticle(EnumParticleTypes.SPELL, (float) (player.posX + (Math.sin(i * (Math.PI / 5))) * 0.5),
                    (float) player.posY + 0.3f, (float) (player.posZ + (Math.cos(i * (Math.PI / 5))) * 0.5), 0, 10).sendToServer();
    }

    @Override
    protected boolean onUpdate() {
        if(KeyPressed.Shift){
            player.motionX = -playerVec.scale(1).x;
            player.motionY = 0.2;
            player.motionZ = -playerVec.scale(1).z;
        }else {
            player.motionX = playerVec.scale(1).x;
            player.motionY = 0.2;
            player.motionZ = playerVec.scale(1).z;
        }

        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
    }
}
