//package com.mod.currentmagic.network;
//
//import com.mod.currentmagic.particles.Flame;
//import hohserg.elegant.networking.api.ElegantPacket;
//import hohserg.elegant.networking.api.ServerToClientPacket;
//import net.minecraft.client.Minecraft;
//import net.minecraft.util.EnumParticleTypes;
//
//@ElegantPacket
//public class ExamplePacket2 implements ServerToClientPacket {
//    final EnumParticleTypes particleType;
//    final float x, y, z, velX, velY, velZ;
//
//    public ExamplePacket2(EnumParticleTypes particleTypes, float x, float y, float z, float velX, float velY, float velZ) {
//        this.particleType = particleTypes;
//        this.x = x;
//        this.y = y;
//        this.z = z;
//        this.velX = velX;
//        this.velY = velY;
//        this.velZ = velZ;
//    }
//
//    @Override
//    public void onReceive(Minecraft mc) {
//        switch (particleType){
//            case FLAME:
//            Minecraft.getMinecraft().effectRenderer.addEffect(new Flame(mc.world, x, y, z, velX, velY, velZ));
//        }
//    }
//}
