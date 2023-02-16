package com.mod.currentmagic.util;

import com.google.common.base.Function;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;

public class Methods {
    public static Entity entityrange(World world, float x, float y, float z, float r, Entity ex){
        return (world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(
                x - (r / 2f),
                y - (r / 2f),
                z - (r / 2f),
                x + (r / 2f),
                y + (r / 2f),
                z + (r / 2f)),
                        null).stream().min(new Object() {
                    Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                        return Comparator.comparing((Function<Entity, Double>) (a -> a.getDistanceSq(_x, _y, _z)));
                    }
                }.compareDistOf(x, y, z)).filter(a -> a != ex).orElse(null));
    }
}
