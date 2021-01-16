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
    public static int counter = 0;
    private String currentLanguage = "en";

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
         // System.out.println(++counter + "instancja klasy settings");
        }
      //System.out.println("ciągle ta sama instancja, nie utworzysz nowej byku");
        return instance;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
        changeLanguage();
    }

    public void changeLanguage(){
        if(currentLanguage.toLowerCase().equals("pl")){
            BLACKSMITH = "Kowal";
            UPGRADE = "Ulepsz";
            TRY_AGAIN = "%) Sprobuj jeszcze raz";
            UPGRADE_FAIL = "Nie udało się ulepszyć :( ";
            CHANCE = "(szansa ";
            ITEM_RECEIVED = "Przedmiot otrzymał ";
            ADDITIONAL_DMG = " dodatkowy atak ";
            UPGRADE_TITLE = "Kuźnia u Harada";
            UPGRADE_INFO = "Mozesz posiadać tylko jeden item z bonusem, idz na wyprawe, aby móc ulepszyc przedmiot jeszcze raz";
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
            ACTIONS_LEFT = " ruchów left.";
            ACTIONS_TO_USE = " ruchów do użycia. Masz";
            ACTIONS_LIMIT_REACHED = " Osiągnieto limit , 0 ruchów left.";
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
            IS_DEFENDING = " broni się, ";
            YOU_ARE_BLOCKING = "Już blokujesz";
            TURN = "Tura ";
            USE = "Użyj ";
            ENDED_STARTED = " zakonczono, rozpoczęto ";
            CHARACTER_NAME = "Wybierz nazwę postaci:";
            CHARACTER_APPEARANCE = "Wybierz wygląd postaci";
            GO_TO_TOWN = "Wróc do miasta";
            YOU_LOST_CHARACTER = "Przegrałes. Twoja postać została usunięta";
            EXIT_MAIN_MENU = "Wyjście do menu głównego";
        } else {
            BLACKSMITH = "Blacksmith";
            UPGRADE = "Upgrade";
            TRY_AGAIN =  "Try again";
            UPGRADE_FAIL = "Upgrade failed :( ";
            CHANCE = "(chance ";
            ITEM_RECEIVED = "Item received ";
            ADDITIONAL_DMG = " additional dmg ";
            UPGRADE_TITLE = "Smithy Info";
            UPGRADE_INFO = "Available only 1 upgraded item, go to dungeon and upgrade item again";
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


}
