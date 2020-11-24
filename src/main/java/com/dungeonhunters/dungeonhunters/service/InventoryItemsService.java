package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.InventoryItemsRepository;
import com.dungeonhunters.dungeonhunters.model.InventoryItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class InventoryItemsService {

    private final InventoryItemsRepository inventoryItemsRepository;

    public void addInventoryItems(InventoryItems inventoryItems){
        inventoryItemsRepository.save(inventoryItems);
    }

    public void deleteInventoryItems(InventoryItems inventoryItems){
        inventoryItemsRepository.delete(inventoryItems);
    }

    public List<InventoryItems> getInventoryItems()
    {
        return inventoryItemsRepository.findAll();
    }

    public int getSize(){
        return (int)inventoryItemsRepository.count();
    }


}
