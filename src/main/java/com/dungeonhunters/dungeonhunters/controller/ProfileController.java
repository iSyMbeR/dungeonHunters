package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.dto.Shop;
import com.dungeonhunters.dungeonhunters.dto.ShopItemDto;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.CardService;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Controller
public class ProfileController extends JFrame {
    public JPanel panel;
    public GameController gameController;
    public Player player;
    public int selected = 1;
    private final Shop shop;
    private final DeckService deckService;
    private final CardService cardService;

    ProfileController(DeckService deckService, CardService cardService, Shop shop){
        this.deckService = deckService;
        this.cardService = cardService;
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
                if(selected == 1) createInventoryView();
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

    private void createInventoryView() {
        shop.refreshItems(player);
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
