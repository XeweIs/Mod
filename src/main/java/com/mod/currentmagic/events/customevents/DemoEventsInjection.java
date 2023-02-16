package com.mod.currentmagic.events.customevents;

import com.mod.currentmagic.MainClass;
import com.mod.currentmagic.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Constants.MODID)
public class DemoEventsInjection {
    @SubscribeEvent
    public static void onLivingUseItem(PlayerInteractEvent.RightClickBlock event) {
        DemoEventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }
    @SubscribeEvent
    public static void onLivingUseItem(PlayerInteractEvent.RightClickEmpty event) {
        DemoEventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }
    @SubscribeEvent
    public static void onLivingUseItem(PlayerInteractEvent.RightClickItem event) {
        DemoEventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }

    @SubscribeEvent
    public static void onLivingUseItem(PlayerInteractEvent.EntityInteractSpecific event) {
        DemoEventFactory.onAbilityCast(event.getWorld(), event.getEntityPlayer());
    }
}
