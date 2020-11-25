package com.dungeonhunters.dungeonhunters;


import com.dungeonhunters.dungeonhunters.model.*;

import com.dungeonhunters.dungeonhunters.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import javax.persistence.criteria.CriteriaBuilder;
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

            player = createPlayer(
                    choiceString,
                    100,
                    1,
                    0,
                    createDeck(new ArrayList<>()),
                    createInventory(new HashSet<>())
            );
            addBasicCardsToDeck(player.getDeck());
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
                        int turn = 1;
                        Integer energy = 3;
                        int playerDefense = 0;
                        boolean victory=false;
                        Enemy enemy = selectEnemy();
                        List<Card> currentDeck = new ArrayList<Card>();
                        Area currentArea = selectArea();

                        String fightView;
                        while (player.getHp() > 0 && enemy.getBase_life() > 0) {
                            System.out.print("\tArea: " + currentArea.getName() + "\n\n");
                            System.out.print("\t" + player.getName());
                            System.out.print(" vs ");
                            System.out.print(enemy.getName()+"\n\n");
                            currentDeck = player.getDeck().getCardSet();
                            int index;
                            int cardIndex = -1;
                            while (cardIndex != '0') {
                                fightView = updateView(player,enemy, turn);
                                System.out.println(fightView);
                                index=1;
                                for(Card c: currentDeck){
                                    printCard(c, index++);
                                }
                                System.out.println("\t[0] Zakończ turę");
                                cardIndex = scanner.nextInt();
                                if (cardIndex == 0) break;
                                if (cardIndex <= currentDeck.size()) {
                                    Card c = currentDeck.get(cardIndex-1);
                                    enemy.setBase_life(enemy.getBase_life() - c.getDmg());
                                    playerDefense+=c.getDefense();
                                    deleteCardFromDeck(player.getDeck(),c);
                                }
                            }
                            if(enemy.getBase_life()<=0){
                                victory = true;
                                break;
                            }
                            player.setHp(player.getHp() - enemy.getDmg());
                            if (player.getHp()<=0) victory=false;
                            turn++;
                        }
                        printAfterBattleScreen(victory);
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


//                            List<Card> allCardsFromBase = cardService.getAllCards();
//                            List<Card> allCardsPlayerFromDeck = deckCardService.getAllCardsFromDeck(player.getDeck().getId());

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
                                    if (player.getDeck().getCardSet().isEmpty())
                                        System.out.println(RED + "Twoj deck jest pusty, powrót do panelu za 2 sec");
                                    else{
                                        List<Card> cards = player.getDeck().getCardSet();
                                        printSet(cards);
                                    }
                                    TimeUnit.SECONDS.sleep(2);
                                    break;
                                }
                                case 3: {
                                    int addCartFromIndex = -1;
                                    while(true){
                                        System.out.println(BLUE + "\tLista dostępnych kart");
                                        List<Card> allCards = cardService.getAllCards();
                                        List<Card> allPlayerCards = player.getDeck().getCardSet();
                                        for (Card c : allPlayerCards) {
                                            allCards.remove(c);
                                        }
                                        int i = 1;
                                        printSet(allCards);
                                        System.out.println("\n" + BLUE + "\tWybierz karte do swojego decku\n" + BLACK + "\tTwój aktualny deck:" );
                                        printSet(player.getDeck().getCardSet());
                                        addCartFromIndex = scanner.nextInt();
                                        if(addCartFromIndex==0) break;
                                        addCardToDeck(player.getDeck(),allCards.get(addCartFromIndex-1));
                                        System.out.println("Wybierz numer karty lub 0 aby wyjść");
                                    }
                                    break;
                                }
                                case 4: {
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
                        System.out.println(RED + "Żegnaj " + GREEN + "ADMIN" + RED + " :)");
                        System.exit(0);
                        break;
                    }
                }
            }
        }
    }
    public void printSet(List<Card> cards){
        int iter = 1;
        for(Card c : cards ){
            System.out.printf(CYAN + "%d."+ BLACK +" %-20s DMG: %-3d DEF: %-3d COST: %-3d\n",iter++,c.getName(),c.getDmg(),c.getDefense(),c.getCost());

        }
    }
    public Card createCard(String name, String type, int dmg, int cost, int def ){
        Card card = Card.builder()
                .cost(cost)
                .defense(def)
                .dmg(dmg)
                .name(name)
                .type(type)
                .build();
        cardService.addCard(card);
        return card;
    }
    public void addCardToDeck(Deck deck, Card card){
        List<Card> set = deck.getCardSet();
        set.add(card);
        deck.setCardSet(set);
        deckService.addDeck(deck);
    }
    public void addBasicCardsToDeck(Deck deck){
        List<Card> allCards = cardService.getAllCards();
        List<Card> basicCards = new ArrayList<>();
        for(Card c:allCards){
            if(c.getType().equals("basic2")) basicCards.add(c);
        }
        deck.setCardSet(basicCards);
        deckService.addDeck(deck);

    }
    public Deck createDeck(List<Card> cardSet){
        Deck deck = Deck.builder()
                .cardSet(cardSet)
                .build();
        deckService.addDeck(deck);
        return deck;
    }
    public Player createPlayer(String name, int hp, int stage, int exp, Deck deck, Inventory inv){
        Player player = Player.builder()
                .name(name)
                .hp(hp)
                .stage(stage)
                .experience(exp)
                .deck(deck)
                .inventory(inv)
                .build();
        playerService.addPlayer(player);
        return player;
    }
    public Inventory createInventory(Set<Item> itemList){
        Inventory inventory = Inventory.builder()
                .itemList(itemList)
                .build();
        inventoryService.addInventory(inventory);
        return inventory;
    }
    public void populateDatabase(){
        Card card = Card.builder().name("karta 1").build();
        Card card2 = Card.builder().name("karta 2").build();
        List<Card> cards = new ArrayList<Card>();
        cards.add(card);
        cards.add(card2);
        System.out.println(cards+"\n");
        Deck deck = Deck.builder().cardSet(cards).build();
        Player player = Player.builder().name("mati5").deck(deck).build();
        cardService.addCard(card);
        cardService.addCard(card2);
        deckService.addDeck(deck);
        playerService.addPlayer(player);
        System.out.println("DONE!");
        System.out.println(player.getDeck().getCardSet());
    }
    public void printAfterBattleScreen(boolean victory){
        if(victory){
            System.out.println("Wygrana!");
        }else{
            System.out.println("Przegrana!");
        }
        System.out.println("Nacisnij dowolny klaiwsz aby kontynuować...");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
    public void deleteCardFromDeck(Deck deck, Card card){
        List<Card>cardList = deck.getCardSet();
        cardList.remove(card);
        deckService.getDeckById(deck.getId()).setCardSet(cardList);
    }
    public String updateView(Player player, Enemy enemy, int turn){
        String fightText = fight;
        int enMaxHp, enHp, plMaxHp, plHp;
        enMaxHp = enemyService.getEnemyById(enemy.getId()).getBase_life();
        enHp = enemy.getBase_life();
        plMaxHp = playerService.getPlayerById(player.getId()).getHp();
        plHp = player.getHp();
        String numericValue = plMaxHp + "/" + plHp;
        Integer len = numericValue.length();
        for (int i = 0; i < (20 - len) / 2; i++) numericValue = " " + numericValue;
        for (int i = 0; i < (20 - len) / 2; i++) numericValue = numericValue + " ";
        if ((20 - len) % 2 == 1) numericValue = " " + numericValue;
        fightText = fightText.replace("#friendly_health_bar", numericValue);
      
        numericValue = enMaxHp + "/" + enHp;
        len = numericValue.length();
        for(int i=0; i<(20-len)/2;i++) numericValue = " " + numericValue;
        for(int i=0; i<(20-len)/2;i++) numericValue = numericValue + " ";
        if((20-len)%2==1) numericValue+=" ";
        fightText = fightText.replace("##enemy_health_bar##",numericValue);
        fightText = fightText.replace("#T",(turn<10)? " "+turn:Integer.toString(turn));

        return fightText;
    }
    public void printCard(Card card, int index){
        String cardText = cardView;
        String space = "";
        cardText = cardText.replace("1", Integer.toString(card.getCost()));
        space = (card.getDmg() > 9) ? "" : " ";
        cardText = cardText.replace("2", space + Integer.toString(card.getDmg()));
        space = (card.getDefense() > 9) ? "" : " ";
        cardText = cardText.replace("3", space + Integer.toString(card.getDefense()));
        cardText = cardText.replace("4", Integer.toString(index));
        System.out.print(cardText);
    }

    public Area selectArea() {
        List<Area> areas = areaService.getAllAreas();
        Random random = new Random();
        return areas.get(random.nextInt(areas.size()));
    }

    public Enemy selectEnemy() {
        List<Enemy> allEnemies = enemyService.getAllEnemies();
        if (allEnemies.size() == 0) System.out.println("Brak przeciwników");
        Random random = new Random();
        return allEnemies.get(random.nextInt(allEnemies.size()));
    }

    public void increaseExp(int exp, Player playerUpdate) {
        player = playerService.updatePlayerExperience(playerUpdate.getId(), playerUpdate.getExperience() + exp);

    }


    @Override
    public void run(String... args) throws Exception {
        showMenu();
//        populateDatabase();

    }
}
