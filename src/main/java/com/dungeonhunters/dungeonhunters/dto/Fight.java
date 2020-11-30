package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.CardService;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import com.dungeonhunters.dungeonhunters.service.PlayerService;
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
    public int actionsLeft = 0;
    public int turn = 0;
    public int enemyMaxHp = 0;
    public boolean playerBlocked;
    public final EnemyService enemyService;
    public final PlayerService playerService;
    public Fight(EnemyService enemyService, PlayerService playerService){
        this.enemyService = enemyService;
        this.playerService = playerService;
    }

    public void createEnemy() {
        if (enemy == null){
            Random r = new Random();
            List<Enemy> allEnemies = enemyService.getAllEnemies();
            this.enemy = allEnemies.get(r.nextInt(allEnemies.size()));
        }
    }

    public void enemyTurn(){
        if(playerBlocked){
            if(player.getDef()<enemy.getDmg()){
                player.setCurrentHp(player.getCurrentHp() - enemy.getDmg() + player.getDef());
            }
            playerBlocked=false;
        }else{
            player.setCurrentHp(player.getCurrentHp() - enemy.getDmg());
        }
    }

    public void useCard(Card card) {
        if(actionsLeft>0){
            System.out.println("used card:" +card.getName());
            actionsLeft--;
        }
    }

    public void playerAttack() {
        if(actionsLeft>0){
            int totalDmgDealt = player.getDmg();
            enemy.setHp(enemy.getHp() - totalDmgDealt);
            actionsLeft--;
        }
    }

    public int checkEndBattleConditions(){
        // 0 - nothing
        // 1 - win
        // -1 lose
        if(player.getCurrentHp()<=0) return -1;
        if(enemy.getHp()<=0) return 1;
        return 0;
    }
    public void playerDefend() {
        if(actionsLeft>0 && !playerBlocked){
            playerBlocked=true;
            actionsLeft--;
        }
    }

    public void nextTurn(){
        if( enemyMaxHp == 0 ){
            enemyMaxHp=enemy.getHp();
        }else{
            enemyTurn();
        }
        turn++;
        actionsLeft=2;
    }

    public void generateLootAndUpdatePlayer() {
        int goldLoot = enemy.getGoldDrop();
        int expGained = enemy.getExperienceDrop();
        if(player.getExperience() + expGained >= 100){
            player.setExperience(player.getExperience() + expGained);
            if (player.getExperience()>=100){
                int stagesGained = player.getExperience()/100;
                player.setStage(player.getStage()+stagesGained);
                player.setExperience(player.getExperience() - stagesGained*100);
                player.setHp(player.getHp()+stagesGained*5);
                player.setDmg(player.getDmg()+stagesGained*2);
                player.setDef(player.getDef()+stagesGained);
            }
        }
        player.setGold(player.getGold()+goldLoot);
        playerService.addPlayer(player);
        clearBattle();

    }

    private void clearBattle() {
        enemy = null;
        actionsLeft = 0;
        turn = 0;
        enemyMaxHp = 0;
    }

    public void looseBattleAndUpdatePlayer() {
        playerService.deletePlayer(playerService.getPlayerById(player.getId()));
        clearBattle();
    }

    public void setPlayer(Player player) {
        this.player = playerService.getPlayerById(player.getId());
    }
}
