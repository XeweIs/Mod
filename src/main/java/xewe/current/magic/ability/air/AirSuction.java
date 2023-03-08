package xewe.current.magic.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import xewe.current.magic.ability.Ability;
import xewe.current.magic.collision.AABB;
import xewe.current.magic.events.KeyPressed;
import xewe.current.magic.network.CPacketParticle;
import xewe.current.magic.network.CPacketSound;
import xewe.current.magic.network.SPacketEntityAction;

public class AirSuction extends Ability {
    public static AirSuction ability = new AirSuction();
    AABB aAirSuction;
    Vec3d playerPosVec;
    RayTraceResult ray;
    BlockPos playerPos;
    boolean revers = false;

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Air Suction", "§f", 15, (short) 2, (short) 16);
        playerPosVec = player.getPositionVector();
        ray = player.rayTrace(16, 1);
        playerPos = player.getPosition();

        if (KeyPressed.Shift) revers = true;
    }

    @Override
    protected boolean onUpdate() {

        Vec3d vec, Cu = null, CurrentPosition;

        if (revers) {
            CurrentPosition = playerVec.scale(maxRepeat - repeat).addVector(0, 1.4, 0).add(playerPosVec);
            vec = playerVec.scale(1);
        } else {
            Cu = playerVec.scale(-(maxRepeat - repeat)).addVector(ray.getBlockPos().getX() - playerPos.getX(), ray.getBlockPos().getY() - playerPos.getY(), ray.getBlockPos().getZ() - playerPos.getZ());
            CurrentPosition = playerVec.scale(Cu.distanceTo(playerVec)).addVector(0, 1, 0).add(playerPosVec);
            vec = playerVec.scale(-1);
        }

        float
                x = (float) CurrentPosition.x,
                y = (float) CurrentPosition.y,
                z = (float) CurrentPosition.z;
        aAirSuction = new AABB(x, y, z, 0.5f);

        if (aAirSuction.onEntityCollided(player.world, player) != null) {
            SPacketEntityAction hit = new SPacketEntityAction(aAirSuction.onEntityCollided(player.world, player).getEntityId());
            hit.motion(vec);
            hit.sendToServer();
        }

        if(!player.world.isAirBlock(new BlockPos(x, y, z))) return false;

        new CPacketSound(SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, x, y, z, 1f, 20f).sendToServer();
        new CPacketParticle(EnumParticleTypes.SPELL, x, y, z, 4).sendToServer();

        if (!revers)
            return ((int) Cu.distanceTo(playerVec) != 1);

        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
        revers = false;
    }
}
