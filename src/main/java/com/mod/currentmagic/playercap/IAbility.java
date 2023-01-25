package com.mod.currentmagic.playercap;

import java.util.ArrayList;

public interface IAbility {

    String getElement();
    ArrayList<String> getAbility();

    void setElement(String e);
    void removeElement(String e);
    void addAbility(String a);
    void removeAbility(String a);
}
