package com.dungeonhunters.dungeonhunters;


import com.dungeonhunters.dungeonhunters.model.*;

import com.dungeonhunters.dungeonhunters.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
    private final CardService cardService;
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

    public void showMenu() {
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
                        System.out.println("Weszlo do 3");
                        x = false;
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
                    .deck(deckService.getDeckById(1L))
                    .inventory(inventoryService.addInventory(new Inventory()))
                    .stage(2D)
                    .experience(100D)
                    .build();
            playerService.addPlayer(player);
            increaseExp(20, player);
            while (x) {
                cleanScreen();
                System.out.println(BLUE + "\tHello " + HIGH_INTENSITY + GREEN + player.getName().toUpperCase() + LOW_INTENSITY +
                        BLUE+"\tXP:"+ GREEN +player.getExperience() +
                        BLUE+"\tITEMS:"+ GREEN + player.getDeck());


                System.out.print(BLACK);
                System.out.println(GREEN + "    -------------------------");
                System.out.println(
                                "\t" + CYAN + "1." + BLACK + " Zawalacz\n" +
                                "\t" + CYAN + "2." + BLACK + " Twoje inventory\n" +
                                "\t" + CYAN + "3." + BLACK + " Twoj deck\n" +
                                "\t" + CYAN + "4." + BLACK + " Zapisz gre\n" +
                                "\t" + CYAN + "5." + BLACK + " Wczytaj gre");
                System.out.println(GREEN + "    -------------------------");


                choiceInt = scanner.nextInt();
                switch (choiceInt) {

                    case 1: {
                        System.out.println(BLUE + "\tTHE GAME HAS STARTED, GOOD LUCK!!");
                        cleanScreen();
                        Integer turn = 1;
                        Enemy enemy = generateEnemy(player.getExperience());
//                        while(player.getHealth()>0 && enemy.getBase_life()>0){
                            System.out.println("Player vs Enemy");
                            System.out.println("\n\n\n");
                            deckService.getAllCards(player.getDeck().getId());
//                        }

                        break;
                    }
                    case 2: {
                        System.out.println(playerService.getPlayerById(player.getId()));
                    }
                    case 3:
                    case 4:
                    case 5:
                }
            }
        }
    }
    public Enemy generateEnemy(Double min_level){
        List<Enemy> allEnemies = enemyService.getAllEnemies();
        List<Enemy> validEnemies = new ArrayList<>();
        for(Enemy enemy:allEnemies){
            if(enemy.getMin_level()<=min_level) validEnemies.add(enemy);
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, validEnemies.size());
        return validEnemies.get(randomNum);
    }
    public void increaseExp(int exp, Player player){
        playerService.updatePlayerExperience(player.getId(), player.getExperience()+exp);
    }


    @Override
    public void run(String... args) throws Exception {
        //dataBaseFiller.FillerDataBase();

        showMenu();
    }
}
