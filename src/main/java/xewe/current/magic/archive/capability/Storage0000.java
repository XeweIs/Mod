package xewe.current.magic.archive.capability;

import xewe.current.magic.enums.ElementEnum;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class Storage0000 implements Capability.IStorage<IAbilityCap> {

    @Override
    public NBTBase writeNBT(Capability<IAbilityCap> capability, IAbilityCap instance, EnumFacing side)
    {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("element", instance.getElement().toString());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IAbilityCap> capability, IAbilityCap instance, EnumFacing side, NBTBase nbt)
    {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setElement(ElementEnum.valueOf(tag.getString("element")));
    }
}
