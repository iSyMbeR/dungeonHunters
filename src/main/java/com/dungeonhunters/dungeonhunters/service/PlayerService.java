package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.PlayerRepository;

import com.dungeonhunters.dungeonhunters.model.Player;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public void addPlayer(Player player){
        playerRepository.save(player);
    }

    public void deletePlayer(Player player){
        playerRepository.delete(player);
    }

    public List<Player> getPlayers()
    {
        return playerRepository.findAll();
    }

    public String getPlayerById(Long id){return playerRepository.findById(id).orElseThrow().getName();}

    public int getSize(){
        return (int)playerRepository.count();
    }

    public Player updatePlayerExperience(Long id, int experience){
        playerRepository.updateExperience(id, experience);
        return playerRepository.findById(id).orElseThrow();
    }


}
