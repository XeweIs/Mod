package com.mod.currentmagic.events;

import com.mod.currentmagic.ability.fire.FireBranch;
import com.mod.currentmagic.events.customevents.AbilityCast;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event {

    @SubscribeEvent
    public void onAbilityCast(AbilityCast event){
        FireBranch.ability.start(event.getCaster());
    }
}
