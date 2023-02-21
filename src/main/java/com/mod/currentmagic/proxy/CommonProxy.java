package com.mod.currentmagic.proxy;

import com.mod.currentmagic.events.CastEvent;
import com.mod.currentmagic.playercap.CapabilityHandler;
import com.mod.currentmagic.playercap.IAbility;
import com.mod.currentmagic.playercap.AbilityStorage;
import com.mod.currentmagic.util.Methods;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event) {
        Object[] events = {
                new CapabilityHandler(),
                new CastEvent(),
        };
        Methods.registerEvents(events);

        CapabilityManager.INSTANCE.register(IAbility.class, new AbilityStorage(), com.mod.currentmagic.playercap.Ability.class);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }



}
