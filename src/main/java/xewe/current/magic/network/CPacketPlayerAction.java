package xewe.current.magic.network;

import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;

@ElegantPacket
public class CPacketPlayerAction implements ServerToClientPacket {
    final Vec3d vec;
    public CPacketPlayerAction(Vec3d vec){this.vec = vec;}
    @Override
    public void onReceive(Minecraft mc) {
        Minecraft.getMinecraft().player.motionZ = vec.z;
        Minecraft.getMinecraft().player.motionY = vec.y;
        Minecraft.getMinecraft().player.motionX = vec.x;
    }
}
