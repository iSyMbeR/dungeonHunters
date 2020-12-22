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
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Ork.png");
                break;
            case "Troll":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Troll.png");
                break;
            case "Elf":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Elf.png");
                break;
            case "Grabarz":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Grabarz.png");
                break;
            case "Smok":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Smok.png");
                break;
            case "Czlowiek":
                file = new File("src\\main\\resources\\Static\\PlayerLogo\\Czlowiek.png");
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

    public static JLabel getLogoEnemy(String enemyName){

        File file = null;

        switch (enemyName){
            case "Cieslak":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Cieslak.png");
                break;
            case "Demon":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Demon.png");
                break;
            case "Golf III gaz":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Golf III gaz.png");
                break;
            case "Gryf":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Gryf.png");
                break;
            case "Hagrid":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Hagrid.png");
                break;
            case "Korwin Klejn":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Korwin Klejn.png");
                break;
            case "Malfoj":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Malfoj.png");
                break;
            case "Najman":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Najman2.png");
                break;
            case "Orshabaal":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Orshabaal.png");
                break;
            case "Passat 1.8 tdi":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Passat 1.8 tdi.png");
                break;
            case "Ron":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Ron.png");
                break;
            case "Rudy":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Rudy.png");
                break;
            case "Stonoga":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Stonoga.png");
                break;
            case "Zgredek":
                file = new File("src\\main\\resources\\Static\\EnemyLogo\\Zgredek.png");
                break;
            default:
                System.out.println(enemyName + " logo not found");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(enemyName);
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
            case "Crystal-small":
                file = new File("src\\main\\resources\\Static\\CardImages\\Crystal-small.png");
                break;
            case "Gold":
                file = new File("src\\main\\resources\\Static\\CardImages\\Gold.png");
                break;
            case "Health":
                file = new File("src\\main\\resources\\Static\\CardImages\\Health.png");
                break;
            default:
                System.out.println("Logo not found");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(cardType);
        }
        JLabel label = new JLabel(new ImageIcon(image));
        return label;
    }
    public static JButton getButtonWithIcon(String iconName){
        File file = new File("src\\main\\resources\\Static\\Icons\\"+iconName+".png");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton button = new JButton(new ImageIcon(image));
        return button;
    }
    public static JLabel getLogoItem(String logoName){
        File file = null;
        System.out.println("weszlo "+ logoName);
        switch (logoName.toLowerCase()){
            case "amulet charm":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Amulet charm.jpg");
                break;
            case "lightning charm":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Lightning charm.jpg");
                break;
            case "none charm":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\None charm.jpg");
                break;
            case "axe":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Axe.jpg");
                break;
            case "boots":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Boots.jpg");
                break;
            case "stiletto":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Stiletto.png");
                break;
            case "dragon shield":
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Dragon shield.png");
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
                file = new File("src\\main\\resources\\Static\\ItemsLogo\\Steel sword.png");
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
            System.out.println(logoName);
        }
        JLabel label = new JLabel(new ImageIcon(image));
        return label;

    }
}
