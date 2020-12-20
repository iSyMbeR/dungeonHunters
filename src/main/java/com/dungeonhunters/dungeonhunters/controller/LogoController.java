package com.dungeonhunters.dungeonhunters.controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LogoController {

    public static JLabel getLogo(String logoName){

        File file = null;


        switch (logoName){
            case "Ork":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static.Images\\Ork.jpg");
                break;
            case "Troll":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static.Images\\Troll.jpg");
                break;
            case "Elf":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static.Images\\Elf.jpg");
                break;
            case "Grabarz":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static.Images\\Grabarz.jpg");
                break;
            case "Smok":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static.Images\\Smok.jpg");
                break;
            case "Czlowiek":
                file = new File("E:\\dungeonHunters\\dungeonHuntersJFrame\\src\\main\\resources\\Static.Images\\Czlowiek.jpg");
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
