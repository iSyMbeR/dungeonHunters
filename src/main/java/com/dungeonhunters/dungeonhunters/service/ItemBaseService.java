package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.ItemBaseRepository;

import com.dungeonhunters.dungeonhunters.model.ItemBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemBaseService {

    private final ItemBaseRepository itemRepository;

    public void addItemBase(ItemBase itemBase){
        itemRepository.save(itemBase);
    }

    public void deleteItemBase(ItemBase itemBase){
        itemRepository.delete(itemBase);
    }

    public List<ItemBase> getItemBases()
    {
        return itemRepository.findAll();
    }

    public ItemBase getItemBaseById(Long id){return itemRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) itemRepository.count();
    }
}
