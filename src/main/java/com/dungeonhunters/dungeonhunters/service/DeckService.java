package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.DeckRepository;
import com.dungeonhunters.dungeonhunters.model.Deck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;

    public void addDeck(Deck deck){
        deckRepository.save(deck);
    }

    public void deleteDeck(Deck deck){
        deckRepository.delete(deck);
    }


    public List<Deck> getAllDecks() { return deckRepository.findAll(); }


    public Deck getDeckById(Long id){return deckRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) deckRepository.count();
    }

}
