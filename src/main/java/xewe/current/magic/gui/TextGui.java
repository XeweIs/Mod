package xewe.current.magic.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class TextGui extends Gui {
    public static String coolDownText = "";
    public static String comboText = "";

    public TextGui(Minecraft mc) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

        //Выводим текст кулдуна
        if(!coolDownText.isEmpty()) drawCenteredString(mc.fontRenderer, String.join("  ", coolDownText),
                width / 2, height - 80, Integer.parseInt("FFFFFF", 16));

        //Выводим комбо
        if(!comboText.isEmpty()) drawCenteredString(mc.fontRenderer, "§l"+comboText,
                width / 2, height - 100, Integer.parseInt("FFFFFF", 16));

    }
}
