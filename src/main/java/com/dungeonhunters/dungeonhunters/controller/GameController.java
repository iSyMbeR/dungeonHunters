package com.dungeonhunters.dungeonhunters.controller;


import com.dungeonhunters.dungeonhunters.model.*;

import com.dungeonhunters.dungeonhunters.service.*;
import org.springframework.stereotype.Controller;


import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.util.List;


import static com.dungeonhunters.dungeonhunters.Ansi.*;

@Controller
public class GameController extends JFrame {
    public final PlayerService playerService;
    public final EnemyService enemyService;
    public final InventoryService inventoryService;
    public final CardService cardService;
    public final DeckService deckService;
    public final AreaService areaService;
    public Player player;
    public JPanel panel;
    public final MenuFrame menuController;
    public final ProfileController profileController;

    GameController(PlayerService playerService,
                   EnemyService enemyService,
                   InventoryService inventoryService,
                   CardService cardService,
                   DeckService deckService,
                   AreaService areaService,
                   MenuFrame menuController,
                   ProfileController profileController) {
        super("Dungeon Hunters");
        this.playerService = playerService;
        this.enemyService = enemyService;
        this.inventoryService = inventoryService;
        this.cardService = cardService;
        this.deckService = deckService;
        this.areaService = areaService;
        this.menuController = menuController;
        this.profileController = profileController;
        this.panel = new JPanel();
        this.setSize(new Dimension(500, 500));
        switchToMenuController();

    }

    private void switchToMenuController() {
        menuController.setGameController(this);
        menuController.createView();
        setMainContent(menuController.panel);
    }

    public void switchToProfileController() {
        profileController.setGameController(this);
        profileController.setPlayer(player);
        profileController.createView();
        setMainContent(profileController.panel);
    }

    public void setMainContent(JPanel content) {
        this.setContentPane(content);
        setVisible(true);
    }

    public void setCurrentPlayer(Player player) {
        this.player = player;
    }

    public void clearScreen() {
        JPanel p = new JPanel();
        this.setContentPane(p);
        repaint();
    }

    public void printSet(List<Card> cards) {
        int iter = 1;
        for (Card c : cards) {
            System.out.printf(CYAN + "%d." + BLACK + " %-20s DMG: %-3d DEF: %-3d COST: %-3d\n", iter++, c.getName(), c.getDmg(), c.getDefense(), c.getCost());

        }
    }

    public Card createCard(String name, String type, int dmg, int cost, int def) {
        Card card = Card.builder()
                .cost(cost)
                .defense(def)
                .dmg(dmg)
                .name(name)
                .type(type)
                .build();
        cardService.addCard(card);
        return card;
    }

    public void addCardToDeck(Deck deck, Card card) {
        List<Card> set = deck.getCardSet();
        set.add(card);
        deck.setCardSet(set);
        deckService.addDeck(deck);
    }

    public void addBasicCardsToDeck(Deck deck) {
        List<Card> allCards = cardService.getAllCards();
        List<Card> basicCards = new ArrayList<>();
        for (Card c : allCards) {
            if (c.getType().equals("basic2")) basicCards.add(c);
        }
        deck.setCardSet(basicCards);
        deckService.addDeck(deck);

    }

    public Deck createDeck() {
        Deck deck = Deck.builder()
                .cardSet(new ArrayList<>())
                .build();
        return deckService.addDeck(deck);
    }

    public Player createPlayer(String name) {
        Player player = Player.builder()
                .name(name)
                .hp(100)
                .stage(1)
                .experience(0)
                .deck(createDeck())
                .inventory(createInventory())
                .gold(0)
                .build();
        return playerService.addPlayer(player);
    }

    public Inventory createInventory() {
        Inventory inventory = Inventory.builder()
                .build();
        return inventoryService.addInventory(inventory);
    }

    public void populateDatabase() {
        Card card = Card.builder().name("karta 1").build();
        Card card2 = Card.builder().name("karta 2").build();
        List<Card> cards = new ArrayList<Card>();
        cards.add(card);
        cards.add(card2);
        System.out.println(cards + "\n");
        Deck deck = Deck.builder().cardSet(cards).build();
        Player player = Player.builder().name("mati5").deck(deck).build();
        cardService.addCard(card);
        cardService.addCard(card2);
        deckService.addDeck(deck);
        playerService.addPlayer(player);
        System.out.println("DONE!");
        System.out.println(player.getDeck().getCardSet());
    }

    public void printAfterBattleScreen(boolean victory) {
        if (victory) {
            System.out.println("Wygrana!");
        } else {
            System.out.println("Przegrana!");
        }
        System.out.println("Nacisnij dowolny klaiwsz aby kontynuować...");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }

    public void deleteCardFromDeck(Deck deck, Card card) {
        List<Card> cardList = deck.getCardSet();
        cardList.remove(card);
        deckService.getDeckById(deck.getId()).setCardSet(cardList);
    }

    public String updateView(Player player, Enemy enemy, int turn) {
        System.out.println(BLACK);
        String fightText = fight;
        int enMaxHp, enHp, plMaxHp, plHp;
        enMaxHp = enemyService.getEnemyById(enemy.getId()).getBase_life();
        enHp = enemy.getBase_life();
        plMaxHp = playerService.getPlayerById(player.getId()).getHp();
        plHp = player.getHp();
        String numericValue = plHp + "/" + plMaxHp;
        Integer len = numericValue.length();
        for (int i = 0; i < (20 - len) / 2; i++) numericValue = " " + numericValue;
        for (int i = 0; i < (20 - len) / 2; i++) numericValue = numericValue + " ";
        if ((20 - len) % 2 == 1) numericValue = " " + numericValue;
        fightText = fightText.replace("#friendly_health_bar", numericValue);

        numericValue = enHp + "/" + enMaxHp;
        len = numericValue.length();
        for (int i = 0; i < (20 - len) / 2; i++) numericValue = " " + numericValue;
        for (int i = 0; i < (20 - len) / 2; i++) numericValue = numericValue + " ";
        if ((20 - len) % 2 == 1) numericValue += " ";
        fightText = fightText.replace("##enemy_health_bar##", numericValue);
        fightText = fightText.replace("#T", (turn < 10) ? " " + turn : Integer.toString(turn));

        return fightText;
    }

    public void printCard(Card card, int index) {
        String cardText = cardView;
        String space = "";
        cardText = cardText.replace("1", Integer.toString(card.getCost()));
        space = (card.getDmg() > 9) ? "" : " ";
        cardText = cardText.replace("2", space + Integer.toString(card.getDmg()));
        space = (card.getDefense() > 9) ? "" : " ";
        cardText = cardText.replace("3", space + Integer.toString(card.getDefense()));
        cardText = cardText.replace("4", Integer.toString(index));
        System.out.println(BLUE);
        System.out.println(cardText);
        System.out.println(RED);
    }

    public Area selectArea() {
        List<Area> areas = areaService.getAllAreas();
        Random random = new Random();
        return areas.get(random.nextInt(areas.size()));
    }

    public Enemy selectEnemy() {
        List<Enemy> allEnemies = enemyService.getAllEnemies();
        if (allEnemies.size() == 0) System.out.println("Brak przeciwników");
        Random random = new Random();
        return allEnemies.get(random.nextInt(allEnemies.size()));
    }

}
