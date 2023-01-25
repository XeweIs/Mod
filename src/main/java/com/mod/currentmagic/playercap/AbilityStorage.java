package com.mod.currentmagic.playercap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class AbilityStorage implements Capability.IStorage<IAbility> {
    @Override
    public NBTBase writeNBT(Capability<IAbility> capability, IAbility instance, EnumFacing side)
    {
        return new NBTTagFloat(1f);
    }

    @Override
    public void readNBT(Capability<IAbility> capability, IAbility instance, EnumFacing side, NBTBase nbt)
    {
        instance.setElement(nbt.toString());
    }
}
