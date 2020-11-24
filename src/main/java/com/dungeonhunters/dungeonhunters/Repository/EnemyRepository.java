package com.dungeonhunters.dungeonhunters.Repository;

import com.dungeonhunters.dungeonhunters.model.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnemyRepository extends JpaRepository<Enemy,Long> {
}
