package com.mod.currentmagic.ability.air;

import com.mod.currentmagic.ability.Ability;
import com.mod.currentmagic.network.CPacketFall;
import com.mod.currentmagic.network.CPacketParticle;
import com.mod.currentmagic.network.CPacketSound;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;

public class AirWhiff extends Ability {
    public static AirWhiff ability = new AirWhiff();
    private float startDistance;
    public void start(EntityPlayer player){
        if(statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Air Whiff", "§f", 10, (short)3, (short)100);
        this.startDistance = (float)player.posY;
        player.motionY = 0.5;
        player.capabilities.allowFlying = true;
        player.capabilities.isFlying = true;
        player.capabilities.setFlySpeed(0.02f);
    }

    @Override
    protected boolean onUpdate(){
        new CPacketParticle(EnumParticleTypes.SPELL, (float)(player.posX+(Math.sin(repeat*(Math.PI/5)))*0.6), (float)player.posY, (float)(player.posZ+(Math.cos(repeat*(Math.PI/5)))*0.6), 4).sendToServer();
        new CPacketSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, (float)player.posX, (float)player.posY, (float)player.posZ, 1f, 1f).sendToServer();
        new CPacketFall().sendToServer();

        if(player.onGround) return false;
        if(!player.capabilities.isFlying) player.capabilities.isFlying = true;
        return (player.posY - startDistance)<=5;
    }

    @Override
    protected void onExit(){
        super.onExit();
        if(!player.isCreative())
            player.capabilities.allowFlying = false;
        player.capabilities.setFlySpeed(0.05f);
        player.capabilities.isFlying = false;
    }
}
