package xewe.current.magic.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public class UtilEntity {
    public static void attackEntityNoKnockBack(Entity entity, DamageSource source, float damage){
        float vx = (float)entity.motionX;
        float vy = (float)entity.motionY;
        float vz = (float)entity.motionZ;
        entity.attackEntityFrom(source, damage);
        entity.motionX = vx;
        entity.motionY = vy;
        entity.motionZ = vz;
    }
}
