package com.mod.currentmagic.playercap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AbilityProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IAbility.class)
    public static final Capability<IAbility> ABILITY_CAP = null;

    private IAbility instance = ABILITY_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == ABILITY_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == ABILITY_CAP ? ABILITY_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return ABILITY_CAP.getStorage().writeNBT(ABILITY_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        ABILITY_CAP.getStorage().readNBT(ABILITY_CAP, this.instance, null, nbt);
    }
}
