package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.BonusRepository;

import com.dungeonhunters.dungeonhunters.model.Bonus;
import com.dungeonhunters.dungeonhunters.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BonusService {

    private final BonusRepository bonusRepository;

    public void addCard(Bonus bonus){
        bonusRepository.save(bonus);
    }

    public void deleteBonus(Bonus bonus){
        bonusRepository.delete(bonus);
    }

    public List<Bonus> getAllBonus() { return bonusRepository.findAll(); }

    public Bonus getBonusById(Long id){return bonusRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) bonusRepository.count();
    }
}
