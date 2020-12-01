package com.dungeonhunters.dungeonhunters.factory;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.CardType;
import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Player;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class BlockCardActionFactory implements AbstractCardActionFactory {

    @Override
    public void use(Card card,
                    Player player,
                    Enemy enemy,
                    Map<Card, Integer> playerStatus,
                    Map<Card, Integer> enemyStatus) {
        playerStatus.put(card, card.getValue());
    }

    @Override
    public CardType getSupportedCardType() {
        return CardType.Block;
    }
}
