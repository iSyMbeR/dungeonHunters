package com.dungeonhunters.dungeonhunters.Repository;


import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.DeckCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface DeckCardRepository extends JpaRepository<DeckCard,Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into Deck_Card (ID, CARD_ID,DECK_ID) VALUES (2,:idCard,:idDeck)", nativeQuery = true)
    void addCardToDeck(@Param("idCard") Long idCard, @Param("idDeck") Long idDeck);
}
