package com.mod.currentmagic.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class CoolDownText extends Gui {
    public static ArrayList<String> text = new ArrayList<>();
    public CoolDownText(Minecraft mc) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        if(text != null) drawCenteredString(mc.fontRenderer, String.join("  ", text), width / 2, height - 60, Integer.parseInt("FF6C61", 16));
    }
}
