package com.mod.currentmagic.collision;

import com.google.common.base.Function;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public class AABB extends AxisAlignedBB {
    public final float x, y, z, r;
    public AABB(float x, float y, float z, float r){
        super(x - r, y - r, z - r, x + r, y + r, z + r);
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public Entity onEntityCollided(World world, Entity exception){
        return world.getEntitiesWithinAABB(Entity.class, this).stream().min(new Object() {
            Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                return Comparator.comparing((Function<Entity, Double>) (a -> a.getDistanceSq(_x, _y, _z)));
            }
        }.compareDistOf(this.x, this.y, this.z)).filter(a -> a.getUniqueID() != exception.getUniqueID()).orElse(null);
    }

    public List<Entity> onListEntityCollided(World world, Entity exception){
        return world.getEntitiesWithinAABB(Entity.class, this, a -> a != exception);
    }
}
