package xewe.current.magic.archive.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import xewe.current.magic.archive.network.ExamplePacket.Message;

/**
 * {@param @Message} - пакет, который получаем
 * {@param @IMessage} - ответ на полученный пакет
 */
public class ExamplePacket implements IMessageHandler<Message, IMessage> {
    @Override
    public IMessage onMessage(Message message, MessageContext ctx) {

        //Значение из этого пакета
        int value = message.value;

        // Чтобы убедиться, что мы находимся на стороне сервера.
        if (ctx.side.isServer()) {

            //Объект серверного игрока, который отправил пакет.
            final EntityPlayerMP player = ctx.getServerHandler().player;

            //Основной поток сервера, где выполняются действия
            player.getServerWorld().addScheduledTask(() -> {
                //Код
            });
        }

        // Чтобы убедиться, что мы находимся на стороне клиента
        if (ctx.side.isClient()) {

            //Объект клиентского игрока, который отправил пакет.
            final EntityPlayerSP player = Minecraft.getMinecraft().player;

            Minecraft.getMinecraft().addScheduledTask(() -> {
                //Код

            });
        }

        return null;
    }

    public static class Message implements IMessage {
        int value;

        // А без этого конструктора всё полетит к чертям собачьим! НЕ ТРОГАТЬ /!\
        public Message() {
        }

        //Конструктор для установки значений, которые будут передаваться на сервер/клиент
        public Message(int value) {
            this.value = value;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            // Главное здесь - порядок. Чтение буфера должно быть в том же порядке, что и запись.
            this.value = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(value);
        }
    }
}
