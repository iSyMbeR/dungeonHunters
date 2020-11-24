//package com.dungeonhunters.dungeonhunters;
//
//import com.dungeonhunters.dungeonhunters.model.Card;
//import com.dungeonhunters.dungeonhunters.model.Deck;
//import com.dungeonhunters.dungeonhunters.service.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//
//import java.util.List;
//import java.util.Set;
//
//
//@RequiredArgsConstructor
//@Component
//public class DataBaseFiller {
//    private final AreaService areaService;
//    private final BonusService bonusService;
//    private final CardService cardService;
//    private final DeckService deckService;
//    private final EnemyService enemyService;
//    private final InventoryService inventoryService;
//    private final ItemBaseService itemBaseService;
//    private final ItemBonusService itemBonusService;
//    private final ItemService itemService;
//    private final PlayerService playerService;
//
//    public void FillerDataBase() {
//        List<Card> cardList = cardService.getAllCards();
//
//        deckService.addDeck(Deck.builder()
//                .cards(cardList)
//                .build());
//
////        playerService.addPlayer(Player.builder()
////                .name("Nora")
////                .experience(5000d)
////                .stage(1d)
////                .inventory()
////                .build());
////    }
//
//    }
//}
