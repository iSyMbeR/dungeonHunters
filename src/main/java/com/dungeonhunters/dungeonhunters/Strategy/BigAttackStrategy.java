package com.dungeonhunters.dungeonhunters.Strategy;

import com.dungeonhunters.dungeonhunters.model.Enemy;

public class BigAttackStrategy implements Strategy{
    @Override
    public int getEnemyDmg(Enemy enemy) {
        return enemy.getDmg()*2;
    }

    @Override
    public int getEnemyDmgReduction(Enemy enemy) {
        return 0;
    }

    @Override
    public String getEnemyIntent() {
        return "Enemy intents to deal double damage.";
    }
}
