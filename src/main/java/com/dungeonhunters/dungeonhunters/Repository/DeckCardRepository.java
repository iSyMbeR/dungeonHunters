package com.dungeonhunters.dungeonhunters.Repository;


import com.dungeonhunters.dungeonhunters.model.DeckCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeckCardRepository extends JpaRepository<DeckCard,Long> {


}
