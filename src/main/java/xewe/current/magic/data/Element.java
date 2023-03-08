package xewe.current.magic.data;

import xewe.current.magic.CurrentMagic;
import xewe.current.magic.enums.ElementEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = CurrentMagic.MODID)
public class Element {

    public static ElementEnum getElement(EntityPlayer player) {

        return ElementEnum.valueOf(player.getDataManager().get(PropertiesRegistry.ELEMENT));
    }

    public static void setElement(EntityPlayer player, ElementEnum value) {

        player.getDataManager().set(PropertiesRegistry.ELEMENT, value.toString());
        player.getEntityData().setString(CurrentMagic.MODID + ":element", getElement(player).toString());
    }

    public static void refillElement(EntityPlayer player) {

        player.getDataManager().set(PropertiesRegistry.ELEMENT, ElementEnum.None.toString());
    }

    private static void saveElementToNBT(EntityPlayer player) {

        player.getEntityData().setString(CurrentMagic.MODID + ":element", getElement(player).toString());
    }

    private static String loadElementFromNBT(EntityPlayer player) {

        return player.getEntityData().hasKey(CurrentMagic.MODID + ":element") ? player.getEntityData().getString(CurrentMagic.MODID + ":element") : ElementEnum.None.toString();
    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {

        setElement(event.player, ElementEnum.valueOf(loadElementFromNBT(event.player)));
    }

    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        saveElementToNBT(event.player);
    }

    @SubscribeEvent
    public static void playerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        EntityPlayer oldPlayer = event.getOriginal();

        Element.setElement(player, Element.getElement(oldPlayer));
    }
}
