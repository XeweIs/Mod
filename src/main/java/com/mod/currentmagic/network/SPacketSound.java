package com.mod.currentmagic.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

@ElegantPacket
public class SPacketSound implements ClientToServerPacket {
    final BlockPos pos;
    final SoundEvent sound;
    final SoundCategory soundCategory;
    final float volume;
    final float pitch;
    public SPacketSound(BlockPos pos, SoundEvent sound, SoundCategory soundCategory, float volume, float pitch){
        this.pos = pos;
        this.sound = sound;
        this.soundCategory = soundCategory;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        player.world.playSound(null, pos, sound, soundCategory, volume, pitch);
    }
}
