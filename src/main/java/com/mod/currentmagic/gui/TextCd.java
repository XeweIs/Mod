package com.mod.currentmagic.gui;

import com.mod.currentmagic.events.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class TextCd extends Gui {
    public TextCd(Minecraft mc) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        String text = (Event.cdlinefire != 0 ? "§l§mLineFire§r§l "+ (5 - Event.cdlinefire / 40) : "")
                + (Event.cdspurt != 0 ? "   §l§mSpurt§r§l "+ (3 - Event.cdspurt / 40) : "");
        drawCenteredString(mc.fontRenderer, text, width / 2, height - 60, Integer.parseInt("FF6C61", 16));
    }
}
