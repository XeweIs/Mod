package com.mod.currentmagic.magic;

import com.mod.currentmagic.magic.elements.AIR;
import com.mod.currentmagic.magic.elements.EARTH;
import com.mod.currentmagic.magic.elements.FIRE;
import com.mod.currentmagic.magic.elements.WATER;

public class Element {
    static FIRE Fire;
    static AIR Air;
    static EARTH Earth;
    static WATER Water;

    public static String Fire(){
        return "Fire";
    }

    public static String Earth(){
        return "Earth";
    }

    public static String Water(){
        return "Water";
    }

    public static String Air(){
        return "Air";
    }
}
