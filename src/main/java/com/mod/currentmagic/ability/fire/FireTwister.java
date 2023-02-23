package com.mod.currentmagic.ability.fire;

import com.mod.currentmagic.ability.Ability;
import com.mod.currentmagic.collision.AABB;
import com.mod.currentmagic.network.CPacketDamageAABB;
import com.mod.currentmagic.network.CPacketParticle;
import com.mod.currentmagic.network.CPacketSound;
import com.mod.currentmagic.util.Damage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FireTwister extends Ability {
    public static FireTwister ability = new FireTwister();
    public static AABB aFireTwister;
    private float x, y, z;
    public void start(EntityPlayer player){
        if(statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;
        super.start(player, "Fire Twister", "§c", 15, (short)2, (short)100);

    }

    @Override
    protected boolean onUpdate(){

        for(byte i = 0; i<20; i++){
            x = (float)(player.posX+i*0.1f*(Math.sin(repeat*(Math.PI/5))));
            y = (float)player.posY+i*0.5f;
            z = (float)(player.posZ+i*0.1f*(Math.cos(repeat*(Math.PI/5))));

            if (!player.world.isAirBlock(new BlockPos(x, y, z)) ||
                    (player.world.canBlockSeeSky(new BlockPos(x, y, z)) && player.world.isRaining()) ||
                    player.world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.WATER)) {

                new CPacketSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, (float)player.posX,
                        (float)player.posY, (float)player.posZ, 1f, 1f).sendToServer();
                new CPacketParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 3).sendToServer();

                break;
            }

            new CPacketParticle(EnumParticleTypes.FLAME, x, y, z, 1).sendToServer();
            aFireTwister = new AABB((float)player.posX, y, (float)player.posZ, i*0.3f);
            new CPacketDamageAABB(aFireTwister, 0.3f, Damage.Fire, 2).sendToServer();
        }

        new CPacketSound(SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, (float)player.posX,
                (float)player.posY, (float)player.posZ, 1f, 1f).sendToServer();

        player.motionX *= 0.3;
        player.motionZ *= 0.3;
        return true;
    }

    @Override
    protected void onExit(){
        super.onExit();
        player.capabilities.setPlayerWalkSpeed(0.1f);
    }

    @SubscribeEvent
    public void Jump(LivingEvent.LivingJumpEvent event){
        if(statusAbility.equals(Status.active)) player.motionY = 0;
    }
}
