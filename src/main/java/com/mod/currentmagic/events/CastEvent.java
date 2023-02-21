package com.mod.currentmagic.events;

import com.mod.currentmagic.ability.air.AirDash;
import com.mod.currentmagic.ability.fire.FireBranch;
import com.mod.currentmagic.events.customevents.AbilityCast;
import com.mod.currentmagic.gui.TextGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class CastEvent {

    public int time;
    private EntityPlayer player;
    @SubscribeEvent
    public void onAbilityCast(AbilityCast event){

        if(TextGui.comboText.equals("ZZ")){
            FireBranch.ability.start(event.getCaster());
        }
        if(TextGui.comboText.equals("X")){
            AirDash.ability.start(event.getCaster());
        }

        TextGui.comboText = "";
        time = 0;
    }

    @SubscribeEvent
    public void onTickClearKey(TickEvent.PlayerTickEvent event){
        if(event.side.isServer()) return;

        //Если вышло время комбинации
        if (time >= 120) {
            TextGui.comboText = "";
            time = 0;
        } else if (!TextGui.comboText.isEmpty()) {
            time++;
        }

        this.player = event.player;
    }

    @SubscribeEvent
    public void KeyPressed(InputEvent.KeyInputEvent event){

        //Здесь отлавливаем нажатия на комбо-клавиши и воспроизводим звук.
        if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
            TextGui.comboText = TextGui.comboText+"Z";
            playSoundCombo(1f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_X)){
            TextGui.comboText = TextGui.comboText+"X";
            playSoundCombo(0.6f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_C)){
            TextGui.comboText = TextGui.comboText+"C";
            playSoundCombo(150f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_V)){
            TextGui.comboText = TextGui.comboText+"V";
            playSoundCombo(20f);
        }
//        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
//            if(!Event.extra.contains("Shift")) Event.extra = Event.extra + "§nShift§r§l ";
//        } else {
//            if(Event.extra.contains("Shift")) Event.extra = Event.extra.replaceAll("§nShift§r§l ", "");
//        }
//        if(Keyboard.isKeyDown(Keyboard.KEY_LMENU)){
//            if(!Event.extra.contains("Alt")) Event.extra = Event.extra + "§nAlt§r§l ";
//        } else {
//            if(Event.extra.contains("Alt")) Event.extra = Event.extra.replaceAll("§nAlt§r§l ", "");
//        }
    }

    @SideOnly(Side.CLIENT)
    private void playSoundCombo(float pitch){
        player.playSound(SoundEvents.ENTITY_CREEPER_DEATH, 1f, pitch);
    }
}
