package xewe.current.magic.newnetwork;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class Packet extends AbstractPacket<Packet> {
    int i;

    public Packet() {
    }

    public Packet(int i) {
        this.i = i;
    }

    @Override
    public void handleClient(Packet message, EntityPlayerSP player) {

    }

    @Override
    public void handleServer(Packet message, EntityPlayerMP player) {
        player.sendMessage(new TextComponentString(""+message.i));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.i = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(i);
    }
}
