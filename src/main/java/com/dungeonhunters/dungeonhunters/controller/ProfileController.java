package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.dto.ItemEquipType;
import com.dungeonhunters.dungeonhunters.dto.ItemType;
import com.dungeonhunters.dungeonhunters.dto.Shop;
import com.dungeonhunters.dungeonhunters.dto.ShopItemDto;
import com.dungeonhunters.dungeonhunters.model.*;
import com.dungeonhunters.dungeonhunters.service.*;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
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
    public Map<Item,ItemEquipType> inventoryItems;
    private int activeItems=0;
    public int selected = 1;
    public int additionalDmg;
    private final Shop shop;
    private final DeckService deckService;
    private final ItemBaseService itemBaseService;
    public JPanel infoPanel, playerPanel,statisticPanel,selectPanel;



    ProfileController(ItemBaseService itemBaseService, DeckService deckService, Shop shop, PlayerService playerService) {
        this.deckService = deckService;
        this.playerService = playerService;
        this.itemBaseService = itemBaseService;
        this.shop = shop;
        this.inventoryItems = new HashMap<>();
    }

    public void createView() {

        boolean contains=false;
        for (Item c : player.getInventory().getItemList()) {
            for(Map.Entry<Item,ItemEquipType> entry : inventoryItems.entrySet()){
                if (entry.getKey().getId().equals(c.getId())) {
                    contains = true;
                    break;
                }
            }
            if(!contains)inventoryItems.put(c, ItemEquipType.UNEQUIPPED);
            contains=false;
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
        createPlayerInventoryView(contentPanel);
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
//        createStatisticPanel();

        topPanel.add(playerPanel);
//        topPanel.add(statisticPanel);
        topPanel.add(infoPanel);
        bottomPanel.add(selectPanel);
        bottomPanel.add(scrollable);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(topPanel);
        panel.add(bottomPanel);
        gameController.setMainContent(panel);
    }
//    private void createStatisticPanel(){
//        statisticPanel.removeAll();
//        statisticPanel.setLayout(new FlowLayout());
//        statisticPanel.setPreferredSize(new Dimension(400, 200));
//
//        JLabel equippedItems = new JLabel("Eqquiped: " + activeItems + " items (+" + bonusDmg + " dmg)");
//        equippedItems.setForeground(Color.RED);
//        statisticPanel.add(equippedItems);
//        statisticPanel.revalidate();
//        statisticPanel.repaint();
//    }
    private void setStyleToLabel(JLabel l, int width, int height, Color color,int fontSize, int fontWeight){
        l.setPreferredSize(new Dimension(width,height));
        l.setForeground(color);
        l.setFont(new Font("Arial", fontWeight,fontSize));
    }
    private void createPlayerPanel(){
        playerPanel.removeAll();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        playerPanel.setPreferredSize(new Dimension(600, 200));
        JLabel playerIcon = LogoController.getLogoPlayer(player.getLogo());
        playerIcon.setPreferredSize(new Dimension(200,200));
        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT,25,0));
        info.setPreferredSize(new Dimension(400,200));

        JLabel name = new JLabel(player.getName()+" ("+player.getLogo()+")");
        setStyleToLabel(name, 350,24, Color.black,20, Font.BOLD);
        JLabel exp = new JLabel("xp: " + player.getExperience());
        setStyleToLabel(exp, 350,20, Color.DARK_GRAY,16, Font.PLAIN);
        JLabel stage = new JLabel("level: " + player.getStage());
        setStyleToLabel(stage, 350,20, Color.DARK_GRAY,16, Font.PLAIN);
        JLabel hp = new JLabel("hp: " + player.getCurrentHp() + "/" + player.getHp());
        setStyleToLabel(hp, 350,20, Color.RED,16, Font.BOLD);
        JLabel gold = new JLabel("gold: " + player.getGold());
        setStyleToLabel(gold, 350,20, Color.DARK_GRAY,16, Font.PLAIN);
        JLabel dmg = new JLabel("dmg: " + player.getDmg());
        setStyleToLabel(dmg, 350,16, Color.DARK_GRAY,14, Font.PLAIN);
        JLabel def = new JLabel("def: " + player.getDef());
        setStyleToLabel(def, 350,16, Color.DARK_GRAY,14, Font.PLAIN);

        info.add(name);
        info.add(hp);
        info.add(exp);
        info.add(stage);
        info.add(gold);
        info.add(dmg);
        info.add(def);
        playerPanel.add(playerIcon);
        playerPanel.add(info);
        playerPanel.revalidate();
        playerPanel.repaint();
    }
    private void createInfoPanel(){
        infoPanel.removeAll();
        infoPanel.setPreferredSize(new Dimension(600, 200));
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        int bonusDmg = 0;
        for(Map.Entry<Item,ItemEquipType> entry : inventoryItems.entrySet()){
            if(entry.getValue() == ItemEquipType.EQUIPPED)bonusDmg+=entry.getKey().getItemBase().getDmg();
        }

        JPanel leftSide = new JPanel(new FlowLayout(FlowLayout.LEFT,25,25));
        leftSide.setPreferredSize(new Dimension(300,200));
        JLabel ownedItems = new JLabel("You have "+inventoryItems.size()+" items");
        setStyleToLabel(ownedItems,250,20,Color.black,14,Font.PLAIN);
        JLabel ownedCards = new JLabel("You have "+deckService.getDeckById(player.getDeck().getId()).getCardSet().size()+" cards");
        setStyleToLabel(ownedCards,250,20,Color.black,14,Font.PLAIN);
        JLabel equippedItems = new JLabel("Eqquiped: " + activeItems + " items (+" + bonusDmg + " dmg)");
        setStyleToLabel(equippedItems,250,20,Color.black,14,Font.PLAIN);

        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        rightSide.setPreferredSize(new Dimension(200,200));
        for(Map.Entry<Item,ItemEquipType> entry : inventoryItems.entrySet()){
            if(entry.getValue() == ItemEquipType.EQUIPPED){
                JLabel equippedItem = LogoController.getLogoItem(entry.getKey().getItemBase().getName());
                equippedItem.setPreferredSize(new Dimension(98,98));
                equippedItem.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
                rightSide.add(equippedItem);
            }
        }
        leftSide.add(ownedItems);
        leftSide.add(ownedCards);
        leftSide.add(equippedItems);
        infoPanel.add(leftSide);
        infoPanel.add(rightSide);
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
        buyButton.setPreferredSize(new Dimension(150, 50));
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
        player.setDmg(player.getDmg() - additionalDmg );
        playerService.addPlayer(player);
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
        }
        panel.revalidate();
        panel.repaint();
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
        Set<Item> playerInventoryItemsList = inventoryItems.keySet();
        panel.setPreferredSize(new Dimension(1000, (playerInventoryItemsList.size() * 105) + 50));
        panel.add(inventoryName);
        if (playerInventoryItemsList.isEmpty()) {
            panel.add(new JLabel("No items")).setForeground(Color.DARK_GRAY);
        } else {

            for (Item c : playerInventoryItemsList) {
                JLabel itemIcon = getLogoItem(c.getItemBase().getName());
                JPanel itemContainer = new JPanel();
                JLabel itemName = new JLabel(c.getItemBase().getName());
                JLabel itemDescription = new JLabel(c.getItemBase().getDmg() + " atk");
                JButton equipButton = new JButton(inventoryItems.get(c).toString());

//                int itemSellValue;
//                if(c.getItemBase().getDmg() <= 5 ) itemSellValue = 1;
//                else if (c.getItemBase().getDmg() > 6 && c.getItemBase().getDmg() < 17) itemSellValue = 2;
//                else itemSellValue = 4;
//
//                JButton sellButton = new JButton(itemSellValue+"");
//                sellButton.setForeground(Color.WHITE);
//                sellButton.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        inventoryItems.remove(c);
//                        player.setGold(player.getGold() + itemSellValue);
//                    }
//
//                    @Override
//                    public void mouseEntered(MouseEvent e) {
//                        sellButton.setForeground(Color.BLUE);
//                    }
//
//                    @Override
//                    public void mouseExited(MouseEvent e) {
//                        sellButton.setForeground(Color.WHITE);
//                    }
//                });
                equipButton.setForeground(Color.WHITE);
                if(inventoryItems.get(c) == ItemEquipType.EQUIPPED) {
                    equipButton.setBackground(Color.LIGHT_GRAY);
                }else{
                    equipButton.setBackground(Color.GRAY);
                }
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
                equipButton.addActionListener(e -> {
                    if (e.getActionCommand().equals(ItemEquipType.UNEQUIPPED.toString())) {
                        if (activeItems < 4) {
                            player.setDmg(player.getDmg() + c.getItemBase().getDmg());
                            inventoryItems.replace(c,ItemEquipType.EQUIPPED);
                            activeItems++;
                            additionalDmg+=c.getItemBase().getDmg();
                            equipButton.setText(ItemEquipType.EQUIPPED.toString());
                            createInfoPanel();
                            createPlayerPanel();
                            createPlayerInventoryView(panel);

                        }
                    } else {
                        player.setDmg(player.getDmg() - c.getItemBase().getDmg());
                        activeItems--;
                        inventoryItems.replace(c,ItemEquipType.UNEQUIPPED);
                        equipButton.setText(ItemEquipType.UNEQUIPPED.toString());
                        additionalDmg-=c.getItemBase().getDmg();
                        createInfoPanel();
                        createPlayerPanel();
                        createPlayerInventoryView(panel);
                    }
                });

                itemContainer.add(itemIcon);
                itemContainer.add(itemName);
                itemContainer.add(itemDescription);
                itemContainer.add(equipButton);
                //itemContainer.add(sellButton);
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
