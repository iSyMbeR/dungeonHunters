package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.model.*;
import com.dungeonhunters.dungeonhunters.service.*;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
    public final MenuController menuController;
    public final ProfileController profileController;
    public final FightController fightController;

    GameController(PlayerService playerService,
                   EnemyService enemyService,
                   InventoryService inventoryService,
                   CardService cardService,
                   DeckService deckService,
                   AreaService areaService,
                   MenuController menuController,
                   ProfileController profileController,
                   FightController fightController) {
        super("Dungeon Hunters");
        this.playerService = playerService;
        this.enemyService = enemyService;
        this.inventoryService = inventoryService;
        this.cardService = cardService;
        this.deckService = deckService;
        this.areaService = areaService;
        this.menuController = menuController;
        this.profileController = profileController;
        this.fightController = fightController;
        this.panel = new JPanel();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(1200,800));
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        switchToMenuController();

    }

    public void switchToMenuController() {
        menuController.setGameController(this);
        menuController.createView();
    }

    public void switchToProfileController() {
        profileController.setGameController(this);
        profileController.setPlayer(playerService.getPlayerById(player.getId()));
        profileController.createView();
    }
    public void switchToFightController() {
        fightController.setGameController(this);
        fightController.setPlayer(playerService.getPlayerById(player.getId()));
        fightController.createView();
    }
    public void setMainContent(JPanel content) {
        this.setContentPane(content);
        setVisible(true);
    }

    public void setCurrentPlayer(Player player) {
        this.player = player;
    }


    public Deck createDeck() {
        Deck deck = Deck.builder()
                .cardSet(new ArrayList<>())
                .build();
        return deckService.addDeck(deck);
    }

    public Player createPlayer(String name, String logo) {
        Player player = Player.builder()
                .name(name)
                .hp(100)
                .currentHp(100)
                .stage(1)
                .experience(0)
                .deck(createDeck())
                .inventory(createInventory())
                .gold(0)
                .dmg(10)
                .def(0)
                .logo(logo)
                .build();
        return playerService.addPlayer(player);
    }

    public Inventory createInventory() {
        Inventory inventory = Inventory.builder()
                .build();
        return inventoryService.addInventory(inventory);
    }

}
