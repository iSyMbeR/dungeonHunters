package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.PlayerRepository;

import com.dungeonhunters.dungeonhunters.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player addPlayer(Player player){
        return playerRepository.save(player);
    }

    public void deletePlayer(Player player){
        playerRepository.delete(player);
    }

    public List<Player> getPlayers()
    {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id){return playerRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int)playerRepository.count();
    }

    public Player updatePlayerExperience(Long id, int experience){
        playerRepository.updateExperience(id, experience);
        return playerRepository.findById(id).orElseThrow();
    }

    public void updateExperience(Long id, int exp){
        playerRepository.updateExperience(id, exp);

    }
    public void setHp(Long id, int dmg){
        playerRepository.setHp(id, dmg);
    }

}
