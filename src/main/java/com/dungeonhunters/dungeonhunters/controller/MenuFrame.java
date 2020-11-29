package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Item;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class MenuFrame extends JFrame {
    public JPanel panel;
    public GameController gameController;
    public final PlayerService playerService;
    public int selected = 1;

    @Autowired
    MenuFrame(PlayerService playerService) {
        this.playerService = playerService;
        this.panel = new JPanel();
    }

    public void createView() {
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
        //panel.setMinimumSize(new Dimension(100, 200));
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 40 && selected != players.size() + 1) selected++;
                if (e.getKeyCode() == 38 && selected != 1) selected--;
                if (e.getKeyCode() == 10) {
                    if (selected == 1) {
                        gameController.clearScreen();
                        showPlayerCreation();
                    } else {
                        gameController.setCurrentPlayer(players.get(selected - 2));
                        gameController.switchToProfileController();
                    }
                }
                refreshColor();
            }
        });
        panel.setFocusable(true);
        gameController.setMainContent(panel);
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
        tf.requestFocus();
        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                tf.requestFocusInWindow();
            }

        });

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
}
