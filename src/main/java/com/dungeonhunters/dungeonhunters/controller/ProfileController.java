package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.model.Player;
import net.bytebuddy.implementation.bind.ParameterLengthResolver;
import org.springframework.stereotype.Controller;

import javax.swing.*;

@Controller
public class ProfileController extends JFrame {
    public JPanel panel;
    public GameController gameController;
    public Player player;
    ProfileController(){
        panel = new JPanel();
    }
    public void createView(){
        JLabel name = new JLabel(player.getName());
        JLabel exp = new JLabel("Exp: "+player.getExperience());
        JLabel hp = new JLabel("HP: " + player.getHp());
        panel.add(name);
        panel.add(exp);
        panel.add(hp);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        this.setContentPane(panel);
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
