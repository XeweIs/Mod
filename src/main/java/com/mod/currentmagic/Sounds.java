package com.mod.currentmagic;

import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Sounds {
    public static final SoundEvent flame = registry("flame");
    public static final SoundEvent extinguish = registry("extinguish");

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> e) {
        ForgeRegistries.SOUND_EVENTS.register(flame);
        ForgeRegistries.SOUND_EVENTS.register(extinguish);
    }

    private static SoundEvent registry(String name) {
        ResourceLocation uniqueName = new ResourceLocation(MainClass.MODID, name);
        return new SoundEvent(uniqueName).setRegistryName(uniqueName);
    }
}
