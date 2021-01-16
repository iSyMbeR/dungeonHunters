package com.dungeonhunters.dungeonhunters.Decorator;

public abstract class Decorator implements Equipment{
    protected Equipment decoratedEquipment;

    public Decorator(Equipment decoratedEquipment ){
        this.decoratedEquipment = decoratedEquipment;
    }
    public int getDmg(){
        return decoratedEquipment.getDmg();
    }

}
