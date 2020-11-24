package com.dungeonhunters.dungeonhunters.Repository;

import com.dungeonhunters.dungeonhunters.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
}
