package com.dungeonhunters.dungeonhunters.controller;

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

    @Autowired
    MenuController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void createView() {
        int buttonHeight = 40;
        int menuWidth = 200;
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        JPanel menu = new JPanel();
        menu.setBorder(BorderFactory.createLineBorder(Color.RED,2));
        List<Player> players = playerService.getPlayers();
        menu.setLayout(new GridBagLayout());
        menu.setPreferredSize(new Dimension(menuWidth,players.size()*buttonHeight));

        JButton b = new JButton("Stwórz nową postać");
        b.setPreferredSize(new Dimension(menuWidth,buttonHeight));
        menu.add(b);
        for (Player player : players) {
            b = new JButton(player.getName());
            b.setPreferredSize(new Dimension(menuWidth,buttonHeight));
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Player selectedPlayer=null;
                    System.out.println(e.getActionCommand());
                    for(Player p : players){
                        if(player.getName().equals(e.getActionCommand())) selectedPlayer = player;
                    }
                    if(selectedPlayer==null){
                        showPlayerCreation();
                    }else{
                        gameController.setCurrentPlayer(selectedPlayer);
                        gameController.switchToProfileController();
                    }
                }
            });
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
