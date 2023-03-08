package xewe.current.magic.archive.network;

import xewe.current.magic.enums.EnumCustomParticles;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xewe.current.magic.archive.network.SPacketParticles.Message;

public class SPacketParticles implements IMessageHandler<Message, IMessage> {

    @Override
    public IMessage onMessage(Message message, MessageContext ctx) {

        if (ctx.side.isServer()) {

            final EntityPlayerMP player = ctx.getServerHandler().player;

            EnumParticleTypes vanillaParticle = message.vanillaParticle;
            EnumCustomParticles customParticle = message.customParticle;
            float
                    x = message.x,
                    y = message.y,
                    z = message.z;
            int
                    count = message.count;

            player.getServerWorld().addScheduledTask(() -> {

                if (vanillaParticle != null)
                    ((WorldServer) player.world).spawnParticle(vanillaParticle, x, y, z, count, 0f, 0f, 0f, 0f);

                else if (customParticle != null)
                    CMPacketHandler.sendToAllAround(new CMPacketHandler.CPacketCustomParticles.Message(customParticle, x, y, z), player.world, x, y, z, 12);

            });
        }

        return null;
    }

    public static class Message implements IMessage {

        EnumParticleTypes vanillaParticle;
        EnumCustomParticles customParticle;
        float x, y, z;
        int count;

        public Message() {
        }

        public Message(EnumParticleTypes particle, float x, float y, float z, int count) {
            this.vanillaParticle = particle;
            this.x = x;
            this.y = y;
            this.z = z;
            this.count = count;
        }

        public Message(EnumCustomParticles particle, float x, float y, float z) {
            this.customParticle = particle;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            // Главное здесь - порядок. Чтение буфера должно быть в том же порядке, что и запись.
            this.vanillaParticle = EnumParticleTypes.values()[buf.readInt()];
            this.x = buf.readFloat();
            this.y = buf.readFloat();
            this.z = buf.readFloat();
            this.count = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(vanillaParticle.ordinal());
            buf.writeFloat(x);
            buf.writeFloat(y);
            buf.writeFloat(z);
            buf.writeInt(count);
        }
    }
}
