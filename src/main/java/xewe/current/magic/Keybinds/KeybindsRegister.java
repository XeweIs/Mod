package xewe.current.magic.Keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeybindsRegister {
    private static final String CATEGORY = "Combo";
    public static final KeyBinding
            z = new KeyBinding("1.One letter", Keyboard.KEY_Z, CATEGORY),
            x = new KeyBinding("2.Two letter", Keyboard.KEY_X, CATEGORY),
            c = new KeyBinding("3.Three letter", Keyboard.KEY_C, CATEGORY),
            v = new KeyBinding("4.Four letter", Keyboard.KEY_V, CATEGORY);


    public static void register() {
        setRegister(v);
        setRegister(c);
        setRegister(x);
        setRegister(z);
    }

    private static void setRegister(KeyBinding binding) {
        ClientRegistry.registerKeyBinding(binding);
    }
}
