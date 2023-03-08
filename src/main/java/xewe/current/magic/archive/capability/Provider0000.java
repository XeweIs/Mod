package xewe.current.magic.archive.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class Provider0000 implements ICapabilitySerializable<NBTTagCompound>
{
    @CapabilityInject(IAbilityCap.class)
    public static final Capability<IAbilityCap> ABILITY_CAP = null;
    private IAbilityCap instance = ABILITY_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        return capability == ABILITY_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        return hasCapability(capability, facing) ? ABILITY_CAP.<T>cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        return (NBTTagCompound) ABILITY_CAP.getStorage().writeNBT(ABILITY_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        ABILITY_CAP.getStorage().readNBT(ABILITY_CAP, instance, null, nbt);
    }
}

