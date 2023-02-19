package com.mod.currentmagic.network;

import com.mod.currentmagic.collision.AABB;
import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;

@ElegantPacket
public class CPacketDamageAABB implements ClientToServerPacket {
    final AABB aabb;
    public CPacketDamageAABB(AABB aabb){
        this.aabb = aabb;
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        if(aabb.onEntityCollided(player.world, player) != null){
            aabb.onEntityCollided(player.world, player).setFire(2);
        }
    }
}
