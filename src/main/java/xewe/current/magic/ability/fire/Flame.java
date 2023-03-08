package xewe.current.magic.ability.fire;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import xewe.current.magic.ability.Ability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xewe.current.magic.network.CPacketSound;
import xewe.current.magic.network.SPacketWorldAction;
import xewe.current.magic.util.UtilVector;

public class Flame extends Ability {

    public static final Flame ability = new Flame();
    Vec3d playerPosVec;

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Flame", "§c", 10, (short) 2, (short) 7);

        playerPosVec = player.getPositionVector();
    }

    @Override
    protected boolean onUpdate() {
        Vec3d CurrentPosition = UtilVector.getVectorForRotation(0, player.rotationYaw).scale(maxRepeat - repeat + 2).addVector(0, 1, 0).add(playerPosVec);
        float
            x = (float) CurrentPosition.x,
            z = (float) CurrentPosition.z;

        if (!player.world.isAirBlock(new BlockPos(x, player.posY, z)) && !player.world.getBlockState(new BlockPos(x, player.posY, z)).getBlock().equals(Blocks.FIRE))
            return false;

        new CPacketSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, x, (float) player.posY, z, 1f, 1f).sendToServer();
        SPacketWorldAction packet = new SPacketWorldAction();
        packet.setFire(new BlockPos(x, player.posY, z));
        packet.sendToServer();
        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
        Vec3d CurrentPosition = UtilVector.getVectorForRotation(0, player.rotationYaw).scale((maxRepeat - repeat)).addVector(0, 1, 0).add(playerPosVec);
        float
                x = (float) CurrentPosition.x,
                y = (float) CurrentPosition.y,
                z = (float) CurrentPosition.z;
        new CPacketSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, x, y, z, 1f, 1f).sendToServer();
    }
}
