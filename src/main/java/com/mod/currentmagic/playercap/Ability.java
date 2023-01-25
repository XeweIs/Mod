package com.mod.currentmagic.playercap;

import java.util.ArrayList;

public class Ability implements IAbility {

    private String element;
    private ArrayList<String> ability;

    @Override
    public String getElement() {
        return this.element;
    }

    @Override
    public ArrayList<String> getAbility() {
        return this.ability;
    }

    public void setElement(String e) {
        this.element = e;
    }

    @Override
    public void removeElement(String e) {
        this.element = null;
        this.ability = null;
    }

    @Override
    public void addAbility(String a) {
        this.ability.add(a);
    }

    @Override
    public void removeAbility(String a) {
        this.ability.remove(a);
    }
}
