package xewe.current.magic.events.customevents;

import xewe.current.magic.enums.TypeCast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AbilityCast extends Event {
    private final EntityPlayer player;
    private final TypeCast typeCast;

    public AbilityCast(EntityPlayer player, TypeCast typeCast) {
        this.player = player;
        this.typeCast = typeCast;
    }

    public EntityPlayer getCaster() {
        return player;
    }

    public TypeCast getTypeCast() {
        return typeCast;
    }
}
