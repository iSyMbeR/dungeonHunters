package com.dungeonhunters.dungeonhunters.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopItemDto {
    public int price;
    public String name;
    public ItemType type;
    public Long id;
    public Object item;
}
