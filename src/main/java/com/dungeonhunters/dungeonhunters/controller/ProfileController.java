package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.Decorator.EpicBonus;
import com.dungeonhunters.dungeonhunters.Decorator.Equipment;
import com.dungeonhunters.dungeonhunters.Decorator.LegendaryBonus;
import com.dungeonhunters.dungeonhunters.Decorator.RareBonus;
import com.dungeonhunters.dungeonhunters.Settings;
import com.dungeonhunters.dungeonhunters.dto.ItemEquipType;
import com.dungeonhunters.dungeonhunters.dto.Shop;
import com.dungeonhunters.dungeonhunters.dto.ShopItemDto;
import com.dungeonhunters.dungeonhunters.model.*;
import com.dungeonhunters.dungeonhunters.service.*;
import org.springframework.stereotype.Controller;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.dungeonhunters.dungeonhunters.controller.FightController.LOST;
import static com.dungeonhunters.dungeonhunters.controller.LogoController.getLogoItem;
import static com.dungeonhunters.dungeonhunters.dto.MenuStrings.*;

@Controller
public class ProfileController extends JFrame {
    public JPanel panel;
    public final PlayerService playerService;
    public GameController gameController;
    public Player player;
    public Map<Item, ItemEquipType> inventoryItems;
    public Map<Item, Integer> upgradedItems;
    private int activeItems = 0;
    public int selected = 1;
    public int additionalDmg;
    private final Shop shop;
    private final DeckService deckService;
    public JPanel infoPanel, playerPanel, statisticPanel, selectPanel;
    private Settings settings;
    public static int upgradeCounter = 0;
    private int bonusValue = 0;


    ProfileController(DeckService deckService, Shop shop, PlayerService playerService) {
        this.deckService = deckService;
        this.playerService = playerService;
        this.shop = shop;
        this.inventoryItems = new HashMap<>();
        this.upgradedItems = new HashMap<>();
    }

    public void createView() {

        settings = Settings.getInstance();
        System.out.println("PLayer stats"+ player.toString() + " i dmg " + player.getDmg());
        resetStatsAfterReloadCharacter();
        if(upgradeCounter == 3){
            upgradedItems.clear();
            upgradeCounter = 0;
            player.setDmg(player.getDmg() - bonusValue);
            additionalDmg = additionalDmg - bonusValue;
        }

        boolean contains = false;
        for (Item c : player.getInventory().getItemList()) {
            for (Map.Entry<Item, ItemEquipType> entry : inventoryItems.entrySet()) {
                if (entry.getKey().getId().equals(c.getId())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) inventoryItems.put(c, ItemEquipType.UNEQUIPPED);
            contains = false;
        }

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setPreferredSize(new Dimension(1200, 200));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setPreferredSize(new Dimension(1200, 600));


        //content panel
        JPanel contentPanel = new JPanel();
        JScrollPane scrollable = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollable.setPreferredSize(new Dimension(1000, 600));
        scrollable.getVerticalScrollBar().setUnitIncrement(16);
        createPlayerInventoryView(contentPanel);
        contentPanel.setBackground(Color.lightGray);

        //select panel
        JButton fightButton = new JButton(ENTER_DUNGEON);
        fightButton.addActionListener(e -> gameController.switchToFightController());
        JButton inventoryButton = new JButton(SHOW_INVENTORY);
        inventoryButton.addActionListener(e -> createPlayerInventoryView(contentPanel));
        JButton deckButton = new JButton(SHOW_CARDS);
        deckButton.addActionListener(e -> createDeckView(contentPanel));
        JButton shopButton = new JButton(SHOP);
        shopButton.addActionListener(e -> createAndDisplayShopView(contentPanel));
        JButton exitButton = new JButton(EXIT);
        exitButton.addActionListener(e -> exitGame());
        JButton blacksmith = new JButton(BLACKSMITH);
        blacksmith.addActionListener(e -> createBlacksmithView(contentPanel));

        JButton changeLanguageButton = new JButton(CHANGE_LANGUAGE);
        changeLanguageButton.setHorizontalTextPosition(AbstractButton.CENTER);
        changeLanguageButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        changeLanguageButton.setIcon(getIcon(settings.getCurrentLanguage()));
        changeLanguageButton.addActionListener(e -> {
            if (settings.getCurrentLanguage().equals("pl")) {
                settings.setCurrentLanguage("en");
            } else {
                settings.setCurrentLanguage("pl");
            }
            createView();
        });
        changeLanguageButton.revalidate();
        selectPanel = new JPanel();
        selectPanel.add(fightButton);
        selectPanel.add(inventoryButton);
        selectPanel.add(deckButton);
        selectPanel.add(shopButton);
        selectPanel.add(exitButton);
        selectPanel.add(blacksmith);
        selectPanel.add(changeLanguageButton);
        selectPanel.setPreferredSize(new Dimension(200, 600));
        styleSelectPanel(selectPanel);

        infoPanel = new JPanel();
        statisticPanel = new JPanel();
        playerPanel = new JPanel();

        createPlayerPanel();
        createInfoPanel();

        topPanel.add(playerPanel);
        topPanel.add(infoPanel);
        bottomPanel.add(selectPanel);
        bottomPanel.add(scrollable);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(topPanel);
        panel.add(bottomPanel);
        gameController.setMainContent(panel);
    }

    private ImageIcon getIcon(String lang) {
        ImageIcon imageEn = null;
        ImageIcon imagePl = null;
        try {
            imagePl = new ImageIcon(ImageIO.read(new File("src\\main\\resources\\Static\\Flag\\pl.png")));
            imageEn = new ImageIcon(ImageIO.read(new File("src\\main\\resources\\Static\\Flag\\en.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lang.equals("en")) return imagePl;
        else return imageEn;

    }


    private void setStyleToLabel(JLabel l, int width, int height, Color color, int fontSize, int fontWeight) {
        l.setPreferredSize(new Dimension(width, height));
        l.setForeground(color);
        l.setFont(new Font("Arial", fontWeight, fontSize));
    }

    private void createPlayerPanel() {
        playerPanel.removeAll();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        playerPanel.setPreferredSize(new Dimension(600, 200));
        JLabel playerIcon = LogoController.getLogoPlayer(player.getLogo());
        playerIcon.setPreferredSize(new Dimension(200, 200));
        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        info.setPreferredSize(new Dimension(400, 200));

        JLabel name = new JLabel(player.getName() + " (" + player.getLogo() + ")");
        setStyleToLabel(name, 350, 24, Color.black, 20, Font.BOLD);
        JLabel exp = new JLabel(XP + player.getExperience());
        setStyleToLabel(exp, 350, 20, Color.DARK_GRAY, 16, Font.PLAIN);
        JLabel stage = new JLabel(LEVEL + player.getStage());
        setStyleToLabel(stage, 350, 20, Color.DARK_GRAY, 16, Font.PLAIN);
        JLabel hp = new JLabel(HP + player.getCurrentHp() + "/" + player.getHp());
        setStyleToLabel(hp, 350, 20, Color.RED, 16, Font.BOLD);
        JLabel gold = new JLabel(GOLD + player.getGold());
        setStyleToLabel(gold, 350, 20, Color.DARK_GRAY, 16, Font.PLAIN);
        JLabel dmg = new JLabel(DMG + player.getDmg());
        setStyleToLabel(dmg, 350, 16, Color.DARK_GRAY, 14, Font.PLAIN);
        JLabel def = new JLabel(DEF + player.getDef());
        setStyleToLabel(def, 350, 16, Color.DARK_GRAY, 14, Font.PLAIN);

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

    private void createInfoPanel() {
        infoPanel.removeAll();
        infoPanel.setPreferredSize(new Dimension(600, 200));
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        int bonusDmg = 0;
        for (Map.Entry<Item, ItemEquipType> entry : inventoryItems.entrySet()) {
            if (entry.getValue() == ItemEquipType.EQUIPPED) bonusDmg += entry.getKey().getItemBase().getDmg();
        }

        JPanel leftSide = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        leftSide.setPreferredSize(new Dimension(300, 200));
        JLabel ownedItems = new JLabel(YOU_HAVE + " " + inventoryItems.size() + " " + ITEMS);
        setStyleToLabel(ownedItems, 250, 20, Color.black, 14, Font.PLAIN);
        JLabel ownedCards = new JLabel(YOU_HAVE + " " + deckService.getDeckById(player.getDeck().getId()).getCardSet().size() + " " + CARDS);
        setStyleToLabel(ownedCards, 250, 20, Color.black, 14, Font.PLAIN);
        JLabel equippedItems = new JLabel(EQUIPPED + ": " + activeItems + " " + ITEMS + " (+" + additionalDmg + " " + DMG + ")");
        setStyleToLabel(equippedItems, 250, 20, Color.black, 14, Font.PLAIN);

        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rightSide.setPreferredSize(new Dimension(200, 200));
        for (Map.Entry<Item, ItemEquipType> entry : inventoryItems.entrySet()) {
            if (entry.getValue() == ItemEquipType.EQUIPPED) {
                JLabel equippedItem = LogoController.getLogoItem(entry.getKey().getItemBase().getName());
                equippedItem.setPreferredSize(new Dimension(98, 98));
                equippedItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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
        JLabel shopName = new JLabel(SHOP);
        shopName.setPreferredSize(new Dimension(800, 40));
        shopName.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel refreshPanel = new JPanel();
        refreshPanel.setPreferredSize(new Dimension(150, 40));
        refreshPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        refreshPanel.setBackground(null);
        JButton refreshItems = new JButton(REFRESH_ITEMS);
        refreshItems.setFocusPainted(false);
        refreshItems.setBackground(null);
        refreshItems.setBorder(null);
        refreshItems.setPreferredSize(new Dimension(100, 40));
        refreshItems.addActionListener(e -> {
            if (shop.buyRefreshItems(player)) {
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
        goldIcon.setPreferredSize(new Dimension(20, 40));
        amount.setPreferredSize(new Dimension(20, 40));
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
            JButton buyButton = new JButton(BUY);
            buyButton.addActionListener(e -> {
                shop.buyItem(player, shopItems.indexOf(c));
                createAndDisplayShopView(panel);
                createInfoPanel();
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

        itemContainer.setPreferredSize(new Dimension(990, 100));
        itemContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel itemIcon = (JLabel) itemContainer.getComponent(0);
        itemIcon.setPreferredSize(new Dimension(100, 100));
        JLabel itemName = (JLabel) itemContainer.getComponent(1);
        itemName.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        itemName.setPreferredSize(new Dimension(200, 100));
        JLabel itemDescription = (JLabel) itemContainer.getComponent(2);
        itemDescription.setPreferredSize(new Dimension(350, 100));
        itemContainer.getComponent(3).setPreferredSize(new Dimension(40, 100));
        itemContainer.getComponent(4).setPreferredSize(new Dimension(160, 100));
        JButton buyButton = (JButton) itemContainer.getComponent(5);
        buyButton.setPreferredSize(new Dimension(100, 50));
        buyButton.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
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
        //usuniecie bonusowego dmg od itemków z ekwipunku
        player.setDmg(player.getDmg() - additionalDmg);
        playerService.addPlayer(player);
        System.exit(0);
    }

    private void createDeckView(JPanel panel) {
        panel.removeAll();
        JLabel deckName = new JLabel(DECK);

        deckName.setPreferredSize(new Dimension(850, 40));
        deckName.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        List<Card> cardList = deckService.getDeckById(player.getDeck().getId()).getCardSet();
        panel.setPreferredSize(new Dimension(1000, (cardList.size() * 105) + 50));
        panel.add(deckName);
        if (cardList.isEmpty()) {
            panel.add(new JLabel(NO_CARDS)).setForeground(Color.DARK_GRAY);
        } else {
            for (Card c : cardList) {
                JPanel itemContainer = new JPanel();
                JLabel cardIcon = LogoController.getLogoCard(c.getType().toString());
                JLabel cardName = new JLabel(c.getName());
                JLabel cardDescription = new JLabel(c.getDescription());
                JLabel cardValue = new JLabel((c.getValue()) + " " + c.getType().toString());
                JLabel costIco = LogoController.getLogoCard("Crystal");
                JLabel costValue = new JLabel(c.getCost() + "");
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

    private void createPlayerInventoryView(JPanel panel) {
        panel.removeAll();
        JLabel inventoryName = new JLabel(INVENTORY);

        inventoryName.setPreferredSize(new Dimension(850, 40));
        inventoryName.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        Set<Item> playerInventoryItemsList = inventoryItems.keySet();
        panel.setPreferredSize(new Dimension(1000, (playerInventoryItemsList.size() * 105) + 50));
        panel.add(inventoryName);

        if (playerInventoryItemsList.isEmpty()) {
            panel.add(new JLabel(NO_ITEMS)).setForeground(Color.DARK_GRAY);
        } else {
            for (Item c : playerInventoryItemsList) {
                boolean upgraded = false;
                if (upgradedItems.containsKey(c)) upgraded = true;
                JLabel itemIcon = getLogoItem(c.getItemBase().getName());
                JPanel itemContainer = new JPanel();
                JLabel itemName = new JLabel(c.getItemBase().getName());
                JLabel itemDescription;
                if (!upgraded) {
                    itemDescription = new JLabel(c.getItemBase().getDmg() + " atk");
                    itemContainer.setBorder(null);
                } else {
                    Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
                    itemDescription = new JLabel(c.getItemBase().getDmg() + " + bonus(" + upgradedItems.get(c) + ")" + " atk");
                    itemContainer.setBorder(border);
                }

                JButton equipButton = new JButton(inventoryItems.get(c).toString());
                equipButton.setForeground(Color.WHITE);
                if (inventoryItems.get(c) == ItemEquipType.EQUIPPED) {
                    equipButton.setBackground(Color.LIGHT_GRAY);
                } else {
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
                boolean finalUpgraded = upgraded;
                equipButton.addActionListener(e -> {
                    if (e.getActionCommand().equals(ItemEquipType.UNEQUIPPED.toString())) {
                        if (activeItems < 4) {
                            if (finalUpgraded) {
                                player.setDmg(player.getDmg() + c.getItemBase().getDmg() + upgradedItems.get(c));
                                additionalDmg = additionalDmg + c.getItemBase().getDmg() + upgradedItems.get(c);
                                upgradeCounter = 2;
                                bonusValue = upgradedItems.get(c);
                            } else {
                                player.setDmg(player.getDmg() + c.getItemBase().getDmg());
                                additionalDmg += c.getItemBase().getDmg();
                            }
                            inventoryItems.replace(c, ItemEquipType.EQUIPPED);
                            activeItems++;
                            equipButton.setText(ItemEquipType.EQUIPPED.toString());
                            createInfoPanel();
                            createPlayerPanel();
                            createPlayerInventoryView(panel);
                        }
                    } else {
                        if (finalUpgraded) {
                            player.setDmg(player.getDmg() - c.getItemBase().getDmg() - upgradedItems.get(c));
                            additionalDmg = additionalDmg - c.getItemBase().getDmg() - upgradedItems.get(c);
                            upgradeCounter = 1;
                        } else {
                            player.setDmg(player.getDmg() - c.getItemBase().getDmg());
                            additionalDmg -= c.getItemBase().getDmg();

                        }
                        activeItems--;
                        inventoryItems.replace(c, ItemEquipType.UNEQUIPPED);
                        equipButton.setText(ItemEquipType.UNEQUIPPED.toString());
                        createInfoPanel();
                        createPlayerPanel();
                        createPlayerInventoryView(panel);
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

    private void createBlacksmithView(JPanel panel) {
        panel.removeAll();
        JLabel blacksmithLabel = new JLabel(BLACKSMITH);

        blacksmithLabel.setPreferredSize(new Dimension(850, 40));
        blacksmithLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        Set<Item> itemListToUpgrade = inventoryItems.keySet();
        panel.setPreferredSize(new Dimension(1000, (itemListToUpgrade.size() * 105) + 50));
        panel.add(blacksmithLabel);

        if (itemListToUpgrade.isEmpty()) {
            panel.add(new JLabel(NO_ITEMS)).setForeground(Color.DARK_GRAY);
        } else {
            for (Item c : itemListToUpgrade) {
                if (upgradedItems.containsKey(c) || inventoryItems.get(c).equals(ItemEquipType.EQUIPPED)) continue;
                JLabel itemIcon = getLogoItem(c.getItemBase().getName());
                JPanel itemContainer = new JPanel();
                JLabel itemName = new JLabel(c.getItemBase().getName());
                JLabel itemDescription = new JLabel(c.getItemBase().getDmg() + " atk");
                JButton upgradeButton = new JButton(UPGRADE);
                upgradeButton.setForeground(Color.WHITE);


                upgradeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        upgradeButton.setForeground(Color.BLUE);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        upgradeButton.setForeground(Color.WHITE);
                    }
                });

                Random random = new Random();

                upgradeButton.addActionListener(e -> {
                    if(upgradeCounter == 0){
                        int die = random.nextInt(100) + 1;
                        JOptionPane pane = null;
                        upgradeCounter = 1;
                        if (die < 20) {
                            upgradeCounter = 0;
                            pane = new JOptionPane(UPGRADE_FAIL+ CHANCE + 18 + TRY_AGAIN);
                            createBlacksmithView(panel);
                        }
                        if (die >= 20 && die <= 50) {
                            Equipment rareItemEq = new RareBonus(c);
                            upgradedItems.put(c, rareItemEq.getDmg());
                            pane = new JOptionPane(ITEM_RECEIVED + rareItemEq.getDmg() + ADDITIONAL_DMG + CHANCE + 30 + "%)");
                            createBlacksmithView(panel);
                        } else if (die >= 51 && die <= 75) {
                            Equipment epicItem = new EpicBonus(c);
                            upgradedItems.put(c, epicItem.getDmg());
                            pane = new JOptionPane(ITEM_RECEIVED + epicItem.getDmg() + ADDITIONAL_DMG + CHANCE + 24 + "%)");
                            createBlacksmithView(panel);
                        } else if (die >= 76 && die <= 90) {
                            Equipment legendaryItem = new LegendaryBonus(c);
                            upgradedItems.put(c, legendaryItem.getDmg());
                            pane = new JOptionPane(ITEM_RECEIVED+ legendaryItem.getDmg() + ADDITIONAL_DMG + CHANCE + 14  + "%");
                            createBlacksmithView(panel);
                        } else if (die >= 91) {
                            Equipment insaneItem = new LegendaryBonus(new RareBonus(c));
                            upgradedItems.put(c, insaneItem.getDmg());
                            pane = new JOptionPane(ITEM_RECEIVED+ insaneItem.getDmg() + ADDITIONAL_DMG + CHANCE + 9 + "%)");
                            createBlacksmithView(panel);
                        }
                        final JDialog d = pane.createDialog((panel), UPGRADE_TITLE);
                        d.setLocation(600,400);
                        d.setVisible(true);
                    } else {
                        JOptionPane pane = new JOptionPane(UPGRADE_INFO);
                        JDialog d = pane.createDialog((panel), UPGRADE_TITLE);
                        d.setLocation(600,400);
                        d.setVisible(true);
                    }
                });



                itemContainer.add(itemIcon);
                itemContainer.add(itemName);
                itemContainer.add(itemDescription);
                itemContainer.add(upgradeButton);
                styleItemInventoryEntry(itemContainer);
                panel.add(itemContainer);
            }
        }
        panel.revalidate();
        panel.repaint();
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

    public void resetStatsAfterReloadCharacter(){
        if(LOST == 1){
            upgradeCounter = 0;
            bonusValue = 0;
            selected = 1;
            activeItems = 0;
            inventoryItems.clear();
            upgradedItems.clear();
            additionalDmg = 0;
            LOST = 2;
        }
    }
}
