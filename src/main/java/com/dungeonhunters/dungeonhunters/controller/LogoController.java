package com.dungeonhunters.dungeonhunters.controller;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LogoController {

    public static JLabel getLogoPlayer(String logoName){

        File file = null;

        switch (logoName){
            case "Ork":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static\\PlayerLogo\\Ork.jpg");
                break;
            case "Troll":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static\\PlayerLogo\\Troll.jpg");
                break;
            case "Elf":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static\\PlayerLogo\\Elf.jpg");
                break;
            case "Grabarz":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static\\PlayerLogo\\Grabarz.jpg");
                break;
            case "Smok":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static\\PlayerLogo\\Smok.jpg");
                break;
            case "Czlowiek":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static\\PlayerLogo\\Czlowiek.jpg");
                break;
            default:
                System.out.println("Logo not found");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel label = new JLabel(new ImageIcon(image));
        return label;
    }

    public static JLabel getLogoCard(String cardType) {
        File file = null;

        switch (cardType){
            case "Attack":
                file = new File("src\\main\\resources\\Static\\CardImages\\Attack.jpg");
                break;
            case "Block":
                file = new File("src\\main\\resources\\Static\\CardImages\\Block.jpg");
                break;
            case "Miss":
                file = new File("src\\main\\resources\\Static\\CardImages\\Miss.jpg");
                break;
            case "Sleep":
                file = new File("src\\main\\resources\\Static\\CardImages\\Sleep.jpg");
                break;
            case "ReducedDmg":
                file = new File("src\\main\\resources\\Static\\CardImages\\ReducedDmg.jpg");
                break;
            default:
                System.out.println("Logo not found");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel label = new JLabel(new ImageIcon(image));
        return label;
    }
}
