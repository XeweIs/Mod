package xewe.current.magic.proxy;

import xewe.current.magic.events.CastEvent;
import xewe.current.magic.events.KeyPressed;
import xewe.current.magic.newnetwork.NetworkHandler;
import xewe.current.magic.archive.capability.AbilityCap;
import xewe.current.magic.archive.capability.CapHandler;
import xewe.current.magic.archive.capability.IAbilityCap;
import xewe.current.magic.archive.capability.Storage0000;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        Object[] events = {
                new CastEvent(),
                new KeyPressed()
        };
        registerEvents(events);
    }

    public void init(FMLInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IAbilityCap.class, new Storage0000(), AbilityCap::new);
        CapHandler.register();

        NetworkHandler.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void registerEvents(Object[] e) {
        for (Object object : e) {
            MinecraftForge.EVENT_BUS.register(object);
        }
    }

}