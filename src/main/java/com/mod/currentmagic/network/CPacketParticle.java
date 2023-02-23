package com.mod.currentmagic.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

@ElegantPacket
public class CPacketParticle implements ClientToServerPacket {
    final EnumParticleTypes particle;
    final float x, y, z, xv, yv, zv, speed;
    final int count;
    public CPacketParticle(EnumParticleTypes particle, float x, float y, float z){
        this.particle = particle;
        this.count = 1;
        this.speed = 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xv = 0f;
        this.yv = 0f;
        this.zv = 0f;
    }

    public CPacketParticle(EnumParticleTypes particle, float x, float y, float z, int count){
        this.particle = particle;
        this.count = count;
        this.speed = 0f;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xv = 0f;
        this.yv = 0f;
        this.zv = 0f;
    }

    public CPacketParticle(EnumParticleTypes particle, float x, float y, float z, float speed, int count){
        this.particle = particle;
        this.count = count;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xv = 0f;
        this.yv = 0f;
        this.zv = 0f;
    }

    public CPacketParticle(EnumParticleTypes particle, float x, float y, float z, float xv, float yv, float zv, float speed, int count){
        this.particle = particle;
        this.count = count;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xv = xv;
        this.yv = yv;
        this.zv = zv;
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        ((WorldServer)player.world).spawnParticle(particle, x, y, z, count, xv, yv, zv, speed);
    }
}
