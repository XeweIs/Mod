package com.mod.currentmagic.utils;

import com.google.common.base.Function;
import com.mod.currentmagic.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Comparator;

public class Methods {
    public static Entity entityR(World world, float x, float y, float z, float r, Entity ent){
        return (world
                .getEntitiesWithinAABB(Entity.class,
                        new AxisAlignedBB(x - (r / 2f),
                                y - (r / 2f),
                                z - (r / 2f),
                                x + (r / 2f),
                                y + (r / 2f),
                                z + (r / 2f)),
                        null)
                .stream().min(new Object() {
                    Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                        return Comparator.comparing((Function<Entity, Double>) (a -> a.getDistanceSq(_x, _y, _z)));
                    }
                }.compareDistOf(x, y, z)).filter(a -> a.getEntityId() != ent.getEntityId()).orElse(null));
    }

    public static void spawnParticle(World world, EnumParticleTypes particleTypes, float x, float y, float z, float vX, float vY, float vZ){
        if(!world.isRemote){
            switch (particleTypes){
                case FLAME:
                    world.playSound(null, x, y, z, Sounds.flame, SoundCategory.PLAYERS, 1f, 1f);
                    break;
                case SMOKE_NORMAL:
                    world.playSound(null, x, y, z, Sounds.extinguish, SoundCategory.PLAYERS, 1f, 1f);
                    break;
            }
            ((WorldServer)world).spawnParticle(particleTypes, x, y, z, 3, vX, vY, vZ, 0);
        }
    }
}
