package com.mod.currentmagic.ability.fire;

import com.mod.currentmagic.ability.Ability;
import com.mod.currentmagic.network.SPacketParticle;
import com.mod.currentmagic.network.SPacketSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;


public class FireBranch extends Ability {

        public static final FireBranch ability = new FireBranch();
        @Override
        public void start(EntityPlayer player){
                MinecraftForge.EVENT_BUS.register(this);
                this.name = "Fire Branch";
                this.setCooldown(10);
                this.setPeriod((short) 40);
                this.setRepeat((short) 5);
                super.start(player);
        }
        @Override
        protected void onUpdate(){
                super.onUpdate();
                new SPacketSound(player.getPosition(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, 1f, 1f).sendToServer();
                new SPacketParticle(EnumParticleTypes.FLAME, player.getPosition(), 1).sendToServer();
        }
}
