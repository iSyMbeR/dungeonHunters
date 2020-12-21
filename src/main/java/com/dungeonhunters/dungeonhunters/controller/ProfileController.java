package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.dto.ItemEquipType;
import com.dungeonhunters.dungeonhunters.dto.Shop;
import com.dungeonhunters.dungeonhunters.dto.ShopItemDto;
import com.dungeonhunters.dungeonhunters.model.*;
import com.dungeonhunters.dungeonhunters.service.*;
import org.springframework.stereotype.Controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.dungeonhunters.dungeonhunters.controller.LogoController.getLogoItem;
import static com.dungeonhunters.dungeonhunters.controller.MusicController.getMusic;
import static org.springframework.boot.ansi.AnsiColor.CYAN;

@Controller

public class ProfileController extends JFrame {
    public JPanel panel;
    public final PlayerService playerService;
    public GameController gameController;
    public Player player;
    public static Map<String, ItemEquipType> equippedItems = new HashMap<>();
    private boolean equipped = false;
    private int activeItems;
    public int selected = 1;
    private final Shop shop;
    private final DeckService deckService;
    private final ItemBaseService itemBaseService;
    public JPanel infoPanel, playerPanel,statisticPanel,selectPanel;


    ProfileController(ItemBaseService itemBaseService, DeckService deckService, Shop shop, PlayerService playerService) {
        this.deckService = deckService;
        this.playerService = playerService;
        this.itemBaseService = itemBaseService;
        this.shop = shop;
    }

    public void createView() {
        if (!equipped) {
            for (Item c : player.getInventory().getItemList()) {
                equippedItems.put(c.getItemBase().getName(), ItemEquipType.NIE);
            }
            equipped = true;
        }
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setPreferredSize(new Dimension(1200, 200));
        //topPanel.setBorder(BorderFactory.createLineBorder(Color.black,5));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setPreferredSize(new Dimension(1200, 600));
        //bottomPanel.setBorder(BorderFactory.createLineBorder(Color.red,5));

        //content panel
        JPanel contentPanel = new JPanel();
        JScrollPane scrollable = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollable.setPreferredSize(new Dimension(1000, 600));
        scrollable.getVerticalScrollBar().setUnitIncrement(16);
        //contentPanel.setPreferredSize(new Dimension(1000,1000));
        JLabel cp = new JLabel("Content panel here");
        contentPanel.add(cp);
        contentPanel.setBackground(Color.lightGray);
        //contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));

        //select panel
        JButton fightButton = new JButton("Enter dungeon");
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.switchToFightController();
            }
        });
        JButton inventoryButton = new JButton("Show inventory");
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPlayerInventoryView(contentPanel);
            }
        });
        JButton deckButton = new JButton("Show cards");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDeckView(contentPanel);
            }
        });
        JButton shopButton = new JButton("Shop");
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndDisplayShopView(contentPanel);
            }
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
        selectPanel = new JPanel();
        selectPanel.add(fightButton);
        selectPanel.add(inventoryButton);
        selectPanel.add(deckButton);
        selectPanel.add(shopButton);
        selectPanel.add(exitButton);
        selectPanel.setPreferredSize(new Dimension(200, 600));
        styleSelectPanel(selectPanel);


        infoPanel = new JPanel();
        statisticPanel = new JPanel();
        playerPanel = new JPanel();

        createPlayerPanel();
        createInfoPanel();
        createStatisticPanel();

        topPanel.add(playerPanel);
        topPanel.add(statisticPanel);
        topPanel.add(infoPanel);
        bottomPanel.add(selectPanel);
        bottomPanel.add(scrollable);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(topPanel);
        panel.add(bottomPanel);
        gameController.setMainContent(panel);
    }
    private void createStatisticPanel(){
        statisticPanel.removeAll();
        statisticPanel = new JPanel();
        statisticPanel.setLayout(new FlowLayout());
        statisticPanel.setPreferredSize(new Dimension(400, 200));
        int bonusDmg = player.getDmg() - playerService.getPlayerById(player.getId()).getDmg();

        JLabel sl = new JLabel("Statistic panel here");
        JLabel eqquipedItems = new JLabel("Eqquiped: " + activeItems + " items (+" + bonusDmg + " dmg)");
        eqquipedItems.setForeground(Color.RED);
        statisticPanel.add(eqquipedItems);
        statisticPanel.add(sl);
        statisticPanel.revalidate();
        statisticPanel.repaint();
    }
    private void createPlayerPanel(){
        playerPanel.removeAll();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        playerPanel.setPreferredSize(new Dimension(400, 200));
        JLabel playerIcon = LogoController.getLogoPlayer(player.getLogo());
        playerIcon.setPreferredSize(new Dimension(200,200));
        playerPanel.add(playerIcon);
        playerPanel.revalidate();
        playerPanel.repaint();
    }
    private void createInfoPanel(){
        infoPanel.removeAll();
        infoPanel.setPreferredSize(new Dimension(400, 200));
        JLabel name = new JLabel(player.getName());
        JLabel exp = new JLabel("Exp: " + player.getExperience());
        exp.setForeground(Color.DARK_GRAY);
        JLabel stage = new JLabel("Stage: " + player.getStage());
        stage.setForeground(Color.black);
        JLabel hp = new JLabel("HP: " + player.getCurrentHp() + "/" + player.getHp());
        JLabel gold = new JLabel("GOLD: " + player.getGold());
        gold.setForeground(Color.ORANGE);
        JLabel dmg = new JLabel("DMG: " + player.getDmg());
        dmg.setForeground(Color.RED);
        JLabel def = new JLabel("DEF: " + player.getDef());
        def.setForeground(Color.BLUE);
        infoPanel.add(name);
        infoPanel.add(exp);
        infoPanel.add(stage);
        infoPanel.add(hp);
        infoPanel.add(gold);
        infoPanel.add(dmg);
        infoPanel.add(def);
        infoPanel.revalidate();
        infoPanel.repaint();
    }
    private void createAndDisplayShopView(JPanel panel) {
        panel.removeAll();
        JLabel shopName = new JLabel("Shop");
        shopName.setPreferredSize(new Dimension(800, 40));
        shopName.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel refreshPanel = new JPanel();
        refreshPanel.setPreferredSize(new Dimension(150,40));
        refreshPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        refreshPanel.setBackground(null);
        JButton refreshItems = new JButton("Refresh items");
        refreshItems.setFocusPainted(false);
        refreshItems.setBackground(null);
        refreshItems.setBorder(null);
        refreshItems.setPreferredSize(new Dimension(100, 40));
        refreshItems.addActionListener(e -> {
            if(shop.buyRefreshItems(player)){
                shop.refreshItems(player);
                createAndDisplayShopView(panel);
                createInfoPanel();
            }
        });
        refreshItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                refreshItems.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                refreshItems.setForeground(Color.BLACK);

            }
        });
        JLabel amount = new JLabel("2");
        JLabel goldIcon = LogoController.getLogoCard("Gold");
        goldIcon.setPreferredSize(new Dimension(20,40));
        amount.setPreferredSize(new Dimension(20,40));
        refreshPanel.add(refreshItems);
        refreshPanel.add(goldIcon);
        refreshPanel.add(amount);
        panel.add(shopName);
        panel.add(refreshPanel);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        List<ShopItemDto> shopItems = shop.getItems();
        panel.setPreferredSize(new Dimension(1000, (shopItems.size() * 105) + 50));
        for (ShopItemDto c : shopItems) {
            JLabel itemIcon = LogoController.getLogoCard(c.getType().toString());
            JPanel itemContainer = new JPanel();
            JLabel itemName = new JLabel(c.getName());
            JLabel itemDescription = new JLabel(c.getDescription());
            goldIcon = LogoController.getLogoCard("Gold");
            JLabel itemCost = new JLabel(String.valueOf(c.getPrice()));
            JButton buyButton = new JButton("Buy");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shop.buyItem(player, shopItems.indexOf(c));
                    createAndDisplayShopView(panel);
                    createInfoPanel();
                }
            });
            if (player.getGold() < c.price) buyButton.setEnabled(false);
            itemContainer.add(itemIcon);
            itemContainer.add(itemName);
            itemContainer.add(itemDescription);
            itemContainer.add(goldIcon);
            itemContainer.add(itemCost);
            itemContainer.add(buyButton);
            styleItemShopEntry(itemContainer);
            panel.add(itemContainer);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void styleCardDeckEntry(JPanel itemContainer) {
        itemContainer.setPreferredSize(new Dimension(990, 100));
        itemContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel itemIcon = (JLabel) itemContainer.getComponent(0);
        itemIcon.setPreferredSize(new Dimension(100, 100));
        JLabel itemName = (JLabel) itemContainer.getComponent(1);
        itemName.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        itemName.setPreferredSize(new Dimension(300, 100));
        JLabel itemDescription = (JLabel) itemContainer.getComponent(2);
        itemDescription.setPreferredSize(new Dimension(300, 100));
        itemContainer.getComponent(3).setPreferredSize(new Dimension(200, 100));
        itemContainer.getComponent(4).setPreferredSize(new Dimension(40, 100));
        itemContainer.getComponent(5).setPreferredSize(new Dimension(50, 100));
    }

    private void styleItemShopEntry(JPanel itemContainer) {

        itemContainer.setPreferredSize(new Dimension(990,100));
        itemContainer.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        JLabel itemIcon = (JLabel)itemContainer.getComponent(0);
        itemIcon.setPreferredSize(new Dimension(100,100));
        JLabel itemName = (JLabel)itemContainer.getComponent(1);
        itemName.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));
        itemName.setPreferredSize(new Dimension(200,100));
        JLabel itemDescription = (JLabel) itemContainer.getComponent(2);
        itemDescription.setPreferredSize(new Dimension(350,100));
        itemContainer.getComponent(3).setPreferredSize(new Dimension(40,100));
        itemContainer.getComponent(4).setPreferredSize(new Dimension(160, 100));
        JButton buyButton = (JButton)itemContainer.getComponent(5);
        buyButton.setPreferredSize(new Dimension(100,50));
        buyButton.setBorder(BorderFactory.createEmptyBorder(25,0,25,0));
        buyButton.setFocusPainted(false);
        buyButton.setFont(new Font("Arial", Font.BOLD, 25));
        buyButton.setBackground(Color.GRAY);
    }

    private void styleItemInventoryEntry(JPanel itemContainer) {
        itemContainer.setPreferredSize(new Dimension(990, 100));
        itemContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel itemIcon = (JLabel) itemContainer.getComponent(0);
        itemIcon.setPreferredSize(new Dimension(100, 100));
        JLabel itemName = (JLabel) itemContainer.getComponent(1);
        itemName.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        itemName.setPreferredSize(new Dimension(300, 100));
        JLabel itemDescription = (JLabel) itemContainer.getComponent(2);
        itemDescription.setPreferredSize(new Dimension(300, 100));
        JButton buyButton = (JButton) itemContainer.getComponent(3);
        buyButton.setPreferredSize(new Dimension(60, 50));
        buyButton.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        buyButton.setFocusPainted(false);
        buyButton.setFont(new Font("Arial", Font.BOLD, 20));
        buyButton.setBackground(Color.GRAY);
    }

    private void styleSelectPanel(JPanel selectPanel) {
        selectPanel.setLayout(new FlowLayout());
        for (Component c : selectPanel.getComponents()) {
            setButtonStyle((JButton) c);

        }
    }

    private void setButtonStyle(JButton button) {
        Color hoveredColor = Color.blue;
        Color normalColor = Color.white;
        button.setBorder(null);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(normalColor);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoveredColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
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

    private void createDeckView(JPanel panel) {
        panel.removeAll();
        JLabel deckName = new JLabel("Deck");

        deckName.setPreferredSize(new Dimension(850, 40));
        deckName.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        List<Card> cardList = deckService.getDeckById(player.getDeck().getId()).getCardSet();
        panel.setPreferredSize(new Dimension(1000, (cardList.size() * 105) + 50));
        panel.add(deckName);
        if (cardList.isEmpty()) {
            panel.add(new JLabel("No cards")).setForeground(Color.DARK_GRAY);
        } else {
            for (Card c : cardList) {
                JPanel itemContainer = new JPanel();
                JLabel cardIcon = LogoController.getLogoCard(c.getType().toString());
                JLabel cardName = new JLabel(c.getName());
                JLabel cardDescription = new JLabel(c.getDescription());
                JLabel cardValue = new JLabel((c.getValue()) + " " + c.getType().toString());
                JLabel costIco = LogoController.getLogoCard("Crystal");
                JLabel costValue = new JLabel(c.getCost()+"");
                costValue.setFont(new Font("Arial", Font.BOLD, 30));
                itemContainer.add(cardIcon);
                itemContainer.add(cardName);
                itemContainer.add(cardValue);
                itemContainer.add(cardDescription);
                itemContainer.add(costIco);
                itemContainer.add(costValue);
                styleCardDeckEntry(itemContainer);
                panel.add(itemContainer);
            }
            panel.revalidate();
            panel.repaint();
        }
    }

    private void createShopView() {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0, 4));
        JPanel options = new JPanel();
        JPanel gold = new JPanel();
        JLabel exit = new JLabel("Exit");
        List<ShopItemDto> shopItems = shop.getItems();
        for (ShopItemDto c : shopItems) {
            JLabel l = new JLabel(c.getName());
            JLabel lg = new JLabel("cost: " + c.getPrice() + " gold");
            gold.add(lg);
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
        gold.setLayout(new BoxLayout(gold, BoxLayout.Y_AXIS));
        container.add(new JLabel(""));
        container.add(new JLabel("NAME")).setForeground(Color.BLUE);
        container.add(new JLabel("COST")).setForeground(Color.darkGray);
        container.add(new JLabel(""));

        container.add(new JLabel(""));
        container.add(options);
        container.add(gold);
        container.add(new JLabel(""));
        container.add(new JLabel(""));
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();

    }


    private void createPlayerInventoryView(JPanel panel) {
        panel.removeAll();
        JLabel inventoryName = new JLabel("Inventory");

        inventoryName.setPreferredSize(new Dimension(850, 40));
        inventoryName.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        Set<Item> playerInventoryItemsList = player.getInventory().getItemList();
        panel.setPreferredSize(new Dimension(1000, (playerInventoryItemsList.size() * 105) + 50));
        panel.add(inventoryName);
        if (playerInventoryItemsList.isEmpty()) {
            panel.add(new JLabel("No items")).setForeground(Color.DARK_GRAY);
        } else {


            for (Item c : playerInventoryItemsList) {

                System.out.println(c.getItemBase().getName());
                JLabel itemIcon = LogoController.getLogoItem(c.getItemBase().getName());
                //JLabel itemIcon = new JLabel("");
                JPanel itemContainer = new JPanel();
                JLabel itemName = new JLabel(c.getItemBase().getName());
                JLabel itemDescription = new JLabel(c.getItemBase().getDmg() + " atk");
                JLabel itemsEquip = new JLabel(String.valueOf(equippedItems.get(c.getItemBase().getName())));
                JButton equipButton = new JButton(itemsEquip.getText());
                equipButton.setForeground(Color.WHITE);
                equipButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        equipButton.setForeground(Color.BLUE);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        equipButton.setForeground(Color.WHITE);
                    }
                });
                equipButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("NIE")) {
                            if (activeItems <= 3) {
                                player.setDmg(player.getDmg() + c.getItemBase().getDmg());
                                equippedItems.replace(itemName.getText(), ItemEquipType.TAK);
                                activeItems++;
                                System.out.println(activeItems);
                                equipButton.setText("TAK");
                                equipButton.setBackground(Color.LIGHT_GRAY);

                            }
                        } else {
                            player.setDmg(player.getDmg() - c.getItemBase().getDmg());
                            activeItems--;
                            System.out.println(activeItems);
                            equippedItems.replace(itemName.getText(), ItemEquipType.NIE);
                            equipButton.setText("NIE");
                            equipButton.setBackground(Color.GRAY);
                        }
                    }
                });

                itemContainer.add(itemIcon);
                itemContainer.add(itemName);
                itemContainer.add(itemDescription);
                itemContainer.add(equipButton);
                styleItemInventoryEntry(itemContainer);
                panel.add(itemContainer);
            }
        }
        panel.revalidate();
        panel.repaint();

    }

    private void createAllItemsView(JPanel panel) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setLayout(new GridLayout(0, 5));
        JPanel options = new JPanel();
        JLabel exit = new JLabel("Back");
        JLabel name;
        JLabel dmg;
        JLabel rarity;
        List<ItemBase> listOfItemsFromBase = itemBaseService.getItemBases();
        container.add(new JLabel(" "));
        container.add(new JLabel(" "));
        container.add(new JLabel(" List of all items to get ")).setForeground(Color.DARK_GRAY);
        container.add(new JLabel(" "));
        container.add(new JLabel(" "));
        JLabel tmp;
        container.add(new JLabel(" "));
        tmp = new JLabel("NAME");
        tmp.setForeground(Color.BLUE);
        container.add(tmp);

        tmp = new JLabel("DMG");
        tmp.setForeground(Color.RED);
        container.add(tmp);

        tmp = new JLabel("RARITY");
        tmp.setForeground(Color.DARK_GRAY);
        container.add(tmp);


        for (ItemBase c : listOfItemsFromBase) {
            container.add(new JLabel(" "));

            name = new JLabel(c.getName());
            dmg = new JLabel("" + c.getDmg());
            rarity = new JLabel("" + c.getRarity());
            container.add(new JLabel(" "));
            container.add(name);
            container.add(dmg);
            container.add(rarity);
        }
        options.add(exit);
        createControls(options, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected == options.getComponentCount()) {
                    createPlayerInventoryView(panel);
                } else {
                    createAllItemsView(panel);
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
