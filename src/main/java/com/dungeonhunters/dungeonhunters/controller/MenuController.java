package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Item;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

@Controller
public class MenuController extends JFrame {
    public JPanel panel;
    public GameController gameController;
    public final PlayerService playerService;
    public int selected = 1;

    @Autowired
    MenuController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void createView() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        List<Player> players = playerService.getPlayers();
        JLabel l = new JLabel("Stwórz nową postać");
        setLabelStyle(l);
        l.setForeground(Color.red);
        panel.add(l);
        int iter = 2;
        for (Player player : players) {
            l = new JLabel(player.getName());
            setLabelStyle(l);
            if (iter == selected) {
                l.setForeground(Color.red);

            }
            ;
            panel.add(l);
            iter++;
        }

        createControls(panel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == 1) {
                    showPlayerCreation();
                }else{
                    gameController.setCurrentPlayer(players.get(selected - 2));
                    gameController.switchToProfileController();
                }
            }
        });
        panel.setFocusable(true);
        gameController.setMainContent(panel);
        panel.requestFocusInWindow();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void refreshColor() {
        Component[] components = panel.getComponents();
        for (Component c : components) {
            c.setForeground(Color.BLACK);
        }
        panel.getComponent(selected - 1).setForeground(Color.red);
    }

    public void setLabelStyle(JLabel label) {
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
//        label.setPreferredSize(new Dimension(100,30));
//        label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    private void setPanel(JPanel panel) {
        this.panel = panel;
    }

    private void showPlayerCreation() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        JLabel l = new JLabel("Podaj nazwę postaci: ");
        JTextField tf = new JTextField(20);
        tf.setBorder(null);
        tf.setBackground(null);
        tf.addActionListener(e -> {
            String name = tf.getText();
            List<Player> players = playerService.getPlayers();
            boolean exists = false;
            int iter = 0;
            Player player;
            for (Player p : players) {
                if (p.getName().equals(name)) {
                    exists = true;
                    break;
                }
                iter++;
            }
            if (exists) {
                player = players.get(iter);
            } else {
                player = gameController.createPlayer(
                        name
                );
            }
            gameController.setCurrentPlayer(player);
            gameController.switchToProfileController();
        });
        panel.add(l);
        panel.add(tf);
        gameController.setMainContent(panel);
        tf.requestFocusInWindow();

    }
    public void createControls(JPanel p, AbstractAction action) {
        selected = 1;
        refreshColor();
        Action incrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected < p.getComponentCount()) selected++;
                refreshColor();
            }
        };
        Action decrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected != 1) selected--;
                refreshColor();
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
