package com.dungeonhunters.dungeonhunters.Decorator;

public class EpicBonus extends Decorator {

    int multiplier = 3;
    public EpicBonus(Equipment decoratedEquipment) {
        super(decoratedEquipment);
    }

    @Override
    public int getDmg() {
        return super.getDmg() * multiplier;
    }
}
