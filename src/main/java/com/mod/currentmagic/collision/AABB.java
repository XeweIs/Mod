package com.mod.currentmagic.collision;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

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
        return world.getEntitiesWithinAABB(Entity.class, this, a -> a != exception).get(0);
    }
}
