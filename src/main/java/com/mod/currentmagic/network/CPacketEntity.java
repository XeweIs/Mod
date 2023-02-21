package com.mod.currentmagic.network;

import com.mod.currentmagic.collision.AABB;
import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

@ElegantPacket
public class CPacketEntity implements ClientToServerPacket {
    final EntityPlayer entity;
    public CPacketEntity(EntityPlayer entity){
        this.entity = entity;
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        entity.fallDistance = 0;
    }
}
