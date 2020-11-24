package com.dungeonhunters.dungeonhunters;


import com.dungeonhunters.dungeonhunters.model.*;

import com.dungeonhunters.dungeonhunters.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.concurrent.ThreadLocalRandom;


import static com.dungeonhunters.dungeonhunters.Ansi.*;

@Component
@RequiredArgsConstructor
public class GameController implements CommandLineRunner {

    private final PlayerService playerService;
    private final EnemyService enemyService;
    private final InventoryService inventoryService;
    private final DeckService deckService;
    Player player;



    static void cleanScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    static void pumpText() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public void showMenu() throws IOException {
      //  System.out.println(deckService.getDeckById(1L).getId());





        Scanner scanner = new Scanner(System.in);
        int choiceInt;
        System.out.println("Podaj swoja nazwe");
        String choiceString;
        boolean x = true;


        choiceString = scanner.nextLine();
        if (choiceString.toUpperCase().equals("ADMIN")) {

            while (x) {
                System.out.println(BLUE + "\tHello " + HIGH_INTENSITY + GREEN + "ADMIN" + LOW_INTENSITY);
                System.out.print(BLUE + "\tU CAN DO MORE THAN OTHERS");
                System.out.println(BLACK);
                System.out.println(GREEN + "-------------------------");
                System.out.println(
                                "\t" + CYAN + "1." + BLACK + " Add player\n" +
                                "\t" + CYAN + "2." + BLACK + " Remove player\n" +
                                "\t" + CYAN + "3." + BLACK + " Show Player List\n" +
                                "\t" + CYAN + "4." + BLACK + " Exit");
                System.out.println(GREEN + "-------------------------");
                choiceInt = scanner.nextInt();

                switch (choiceInt) {
                    case 1: {
                        System.out.println("weszlo do 1");
                        scanner.nextLine();
                        System.out.println("Podaj nazwe gracza");
                        Player player = Player.builder()
                                .name(scanner.nextLine())
                                .build();
                        playerService.addPlayer(player);
                        break;
                    }
                    case 2: {
                        System.out.println("Podaj index gracza do usuniecia");
                        List<Player> players = playerService.getPlayers();
                        for (Player player : players) {
                            String s = player.getId() + " " + player.getName();
                            System.out.println(s);
                        }
                        choiceInt = scanner.nextInt();
                        playerService.deletePlayer(players.get(choiceInt - 1));
                        break;
                    }
                    case 3: {
                        System.out.println("weszlo do 4");
                        List<Player> players = playerService.getPlayers();
                        for (Player player : players) {
                            String s = player.getId() + ". " + player.getName();
                            System.out.println(s);
                        }
                        break;
                    }
                    case 4: {
                        System.out.println(RED + "Żegnaj " +GREEN + "ADMIN" + RED + " :)");
                        System.exit(0);
                        break;
                    }
                    default: {
                        System.out.println("jestesmy w defaulcie");
                        return;
                    }
                }
            }
        } else {
            //dodawanie gracza do bazy trzeba potem dodac sprawdzanie czy nie jest juz w bazie
            player = Player.builder()
                    .name(choiceString)
                    .deck(deckService.addDeck(new Deck()))
                    .inventory(inventoryService.addInventory(new Inventory()))
                    .hp(100)
                    .stage(2)
                    .experience(100)
                    .build();
            playerService.addPlayer(player);

            while (x) {
                cleanScreen();
                System.out.println(BLUE + "\tHello " + HIGH_INTENSITY + GREEN + player.getName().toUpperCase() + LOW_INTENSITY +
                        BLUE+"\tXP:"+ GREEN + player.getExperience() +
                        BLUE+"\tITEMS:"+ GREEN + player.getDeck());


                System.out.print(BLACK);
                System.out.println(GREEN + "    -------------------------");
                System.out.println(
                                "\t" + CYAN + "1." + BLACK + " Zawalacz\n" +
                                "\t" + CYAN + "2." + BLACK + " Twoje inventory\n" +
                                "\t" + CYAN + "3." + BLACK + " Twoj deck\n" +
                                "\t" + CYAN + "4." + BLACK + " Zapisz gre\n" +
                                "\t" + CYAN + "5." + BLACK + " Zakoncz gre\n");
                System.out.println(GREEN + "    -------------------------");


                choiceInt = scanner.nextInt();
                switch (choiceInt) {

                    case '1': {
                        System.out.println(BLUE + "\tTHE GAME HAS STARTED, GOOD LUCK!!");
                        cleanScreen();
                        cleanScreen();
                        System.out.println("  ||||||||||||||||||||||||                   ||||||||||||||||||||||||");
                        System.out.println("  ||                    ||                   ||                    ||");
                        System.out.println("  ||      ()(()         ||                   ||    ((________))    ||");
                        System.out.println("  ||    ((()()())       ||                   ||    /  v   v   |    ||");
                        System.out.println("  ||    | O   O |       ||                   ||   /   o   o   |    ||");
                        System.out.println("  ||   (| * u * |)      ||                   ||  *_______     |    ||");
                        System.out.println("  ||    |_______|       ||                   ||   _|    __    |__  ||");
                        System.out.println("  ||   ____| |____      ||                   ||  |    vvvvvv     | ||");
                        System.out.println("  ||  |           |     ||                   ||  |     vvvv      | ||");
                        System.out.println("  ||  |           |     ||                   ||  | |    vv     | | ||");
                        System.out.println("  ||||||||||||||||||||||||                   ||||||||||||||||||||||||");

                        //boolean game = true;
                        //while (game)
                        //increaseExp(20, player);
                        break;
                    }
                    case '2': {
                        System.out.println(inventoryService.getAllItemsFromPlayerInventory(player.getInventory().getId()));
                        break;
                    }
                    case '3': {
                        System.out.println(deckService.getAllCards(player.getDeck().getId()));
                        break;
                    }
                    case '4': {
                        System.out.println(BLUE +"UŻYTO BARDZO SKOMPLIKOWANY ALGORYTM ZAPISYWANIA GRY."+GREEN+" GRA ZOSTALA ZAPISANA");
                        break;
                    }
                    case '5': {
                        System.out.println(RED + "Żegnaj " +GREEN + player.getName() + RED + " :)");
                        System.exit(0);
                        break;
                    }

                }
            }
        }
    }





    public Enemy generateEnemy(int min_level){
        List<Enemy> allEnemies = enemyService.getAllEnemies();
        List<Enemy> validEnemies = new ArrayList<>();
        for(Enemy enemy:allEnemies){
            if(enemy.getMin_level()<=min_level) validEnemies.add(enemy);
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, validEnemies.size());
        return validEnemies.get(randomNum);
    }

    public void increaseExp(int exp, Player playerUpdate){
        player = playerService.updatePlayerExperience(playerUpdate.getId(), playerUpdate.getExperience()+exp);

    }





    @Override
    public void run(String... args) throws Exception {
        //dataBaseFiller.FillerDataBase();

        showMenu();
    }
}
