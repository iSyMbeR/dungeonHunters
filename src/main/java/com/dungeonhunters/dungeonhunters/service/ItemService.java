package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.ItemRepository;

import com.dungeonhunters.dungeonhunters.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public void deleteItem(Item item){
        itemRepository.delete(item);
    }

    public List<Item> getItems()
    {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id){return itemRepository.findById(id).orElseThrow();}

    public int getSize(){
        return (int) itemRepository.count();
    }
}
