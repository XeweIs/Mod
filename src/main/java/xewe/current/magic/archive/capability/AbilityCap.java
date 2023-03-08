package xewe.current.magic.archive.capability;

import xewe.current.magic.enums.AbilityEnum;
import xewe.current.magic.enums.ElementEnum;

import java.util.ArrayList;
import java.util.List;

public class AbilityCap implements IAbilityCap{
    public ElementEnum element = ElementEnum.None;
    public List<AbilityEnum> ability = new ArrayList<>();

    @Override
    public void setElement(ElementEnum element) {
        this.element = element;
    }

    @Override
    public ElementEnum getElement() {
        return this.element;
    }

    @Override
    public void removeElement() {
        this.element = null;
    }

    @Override
    public void addAbility(AbilityEnum ability) {
        this.ability.add(ability);
    }

    @Override
    public void addAllAbility(List<AbilityEnum> ability) {
        this.ability.addAll(ability);
    }

    @Override
    public List<AbilityEnum> getAbility() {
        return this.ability;
    }

    @Override
    public void removeAbility(AbilityEnum ability) {
        this.ability.remove(ability);
    }
}
