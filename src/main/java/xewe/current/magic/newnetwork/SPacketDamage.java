package xewe.current.magic.newnetwork;

import xewe.current.magic.collision.AABB;
import xewe.current.magic.enums.ActionEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;

public class SPacketDamage extends AbstractPacket<SPacketDamage> {
    AABB aabb;
    ActionEntity TypeDamage;
    float damage;
    ByteBuf buf;

    public SPacketDamage() {
    }

    public SPacketDamage(AABB aabb, float damage, ActionEntity TypeDamage) {
        this.aabb = aabb;
        this.damage = damage;
        this.TypeDamage = TypeDamage;
    }

    public SPacketDamage(AABB aabb, ActionEntity TypeDamage) {
        this.aabb = aabb;
        this.damage = 0f;
        this.TypeDamage = TypeDamage;
    }

    @Override
    public void handleClient(SPacketDamage message, EntityPlayerSP player) {

    }

    @Override
    public void handleServer(SPacketDamage message, EntityPlayerMP player) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(aabb.x);
        buf.writeFloat(aabb.y);
        buf.writeFloat(aabb.z);
        buf.writeFloat(aabb.r);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        aabb = new AABB(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }
}
