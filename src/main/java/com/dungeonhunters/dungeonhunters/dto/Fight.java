package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Random;

@Data
@Component
public class Fight {
    public Player player;
    public Enemy enemy;
    public final EnemyService enemyService;
    public Fight(EnemyService enemyService){
        this.enemyService = enemyService;
    }
    public void enemyAttack(){
        player.setHp(player.getHp() - enemy.getDmg());
    }

    public void createEnemy() {
        Random r = new Random();
        List<Enemy> allEnemies = enemyService.getAllEnemies();
        this.enemy = allEnemies.get(r.nextInt(allEnemies.size()));
    }

    public void useCard(Card card) {
        System.out.println(card.getName());
    }

    public void playerAttack() {
        System.out.println("attack");
    }

    public void playerDefend() {
        System.out.println("defend");
    }
}
