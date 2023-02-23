package com.mod.currentmagic.network;

import com.mod.currentmagic.collision.AABB;
import com.mod.currentmagic.util.Damage;
import com.mod.currentmagic.util.Methods;
import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

@ElegantPacket
public class CPacketDamageAABB implements ClientToServerPacket {
    final AABB aabb;
    final float damage;
    final Damage TypeDamage;
    int sec = 0;
    public CPacketDamageAABB(AABB aabb, float damage, Damage TypeDamage){
        this.aabb = aabb;
        this.damage = damage;
        this.TypeDamage = TypeDamage;
    }

    public CPacketDamageAABB(AABB aabb, float damage, Damage TypeDamage, int...args){
        this.aabb = aabb;
        this.damage = damage;
        this.TypeDamage = TypeDamage;
        this.sec = args[0];
    }

    public CPacketDamageAABB(AABB aabb, Damage TypeDamage){
        this.aabb = aabb;
        this.damage = 0f;
        this.TypeDamage = TypeDamage;
    }

    public CPacketDamageAABB(AABB aabb, Damage TypeDamage, int...args){
        this.aabb = aabb;
        this.damage = 0f;
        this.TypeDamage = TypeDamage;
        this.sec = args[0];
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        if(aabb.onEntityCollided(player.world, player) != null){

            if(TypeDamage.equals(Damage.OnlyFire)) {
                Methods.attackEntityNoKnockBack(aabb.onEntityCollided(player.world, player), DamageSource.causePlayerDamage(player), damage);
                aabb.onEntityCollided(player.world, player).setFire(sec);
            }

            if(TypeDamage.equals(Damage.Fire)){
                aabb.onEntityCollided(player.world, player).attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                aabb.onEntityCollided(player.world, player).setFire(sec);
            }
        }
    }
}
