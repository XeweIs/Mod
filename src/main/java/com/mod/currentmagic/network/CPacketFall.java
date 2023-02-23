package com.mod.currentmagic.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;

@ElegantPacket
public class CPacketFall implements ClientToServerPacket {
    public CPacketFall(){
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        player.fallDistance = 0;
    }
}
