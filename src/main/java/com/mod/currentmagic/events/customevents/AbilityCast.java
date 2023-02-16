package com.mod.currentmagic.events.customevents;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AbilityCast extends Event {
    private final World world;
    private final EntityPlayer player;
    public AbilityCast(World world, EntityPlayer player){
        this.world = world;
        this.player = player;
    }

    public EntityPlayer getCaster(){
        return player;
    }
    public World getWorld(){
        return world;
    }
}
