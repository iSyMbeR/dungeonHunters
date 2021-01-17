package com.dungeonhunters.dungeonhunters.Strategy;

import com.dungeonhunters.dungeonhunters.model.Enemy;

public class AttackAndBlockStrategy implements Strategy{

    @Override
    public int getEnemyDmg(Enemy enemy) {
        return enemy.getDmg()/2;
    }

    @Override
    public int getEnemyDmgReduction(Enemy enemy) {
        return enemy.getDefense();
    }

    @Override
    public String getEnemyIntent() {
        return "Enemy intent to attack and block.";
    }
}
