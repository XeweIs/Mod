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
import net.minecraft.util.math.Vec3d;

public class FireBranch extends Ability {

        public static final FireBranch ability = new FireBranch();
        public static AABB aFireBranch;
        float x, y, z;

        public void start(EntityPlayer player){
                if(statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

                super.start(player, "Fire Branch", "§c", 5, (short)2, (short)10);
        }

        @Override
        protected boolean onUpdate(){

                Vec3d CurrentPosition = playerVec.scale((maxRepeat-repeat)*0.5).addVector(0, playerEyeHeight, 0).add(playerPosVec);
                x = (float)CurrentPosition.x; y = (float)CurrentPosition.y; z = (float) CurrentPosition.z;
                aFireBranch = new AABB(x, y, z, 0.5f);

                if (!player.world.isAirBlock(new BlockPos(x, y, z)) ||
                        (player.world.canBlockSeeSky(new BlockPos(x, y, z)) && player.world.isRaining()) ||
                        player.world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.WATER)) return false;

                new CPacketSound(SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, x, y, z, 1f, 1f).sendToServer();
                new CPacketParticle(EnumParticleTypes.FLAME, x, y, z, 4).sendToServer();
                new CPacketDamageAABB(aFireBranch, Damage.OnlyFire, 2).sendToServer();

                return true;
        }

        @Override
        protected void onExit(){
                super.onExit();
                Vec3d CurrentPosition = playerVec.scale(((maxRepeat-repeat)-1)*0.5).addVector(0, playerEyeHeight, 0).add(playerPosVec);
                x = (float)CurrentPosition.x; y = (float)CurrentPosition.y; z = (float)CurrentPosition.z;
                new CPacketSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, x, y, z, 1f, 1f).sendToServer();
                new CPacketParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 3).sendToServer();
        }
}
