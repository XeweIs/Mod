package com.mod.currentmagic.proxy;

import com.mod.currentmagic.ability.Ability;
import com.mod.currentmagic.events.Event;
import com.mod.currentmagic.playercap.CapabilityHandler;
import com.mod.currentmagic.playercap.IAbility;
import com.mod.currentmagic.playercap.AbilityStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event) {
//        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
//        MinecraftForge.EVENT_BUS.register(new Event());
//        MinecraftForge.EVENT_BUS.register(new LineFire());
//        MinecraftForge.EVENT_BUS.register(new Ability());
        Object[] events = {
                new CapabilityHandler(),
                new Event(),
                new Ability(),
        };
        registerEvents(events);

        CapabilityManager.INSTANCE.register(IAbility.class, new AbilityStorage(), com.mod.currentmagic.playercap.Ability.class);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void registerEvents(Object[] e){
        for(Object object : e){
            MinecraftForge.EVENT_BUS.register(object);
        }
    }

}
