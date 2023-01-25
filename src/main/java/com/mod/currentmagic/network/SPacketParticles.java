package com.mod.currentmagic.network;

import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;

@ElegantPacket
public class SPacketParticles implements ServerToClientPacket {

    final EnumParticleTypes particleType;
    final float x, y, z, velX, velY, velZ;

    public SPacketParticles(EnumParticleTypes particleType, float x, float y, float z, float velX, float velY, float velZ) {
        this.particleType = particleType;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
    }

    @Override
    public void onReceive(Minecraft mc) {
        mc.player.sendMessage(new TextComponentString("lol"));
    }
}
