package xewe.current.magic.ability.air;

import net.minecraft.entity.player.EntityPlayer;
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
import xewe.current.magic.network.SPacketEntityAction;

public class AirSplash extends Ability {
    public static AirSplash ability = new AirSplash();
    public static AABB aAirSplash;
    Vec3d playerPosVec;
    Boolean[] AirShootBoolean = new Boolean[]{true, true, true, true, true};

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Air Splash", "§f", 6, (short) 2, (short) 10);
        playerPosVec = player.getPositionVector();
    }

    @Override
    protected boolean onUpdate() {
        if(AirShootBoolean[0]) AirShoot(0f);
        if(AirShootBoolean[1]) AirShoot(0.25f);
        if(AirShootBoolean[2]) AirShoot(-0.25f);
        if(AirShootBoolean[3]) AirShoot(0.5f);
        if(AirShootBoolean[4]) AirShoot(-0.5f);

        return true;
    }

    private void AirShoot(float yaw) {
        Vec3d
                vec = playerVec.rotateYaw(yaw),
                CurrentPosition = vec.scale((maxRepeat - repeat) * 0.5 + 1).addVector(0, player.getEyeHeight(), 0).add(playerPosVec);
        float
                x = (float) CurrentPosition.x,
                y = (float) CurrentPosition.y,
                z = (float) CurrentPosition.z;
        if (!player.world.isAirBlock(new BlockPos(x, y, z))){
            if(yaw == 0) AirShootBoolean[0] = false;
            if(yaw == 0.25) AirShootBoolean[1] = false;
            if(yaw == -0.25) AirShootBoolean[2] = false;
            if(yaw == 0.5) AirShootBoolean[3] = false;
            if(yaw == -0.5) AirShootBoolean[4] = false;
         }

        if (yaw == 0)
            new CPacketSound(SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, x, y, z,
                    1f, 2f).sendToServer();

        aAirSplash = new AABB(x, y, z, 0.5f);
        new CPacketParticle(EnumParticleTypes.SPELL, x, y, z, 3).sendToServer();

        if (aAirSplash.onEntityCollided(player.world, player) != null) {
            SPacketEntityAction hit = new SPacketEntityAction(aAirSplash.onEntityCollided(player.world, player).getEntityId());
            hit.attack(0.6f);
            hit.motion(vec);
            hit.sendToServer();
        }

//        if (aAirSplash.intersects(aAirSplash.minX, aAirSplash.minY, aAirSplash.minZ, aAirSplash.maxX, aAirSplash.maxY, aAirSplash.maxZ) &&
//                !aAirSplash.intersects(aAirSplash))
//            player.sendMessage(new TextComponentString("Столкновение"));

        if (KeyPressed.Alt && aAirSplash.onEntityCollided(player.world, null) == player) {
            player.motionX = vec.x;
            player.motionY = vec.y;
            player.motionZ = vec.z;
        }

    }

    @Override
    protected void onExit() {
        super.onExit();
        AirShootBoolean = new Boolean[]{true, true, true, true, true};
    }
}
