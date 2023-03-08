package xewe.current.magic;

import xewe.current.magic.command.CommandChoose;
import xewe.current.magic.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = CurrentMagic.MODID, name = CurrentMagic.NAME, version = CurrentMagic.VERSION)
public class CurrentMagic
{
    public static final String MODID = "current-magic";
    public static final String NAME = "Current Magic";
    public static final String VERSION = "1.0";
    @Mod.Instance
    public static CurrentMagic instance;

    @SidedProxy(clientSide = "xewe.current.magic.proxy.ClientProxy", serverSide = "xewe.current.magic.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandChoose());
    }

}
