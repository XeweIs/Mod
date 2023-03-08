package xewe.current.magic.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import xewe.current.magic.enums.ActionEntity;
import xewe.current.magic.util.UtilEntity;

import java.util.ArrayList;
import java.util.List;

@ElegantPacket
public class SPacketEntityAction implements ClientToServerPacket {

    final int entityId;
    public SPacketEntityAction(int entityId) {
        this.entityId = entityId;
    }

    List<ActionEntity> actions = new ArrayList<>();
    short sec;
    float damage, x, y, z;

    public void attackNoKnockBack(float damage) {
        this.actions.add(ActionEntity.AttackNoKnockBack);
        this.damage = damage;
    }

    public void attack(float damage) {
        this.actions.add(ActionEntity.AttackEntity);
        this.damage = damage;
    }

    public void motion(Vec3d vec){
        this.actions.add(ActionEntity.Motion);
        x = (float)vec.x;
        y = (float)vec.y;
        z = (float)vec.z;
    }

    public void paralyze(){
        this.actions.add(ActionEntity.Paralyze);
    }

    public void setFire(short sec) {
        this.actions.add(ActionEntity.Ignition);
        this.sec = sec;
    }

    public void notDamageFall(){
        this.actions.add(ActionEntity.NotDamageFall);
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        EntityLivingBase entity = (EntityLivingBase) player.world.getEntityByID(entityId);
        assert entity != null;

        for(ActionEntity action : actions) {
            switch (action) {
                case Ignition:
                    if (!player.isOnSameTeam(entity)) {
                        entity.setFire(sec);
                        UtilEntity.attackEntityNoKnockBack(entity, DamageSource.causePlayerDamage(player).setFireDamage(), 0);
                    }
                    break;
                case AttackNoKnockBack:
                    UtilEntity.attackEntityNoKnockBack(entity, DamageSource.causePlayerDamage(player), damage);
                    break;
                case AttackEntity:
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                    break;
                case NotDamageFall:
                    entity.fallDistance = 0;
                    break;
                case Motion:
                    entity.motionY = y;
                    entity.motionX = x;
                    entity.motionZ = z;
                    if(entity instanceof EntityPlayer)
                        new CPacketPlayerAction(new Vec3d(x, y, z)).sendToPlayer((EntityPlayerMP) entity);
                    break;
                default:
                    System.out.print("Not action");
            }
        }
    }
}
