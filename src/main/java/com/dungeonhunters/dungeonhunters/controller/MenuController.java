package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

@Controller
public class MenuController extends JFrame {
    public JPanel panel;
    public GameController gameController;
    public final PlayerService playerService;

    @Autowired
    MenuController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void createView() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JPanel menu = new JPanel();
        List<Player> players = playerService.getPlayers();
        menu.setLayout(new FlowLayout());
        menu.setPreferredSize(new Dimension(200,600));
        JButton b = new JButton("Stwórz nową postać");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlayerCreation();
            }
        });
        setButtonStyle(b, Color.LIGHT_GRAY);
        menu.add(b);
        for (Player player : players) {
            b = new JButton(player.getName());
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        gameController.setCurrentPlayer(player);
                        gameController.switchToProfileController();
                }
            });
            setButtonStyle(b, Color.white);
            menu.add(b);
        }
        menu.setFocusable(true);
        panel.add(menu);
        gameController.setMainContent(panel);
        panel.requestFocusInWindow();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void setPanel(JPanel panel) {
        this.panel = panel;
    }

    private void setButtonStyle(JButton button, Color color){
        Color hoveredColor = Color.red;
        button.setBorder(null);
        button.setPreferredSize(new Dimension(200,40));
        button.setBackground(color);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoveredColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
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
}
