package com.dungeonhunters.dungeonhunters;

import com.dungeonhunters.dungeonhunters.model.Player;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import static com.dungeonhunters.dungeonhunters.Ansi.*;

@Component
@RequiredArgsConstructor
public class GameController implements CommandLineRunner {
    private final PlayerService playerService;


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
        Scanner scanner = new Scanner(System.in);
        int choiceInt;
        System.out.println("Podaj swoja nazwe");
        String choiceString;
        boolean x = true;


        choiceString = scanner.nextLine();
        if (choiceString.toUpperCase().equals("ADMIN")) {

            while (x) {
                cleanScreen();
                System.out.println(BLUE + "\tHello " + HIGH_INTENSITY + GREEN + "ADMIN" + LOW_INTENSITY);
                System.out.print(BLUE + "\tU CAN DO MORE THAN OTHERS");
                System.out.print(BLACK);
                System.out.println(GREEN + "-------------------------");
                System.out.println(
                                "\t" + CYAN + "1." + BLACK + " Add player\n" +
                                "\t" + CYAN + "2." + BLACK + " Remove player\n" +
                                "\t" + CYAN + "3." + BLACK + " Exit\n" +
                                "\t" + CYAN + "4." + BLACK + " Show Player List");
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
                        System.out.println("weszlo do 2");
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
                        System.out.println("Weszlo do 3");
                        x = false;
                        return;
                    }
                    case 4: {
                        System.out.println("weszlo do 4");
                        List<Player> players = playerService.getPlayers();
                        for (Player player : players) {
                            String s = player.getId() + ". " + player.getName();
                            System.out.println(s);
                        }
                        break;
                    }
                    default: {
                        System.out.println("jestesmy w defaulcie");
                        return;
                    }
                }

                return;
            }
        } else {
            //dodawanie gracza do bazy trzeba potem dodac sprawdzanie czy nie jest juz w bazie
            Player player = Player.builder()
                    .name(choiceString)
                    .build();
            playerService.addPlayer(player);
            while (x) {
                cleanScreen();
                System.out.println(BLUE + "\tHello " + HIGH_INTENSITY + GREEN + player.getName().toUpperCase() + LOW_INTENSITY +
                        BLUE+"\tXP:"+ GREEN +player.getExperience() +
                        BLUE+"\tITEMS:"+ GREEN + player.getDeck());


                System.out.print(BLACK);
                System.out.println(GREEN + "    -------------------------");
                System.out.println(
                                "\t" + CYAN + "1." + BLACK + " Zawalacz\n" +
                                "\t" + CYAN + "2." + BLACK + " \n" +
                                "\t" + CYAN + "3." + BLACK + " Twoj deck\n" +
                                "\t" + CYAN + "4." + BLACK + " x3\n" +
                                "\t" + CYAN + "5." + BLACK + " x4");
                System.out.println(GREEN + "    -------------------------");


                choiceInt = scanner.nextInt();
                switch (choiceInt) {

                    case 1: {
                        System.out.println(BLUE + "\tTHE GAME HAS STARTED, GOOD LUCK!!");
                        cleanScreen();
                        cleanScreen();

                        //boolean game = true;
                        //while (game)
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void run(String... args) throws Exception {
        showMenu();
    }
}
