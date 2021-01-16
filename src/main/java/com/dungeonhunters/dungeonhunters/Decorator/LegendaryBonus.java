package com.dungeonhunters.dungeonhunters.Decorator;

public class LegendaryBonus extends Decorator {

    int multiplier = 4;
    public LegendaryBonus(Equipment decoratedEquipment) {
        super(decoratedEquipment);
    }

    @Override
    public int getDmg() {
        return super.getDmg() * multiplier;
    }

}
