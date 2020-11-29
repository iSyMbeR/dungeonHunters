package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Player;
import lombok.Data;

@Data
public class Fight {
    public Player player;
    public Enemy enemy;

    public void enemyAttack(){
        player.setHp(player.getHp() - enemy.getDmg());
    }
}
