package com.dungeonhunters.dungeonhunters;

import com.dungeonhunters.dungeonhunters.model.Player;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class GameController implements CommandLineRunner {
    private final PlayerService playerService;

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choiceInt;
        boolean x = true;

        while (x) {
            System.out.println("-------------------------");
            System.out.println("\t1. Add player\n" +
                    "\t2. Remove player\n" +
                    "\t3. Exit\n" +
                    "\t4. Show Player List");
            System.out.println("-------------------------");
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
                    playerService.deletePlayer(players.get(choiceInt-1));
                    break;
                }
                case 3: {
                    System.out.println("Weszlo do 3");
                    x = false;
                    break;
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
                default:
                {
                    System.out.println("jestesmy w defaulcie");
                }
            }
        }
        return;

    }
    @Override
    public void run(String... args) throws Exception {
        showMenu();
    }
}
