package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.EnemyRepository;

import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EnemyService {

    private final EnemyRepository enemyRepository;

    public void addInventory( Enemy enemy){enemyRepository.save(enemy); }

    public void deleteInventory(Enemy enemy){
        enemyRepository.delete(enemy);
    }

    public List<Enemy> getAllInventory()
    {
        return enemyRepository.findAll();
    }

    public Enemy getItemById(Long id){return enemyRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) enemyRepository.count();
    }
}
