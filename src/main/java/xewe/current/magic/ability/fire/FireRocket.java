package xewe.current.magic.ability.fire;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xewe.current.magic.ability.Ability;
import xewe.current.magic.collision.AABB;
import xewe.current.magic.events.KeyPressed;
import xewe.current.magic.network.CPacketParticle;
import xewe.current.magic.network.CPacketSound;
import xewe.current.magic.network.SPacketWorldAction;

public class FireRocket extends Ability {

    public static final FireRocket ability = new FireRocket();
    public static AABB aFireBranch;
    float playerEyeHeight;
    Vec3d playerPosVec;
    boolean control = true;

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Fire Rocket", "§c", 25, (short) 2, (short) 20);

        playerEyeHeight = player.getEyeHeight();
        playerPosVec = player.getPositionVector();
        if(KeyPressed.Alt) control = false;
    }

    @Override
    protected boolean onUpdate() {
        Vec3d CurrentPosition;
        if(control)
            CurrentPosition = player.getLookVec().scale((maxRepeat - repeat)+1).addVector(0, playerEyeHeight, 0).add(playerPosVec);
        else
            CurrentPosition = playerVec.scale((maxRepeat - repeat)+1).addVector(0, playerEyeHeight, 0).add(playerPosVec);
        float
            x = (float) CurrentPosition.x,
            y = (float) CurrentPosition.y,
            z = (float) CurrentPosition.z;
        aFireBranch = new AABB(x, y, z, 0.5f);

        if (!player.world.isAirBlock(new BlockPos(x, y, z)) ||
                player.world.isRainingAt(new BlockPos(x, y, z)) ||
                player.world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.WATER)) return false;

        if(aFireBranch.onEntityCollided(player.world, player) != null) return false;

        new CPacketSound(SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, x, y, z, 1f, 1f).sendToServer();
        new CPacketParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 3).sendToServer();
        new CPacketParticle(EnumParticleTypes.FLAME, x, y, z, 4).sendToServer();

        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
        Vec3d CurrentPosition;
        if(control)
            CurrentPosition = player.getLookVec().scale((maxRepeat - repeat)+1).addVector(0, playerEyeHeight, 0).add(playerPosVec);
        else
            CurrentPosition = playerVec.scale((maxRepeat - repeat)+1).addVector(0, playerEyeHeight, 0).add(playerPosVec);
        float
            x = (float) CurrentPosition.x,
            y = (float) CurrentPosition.y,
            z = (float) CurrentPosition.z;
        SPacketWorldAction packet = new SPacketWorldAction();
        packet.explosion(new BlockPos(x, y, z), 1f);
        packet.sendToServer();
        control = true;
    }
}
