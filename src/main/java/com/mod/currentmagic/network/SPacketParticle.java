package com.mod.currentmagic.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

@ElegantPacket
public class SPacketParticle implements ClientToServerPacket {
    final EnumParticleTypes particle;
    final BlockPos pos;
    final int count, speed;
    final float xv, yv, zv;
    public SPacketParticle(EnumParticleTypes particle, BlockPos pos){
        this.particle = particle;
        this.pos = pos;
        this.xv = 0;
        this.yv = 0;
        this.zv = 0;
        this.count = 1;
        this.speed = 0;
    }

    public SPacketParticle(EnumParticleTypes particle, BlockPos pos, int count){
        this.particle = particle;
        this.pos = pos;
        this.xv = 0f;
        this.yv = 0f;
        this.zv = 0f;
        this.count = count;
        this.speed = 0;
    }

    public SPacketParticle(EnumParticleTypes particle, BlockPos pos, int count, int speed){
        this.particle = particle;
        this.pos = pos;
        this.xv = 0;
        this.yv = 0;
        this.zv = 0;
        this.count = count;
        this.speed = speed;
    }

    public SPacketParticle(EnumParticleTypes particle, BlockPos pos, int count, int speed, float xv, float yv, float zv){
        this.particle = particle;
        this.pos = pos;
        this.xv = xv;
        this.yv = yv;
        this.zv = zv;
        this.count = count;
        this.speed = speed;
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        ((WorldServer)player.world).spawnParticle(particle, pos.getX(), pos.getY(), pos.getZ(), count, xv, yv, zv, 0);
    }
}
