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

    public void addItem(ItemBase itemBase){
        itemRepository.save(itemBase);
    }

    public void deleteItem(ItemBase itemBase){
        itemRepository.delete(itemBase);
    }

    public List<ItemBase> getItems()
    {
        return itemRepository.findAll();
    }

    public ItemBase getItemById(Long id){return itemRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) itemRepository.count();
    }
}
