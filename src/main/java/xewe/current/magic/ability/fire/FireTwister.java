package xewe.current.magic.ability.fire;

import net.minecraft.client.entity.EntityPlayerSP;
import xewe.current.magic.ability.Ability;
import xewe.current.magic.collision.AABB;
import xewe.current.magic.network.CPacketParticle;
import xewe.current.magic.network.CPacketSound;
import xewe.current.magic.enums.ActionEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xewe.current.magic.network.SPacketEntityAction;

public class FireTwister extends Ability {
    public static FireTwister ability = new FireTwister();
    public static AABB aFireTwister;

    public void start(EntityPlayer player) {
        if (statusCoolDown.equals(Status.active) || statusAbility.equals(Status.active)) return;

        super.start(player, "Fire Twister", "§c", 15, (short) 2, (short) 100);
    }

    @Override
    protected boolean onUpdate() {

        for (byte i = 0; i < 10; i++) {
            float x = (float) (player.posX + i * 0.1f * (Math.sin(repeat * (Math.PI / 5))));
            float y = (float) player.posY + i * 0.5f;
            float z = (float) (player.posZ + i * 0.1f * (Math.cos(repeat * (Math.PI / 5))));

            if (!player.world.isAirBlock(new BlockPos(x, y, z)) ||
                    (player.world.isRainingAt(new BlockPos(x, y, z))) ||
                    player.world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.WATER)) {

                new CPacketSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, (float) player.posX,
                        (float) player.posY, (float) player.posZ, 1f, 1f).sendToServer();
                new CPacketParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 3).sendToServer();

                break;
            }

            new CPacketParticle(EnumParticleTypes.FLAME, x, y, z, 1).sendToServer();
            aFireTwister = new AABB(x, y, z, 0.8f);
            if(aFireTwister.onEntityCollided(player.world, player) != null) {
                SPacketEntityAction hit = new SPacketEntityAction(aFireTwister.onEntityCollided(player.world, player).getEntityId());
                hit.setFire((short) 2);
                hit.attack(0.3f);
                hit.sendToServer();
            }
        }

        new CPacketSound(SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, (float) player.posX,
                (float) player.posY, (float) player.posZ, 1f, 1f).sendToServer();

        player.motionX *= 0.3;
        player.motionZ *= 0.3;
        return true;
    }

    @Override
    protected void onExit() {
        super.onExit();
    }

    @SubscribeEvent
    public void Jump(LivingEvent.LivingJumpEvent event) {
        if (statusAbility.equals(Status.active)) player.motionY = 0;
    }
}
