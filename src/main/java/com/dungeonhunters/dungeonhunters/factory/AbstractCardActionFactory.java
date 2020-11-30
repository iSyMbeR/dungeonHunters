package com.dungeonhunters.dungeonhunters.factory;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.CardType;
import com.dungeonhunters.dungeonhunters.model.Enemy;
import com.dungeonhunters.dungeonhunters.model.Player;

import java.util.Map;

public interface AbstractCardActionFactory {
    void use(Card card, Player player, Enemy enemy, Map<Card, Integer> playerDebuffs, Map<Card, Integer> enemyDebuffs);
    CardType getSupportedCardType();
}
