package com.dungeonhunters.dungeonhunters.dto;

import com.dungeonhunters.dungeonhunters.model.CardType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopItemDto {
    public int price;
    public String name;
    public CardType type;
    public String description;
    public Long id;
    public Object item;
}
