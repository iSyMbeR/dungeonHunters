package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.CardRepository;

import com.dungeonhunters.dungeonhunters.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public void addCard(Card card){
        cardRepository.save(card);
    }

    public void deleteInventory(Card card){
        cardRepository.delete(card);
    }

    public List<Card> getAllInventory() { return cardRepository.findAll(); }

    public Card getItemById(Long id){return cardRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) cardRepository.count();
    }
}
