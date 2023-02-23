package com.mod.currentmagic.events;

import com.mod.currentmagic.ability.air.AirDash;
import com.mod.currentmagic.ability.air.AirWhiff;
import com.mod.currentmagic.ability.fire.FireBranch;
import com.mod.currentmagic.ability.fire.FireTwister;
import com.mod.currentmagic.collision.AABB;
import com.mod.currentmagic.events.customevents.AbilityCast;
import com.mod.currentmagic.gui.TextGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class CastEvent {

    public int time;
    private final String z = "§cᚱ";
    private final String x = "§aᛩ";
    private final String c = "§9ᛈ";
    private final String v = "§eᛖ";
    @SubscribeEvent
    public void onAbilityCast(AbilityCast event){

        if(TextGui.comboText.equals(z+z)){
            FireBranch.ability.start(event.getCaster());
        }
        if(TextGui.comboText.equals(x+c)){
            AirWhiff.ability.start(event.getCaster());
        }
        if(TextGui.comboText.equals(x)){
            AirDash.ability.start(event.getCaster());
        }
        if (TextGui.comboText.equals(v)) {
            FireTwister.ability.start(event.getCaster());
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
    }

    @SubscribeEvent
    public void KeyPressed(InputEvent.KeyInputEvent event){

        //Здесь отлавливаем нажатия на комбо-клавиши и воспроизводим звук.
        if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
            TextGui.comboText = TextGui.comboText+z;
            playSoundCombo(1f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_X)){
            TextGui.comboText = TextGui.comboText+x;
            playSoundCombo(0.6f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_C)){
            TextGui.comboText = TextGui.comboText+c;
            playSoundCombo(150f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_V)){
            TextGui.comboText = TextGui.comboText+v;
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
        Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_CREEPER_DEATH, 1f, pitch);
    }
}
