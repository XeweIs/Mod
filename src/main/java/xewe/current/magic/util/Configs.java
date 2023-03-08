package xewe.current.magic.util;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

@Config(modid = "current-magic", name = "CurrentMagic", type = Type.INSTANCE)
public class Configs {
    public static ExtraCategory extra = new ExtraCategory();

    public static class ExtraCategory {
        @Comment({"How many ticks will it take before your combo disappears"})
        @Name("Ticks until the combo disappears")
        public int tickCombo = 120;

    }
}
