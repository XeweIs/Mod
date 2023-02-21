package com.mod.currentmagic.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import javax.swing.*;
import javax.xml.soap.Text;
import java.util.ArrayList;

public class TextGui extends Gui {
    public static ArrayList<String> coolDownText = new ArrayList<>();
    public static String comboText = "";

    //Игрался с созданием цветов для определенных слов в строке. Оставлю тут, позже может доиграюсь.
//    public static ITextComponent text = new TextComponentString("");
//    public static void Foo(String text, String hex){
//        TextGui.text.appendText("Лол").setStyle();
//    }

    public TextGui(Minecraft mc) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

        //Выводим текст куладуна
        if(!coolDownText.isEmpty()) drawCenteredString(mc.fontRenderer, String.join("  ", coolDownText),
                width / 2, height - 60, Integer.parseInt("FFFFFF", 16));

        //Выводим комбо
        if(!comboText.isEmpty()) drawCenteredString(mc.fontRenderer, comboText,
                width / 2, height - 80, Integer.parseInt("FFFFFF", 16));

    }
}
