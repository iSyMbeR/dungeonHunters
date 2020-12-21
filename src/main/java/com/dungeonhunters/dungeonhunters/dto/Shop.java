package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Deck;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Shop {
    private final CardService cardService;
    private final DeckService deckService;
    public List<ShopItemDto> shopItems;
    private final PlayerService playerService;

    Shop(CardService cardService, DeckService deckService, PlayerService playerService){
        this.cardService = cardService;
        this.playerService = playerService;
        this.shopItems = new ArrayList<>();
        this.deckService = deckService;
    }
    public void refreshItems(Player player) {
        shopItems = new ArrayList<>();
        List<Card> allCardList = cardService.getAllCards();
        List<Card> playerCards = player.getDeck().getCardSet();
        List<Card> duplicates = new ArrayList<>();
        for(Card c : playerCards){
            allCardList.remove(c);
        }
        if (allCardList.size() > 0) {
            Random r = new Random();
            for (int i = 0; i < 6; i++) {
                Card card = allCardList.get(r.nextInt(allCardList.size()));
                if (!duplicates.contains(card)) {
                    int max = 6;
                    int min = 2;
                    ShopItemDto item = ShopItemDto.builder()
                            .name(card.getName())
                            .type(card.getType())
                            .id(card.getId())
                            .description(card.getDescription())
                            .price(r.nextInt((max-min)+1)+min)
                            .build();
                    duplicates.add(card);
                    shopItems.add(item);
                }
            }
        }
    }
    public List<ShopItemDto> getItems() {
        return shopItems;
    }

    public void buyItem(Player player, int selected) {
        if(player.getGold()>=shopItems.get(selected).getPrice()){
            player.setGold(player.getGold() - shopItems.get(selected).getPrice());
            Deck deck = deckService.getDeckById(player.getDeck().getId());
            List<Card> set = deck.getCardSet();
            set.add(cardService.getCardById(shopItems.get(selected).getId()));
            deck.setCardSet(set);
            playerService.addPlayer(player);
            deckService.addDeck(deck);
            shopItems.remove(selected);

//            if(shopItems.get(selected).getType() == ItemType.ITEM){
//                List<Item> itemList =  player.getInventory().getItemList();
//                itemList.add(itemService.getItemById(shopItems.get(selected).getId()));
//                player.getInventory().setItemList(itemList);
//            }
        }
    }

    public boolean buyRefreshItems(Player player) {
        if(player.getGold()>=2){
            player.setGold(player.getGold() - 2);
            playerService.addPlayer(player);
            return true;
        }
        return false;
    }
}
