package com.mod.currentmagic.events;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class CEvent {

    @SubscribeEvent
    public void TickPlayer(TickEvent.PlayerTickEvent event){
        event.player.sendStatusMessage(new TextComponentString("§f§lCombo: §nZ§r§f§l"+Event.Z+" §nX§r§f§l"+Event.X+" §nC§r§f§l"+Event.C+" §nV§r§f§l"+Event.V+" §nExtra§r: §f§l"+Event.extra), true);
    }

    @SubscribeEvent
    public void KeyPressed(InputEvent.KeyInputEvent event){
        if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
            Event.Z+=1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_X)){
            Event.X+=1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_C)){
            Event.C+=1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_V)){
            Event.V+=1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            if(!Event.extra.contains("Shift")) Event.extra = Event.extra + "§nShift§r§l ";
        } else {
            if(Event.extra.contains("Shift")) Event.extra = Event.extra.replaceAll("§nShift§r§l ", "");
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LMENU)){
            if(!Event.extra.contains("Alt")) Event.extra = Event.extra + "§nAlt§r§l ";
        } else {
            if(Event.extra.contains("Alt")) Event.extra = Event.extra.replaceAll("§nAlt§r§l ", "");
        }
    }

}
