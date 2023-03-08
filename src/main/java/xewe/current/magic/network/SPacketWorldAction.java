package xewe.current.magic.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xewe.current.magic.enums.ActionWorld;

import java.util.ArrayList;
import java.util.List;

@ElegantPacket
public class SPacketWorldAction implements ClientToServerPacket {
    public SPacketWorldAction() {
    }
    BlockPos pos;
    List<ActionWorld> actions = new ArrayList<>();
    float strength;

    public void setFire(BlockPos pos){
        this.actions.add(ActionWorld.SetFire);
        this.pos = pos;
    }

    public void explosion(BlockPos pos, float strength){
        this.actions.add(ActionWorld.Explosion);
        this.pos =  pos;
        this.strength = strength;
    }

    public void setAir(BlockPos pos){
        this.actions.add(ActionWorld.SetBlock);
        this.pos = pos;
    }

    @Override
    public void onReceive(EntityPlayerMP player) {
        World world = player.world;
        for(ActionWorld action : actions) {
            switch (action) {
                case SetFire:
                    PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState().withProperty(AGE, 1), 2);
                    break;
                case Explosion:
                    world.newExplosion(player, pos.getX(), pos.getY(), pos.getZ(), strength, true, false);
                    break;
                case SetBlock:
                    world.setBlockToAir(pos);
                    break;
                default:
                    System.out.print("Not action");
            }
        }
    }
}
