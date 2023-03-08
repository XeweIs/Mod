package xewe.current.magic.collision;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xewe.current.magic.events.KeyPressed;

import java.lang.reflect.Array;

public class AABB extends AxisAlignedBB {
    public final float x, y, z, r;

    public AABB(float x, float y, float z, float r) {
        super(x - r, y - r, z - r, x + r, y + r, z + r);

        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public EntityLivingBase onEntityCollided(World world, Entity exception) {
        try {
            if (exception != null)
                return world.getEntitiesWithinAABB(EntityLivingBase.class, this, a -> a != exception).get(0);
            else
                return world.getEntitiesWithinAABB(EntityLivingBase.class, this, null).get(0);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
