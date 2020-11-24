package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.DeckCardRepository;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Deck;
import com.dungeonhunters.dungeonhunters.model.DeckCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DeckCardService {

    private final DeckCardRepository deckCardRepository;

    public void addDeckCard(DeckCard deckCard){
        deckCardRepository.save(deckCard);
    }

    public void deleteDeckCard(DeckCard deckCard){
        deckCardRepository.delete(deckCard);
    }

    public List<DeckCard> getDeckCard()
    {
        return deckCardRepository.findAll();
    }

    public int getSize(){
        return (int)deckCardRepository.count();
    }

    public void addCardToDeck(Long idCard, Long idDeck){
        deckCardRepository.addCardToDeck(idCard,idDeck);
    }
    public List<Card> getAllCardsFromDeck(Long id){
        return deckCardRepository.getAllCardsFromDeck(id);
    }
    public void deleteCardFromDeck(Deck deck, Card card){
        deckCardRepository.deleteCardFromDeck(deck,card);
    }

    public List<Card> getAllCardsFromDeck (Long id) {
        return deckCardRepository.getAllCardsFromDeck(id);
    }

}
