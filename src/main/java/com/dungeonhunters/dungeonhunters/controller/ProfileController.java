package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.dto.ItemEquipType;
import com.dungeonhunters.dungeonhunters.dto.Shop;
import com.dungeonhunters.dungeonhunters.dto.ShopItemDto;
import com.dungeonhunters.dungeonhunters.model.*;
import com.dungeonhunters.dungeonhunters.service.*;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.tritonus.share.ArraySet;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.dungeonhunters.dungeonhunters.controller.MusicController.getMusic;

@Controller

public class ProfileController extends JFrame {
    public JPanel panel;
    public final PlayerService playerService;
    public GameController gameController;
    public Player player;
    private Map<String, ItemEquipType> equippedItems = new HashMap<>();
    private boolean putted = false;
    private String tabNames[];
    private int tabDmg[];
    private int activeItems;
    public int selected = 1;
    private final Shop shop;
    private final DeckService deckService;
    private final CardService cardService;
    private final ItemService itemService;
    private final BonusService bonusService;
    private final ItemBaseService itemBaseService;
    private final InventoryService inventoryService;


    ProfileController(InventoryService inventoryService, ItemBaseService itemBaseService, BonusService bonusService, DeckService deckService, CardService cardService, Shop shop, ItemService itemService, PlayerService playerService) {
        this.deckService = deckService;
        this.cardService = cardService;
        this.itemService = itemService;
        this.playerService = playerService;
        this.bonusService = bonusService;
        this.itemBaseService = itemBaseService;
        this.inventoryService = inventoryService;
        this.shop = shop;
    }

    public void createView() {
        if (!putted) {
            for (Item c : player.getInventory().getItemList()) {
                equippedItems.put(c.getItemBase().getName(), ItemEquipType.NIE);
            }
            putted = true;
        }
        panel = new JPanel();
        JPanel infoPanel = new JPanel();
//        infoPanel.setBackground(Color.GRAY);
        JLabel name = new JLabel(player.getName());

//        name.setForeground(Color.BLUE);
        JLabel exp = new JLabel("Exp: " + player.getExperience());
        exp.setForeground(Color.MAGENTA);
        JLabel stage = new JLabel("Stage: " + player.getStage());
        stage.setForeground(Color.BLUE);
        JLabel hp = new JLabel("HP: " + player.getCurrentHp() + "/" + player.getHp());

        hp.setForeground(Color.RED);
        JLabel gold = new JLabel("GOLD: " + player.getGold());
        gold.setForeground(Color.ORANGE);
        JLabel dmg = new JLabel("DMG: " + player.getDmg());
        JLabel def = new JLabel("DEF: " + player.getDef());
        infoPanel.add(name);
        infoPanel.add(exp);
        infoPanel.add(stage);
        infoPanel.add(hp);
        infoPanel.add(gold);
        infoPanel.add(dmg);
        infoPanel.add(def);

        JLabel fightLabel = new JLabel("Wejdź do lochu");
        JLabel inventoryLabel = new JLabel("Pokaż ekwipunek");
        JLabel deckLabel = new JLabel("Pokaż karty");
        JLabel addCardLabel = new JLabel("Sklep");
        JLabel exitLabel = new JLabel("Wyjdź");
        JPanel selectPanel = new JPanel();

        //selectPanel.setBackground(Color.GRAY);
        selectPanel.add(fightLabel);
        selectPanel.add(inventoryLabel);
        selectPanel.add(deckLabel);
        selectPanel.add(addCardLabel);
        selectPanel.add(exitLabel);
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

        createControls(selectPanel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected == 1) {
                    // mati we zobacz czemu tego widoku nie chce
                    // wczytywac tylko sama muzaczka idzie bo mnie zaraz chui sttrzeli
                    //createPerformanceBeforeFight();
                    gameController.switchToFightController();
                }
                if (selected == 2) createPlayerInventoryView();
                if (selected == 3) createDeckView();
                if (selected == 4) createShopView();
                if (selected == 5) exitGame();
            }
        });
        panel.setLayout(new GridLayout(2, 1));
        panel.add(infoPanel);
        panel.add(selectPanel);
        selectPanel.setFocusable(true);
        gameController.setMainContent(panel);
        selectPanel.requestFocusInWindow();
    }

    private void exitGame() {
        System.exit(0);
    }

    private void createPerformanceBeforeFight() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setLayout(new GridLayout(0, 3));
        JLabel gamer = new JLabel(player.getName());
        JLabel opponent = new JLabel("enemy");
        container.add(gamer);
        container.add(new JLabel(""));
        container.add(opponent);
        gameController.setMainContent(container);
        getMusic("start");
    }

    private void createDeckView() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel p = new JPanel();
        JPanel options = new JPanel();
        JLabel exit = new JLabel("Wyjdź");
        options.add(exit);
        List<Card> cardList = deckService.getDeckById(player.getDeck().getId()).getCardSet();
        if (cardList.size() == 0) {
            JLabel empty = new JLabel("W Twoim decku nie ma żadnych kart");
            p.add(empty);
        } else {
            for (Card c : cardList) {
                JLabel l = new JLabel(c.getName() + " " + c.getType());
                p.add(l);
            }
        }
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        createControls(options, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected == 1) createView();
            }
        });

        container.add(p);
        container.add(options);
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();
    }

    private void createShopView() {
        shop.refreshItems(player);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel options = new JPanel();
        JLabel exit = new JLabel("Wyjdź");
        List<ShopItemDto> shopItems = shop.getItems();
        for (ShopItemDto c : shopItems) {
            JLabel l = new JLabel(c.getName() + "  cost: " + c.getPrice() + " gold");
            options.add(l);
        }
        options.add(exit);
        createControls(options, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected == options.getComponentCount()) {
                    createView();
                } else {
                    shop.buyItem(player, selected - 1);
                    createShopView();
                }
            }
        });
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        container.add(options);
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();

    }

    private void createPlayerInventoryView() {
        //generateRandomItem();
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3, 3));
        //container.add(new JLabel(" "));
        container.setBackground(Color.GRAY);
        container.add(new JLabel(""));
        container.add(new JLabel("Lista twoich przedmiotów"));
        container.add(new JLabel(""));
        JPanel options = new JPanel();
        options.setBackground(Color.GRAY);
        JPanel itemDmgList = new JPanel();
        itemDmgList.setBackground(Color.GRAY);
        JPanel equip = new JPanel();
        equip.setBackground(Color.GRAY);
        JLabel tmp;
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        itemDmgList.setLayout(new BoxLayout(itemDmgList, BoxLayout.Y_AXIS));
        equip.setLayout(new BoxLayout(equip, BoxLayout.Y_AXIS));
        int count = 0;

        Set<Item> playerInventoryItemsList = player.getInventory().getItemList();
        if (playerInventoryItemsList.isEmpty())
            container.add(new JLabel("Nic nie posiadasz :(")).setForeground(Color.pink);
        else {
            tmp = new JLabel("NAZWA");
            tmp.setForeground(Color.BLUE);
            container.add(tmp);

            tmp = new JLabel("DMG");
            tmp.setForeground(Color.MAGENTA);
            container.add(tmp);

            tmp = new JLabel("ZALOŻONY");
            tmp.setForeground(Color.CYAN);
            container.add(tmp);
            tabNames = new String[playerInventoryItemsList.size()];
            tabDmg = new int[playerInventoryItemsList.size()];
            for (Item c : playerInventoryItemsList) {

                tabNames[count] = c.getItemBase().getName();
                tabDmg[count] = c.getItemBase().getDmg();
                options.add(new JLabel(c.getItemBase().getName()));
                itemDmgList.add(new JLabel("" + c.getItemBase().getDmg()));
                tmp = new JLabel(String.valueOf(equippedItems.get(c.getItemBase().getName())));
                if (equippedItems.get(tabNames[count]) == ItemEquipType.TAK) {

                    tmp.setForeground(Color.GREEN);
                }
                equip.add(tmp);
                count++;
            }
        }
        final int counter = count;
        JLabel changePage = new JLabel();

        options.add(new JLabel("Wszystkie przedmioty"));
        options.add(new JLabel("Wroc"));
        changePage.add(new JLabel());
        changePage.setLayout(new BoxLayout(changePage, BoxLayout.Y_AXIS));
        createControls(options, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected <= counter) {
                    if (equippedItems.get(tabNames[selected - 1]) == ItemEquipType.NIE && activeItems <= 3) {
                        equippedItems.put(tabNames[selected - 1], ItemEquipType.TAK);
                        activeItems++;
                        player.setDmg(player.getDmg() + tabDmg[selected - 1]);
                        playerService.addPlayer(player);
                    } else {
                        equippedItems.put(tabNames[selected - 1], ItemEquipType.NIE);
                        activeItems--;
                        player.setDmg(player.getDmg() - tabDmg[selected - 1]);
                        playerService.addPlayer(player);
                    }
                    createPlayerInventoryView();
                } else if (selected == (counter + 1)) {
                    createAllItemsView();
                } else if (selected == counter + 2) {
                    createView();
                }
            }
        });


        container.add(options);
        container.add(itemDmgList);
        container.add(equip);
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();
    }


    public void generateRandomItem() {
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

        boolean exist = false;
        for (Item i : playerListItem) {
            if (i.getItemBase().getName().equals(item.getItemBase().getName())) {
                System.out.println("Niestety wylosowałes item, który już posiadasz " + item.getItemBase().getName());
                exist = true;
            }
        }
        if(!exist) {
            playerListItem.add(item);

            player.getInventory().setItemList(playerListItem);
            player.setInventory(player.getInventory());
            // nie trybi zapisanie inventory
            //inventoryService.addInventory(player.getInventory());
            itemService.addItem(item);
            playerService.addPlayer(player);
            equippedItems.put(item.getItemBase().getName(), ItemEquipType.NIE);
        }
    }

    private void createAllItemsView() {
        shop.refreshItems(player);
        JPanel container = new JPanel();
        container.setBackground(Color.GRAY);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setLayout(new GridLayout(0, 5));
        JPanel options = new JPanel();
        options.setBackground(Color.GRAY);
        JLabel exit = new JLabel("Back");
        JLabel name;
        JLabel dmg;
        List<ItemBase> listOfItemsFromBase = itemBaseService.getItemBases();
        container.add(new JLabel(" "));
        container.add(new JLabel(" "));
        container.add(new JLabel("List of all items to get"));
        container.add(new JLabel(" "));
        container.add(new JLabel(" "));
        JLabel tmp;
        container.add(new JLabel(" "));
        container.add(new JLabel(" "));
        tmp = new JLabel("NAZWA");
        tmp.setForeground(Color.BLUE);
        container.add(tmp);

        tmp = new JLabel("DMG");
        tmp.setForeground(Color.MAGENTA);
        container.add(tmp);


        for (ItemBase c : listOfItemsFromBase) {
            container.add(new JLabel(" "));

            name = new JLabel(c.getName());
            dmg = new JLabel("" + c.getDmg());

            container.add(new JLabel(" "));
            container.add(new JLabel(" "));
            container.add(name);
            container.add(dmg);
        }
        options.add(exit);
        createControls(options, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected == options.getComponentCount()) {
                    createPlayerInventoryView();
                } else {
                    createAllItemsView();
                }
            }
        });
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        container.add(options);
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();

    }

    public void refreshColor(JPanel p) {
        Component[] components = p.getComponents();
        for (Component c : components) {
            c.setForeground(Color.BLACK);
        }
        p.getComponent(selected - 1).setForeground(Color.red);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void createControls(JPanel p, AbstractAction action) {
        selected = 1;
        refreshColor(p);
        Action incrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected < p.getComponentCount()) selected++;
                refreshColor(p);
            }
        };
        Action decrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected != 1) selected--;
                refreshColor(p);
            }
        };
        p.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressedUp");
        p.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressedDown");
        p.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "pressedEnter");
        p.getActionMap().put("pressedUp", decrementSelection);
        p.getActionMap().put("pressedDown", incrementSelection);
        p.getActionMap().put("pressedEnter", action);
    }
}
