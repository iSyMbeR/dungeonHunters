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

import static com.dungeonhunters.dungeonhunters.controller.MusicController.getMusic;

@Controller

public class ProfileController extends JFrame {
    public JPanel panel;
    public final PlayerService playerService;
    public GameController gameController;
    public Player player;
    public static Map<String, ItemEquipType> equippedItems = new HashMap<>();
    private boolean equipped = false;
    private String tabNames[];
    private int tabDmg[];
    private int activeItems;
    public int selected = 1;
    private final Shop shop;
    private final DeckService deckService;
    private final ItemBaseService itemBaseService;


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
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        topPanel.setPreferredSize(new Dimension(1200,200));
        //topPanel.setBorder(BorderFactory.createLineBorder(Color.black,5));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        bottomPanel.setPreferredSize(new Dimension(1200,600));
        //bottomPanel.setBorder(BorderFactory.createLineBorder(Color.red,5));

        //content panel
        JPanel contentPanel = new JPanel();
        JScrollPane scrollable = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollable.setPreferredSize(new Dimension(1000,600));
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
                createPlayerInventoryView();
            }
        });
        JButton deckButton = new JButton("Show cards");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDeckView();
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
        JPanel selectPanel = new JPanel();
        selectPanel.add(fightButton);
        selectPanel.add(inventoryButton);
        selectPanel.add(deckButton);
        selectPanel.add(shopButton);
        selectPanel.add(exitButton);
        selectPanel.setPreferredSize(new Dimension(200,600));
        styleSelectPanel(selectPanel);




        //info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(400,200));
        //infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        JLabel name = new JLabel(player.getName());
        JLabel exp = new JLabel("Exp: " + player.getExperience());
        JLabel stage = new JLabel("Stage: " + player.getStage());
        JLabel hp = new JLabel("HP: " + player.getCurrentHp() + "/" + player.getHp());
        JLabel gold = new JLabel("GOLD: " + player.getGold());
        JLabel dmg = new JLabel("DMG: " + player.getDmg());
        JLabel def = new JLabel("DEF: " + player.getDef());
        infoPanel.add(name);
        infoPanel.add(exp);
        infoPanel.add(stage);
        infoPanel.add(hp);
        infoPanel.add(gold);
        infoPanel.add(dmg);
        infoPanel.add(def);

        //statistic panel
        JPanel statisticPanel = new JPanel();
        statisticPanel.setPreferredSize(new Dimension(400,200));
        JLabel sl = new JLabel("Statistic panel here");
        statisticPanel.add(sl);
        //statisticPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,3));

        //player panel
        JPanel playerPanel = new JPanel();
        playerPanel.setPreferredSize(new Dimension(400,200));
        JLabel pl = new JLabel("Player panel here");
        playerPanel.add(pl);
        //playerPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));

        topPanel.add(playerPanel);
        topPanel.add(statisticPanel);
        topPanel.add(infoPanel);
        bottomPanel.add(selectPanel);
        bottomPanel.add(scrollable);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        panel.add(topPanel);
        panel.add(bottomPanel);
        gameController.setMainContent(panel);
    }

    private void createAndDisplayShopView(JPanel panel) {
        panel.removeAll();
        JLabel shopName = new JLabel("Shop");
        shopName.setPreferredSize(new Dimension(850,40));
        shopName.setFont(new Font("Arial",Font.BOLD,20));
        JButton refreshItems = new JButton("Refresh items");
        refreshItems.setFocusPainted(false);
        refreshItems.setBackground(null);
        refreshItems.setBorder(null);
        refreshItems.setPreferredSize(new Dimension(100, 40));
        refreshItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shop.refreshItems(player);
                createAndDisplayShopView(panel);

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
        panel.add(shopName);
        panel.add(refreshItems);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        List<ShopItemDto> shopItems = shop.getItems();
        panel.setPreferredSize(new Dimension(1000, (shopItems.size() * 105) + 50));
        for (ShopItemDto c : shopItems) {
            JLabel itemIcon = LogoController.getLogoCard(c.getType().toString());
            JPanel itemContainer = new JPanel();
            JLabel itemName = new JLabel(c.getName());
            JLabel itemDescription = new JLabel(c.getDescription());
            JLabel itemCost = new JLabel( c.getPrice() + " gold");
            JButton buyButton = new JButton("Buy");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shop.buyItem(player, shopItems.indexOf(c));
                }
            });
            if(player.getGold()<c.price) buyButton.setEnabled(false);
            itemContainer.add(itemIcon);
            itemContainer.add(itemName);
            itemContainer.add(itemDescription);
            itemContainer.add(itemCost);
            itemContainer.add(buyButton);
            styleItemShopEntry(itemContainer);
            panel.add(itemContainer);
        }
        panel.revalidate();
        panel.repaint();
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
        itemContainer.getComponent(3).setPreferredSize(new Dimension(200,100));
        JButton buyButton = (JButton)itemContainer.getComponent(4);
        buyButton.setPreferredSize(new Dimension(100,50));
        buyButton.setBorder(BorderFactory.createEmptyBorder(25,0,25,0));
        buyButton.setFocusPainted(false);
        buyButton.setFont(new Font("Arial", Font.BOLD, 25));
        buyButton.setBackground(Color.GRAY);
    }

    private void styleSelectPanel(JPanel selectPanel) {
        selectPanel.setLayout(new FlowLayout());
        for(Component c : selectPanel.getComponents()){
            setButtonStyle((JButton)c);

        }
    }
    private void setButtonStyle(JButton button){
        Color hoveredColor = Color.blue;
        Color normalColor = Color.white;
        button.setBorder(null);
        button.setPreferredSize(new Dimension(150,40));
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

    private void createDeckView() {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0, 5));

        JPanel pName = new JPanel();
        JPanel pType = new JPanel();
        JPanel pDescription = new JPanel();
        pName.setLayout(new BoxLayout(pName, BoxLayout.Y_AXIS));
        pType.setLayout(new BoxLayout(pType, BoxLayout.Y_AXIS));
        pDescription.setLayout(new BoxLayout(pDescription, BoxLayout.Y_AXIS));
        JPanel options = new JPanel();
        JLabel exit = new JLabel("Wyjdź");
        options.add(exit);

        List<Card> cardList = deckService.getDeckById(player.getDeck().getId()).getCardSet();
        if (cardList.size() == 0) {
            JLabel empty = new JLabel("Deck is empty");
            empty.setForeground(Color.pink);
            pName.add(new JLabel(""));
            pName.add(empty);
        } else {
            for (Card c : cardList) {
                JLabel l = new JLabel(c.getName());
                JLabel ld = new JLabel(c.getType() + "");
                JLabel ldescription = new JLabel(c.getDescription());
                pName.add(l);
                pType.add(ld);
                pDescription.add(ldescription);
            }
        }
        createControls(options, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected == 1) createView();
            }
        });
        container.add(new JLabel(""));
        container.add(new JLabel("NAME")).setForeground(Color.BLUE);
        container.add(new JLabel("DESCRIPTION")).setForeground(Color.GRAY);
        container.add(new JLabel("TYPE")).setForeground(Color.MAGENTA);
        container.add(new JLabel(""));

        container.add(new JLabel(""));
        container.add(pName);
        container.add(pDescription);
        container.add(pType);
        container.add(new JLabel(""));
        container.add(new JLabel(""));
        container.add(new JLabel(""));
        container.add(options);
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();
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


    private void createPlayerInventoryView() {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3, 3));
        container.add(new JLabel(""));
        container.add(new JLabel("List of your items"));
        container.add(new JLabel(""));
        JPanel options = new JPanel();
        JPanel itemDmgList = new JPanel();
        JPanel equip = new JPanel();
        JLabel tmp;
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        itemDmgList.setLayout(new BoxLayout(itemDmgList, BoxLayout.Y_AXIS));
        equip.setLayout(new BoxLayout(equip, BoxLayout.Y_AXIS));
        int count = 0;

        Set<Item> playerInventoryItemsList = player.getInventory().getItemList();
        if (playerInventoryItemsList.isEmpty()) {
            container.add(new JLabel(" "));
            container.add(new JLabel(" "));
            container.add(new JLabel("No items")).setForeground(Color.DARK_GRAY);
            container.add(new JLabel(" "));
            container.add(new JLabel(" "));
            container.add(new JLabel(" "));
        } else {
            tmp = new JLabel("NAME");
            tmp.setForeground(Color.BLUE);
            container.add(tmp);

            tmp = new JLabel("DMG");
            tmp.setForeground(Color.RED);
            container.add(tmp);

            tmp = new JLabel("EQUIPPED");
            tmp.setForeground(Color.GRAY);
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
                    tmp.setForeground(Color.darkGray);
                }
                equip.add(tmp);
                count++;
            }
        }
        final int counter = count;
        JLabel changePage = new JLabel();

        options.add(new JLabel("All items"));
        options.add(new JLabel("Back"));
        changePage.add(new JLabel());
        changePage.setLayout(new BoxLayout(changePage, BoxLayout.Y_AXIS));
        createControls(options, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected <= counter) {
                    if (equippedItems.get(tabNames[selected - 1]) == ItemEquipType.NIE && activeItems < 3) {
                        equippedItems.put(tabNames[selected - 1], ItemEquipType.TAK);
                        activeItems++;
                        player.setDmg(player.getDmg() + tabDmg[selected - 1]);
                        playerService.addPlayer(player);
                    } else if(equippedItems.get(tabNames[selected - 1]) == ItemEquipType.TAK){
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

    private void createAllItemsView() {
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
