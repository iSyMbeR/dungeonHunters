package com.dungeonhunters.dungeonhunters.Decorator;

public class RareBonus extends Decorator {

    int multiplier = 2;
    public RareBonus(Equipment decoratedEquipment) {
        super(decoratedEquipment);
    }

    @Override
    public int getDmg() {
        return super.getDmg() * multiplier;
    }

}
