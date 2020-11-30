package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.factory.AbstractCardActionFactory;
import com.dungeonhunters.dungeonhunters.factory.CardActionStrategyFactory;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import com.dungeonhunters.dungeonhunters.service.PlayerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component
public class Fight {
    public Player player;
    public Enemy enemy;
    public Map<Card, Integer> playerStatus;
    public Map<Card, Integer> enemyStatus;
    public int actionsLeft = 0;
    public int turn = 0;
    public int enemyMaxHp = 0;
    public CardActionStrategyFactory cardActionFactory;
    public List<String> loot = new ArrayList<>();
    public boolean playerBlocked;
    public final EnemyService enemyService;
    public final PlayerService playerService;
    public final DeckService deckService;

    @Autowired
    public void setCardActionFactory(CardActionStrategyFactory cardActionFactory) {
        this.cardActionFactory = cardActionFactory;
    }

    public Fight(EnemyService enemyService, PlayerService playerService, DeckService deckService){
        this.enemyService = enemyService;
        this.playerService = playerService;
        this.deckService = deckService;
        this.playerStatus = new HashMap<>();
        this.enemyStatus = new HashMap<>();
    }

    public void createEnemy() {
        if (enemy == null){
            Random r = new Random();
            List<Enemy> allEnemies = enemyService.getAllEnemies();
            List<Enemy> validEnemies = new ArrayList<>();
            for(Enemy e : allEnemies){
                if(e.getStage() <= player.getStage()) validEnemies.add(e);
            }
            this.enemy = validEnemies.get(r.nextInt(validEnemies.size()));
        }
    }

    public String enemyTurn(){
        if(playerBlocked){
            if(player.getDef()<enemy.getDmg()){
                player.setCurrentHp(player.getCurrentHp() - enemy.getDmg() + player.getDef());
            }
            playerBlocked=false;
        }else{
            player.setCurrentHp(player.getCurrentHp() - enemy.getDmg());
            return player.getName()+" received " +enemy.getDmg()+"damage from "+enemy.getName()+" attack.";
        }
        return null;
    }

    public String useCard(Card card) {
        if(actionsLeft>0){
            actionsLeft--;
            Optional<AbstractCardActionFactory> abstractCardActionFactory = cardActionFactory.get(card.getType());
            abstractCardActionFactory.ifPresent(a -> a.use(card, player, enemy, playerStatus, enemyStatus));
            player.getDeck().getCardSet().remove(card);
            deckService.addDeck(player.getDeck());
            return "Card: "+card.getName()+" used, "+actionsLeft+" actions left.";
        }
        return "Action limit reached, 0 actions left.";
    }

    public String playerAttack() {
        if(actionsLeft>0){
            int totalDmgDealt = player.getDmg();
            enemy.setHp(enemy.getHp() - totalDmgDealt);
            actionsLeft--;
            return player.getName()+" deal "+totalDmgDealt+" to "+enemy.getName()+", "+actionsLeft+" actions left.";
        }
        return "Action limit reached, 0 actions left.";
    }

    public int checkEndBattleConditions(){
        if(player.getCurrentHp()<=0) return -1;
        if(enemy.getHp()<=0) return 1;
        return 0;
    }
    public String playerDefend() {
        if(actionsLeft>0 && !playerBlocked){
            playerBlocked=true;
            actionsLeft--;
            return player.getName()+" is defending himself, "+actionsLeft+" actions left.";
        }
        return "Action limit reached, 0 actions left.";
    }

    public String nextTurn(){
        if( enemyMaxHp == 0 )enemyMaxHp=enemy.getHp();
        turn++;
        actionsLeft=2;
        return "Turn "+(turn - 1)+" ended, started "+turn+" turn, "+actionsLeft+" actions left.";
    }

    public void generateLootAndUpdatePlayer() {
        int goldLoot = enemy.getGoldDrop();
        int expGained = enemy.getExperienceDrop();
        loot.add("You dropped "+goldLoot+" gold.");
        loot.add("You gained "+expGained+" experience.");
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

        //clearBattle();

    }

    public void clearBattle() {
        enemy = null;
        actionsLeft = 0;
        turn = 0;
        enemyMaxHp = 0;
        loot = new ArrayList<>();
    }

    public void looseBattleAndUpdatePlayer() {
        playerService.deletePlayer(playerService.getPlayerById(player.getId()));
        clearBattle();
    }

    public void setPlayer(Player player) {
        this.player = playerService.getPlayerById(player.getId());
    }
}
