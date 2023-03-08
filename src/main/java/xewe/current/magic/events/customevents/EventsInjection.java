package xewe.current.magic.events.customevents;

import javafx.scene.input.MouseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xewe.current.magic.CurrentMagic;
import xewe.current.magic.enums.TypeCast;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = CurrentMagic.MODID)
public class EventsInjection {

    static TypeCast typeCast;
    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide().isServer() || event.getHand().equals(EnumHand.OFF_HAND)) return;
        EventFactory.onAbilityCast(event.getEntityPlayer(), TypeCast.RightBlock);
        typeCast = TypeCast.RightBlock;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickEmpty event) {
        if (event.getSide().isServer() || event.getHand().equals(EnumHand.OFF_HAND)) return;
        EventFactory.onAbilityCast(event.getEntityPlayer(), TypeCast.RightEmpty);
        typeCast = TypeCast.RightEmpty;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickItem event) {
        if (event.getSide().isServer() || event.getHand().equals(EnumHand.OFF_HAND)) return;
        EventFactory.onAbilityCast(event.getEntityPlayer(), TypeCast.RightItem);
        typeCast = TypeCast.RightItem;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getSide().isServer() || event.getHand().equals(EnumHand.OFF_HAND)) return;
        EventFactory.onAbilityCast(event.getEntityPlayer(), TypeCast.RightEntity);
        typeCast = TypeCast.RightEntity;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getSide().isServer()) return;
        EventFactory.onAbilityCast(event.getEntityPlayer(), TypeCast.LeftBlock);
        typeCast = TypeCast.LeftBlock;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getSide().isServer()) return;
        EventFactory.onAbilityCast(event.getEntityPlayer(), TypeCast.LeftEmpty);
        typeCast = TypeCast.LeftEmpty;
    }

    @SubscribeEvent
    public static void Jump(LivingEvent.LivingJumpEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer)
            EventFactory.onAbilityCast((EntityPlayer) event.getEntityLiving(), TypeCast.Jump);
    }

    @SubscribeEvent
    public static void Attack(AttackEntityEvent event){
        if (!event.getEntityPlayer().world.isRemote) return;
        EventFactory.onAbilityCast((EntityPlayer) event.getEntityLiving(), TypeCast.Left);
    }
}
