package com.dungeonhunters.dungeonhunters.controller;


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
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Ork.jpg");
                break;
            case "Troll":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Troll.jpg");
                break;
            case "Elf":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Elf.jpg");
                break;
            case "Grabarz":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Grabarz.jpg");
                break;
            case "Smok":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Smok.jpg");
                break;
            case "Czlowiek":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Czlowiek.jpg");
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
            case "Crystal":
                file = new File("src\\main\\resources\\Static\\CardImages\\Crystal.png");
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

    public static JLabel getLogoItem(String logoName){
        File file = null;
        System.out.println("weszlo "+ logoName);
        switch (logoName.toLowerCase()){
            case "amulet charm":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Amulet charm.jpg");
                break;
            case "axe":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Axe.jpg");
                break;
            case "boots":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Boots.jpg");
                break;
            case "stiletto":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\stiletto.jpg");
                break;
            case "dragon shield":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Dragon shield.jpg");
                break;
            case "silver armor":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Silver armor.jpg");
                break;
            case "silver helmet":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Silver helmet.jpg");
                break;
            case "silver panths":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Silver pants.jpg");
                break;
            case "silver sword":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Silver sword.jpg");
                break;
            case "steel armor":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Steel armor.jpg");
                break;
            case "steel helmet":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Steel helmet.jpg");
                break;
            case "steel pants":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Steel pants.jpg");
                break;
            case "steel sword":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Steel sword.jpg");
                break;
            case "wooden armor":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Wooden armor.jpg");
                break;
            case "wooden helmet":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Wooden helmet.jpg");
                break;
            case "wooden pants":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Wooden pants.jpg");
                break;
            case "wooden sword":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Wooden sword.jpg");
                break;
            default:
                System.out.println("Logo not found");
        }
        System.out.println("Wyszlo z switcha" +" kk");
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
