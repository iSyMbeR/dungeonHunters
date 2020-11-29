package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.Ansi;
import com.dungeonhunters.dungeonhunters.dto.Fight;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import org.springframework.stereotype.Controller;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Controller
public class FightController extends JFrame {
    public GameController gameController;
    public Player player;
    public final EnemyService enemyService;
    private final DeckService deckService;
    public final Fight fight;
    public int selected = 1;
    public JPanel optionsPanel;
    public JPanel cardPanel;

    FightController(EnemyService enemyService, DeckService deckService, Fight fight){
        this.enemyService = enemyService;
        this.deckService = deckService;
        this.fight = fight;
    }

    public void createView() {
        generateEnemy();
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(2,2));

        JPanel playerPanel = new JPanel();
        JLabel playerLabel = new JLabel("Player");
        playerPanel.add(playerLabel);

        JPanel enemyPanel = new JPanel();
        JLabel enemyLabel = new JLabel("Enemy");
        enemyPanel.add(enemyLabel);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel,BoxLayout.Y_AXIS));
        JLabel attackOption = new JLabel("Attack");
        JLabel defendOption = new JLabel("Defend");
        JLabel useCardOption = new JLabel("Use card");
        optionsPanel.add(attackOption);
        optionsPanel.add(defendOption);
        optionsPanel.add(useCardOption);

        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel,BoxLayout.Y_AXIS));
        List<Card> cardList = deckService.getDeckById(player.getDeck().getId()).getCardSet();
        for(Card c : cardList){
            JLabel name = new JLabel(c.getName()+" dmg: "+c.getDmg()+" def: "+c.getDefense());
            cardPanel.add(name);
        }
        cardPanel.add(new JLabel("I choose nothing"));
        cardPanel.setFocusable(true);
        createControls(cardPanel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected!=cardPanel.getComponentCount())fight.useCard(cardList.get(selected-1));
                switchControlToOptionsMenu();
            }
        });
        container.add(playerPanel);
        container.add(enemyPanel);
        container.add(optionsPanel);
        container.add(cardPanel);
        createControls(optionsPanel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == 1) attack();
                if(selected == 2) defend();
                if(selected == 3) switchControlToCardMenu();
            }
        });
        optionsPanel.setFocusable(true);
        gameController.setMainContent(container);
        optionsPanel.requestFocusInWindow();
    }

    private void generateEnemy() {
        fight.createEnemy();
    }

    public void attack(){
        fight.playerAttack();
    }
    public void defend(){
        fight.playerDefend();
    }
    public void switchControlToCardMenu(){
        selected = 1;
        refreshColor(cardPanel);
        cardPanel.requestFocusInWindow();
    }
    public void switchControlToOptionsMenu(){
        selected = 1;
        refreshColor(optionsPanel);
        optionsPanel.requestFocusInWindow();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void refreshColor(JPanel p) {
        Component[] components = optionsPanel.getComponents();
        for (Component c : components) {
            c.setForeground(Color.BLACK);
        }
        components = cardPanel.getComponents();
        for (Component c : components) {
            c.setForeground(Color.BLACK);
        }
        p.getComponent(selected - 1).setForeground(Color.red);
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