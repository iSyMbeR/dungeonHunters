package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.dto.Shop;
import com.dungeonhunters.dungeonhunters.dto.ShopItemDto;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Item;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.CardService;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.ItemService;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

@Controller
public class ProfileController extends JFrame {
    public JPanel panel;
    public GameController gameController;
    public Player player;
    public int selected = 1;
    private final Shop shop;
    private final DeckService deckService;
    private final CardService cardService;
    private final ItemService itemService;

    ProfileController(DeckService deckService, CardService cardService, Shop shop,ItemService itemService){
        this.deckService = deckService;
        this.cardService = cardService;
        this.itemService = itemService;
        this.shop = shop;
    }
    public void createView(){
        panel = new JPanel();
        JPanel infoPanel = new JPanel();
        JLabel name = new JLabel(player.getName());
        JLabel exp = new JLabel("Exp: "+player.getExperience());
        JLabel hp = new JLabel("HP: " + player.getHp());
        infoPanel.add(name);
        infoPanel.add(exp);
        infoPanel.add(hp);
        JLabel inventoryLabel = new JLabel("Pokaż ekwipunek");
        JLabel deckLabel = new JLabel("Pokaż karty");
        JLabel addCardLabel = new JLabel("Sklep");
        JLabel exitLabel = new JLabel("Wyjdź");
        JPanel selectPanel = new JPanel();
        selectPanel.add(inventoryLabel);
        selectPanel.add(deckLabel);
        selectPanel.add(addCardLabel);
        selectPanel.add(exitLabel);
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
        createControls(selectPanel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == 1) createPlayerInventoryView();
                if(selected == 2) createDeckView();
                if(selected == 3) createShopView();
                if(selected == 4) exitGame();
            }
        });
        panel.setLayout(new GridLayout(2,1));
        panel.add(infoPanel);
        panel.add(selectPanel);
        selectPanel.setFocusable(true);
        gameController.setMainContent(panel);
        selectPanel.requestFocusInWindow();
    }

    private void exitGame() {
        System.exit(0);
    }

    private void createDeckView() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel p = new JPanel();
        JPanel options = new JPanel();
        JLabel exit = new JLabel("Wyjdź");
        options.add(exit);
        List<Card> cardList = deckService.getDeckById(player.getDeck().getId()).getCardSet();
        if(cardList.size()==0){
            JLabel empty = new JLabel("W Twoim decku nie ma żadnych kart");
            p.add(empty);
        }else{
            for(Card c : cardList ){
                JLabel l = new JLabel(c.getName()+" "+c.getType());
                p.add(l);
            }
        }
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        createControls(options,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == 1) createView();
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
        for(ShopItemDto c : shopItems){
            JLabel l = new JLabel( c.getName() + "  cost: "+c.getPrice()+" gold");
            options.add(l);
        }
        options.add(exit);
        createControls(options,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == options.getComponentCount()) {
                    createView();
                }else{
                    shop.buyItem(player,selected-1);
                    createShopView();
                }
            }
        });
        options.setLayout(new BoxLayout(options,BoxLayout.Y_AXIS));
        container.add(options);
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();

    }

    private void createPlayerInventoryView() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setLayout(new GridLayout(0,3));
        JPanel options = new JPanel();


        Set<Item> playerInventoryItemsList = player.getInventory().getItemList();
        container.add(new JLabel(" "));
        container.add(new JLabel("Your list of items"));
        container.add(new JLabel(" "));

        if (playerInventoryItemsList.isEmpty()) container.add(new JLabel("U've 0 items"));
        else {
            container.add(new JLabel("NAZWA"));
            container.add(new JLabel(" "));
            container.add(new JLabel("DMG"));
            for (Item c : playerInventoryItemsList) {
                container.add(new JLabel(c.getName()));
                container.add(new JLabel(" "));
                container.add(new JLabel("" + c.getItemBase().getDmg()));
            }
        }
        JLabel exit = new JLabel("Back");
        JLabel allItems = new JLabel("Show all items");
        options.add(allItems);
        options.add(exit);
        createControls(options,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == 1) {
                    createAllItemsView();
                }
                else if (selected == 2){
                    createView();
                }else{
                    createPlayerInventoryView();
                }
            }
        });
        options.setLayout(new BoxLayout(options,BoxLayout.Y_AXIS));
        container.add(options);
        options.setFocusable(true);
        gameController.setMainContent(container);
        options.requestFocusInWindow();
    }


    private void createAllItemsView() {
        shop.refreshItems(player);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setLayout(new GridLayout(0,3));
        JPanel options = new JPanel();
        JLabel exit = new JLabel("Back");
        JLabel name ;
        JLabel dmg ;
        List<Item> listOfItemsFromBase =itemService.getItems();
        container.add(new JLabel(" "));
        container.add(new JLabel("List of all items to get"));
        container.add(new JLabel(" "));

        container.add(new JLabel("NAZWA"));
        container.add(new JLabel(" "));
        container.add(new JLabel("DMG"));
        for(Item c : listOfItemsFromBase){
            name = new JLabel(c.getName());
            dmg  = new JLabel(""+c.getItemBase().getDmg());
            name.setForeground(Color.BLUE);
            dmg.setForeground(Color.RED);
            container.add(name);
            container.add(new JLabel(" "));
            container.add(dmg);
        }
        options.add(exit);
        createControls(options,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == options.getComponentCount()) {
                    createPlayerInventoryView();
                }else{
                    createAllItemsView();
                }
            }
        });
        options.setLayout(new BoxLayout(options,BoxLayout.Y_AXIS));
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
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void createControls(JPanel p, AbstractAction action){
        selected = 1;
        refreshColor(p);
        Action incrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected < p.getComponentCount()) selected++;
                refreshColor(p);
            }
        };
        Action decrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected != 1) selected--;
                refreshColor(p);
            }
        };
        p.getInputMap().put(KeyStroke.getKeyStroke("UP"),"pressedUp");
        p.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"pressedDown");
        p.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressedEnter");
        p.getActionMap().put("pressedUp",decrementSelection);
        p.getActionMap().put("pressedDown",incrementSelection);
        p.getActionMap().put("pressedEnter",action);
    }
}
