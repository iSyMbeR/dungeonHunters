package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.EnemyRepository;

import com.dungeonhunters.dungeonhunters.model.Enemy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EnemyService {

    private final EnemyRepository enemyRepository;


    public void addEnemy(Enemy enemy){enemyRepository.save(enemy); }


    public void deleteEnemy(Enemy enemy){
        enemyRepository.delete(enemy);
    }


    public List<Enemy> getAllEnemies()

    {
        return enemyRepository.findAll();
    }

    public Enemy getEnemyById(Long id){return enemyRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) enemyRepository.count();
    }
}
