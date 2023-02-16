package com.mod.currentmagic.events.customevents;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.World;

public class DemoEventFactory {
    public static void onAbilityCast(World world, EntityPlayer player) {
        MinecraftForge.EVENT_BUS.post(new AbilityCast(world, player));
    }
}
