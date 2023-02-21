package com.mod.currentmagic.ability.air;

import com.mod.currentmagic.ability.Ability;
import com.mod.currentmagic.network.CPacketEntity;
import com.mod.currentmagic.network.CPacketParticle;
import com.mod.currentmagic.network.CPacketSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;

public class AirDash extends Ability {
    public static AirDash ability = new AirDash();

    public void start(EntityPlayer player){
        if(statusCoolDown.equals(Status.active)) return;

        super.start(player, "Air Dash", "§f", 3, (short)10, (short)3);

        //Направление рывка будет зависеть от движения игрока по сёрфу. Движется влево - рывок влево.
        if(player.moveStrafing != 0) {
            this.playerVec = player.getLookVec().rotateYaw(Math.signum(player.moveStrafing) * (float)Math.PI/2f);
        }

        new CPacketEntity(player).sendToServer();
        new CPacketSound(SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, (float)player.posX, (float)player.posY, (float)player.posZ, 1f, 3f).sendToServer();
        for(byte i = 0; i <= 10 ; i++) new CPacketParticle(EnumParticleTypes.SPELL, (float)(player.posX+(Math.sin(i*(Math.PI/5)))*0.5),
                (float)player.posY+0.3f, (float)(player.posZ+(Math.cos(i*(Math.PI/5)))*0.5), 10, 0).sendToServer();
    }

    @Override
    public boolean onUpdate(){

        player.motionX = playerVec.scale(1).x;
        player.motionY = 0.2;
        player.motionZ = playerVec.scale(1).z;

        return true;
    }
}
