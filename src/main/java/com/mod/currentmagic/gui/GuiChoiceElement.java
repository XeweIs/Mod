package com.mod.currentmagic.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiChoiceElement extends GuiScreen {
    private final int guiHeight = 192;
    private final int guiWidht = 256;
    private static ResourceLocation guiTexture;

    public GuiChoiceElement(){
        guiTexture = new ResourceLocation("");
    }
}
