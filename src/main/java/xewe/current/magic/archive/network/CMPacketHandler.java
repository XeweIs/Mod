package xewe.current.magic.archive.network;

import xewe.current.magic.CurrentMagic;
import xewe.current.magic.enums.EnumCustomParticles;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CMPacketHandler {
    public static SimpleNetworkWrapper INSTANCE;
    private static short id = 0;

    //Для регистрации пакетов
    public static void initPackets() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CurrentMagic.MODID.toUpperCase());
        registerMessage(SPacketParticles.class, SPacketParticles.Message.class);
        registerMessage(ExamplePacket.class, ExamplePacket.Message.class);
    }


    public static void sendToAllAround(IMessage packet, World world, double x, double y, double z, double distance) {
        CMPacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, distance));
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> packet, Class<REQ> message) {
        INSTANCE.registerMessage(packet, message, id, Side.CLIENT);
        INSTANCE.registerMessage(packet, message, id, Side.SERVER);
        id++;
    }

    public static class CPacketCustomParticles implements IMessageHandler<CPacketCustomParticles.Message, IMessage> {

        @Override
        public IMessage onMessage(Message message, MessageContext ctx){

            EnumCustomParticles particle = message.particle;
            Particle part = null;

            // Опять же, чтобы убиться, что мы находимся на клиенте
            if(ctx.side.isClient()){
                net.minecraft.client.Minecraft.getMinecraft().addScheduledTask(() ->{
                    switch(particle) {
                        case BlueFire:
                            Minecraft.getMinecraft().effectRenderer.addEffect(part);
                            break;
                    }});
            }

            return null;
        }

        public static class Message implements IMessage {
            public EnumCustomParticles particle;
            public float x, y, z;

            // Этот конструктор просто нужен. Говорю, не трогать, опасно /!\
            public Message(){}

            public Message(EnumCustomParticles particle, float x, float y, float z){
                this.particle = particle;
                this.x = x;
                this.y = y;
                this.z = z;
            }

            @Override
            public void fromBytes(ByteBuf buf){
                this.particle = EnumCustomParticles.values()[buf.readInt()];
                this.x = buf.readFloat();
                this.y = buf.readFloat();
                this.z = buf.readFloat();
            }

            @Override
            public void toBytes(ByteBuf buf){
                buf.writeInt(particle.ordinal());
                buf.writeFloat(x);
                buf.writeFloat(y);
                buf.writeFloat(z);
            }
        }
    }
}
//    public static <REQ extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, IMessage>> packet, Class<REQ> message) {
//        INSTANCE.registerMessage(packet, message, id, Side.CLIENT);
//    }
//}
