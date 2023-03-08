package xewe.current.magic.archive.capability;

import xewe.current.magic.enums.AbilityEnum;
import xewe.current.magic.enums.ElementEnum;

import java.util.List;

public interface IAbilityCap {

    void setElement(ElementEnum element);
    ElementEnum getElement();
    void removeElement();

    void addAbility(AbilityEnum ability);
    void addAllAbility(List<AbilityEnum> ability);
    List<AbilityEnum> getAbility();
    void removeAbility(AbilityEnum ability);
}
