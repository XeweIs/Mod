package xewe.current.magic.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

@ElegantPacket
public class CPacketSound implements ClientToServerPacket {
    final SoundEvent sound;
    final SoundCategory soundCategory;
    final float x, y, z, volume, pitch;
    public CPacketSound(SoundEvent sound, SoundCategory soundCategory, float x, float y, float z, float volume, float pitch){
        this.sound = sound;
        this.soundCategory = soundCategory;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        player.world.playSound(null, x, y, z, sound, soundCategory, volume, pitch);
    }
}
