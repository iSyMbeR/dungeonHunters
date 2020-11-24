package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.ItemBonusRepository;
import com.dungeonhunters.dungeonhunters.model.ItemBonus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemBonusService {

    private final ItemBonusRepository itemBonusRepository;

    public void addItem(ItemBonus itemBonus){
        itemBonusRepository.save(itemBonus);
    }

    public void deleteItem(ItemBonus itemBonus){
        itemBonusRepository.delete(itemBonus);
    }

    public List<ItemBonus> getItems()
    {
        return itemBonusRepository.findAll();
    }

    public ItemBonus getItemById(Long id){return itemBonusRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) itemBonusRepository.count();
    }
}
