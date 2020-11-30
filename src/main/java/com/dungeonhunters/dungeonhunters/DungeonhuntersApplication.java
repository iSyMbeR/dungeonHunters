package com.dungeonhunters.dungeonhunters;


import com.dungeonhunters.dungeonhunters.controller.GameController;
import com.dungeonhunters.dungeonhunters.factory.AbstractCardActionFactory;
import com.dungeonhunters.dungeonhunters.factory.CardActionStrategyFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication
public class DungeonhuntersApplication extends JFrame {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DungeonhuntersApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}