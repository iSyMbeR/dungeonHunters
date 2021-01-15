package com.dungeonhunters.dungeonhunters;

import com.dungeonhunters.dungeonhunters.controller.GameController;
import com.dungeonhunters.dungeonhunters.controller.ProfileController;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.ItemBaseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.dungeonhunters.dungeonhunters.dto.MenuStrings.*;


@Getter
@Setter
public final class Settings {
    private static Settings instance = null;
    private String currentLanguage = "en";

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
        changeLanguage();
    }

    public void changeLanguage(){
        if(currentLanguage.toLowerCase().equals("pl")){
            ENTER_DUNGEON = "Wejdz do dungeonu";
            SHOW_INVENTORY = "Pokaz ekwipunek";
            INVENTORY = "Ekwipunek";
            SHOW_CARDS = "Pokaz karty";
            SHOP = "Sklep";
            EXIT = "Wyjście";
            CHANGE_LANGUAGE = "Zmien język";
            EQUIPPED = "Założono";
            UNEQUIPPED = "Zdjęty";
            RACE = "Rasa";
            DECK = "Układ";
            GOLD = "złoto: ";
            DMG = "obrażenia: ";
            DEF = "obrona: ";
            LEVEL = "Poziom: ";
            ATTACK_DMG = "Obrażenia od ataku: ";
            DEFENSE = "Obrona: ";
            NO_CARDS = "Brak kart";
            REFRESH_ITEMS = "Odswież";
            YOU_HAVE = "Masz";
            ITEMS = "przedmioty";
            NO_ITEMS = "Brak przedmiotów";
            CARDS = "karty";
            CARD = "Karta: ";
            CARD_REQUIRE = "Karta wymaga ";
            USED = " użyto, ";
            ACTIONS_LEFT = " ruchów pozostało.";
            ACTIONS_TO_USE = " ruchów do użycia. Masz";
            ACTIONS_LIMIT_REACHED = " Osiągnieto limit ruchów, 0 ruchów pozostało.";
            DEAL = " zadał ";
            TO = " dla ";
            BUY = "Kup";
            OPPONENT = "Przeciwnik";
            MISSED = " spudłował";
            IS_SLEEPING = " śpi ...";
            RECEIVED = " otrzymał ";
            DAMAGE_FROM = " obrażeń od ";
            DAMAGE_FROM_REDUCED_BY_DEFENSE = " obrażeń (obrażenia zmniejszone przez obronę) od ";
            ATTACK = " attack.";
            IS_DEFENDING = " is defending himself, ";
            YOU_ARE_BLOCKING = "Już blokujesz";
            TURN = "Kolejka ";
            USE = "Użyj ";
            ENDED_STARTED = " zakonczono, rozpoczęto ";
            CHARACTER_NAME = "Wybierz nazwę postaci:";
            CHARACTER_APPEARANCE = "Wybierz wygląd postaci";
            GO_TO_TOWN = "Wróc do miasta";
            YOU_LOST_CHARACTER = "Przegrałes. Twoja postać została usunięta";
            EXIT_MAIN_MENU = "Wyjście do menu głównego";
        } else {
            ENTER_DUNGEON = "Enter Dungeon";
            SHOW_INVENTORY = "Show Inventory";
            INVENTORY = "Inventory";
            SHOW_CARDS = "Show Cards";
            SHOP = "Shop";
            EXIT = "Exit";
            CHANGE_LANGUAGE = "Change Language";
            EQUIPPED = "Equipped";
            UNEQUIPPED = "UnEquipped";
            RACE = "Race";
            DECK = "Deck";
            HP = "hp: ";
            XP = "xp: ";
            LEVEL = "level: ";
            GOLD = "gold: ";
            DMG = "dmg: ";
            ATTACK_DMG = "Attack damage: ";
            DEF = "def: ";
            DEFENSE = "Defense: ";
            NO_CARDS = "No cards";
            REFRESH_ITEMS = "Refresh items";
            YOU_HAVE = "You have";
            ITEMS = "items";
            NO_ITEMS = "No items";
            CARDS = "cards";
            CARD = "Card: ";
            CARD_REQUIRE = "Card require ";
            USED = " used, ";
            ACTIONS_LEFT = " actions left.";
            ACTIONS_TO_USE = " actions to use. You have";
            ACTIONS_LIMIT_REACHED = " Action limit reached, 0 actions left.";
            DEAL = " deal ";
            TO = " to ";
            BUY = "Buy";
            OPPONENT = "Opponent";
            MISSED = " missed";
            IS_SLEEPING = " is sleeping ...";
            RECEIVED = " received ";
            DAMAGE_FROM = " damage from ";
            DAMAGE_FROM_REDUCED_BY_DEFENSE = " damage (damage reduced by defense) from ";
            ATTACK = " attack.";
            IS_DEFENDING = " is defending himself, ";
            YOU_ARE_BLOCKING = "You are already blocking";
            TURN = "Turn ";
            USE = "Use ";
            ENDED_STARTED = " ended, started ";
            CHARACTER_NAME = "Choose character name:";
            CHARACTER_APPEARANCE = "Choose character appearance";
            GO_TO_TOWN = "Go to town";
            YOU_LOST_CHARACTER = "You lost. Your character has been deleted";
            EXIT_MAIN_MENU = "Exit to main menu";
        }

    }

//    public void getSettingsView(JPanel panel){
//        JPanel settingButtons = new JPanel();
//        settingButtons.setPreferredSize(new Dimension(100,50));
//        JPanel contentPanel = new JPanel();
//        settingButtons.setBackground(Color.lightGray);
//        JLabel settingsLabel = new JLabel("Settings");
//
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.removeAll();
//        settingButtons.removeAll();
//        settingButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
//        panel.setBackground(Color.lightGray);
//        //settingButtons.setBackground(Color.lightGray);
//
//        JButton changeCharacterLogo = new JButton("Zmien Logo");
//        JButton changeCharacterName = new JButton("Zmien nazwe");
//        changeCharacterName.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                settingButtons.setBackground(Color.white);
//                contentPanel.setBackground(Color.white);
//                contentPanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
//                changeNameView(contentPanel);
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                super.mouseEntered(e);
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                super.mouseExited(e);
//            }
//        });
//        JButton logout = new JButton("Wyloguj się");
//        JButton deleteAccount = new JButton("Usun konto");
//
//        settingButtons.add(changeCharacterName);
//        settingButtons.add(changeCharacterLogo);
//        settingButtons.add(logout);
//        settingButtons.add(deleteAccount);
//
//        contentPanel.revalidate();
//        contentPanel.repaint();
//
//        settingButtons.revalidate();
//        settingButtons.repaint();
//
//        panel.add(settingsLabel);
//        panel.add(settingButtons);
//        panel.add(contentPanel);
//        panel.revalidate();
//        panel.repaint();
//
//    }
//
//    public void changeNameView(JPanel panel){
//        panel.removeAll();
//        JTextField tf = new JTextField(20);
//        tf.setBorder(BorderFactory.createLineBorder(Color.GREEN,2));
//        tf.setBackground(Color.lightGray);
//        JButton confirmButton = new JButton("Zmien");
//        System.out.println(player.getName() + "Przed");
//        confirmButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                player.setName(tf.getText());
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                super.mouseEntered(e);
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                super.mouseExited(e);
//            }
//        });
//        //panel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        panel.add(tf);
//        panel.add(confirmButton);
//        panel.revalidate();
//        panel.repaint();
//    }


}
