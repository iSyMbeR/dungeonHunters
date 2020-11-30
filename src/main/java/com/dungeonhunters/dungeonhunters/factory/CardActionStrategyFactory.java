package com.dungeonhunters.dungeonhunters.factory;

import com.dungeonhunters.dungeonhunters.model.CardType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class CardActionStrategyFactory {
    private final Map<CardType, AbstractCardActionFactory> strategies;

    protected CardActionStrategyFactory(Set<AbstractCardActionFactory> strategiesSet) {
        this.strategies = new HashMap<>();
        strategiesSet.forEach(strategy -> strategies.put(strategy.getSupportedCardType(), strategy));
    }

    public Optional<AbstractCardActionFactory> get(CardType cardType) {
        return Optional.ofNullable(strategies.get(cardType));
    }
}
