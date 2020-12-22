package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.controller.GameController;
import com.dungeonhunters.dungeonhunters.controller.ProfileController;
import com.dungeonhunters.dungeonhunters.factory.AbstractCardActionFactory;
import com.dungeonhunters.dungeonhunters.factory.CardActionStrategyFactory;

import com.dungeonhunters.dungeonhunters.model.*;
import com.dungeonhunters.dungeonhunters.service.*;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Data
@Component
public class Fight {
    public Player player;
    public Enemy enemy;
    public Area area;
    public Map<Card, Integer> playerStatus;
    public Map<Card, Integer> enemyStatus;
    public int actionsLeft = 0;
    public int turn = 0;
    public int enemyMaxHp = 0;
    public CardActionStrategyFactory cardActionFactory;
    public List<String> loot = new ArrayList<>();
    public boolean playerBlocked, miss, reducedDmg, sleep;
    public final EnemyService enemyService;
    public final PlayerService playerService;
    public final BonusService bonusService;
    public final DeckService deckService;
    public final ItemBaseService itemBaseService;
    public final ItemService itemService;
    public final InventoryService inventoryService;
    public final AreaService areaService;
    public ProfileController profileController;
    public GameController gameController;
    public final Shop shop;

    @Autowired
    public void setCardActionFactory(CardActionStrategyFactory cardActionFactory) {
        this.cardActionFactory = cardActionFactory;
    }

    public Fight(AreaService areaService, InventoryService inventoryService, ItemService itemService, BonusService bonusService, EnemyService enemyService, PlayerService playerService, DeckService deckService, ItemBaseService itemBaseService, Shop shop) {
        this.enemyService = enemyService;
        this.playerService = playerService;
        this.deckService = deckService;
        this.shop = shop;
        this.playerStatus = new HashMap<>();
        this.enemyStatus = new HashMap<>();
        this.itemBaseService = itemBaseService;
        this.bonusService = bonusService;
        this.itemService = itemService;
        this.inventoryService = inventoryService;
        this.areaService = areaService;
    }

    public void createEnemy() {
        if (enemy == null) {
            Random r = new Random();
            List<Enemy> allEnemies = enemyService.getAllEnemies();
            List<Enemy> validEnemies = new ArrayList<>();
            for (Enemy e : allEnemies) {
                if (e.getStage() <= player.getStage()) validEnemies.add(e);
            }
            if (validEnemies.size() == 0) {
                Enemy en = Enemy.builder()
                        .name("Ostry przeciwnik")
                        .dmg(2)
                        .hp(200)
                        .stage(15)
                        .defense(0)
                        .experienceDrop(80)
                        .goldDrop(50)
                        .build();
                enemyService.addEnemy(en);
                validEnemies.add(en);
            }
            this.enemy = validEnemies.get(r.nextInt(validEnemies.size()));
        }
    }

    public String enemyTurn() {
        if (miss) {
            miss = false;
            return enemy.getName() + " missed";
        }
        if (sleep) {
            sleep = false;
            return enemy.getName() + " is sleeping ...";
        }
        int damage = enemy.getDmg();
        if (reducedDmg) {
            damage = damage / 2;
            reducedDmg = false;
        }
        if (playerBlocked) {
            if (player.getDef() < damage) {
                damage = damage - player.getDef();
                player.setCurrentHp(player.getCurrentHp() - damage);
            }
            playerBlocked = false;
            return player.getName() + " received " + damage + " damage (damage reduced by defense) from " + enemy.getName() + " attack.";
        } else {
            player.setCurrentHp(player.getCurrentHp() - enemy.getDmg());
            return player.getName() + " received " + damage + " damage from " + enemy.getName() + " attack.";
        }
    }

    public String useCard(Card card) {
        if (actionsLeft >= card.getCost()) {
            actionsLeft = actionsLeft - card.getCost();
            Optional<AbstractCardActionFactory> abstractCardActionFactory = cardActionFactory.get(card.getType());
            abstractCardActionFactory.ifPresent(a -> a.use(card, player, enemy, playerStatus, enemyStatus));
            Deck deck = deckService.getDeckById(player.getDeck().getId());
            List<Card> cardSet = deck.getCardSet();
            cardSet.remove(card);
            deck.setCardSet(cardSet);
//            player.setDeck(deck);
            deckService.addDeck(deck);
            return "Card: " + card.getName() + " used, " + actionsLeft + " actions left.";
        }
        return "Card require " + card.getCost() + " actions to use. You have " + actionsLeft;
    }

    public String playerAttack() {
        if (actionsLeft > 0) {
            int totalDmgDealt = player.getDmg();
            enemy.setHp(enemy.getHp() - totalDmgDealt);
            actionsLeft--;
            return player.getName() + " deal " + totalDmgDealt + " to " + enemy.getName() + ", " + actionsLeft + " actions left.";
        }
        return "Action limit reached, 0 actions left.";
    }

    public int checkEndBattleConditions() {
        if (player.getCurrentHp() <= 0) return -1;
        if (enemy.getHp() <= 0) return 1;
        return 0;
    }

    public String playerDefend() {
        if(!playerBlocked){
            if (actionsLeft > 0) {
                Card card = Card.builder().type(CardType.Block).value(1).build();
                playerStatus.put(card,card.getValue());
                playerBlocked = true;
                actionsLeft--;
                return player.getName() + " is defending himself, " + actionsLeft + " actions left.";
            }
            return "Action limit reached, 0 actions left.";
        }
        return "You are already blocking";

    }

    public String nextTurn() {
        if (enemyMaxHp == 0) enemyMaxHp = enemy.getHp();
        turn++;
        actionsLeft = 2;
        updateStatus();
        refreshStatus();
        return "Turn " + (turn - 1) + " ended, started " + turn + " turn, " + actionsLeft + " actions left.";
    }

    private void updateStatus() {
        for (Map.Entry<Card, Integer> entry : playerStatus.entrySet()) {
            entry.setValue(entry.getValue() - 1);
            if (entry.getValue() == 0) playerStatus.remove(entry.getKey(), 0);
        }
        for (Map.Entry<Card, Integer> entry : enemyStatus.entrySet()) {
            entry.setValue(entry.getValue() - 1);
            if (entry.getValue() == 0) enemyStatus.remove(entry.getKey(), 0);
        }
    }

    public void refreshStatus() {
        for (Map.Entry<Card, Integer> entry : playerStatus.entrySet()) {
            if (entry.getKey().getType() == CardType.Block) playerBlocked = true;
        }
        for (Map.Entry<Card, Integer> entry : enemyStatus.entrySet()) {
            if (entry.getKey().getType() == CardType.Miss) miss = true;
            if (entry.getKey().getType() == CardType.ReducedDmg) reducedDmg = true;
            if (entry.getKey().getType() == CardType.Sleep) sleep = true;
        }
    }

    public void generateLootAndUpdatePlayer() {
        int goldLoot = enemy.getGoldDrop();
        int expGained = enemy.getExperienceDrop();

        loot.add("You dropped " + goldLoot + " gold");
        loot.add("You gained " + expGained + " experience");
        loot.add(generateRandomItem());

        if (player.getExperience() + expGained >= 100) {
            player.setExperience(player.getExperience() + expGained);
            if (player.getExperience() >= 100) {
                int stagesGained = player.getExperience() / 100;
                player.setStage(player.getStage() + stagesGained);
                player.setExperience(player.getExperience() - stagesGained * 100);
                player.setHp(player.getHp() + stagesGained * 5);
                player.setDmg(player.getDmg() + stagesGained);
                player.setDef(player.getDef() + stagesGained);
            }
        }
        player.setGold(player.getGold() + goldLoot);
        playerService.addPlayer(player);
        shop.refreshItems(player);
        //clearBattle();

    }

    public void clearBattle() {
        enemy = null;
        actionsLeft = 0;
        turn = 0;
        enemyMaxHp = 0;
        loot = new ArrayList<>();
        playerStatus = new HashMap<>();
        enemyStatus = new HashMap<>();
        playerBlocked = false;
        miss = false;
        reducedDmg = false;
        sleep = false;
    }

    public void looseBattleAndUpdatePlayer() {
        playerService.deletePlayer(playerService.getPlayerById(player.getId()));
        clearBattle();
    }

    public void setPlayer(Player player) {
        this.player = playerService.getPlayerById(player.getId());
    }

    public String generateRandomItem() {
        boolean exist = false;
        Set<Item> playerListItem = player.getInventory().getItemList();
        int generatedLongItemBase = 1 + (int) (Math.random() * (itemBaseService.getSize()) - 1);
        int generatedLongBonus = 1 + (int) (Math.random() * (bonusService.getSize()) - 1);

        List<ItemBase> itemBaseList = itemBaseService.getItemBases();
        List<Bonus> bonusListFromBase = bonusService.getAllBonuses();
        List<Bonus> bonusList = new ArrayList<>();
        bonusList.add(bonusListFromBase.get(generatedLongBonus - 1));

        Item item = Item.builder()
                .itemBase(itemBaseList.get(generatedLongItemBase - 1))
                .bonus(bonusList)
                .build();


        for (Item i : playerListItem) {
            if (i.getItemBase().getName().equals(item.getItemBase().getName())) {
                exist = true;
            }

        }
        if (!exist) {
            playerListItem.add(item);

            player.getInventory().setItemList(playerListItem);
            player.setInventory(player.getInventory());
            // nie trybi zapisanie inventory
            itemService.addItem(item);
            playerService.addPlayer(player);
            //inventoryService.updateItemsSetList(player.getInventory().getId(), player.getInventory().getItemList());
            inventoryService.addInventory(player.getInventory());
            return "You have received " + item.getItemBase().getName();
        }

        return item.getItemBase().getName() + " You already own it";
    }

    public void generateArea() {
        List<Area> areas = areaService.getAllAreas();
        Random r = new Random();
        setArea(areas.get(r.nextInt(areas.size())));
    }
}
