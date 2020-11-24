package com.dungeonhunters.dungeonhunters.Repository;

import com.dungeonhunters.dungeonhunters.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BonusRepository extends JpaRepository<Bonus,Long> {
}
