package com.dungeonhunters.dungeonhunters.service;

import com.dungeonhunters.dungeonhunters.Repository.InventoryRepository;

import com.dungeonhunters.dungeonhunters.model.Inventory;
import com.dungeonhunters.dungeonhunters.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory addInventory(Inventory inv) {
        return inventoryRepository.save(inv);
    }

    public void deleteInventory(Inventory inv) {
        inventoryRepository.delete(inv);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow();
    }

    public int getSize() {
        return (int) inventoryRepository.count();
    }

    public List<Item> getAllItemsFromPlayerInventory(Long id) {
        return inventoryRepository.getAllItems(id);
    }

}
