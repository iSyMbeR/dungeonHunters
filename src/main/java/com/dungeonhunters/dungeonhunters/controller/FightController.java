package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.Ansi;
import com.dungeonhunters.dungeonhunters.dto.Fight;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Controller
public class FightController extends JFrame {
    public JPanel panel;
    public GameController gameController;
    public Player player;
    public final EnemyService enemyService;
    public Fight fight;
    public int selected = 1;

    FightController(EnemyService enemyService){
        this.enemyService = enemyService;
    }

    public void createView() {
        panel = new JPanel();
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(2,2));

        JPanel playerPanel = new JPanel();
        JLabel playerLabel = new JLabel(Ansi.player);
        playerPanel.add(playerLabel);

        JPanel enemyPanel = new JPanel();
        JLabel enemyLabel = new JLabel(Ansi.enemy);
        enemyPanel.add(enemyLabel);

        JPanel optionsPanel = new JPanel();
        JLabel attackOption = new JLabel("Attack");
        JLabel defendOption = new JLabel("Defend");
        JLabel useCardOption = new JLabel("Use card");
        optionsPanel.add(attackOption);
        optionsPanel.add(defendOption);
        optionsPanel.add(useCardOption);

        container.add(playerPanel);
        container.add(enemyPanel);
        container.add(optionsPanel);
        optionsPanel.setFocusable(true);
        gameController.setMainContent(container);
        optionsPanel.requestFocusInWindow();
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void refreshColor(JPanel p) {
        Component[] components = p.getComponents();
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