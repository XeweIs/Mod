package xewe.current.magic.ability;

import net.minecraft.entity.player.EntityPlayer;

public class ExampleAbility extends Ability {
    public static ExampleAbility ability = new ExampleAbility();

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Example Ability", "§c", 1, (short) 1, (short) 1);
    }

    @Override
    protected boolean onUpdate() {
        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
    }
}
