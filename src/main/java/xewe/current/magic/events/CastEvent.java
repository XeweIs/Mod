package xewe.current.magic.events;

import net.minecraft.util.text.TextComponentString;
import xewe.current.magic.Keybinds.KeybindsRegister;
import xewe.current.magic.ability.air.*;
import xewe.current.magic.ability.fire.*;
import xewe.current.magic.data.Element;
import xewe.current.magic.enums.ElementEnum;
import xewe.current.magic.enums.TypeCast;
import xewe.current.magic.events.customevents.AbilityCast;
import xewe.current.magic.gui.TextGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CastEvent {

    @SubscribeEvent
    public void onAbilityCast(AbilityCast event) {

        EntityPlayer player = event.getCaster();

        String
                v = KeyPressed.v,
                c = KeyPressed.c,
                x = KeyPressed.x,
                z = KeyPressed.z;

        switch (Element.getElement(player)) {
            case Fire:

                if (trigger(z + z, event, true, TypeCast.LeftBlock, TypeCast.LeftEmpty, TypeCast.RightEntity))
                    Flame.ability.start(player);

                if (trigger(v + v + c, event, true, TypeCast.RightBlock, TypeCast.RightEmpty, TypeCast.RightEntity))
                    FireTwister.ability.start(player);

                if (trigger(z + v, event, true, TypeCast.Left, TypeCast.LeftBlock, TypeCast.LeftEmpty))
                    FireRocket.ability.start(player);

                if (trigger(x + x, event, true, TypeCast.RightBlock, TypeCast.RightEmpty, TypeCast.RightEntity))
                    FireEncierro.ability.start(player);

                if(trigger(v+v, event, true, TypeCast.RightBlock, TypeCast.RightEntity, TypeCast.RightEmpty))
                    JetPack.ability.start(player);

                break;

            case Air:

                if (trigger(x, event, true, TypeCast.RightBlock, TypeCast.RightEmpty, TypeCast.RightEntity))
                    AirDash.ability.start(player);

                if (trigger(x + c, event, true, TypeCast.RightBlock, TypeCast.RightEmpty, TypeCast.RightEntity))
                    AirWhiff.ability.start(player);

                if (trigger(z + x, event, true, TypeCast.Left, TypeCast.LeftBlock, TypeCast.LeftEmpty))
                    AirSplash.ability.start(player);

                if (trigger(x + x, event, true, TypeCast.Jump))
                    Jump.ability.start(player);

                if (trigger(x + v, event, true, TypeCast.RightBlock, TypeCast.RightEmpty, TypeCast.RightEntity))
                    AirSuction.ability.start(player);

                break;
        }

        if (!event.getTypeCast().equals(TypeCast.Jump)) {
            TextGui.comboText = "";
            KeyPressed.time = 0;
        } else if (event.getTypeCast().equals(TypeCast.Jump) && TextGui.comboText.equals(x + x) && Element.getElement(player).equals(ElementEnum.Air)) {
            TextGui.comboText = "";
            KeyPressed.time = 0;
        }

    }

    public boolean trigger(String combo, AbilityCast event, boolean key, TypeCast... cast) {
        boolean booleanCast = false;
        if (TextGui.comboText.equals(combo)) {
            for (TypeCast curCast : cast) {
                if (event.getTypeCast().equals(curCast)) {
                    booleanCast = true;
                    break;
                }
            }
            return (booleanCast && key);
        }

        return false;
    }
}
