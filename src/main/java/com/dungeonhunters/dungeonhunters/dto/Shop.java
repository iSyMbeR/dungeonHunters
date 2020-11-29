package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Deck;
import com.dungeonhunters.dungeonhunters.model.Item;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.CardService;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.InventoryService;
import com.dungeonhunters.dungeonhunters.service.ItemService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class Shop {
    private final CardService cardService;
    private final DeckService deckService;
    public List<ShopItemDto> shopItems;

    Shop(CardService cardService, DeckService deckService){
        this.cardService = cardService;
        this.shopItems = new ArrayList<>();
        this.deckService = deckService;
    }
    public void refreshItems(Player player) {
        shopItems = new ArrayList<>();
        List<Card> allCardList = cardService.getAllCards();
        if (allCardList.size() > 0) {
            Random r = new Random();
            List<Card> duplicates = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Card card = allCardList.get(r.nextInt(allCardList.size()));
                if (!player.getDeck().getCardSet().contains(card)) {
                    ShopItemDto item = ShopItemDto.builder()
                            .name(card.getName())
                            .type(ItemType.CARD)
                            .id(card.getId())
                            .price(0) //r.nextInt((100-80)+1)+80
                            .build();
                    if(!duplicates.contains(card)){
                        shopItems.add(item);
                        duplicates.add(card);
                    }
                }
            }
        }
    }
    public List<ShopItemDto> getItems() {
        return shopItems;
    }

    public void buyItem(Player player, int selected) {
        if(player.getGold()>=shopItems.get(selected).getPrice()){
            if(shopItems.get(selected).getType() == ItemType.CARD){
                Deck deck = deckService.getDeckById(player.getDeck().getId());
                List<Card> set = deck.getCardSet();
                set.add(cardService.getCardById(shopItems.get(selected).getId()));
                deck.setCardSet(set);
                deckService.addDeck(deck);
                shopItems.remove(selected);
            }
//            if(shopItems.get(selected).getType() == ItemType.ITEM){
//                List<Item> itemList =  player.getInventory().getItemList();
//                itemList.add(itemService.getItemById(shopItems.get(selected).getId()));
//                player.getInventory().setItemList(itemList);
//            }
        }
    }
}
