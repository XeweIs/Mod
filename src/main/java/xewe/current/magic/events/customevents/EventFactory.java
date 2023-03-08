package xewe.current.magic.events.customevents;

import xewe.current.magic.enums.TypeCast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class EventFactory {
    public static void onAbilityCast(EntityPlayer player, TypeCast typeCast) {
        MinecraftForge.EVENT_BUS.post(new AbilityCast(player, typeCast));
    }
}
