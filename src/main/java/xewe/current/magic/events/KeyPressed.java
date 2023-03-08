package xewe.current.magic.events;

import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import xewe.current.magic.Keybinds.KeybindsRegister;
import xewe.current.magic.gui.TextGui;
import xewe.current.magic.util.Configs;

public class KeyPressed {
    public static byte time;
    public static String
            z = "§c" + KeybindsRegister.z.getDisplayName().toUpperCase(),
            x = "§a" + KeybindsRegister.x.getDisplayName().toUpperCase(),
            c = "§9" + KeybindsRegister.c.getDisplayName().toUpperCase(),
            v = "§e" + KeybindsRegister.v.getDisplayName().toUpperCase();
    public static boolean
            Shift = false,
            Alt = false;

    @SubscribeEvent
    public void onTickClearKey(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer()) return;

        //Если вышло время комбинирования
        if (time >= Configs.extra.tickCombo) {
            TextGui.comboText = "";
            time = 0;
        } else if (!TextGui.comboText.isEmpty()) {
            time++;
        }
    }

    @SubscribeEvent
    public void keyPressed(InputEvent.KeyInputEvent event) {

        z = "§c" + KeybindsRegister.z.getDisplayName().toUpperCase();
        x = "§a" + KeybindsRegister.x.getDisplayName().toUpperCase();
        c = "§9" + KeybindsRegister.c.getDisplayName().toUpperCase();
        v = "§e" + KeybindsRegister.v.getDisplayName().toUpperCase();

        if (KeybindsRegister.z.isKeyDown()) {
            TextGui.comboText += z;
            playSoundCombo(1f);
        }
        if (KeybindsRegister.x.isKeyDown()) {
            TextGui.comboText += x;
            playSoundCombo(0.6f);
        }
        if (KeybindsRegister.c.isKeyDown()) {
            TextGui.comboText += c;
            playSoundCombo(150f);
        }
        if (KeybindsRegister.v.isKeyDown()) {
            TextGui.comboText += v;
            playSoundCombo(20f);
        }

        Shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        Alt = Keyboard.isKeyDown(Keyboard.KEY_LMENU);
    }

    @SideOnly(Side.CLIENT)
    private void playSoundCombo(float pitch) {
        Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_CREEPER_DEATH, 1f, pitch);
    }
}
