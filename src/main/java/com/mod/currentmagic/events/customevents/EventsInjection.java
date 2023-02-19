package com.mod.currentmagic.events.customevents;

import com.mod.currentmagic.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Constants.MODID)
public class EventsInjection {

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickBlock event) {
        EventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }
    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickEmpty event) {
        EventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }
    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickItem event) {
        EventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.EntityInteractSpecific event) {
        EventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }
}
