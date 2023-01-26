package com.mod.currentmagic.magic;

import com.mod.currentmagic.events.Event;
import com.mod.currentmagic.utils.Methods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AbilityMech {
    private static Vec3d playerLook, playerPosVector;
    private static float playerEyeHeight;
    private static EntityPlayer playerc;

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event){
        //LineFire
        if (isActiveLineFire) {
            ticklf++;
            if (ticklf >= 2) {
                contLineFire(xlf +=0.5, Event.world);
                ticklf = 0;
                if (xlf == 10) {
                    isActiveLineFire = false;
                    xlf = 1;
                }
            }
        }

        //Spurt
        if(isActiveSpurt){
            ticksp++;
            if(ticksp >= 4){
                contSpurt(xsp++);
                ticksp = 0;
                if(xsp > 2){
                    isActiveSpurt = false;
                    xsp = 0;
                }
            }
        }
    }


    public static boolean cdlf = true;
    private byte ticklf = 0;
    private float xlf = 1;
    private static boolean isActiveLineFire = false;
    public static void LineFire(EntityPlayer player){
        if(cdlf) {
            cdlf = false;
            isActiveLineFire = true;
            playerc = player;
            playerLook = player.getLookVec();
            playerPosVector = player.getPositionVector();
            playerEyeHeight = player.getEyeHeight();
        }
    }
    private void contLineFire(float i, World world){
        Vec3d ParticleVec = playerLook.scale(i).addVector(0, playerEyeHeight, 0).add(playerPosVector);
        float x = (float) ParticleVec.x, y = (float) (ParticleVec.y - (0.5 - (i * 0.05))), z = (float) ParticleVec.z;

        if (world.getBlockState(new BlockPos(x, y, z)).isFullBlock() ||
                (world.canBlockSeeSky(new BlockPos(x, y, z)) && world.isRaining()) ||
                world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.WATER)) {

            Vec3d ParticleVecB = playerLook.scale(i - 0.5).addVector(0, playerEyeHeight, 0).add(playerPosVector);
            float xB = (float) ParticleVecB.x, yB = (float) (ParticleVecB.y - (0.5 - (i * 0.05))), zB = (float) ParticleVecB.z;
            Methods.spawnParticle(world, EnumParticleTypes.SMOKE_NORMAL, xB, yB, zB, 0, 0, 0);
//            world.playSound(null, x, y, z, Sounds.extinguish, SoundCategory.PLAYERS, 3f, 1f);
            isActiveLineFire = false; this.xlf = 1;

        } else {

            if (Methods.entityR(world, x, y, z, 1, playerc) != null) {
                Methods.entityR(world, x, y, z, 1, playerc).attackEntityFrom(DamageSource.causePlayerDamage(playerc).setFireDamage(), 0);
                Methods.entityR(world, x, y, z, 1, playerc).setFire(3);
                isActiveLineFire = false; this.xlf = 1;
            }

//            world.playSound(null, x, y, z, Sounds.flame, SoundCategory.PLAYERS, 1f, 1f);
            Methods.spawnParticle(world, EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
        }
    }


    public static boolean cdsp = true;
    private byte ticksp = 0;
    private byte xsp = 0;
    private static boolean isActiveSpurt = false;
    public static void Spurt(EntityPlayer player){
        if(!player.onGround) return;

        if(cdsp) {
            cdsp = false;
            isActiveSpurt = true;
            playerc = player;
            if(player.moveStrafing == 0) {
                playerLook = player.getLookVec();
            }else{
                playerLook = player.getLookVec().rotateYaw(Math.signum(player.moveStrafing) * (float)Math.PI/2f);
            }

            player.getWorldScoreboard().createTeam(String.valueOf(player.getEntityId()));
            player.getWorldScoreboard().addPlayerToTeam(player.getName(), String.valueOf(player.getEntityId()));
            player.getWorldScoreboard().getTeam(String.valueOf(player.getEntityId())).setSeeFriendlyInvisiblesEnabled(true);
        }
    }
    private void contSpurt(byte i){

        if(Event.extra.contains("Shift")) {
            playerc.motionX = -playerLook.scale(1).x;
            playerc.motionZ = -playerLook.scale(1).z;
            playerc.motionY = -0.1;
        }else{
            playerc.motionX = playerLook.scale(1).x;
            playerc.motionZ = playerLook.scale(1).z;
            playerc.motionY = 0.1;
        }

        playerc.setInvisible(i!=2);
        if(i == 2)playerc.getWorldScoreboard().removeTeam(playerc.getWorldScoreboard().getTeam(String.valueOf(playerc.getEntityId())));
    }


    public static void RingsFire(EntityPlayer player){
        playerLook = player.getLookVec();
        playerPosVector = player.getPositionVector();
        playerEyeHeight = player.getEyeHeight();

        for(byte i = 1; i < 10; i++){
            Vec3d ParticleVec = playerLook.scale(Math.sin(i*(Math.PI / 4))).addVector(0, playerEyeHeight, 0).add(playerPosVector);
            float x = (float)(ParticleVec.x), y = (float)(ParticleVec.y + Math.cos(i*(Math.PI / 4))) , z = (float)(ParticleVec.z);

            Methods.spawnParticle(Event.world, EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
        }
    }


    public static void Und(EntityPlayer player){
        Entity enemy = Methods.entityR(Event.world, (float)player.posX, (float)player.posY, (float)player.posZ, 4, player);
    }

}
