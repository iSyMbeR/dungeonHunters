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

    public void addCard(Deck deck){
        deckRepository.save(deck);
    }

    public void deleteInventory(Deck deck){
        deckRepository.delete(deck);
    }

    public List<Deck> getAllInventory() { return deckRepository.findAll(); }

    public Deck getItemById(Long id){return deckRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) deckRepository.count();
    }
}
