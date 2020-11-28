package com.dungeonhunters.dungeonhunters;

import com.dungeonhunters.dungeonhunters.controller.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class InitController implements CommandLineRunner {
    private final GameController controller;

    @Autowired
    public InitController(GameController controller){
        this.controller = controller;
    }

    @Override
    public void run(String... args) throws Exception {
        EventQueue.invokeLater(() -> controller.setVisible(true));
    }
}
