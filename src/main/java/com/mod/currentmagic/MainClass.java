package com.mod.currentmagic;

import com.mod.currentmagic.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MainClass.MODID, name = MainClass.NAME, version = MainClass.VERSION)
public class MainClass
{
    public static final String MODID = "current-magic";
    public static final String NAME = "Current Magic";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @SidedProxy(clientSide = "com.mod.currentmagic.proxy.ClientProxy", serverSide = "com.mod.currentmagic.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
//        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

//    private final int[] keys = {
//            Keyboard.KEY_1,
//            Keyboard.KEY_2,
//            Keyboard.KEY_3,
//            Keyboard.KEY_4,
//            Keyboard.KEY_5,
//            Keyboard.KEY_6,
//            Keyboard.KEY_7,
//            Keyboard.KEY_8,
//            Keyboard.KEY_9,
//            Keyboard.KEY_0
//    };
//
//    @SubscribeEvent
//    public void onKeyPressed(InputEvent.KeyInputEvent event) {
//        if(Event.world.isRemote) {
//            for (int key : keys)
//                if (Keyboard.isKeyDown(key)) {
//                    new ExamplePacket1(key).sendToServer();
//                    return;
//                }
//        }
//    }
}
