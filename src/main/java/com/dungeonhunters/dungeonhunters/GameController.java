package com.dungeonhunters.dungeonhunters;


import com.dungeonhunters.dungeonhunters.model.*;

import com.dungeonhunters.dungeonhunters.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


import static com.dungeonhunters.dungeonhunters.Ansi.*;

@Component
@RequiredArgsConstructor
public class GameController implements CommandLineRunner {

    private final PlayerService playerService;
    private final EnemyService enemyService;
    private final InventoryService inventoryService;
    private final CardService cardService;
    private final DeckService deckService;
    private final AreaService areaService;
    private final DeckCardService deckCardService;
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

    public void showMenu() throws IOException, InterruptedException {
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
                        System.out.println(RED + "Żegnaj " + GREEN + "ADMIN" + RED + " :)");
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
                    .hp(100)
                    .experience(100)
                    .build();
            playerService.addPlayer(player);

            while (x) {
                cleanScreen();
                System.out.println(BLUE + "\tHello " + HIGH_INTENSITY + GREEN + player.getName().toUpperCase() + LOW_INTENSITY +
                        BLUE + "\tXP:" + GREEN + player.getExperience() +
                        BLUE + "\tITEMS:" + GREEN + player.getDeck());


                System.out.print(BLACK);
                System.out.println(GREEN + "    -------------------------");
                System.out.println(
                        "\t" + CYAN + "1." + BLACK + " Zawalacz\n" +
                                "\t" + CYAN + "2." + BLACK + " Panel Gracza\n" +
                                "\t" + CYAN + "3." + BLACK + " Zapisz gre\n" +
                                "\t" + CYAN + "4." + BLACK + " Zakoncz gre\n");
                System.out.println(GREEN + "    -------------------------");


                choiceInt = scanner.nextInt();
                switch (choiceInt) {

                    case 1: {
                        cleanScreen();
                        System.out.println(BLUE + "\tTHE GAME HAS STARTED, GOOD LUCK!!");
                        Integer turn = 1;
                        Integer energy = 3;
                        List<Card> currentDeck;
                        Area currentArea = selectArea();
                        Enemy enemy = selectEnemy(player.getExperience());
//                        while(player.getHp()>0 && enemy.getBase_life()>0){
                        System.out.print("\tArea: " + currentArea.getName() + "\n\n");
                        System.out.print("\t" + player.getName());
                        System.out.print(" vs ");
                        System.out.print(enemy.getName() + "\n\n");
                        System.out.println(BLUE + "\tTHE GAME HAS STARTED, GOOD LUCK!!");
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
                        currentDeck = deckService.getAllCards(player.getDeck().getId());
                        for (Card c : currentDeck) {
                            printCard(c, currentDeck.indexOf(c));
                        }
                        int cardIndex = scanner.nextInt();
//                            switch (cardIndex)

//                            useCard(currentDeck.get(cardIndex));
                        turn++;
//                        }

                        break;
                    }

                    case 2: {
                        boolean w = true;
                        while (w) {
                            cleanScreen();
                            System.out.println(BLUE + gamePanel);
                            System.out.println(
                                    "\t" + CYAN + "1." + BLACK + " Pokaz inventory\n" +
                                            "\t" + CYAN + "2." + BLACK + " Pokaz deck\n" +
                                            "\t" + CYAN + "3." + BLACK + " Dodaj karte do decku\n" +
                                            "\t" + CYAN + "4." + BLACK + " Dodaj item do inventory\n" +
                                            "\t" + CYAN + "5." + BLACK + " Wróc");


                            List<Card> allCardsFromBase = cardService.getAllCards();
                            List<Card> allCardsPlayerFromDeck = deckService.getAllCards(player.getDeck().getId());

                            choiceInt = scanner.nextInt();
                            switch (choiceInt) {
                                case 1: {
                                    cleanScreen();
                                    if (inventoryService.getAllItemsFromPlayerInventory(player.getInventory().getId()).isEmpty())
                                        System.out.println(RED + "Twoje inventory jest puste powrót do panelu za 5 sec");
                                    else
                                        System.out.println(inventoryService.getAllItemsFromPlayerInventory(player.getInventory().getId()));

                                    TimeUnit.SECONDS.sleep(5);
                                    break;
                                }
                                case 2: {
                                    cleanScreen();
                                    if (deckService.getAllCards(player.getDeck().getId()).isEmpty())
                                        System.out.println(RED + "Twoj deck jest pusty, powrót do panelu za 5 sec");
                                    else
                                        System.out.println(deckService.getAllCards(player.getDeck().getId()));

                                    TimeUnit.SECONDS.sleep(5);
                                    break;
                                }
                                case 3: {
                                    System.out.println(BLUE + "\tLista dostępnych kart");

                                    //usuwa z listy karty które aktualnie gracz posiada
                                    for (Card c : allCardsPlayerFromDeck) {
                                        if (allCardsFromBase.contains(c))
                                            allCardsFromBase.remove(c);
                                    }

                                    int i = 1;
                                    //wypisuje wszystkie karty z bazy
                                    for (Card c : allCardsFromBase) {
                                        System.out.println(CYAN + i + BLACK + ". " + "Name:" + c.getName() + "\t Dmg:" + c.getDmg() + "\t Defense:" + c.getDefense() + "\t Cose:" + c.getCost());
                                        i++;
                                    }

                                    System.out.println("\n" + BLUE + "\tWybierz karte do swojego decku\n" + BLACK + "\tTwój aktualny deck:" + allCardsPlayerFromDeck);

                                    choiceInt = scanner.nextInt();
//                                    allCardsPlayerFromDeck.add(allCardsSet.get(choiceInt-1));
//                                    System.out.println(allCardsSet.get(choiceInt-1));
                                    deckCardService.addCardToDeck(allCardsFromBase.get(choiceInt-1).getId(), player.getDeck().getId());
                                    System.out.println(deckCardService.getDeckCard());
                                    System.out.println("Poprawnie dodano "+ allCardsFromBase.get(choiceInt -1).getName());
                                    TimeUnit.SECONDS.sleep(2);
                                    break;
                                }
                                case 4: {
                                    break;
                                }
                                case 5: {
                                    w = false;
                                    break;
                                }
                                default:
                                    throw new IllegalStateException("Unexpected value: " + choiceInt);
                            }
                        }
                    }

                    case 3: {
                        System.out.println(BLUE + "UŻYTO BARDZO SKOMPLIKOWANY ALGORYTM ZAPISYWANIA GRY." + GREEN + " GRA ZOSTALA ZAPISANA");
                        break;

                    }
                    case 4: {
                        System.out.println(RED + "Żegnaj " + GREEN + player.getName() + RED + " :)");
                        System.exit(0);
                        break;
                    }

                }
            }
        }
    }

    //    public void useCard(int index){
//        deckService.deleteCardFromDeck(index);
//    }
    public void printCard(Card card, int index) {
        String cardText = cardView;
        System.out.println("1");
        String space = "";
        cardText = cardText.replace("1", Integer.toString(card.getCost()));
        space = (card.getDmg() > 9) ? "" : " ";
        cardText = cardText.replace("2", space + Integer.toString(card.getDmg()));
        space = (card.getDefense() > 9) ? "" : " ";
        cardText = cardText.replace("3", space + Integer.toString(card.getDefense()));
        cardText = cardText.replace("4", Integer.toString(index));
        System.out.println(cardText);
    }

    public Area selectArea() {
        List<Area> areas = areaService.getAllAreas();
        Random random = new Random();
        return areas.get(random.nextInt(areas.size()));
    }

    public Enemy selectEnemy(int min_level) {
        List<Enemy> allEnemies = enemyService.getAllEnemies();
        List<Enemy> validEnemies = new ArrayList<>();
        for (Enemy enemy : allEnemies) {
            if (enemy.getMin_level() <= min_level) validEnemies.add(enemy);
        }
        if (validEnemies.size() == 0) System.out.println("Brak przeciwników");
        ;
        Random random = new Random();
        return validEnemies.get(random.nextInt(validEnemies.size()));
    }

    public void increaseExp(int exp, Player playerUpdate) {
        player = playerService.updatePlayerExperience(playerUpdate.getId(), playerUpdate.getExperience() + exp);

    }


    @Override
    public void run(String... args) throws Exception {
        showMenu();

    }
}
