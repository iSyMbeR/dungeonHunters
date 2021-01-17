package com.dungeonhunters.dungeonhunters.Strategy;

import com.dungeonhunters.dungeonhunters.model.Enemy;

public interface Strategy {
    int getEnemyDmg(Enemy enemy);
    int getEnemyDmgReduction(Enemy enemy);
    String getEnemyIntent();
}
