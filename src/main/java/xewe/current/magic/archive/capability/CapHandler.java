package xewe.current.magic.archive.capability;

import xewe.current.magic.CurrentMagic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;

public class CapHandler {
    public static void register(){
        MinecraftForge.EVENT_BUS.register(new CapHandler());
    }

    public static final ResourceLocation ABILITY_CAP = new ResourceLocation(CurrentMagic.MODID, "Element");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof EntityPlayerMP)) return;

        event.addCapability(ABILITY_CAP, new Provider0000());
    }

    @SubscribeEvent
    public void playerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        IAbilityCap newFactor = player.getCapability(Provider0000.ABILITY_CAP, null);
        IAbilityCap oldFactor = event.getOriginal().getCapability(Provider0000.ABILITY_CAP, null);

        assert newFactor != null;
        assert oldFactor != null;
        newFactor.setElement(oldFactor.getElement());
        newFactor.addAllAbility(oldFactor.getAbility());
    }
}
