package com.dungeonhunters.dungeonhunters.Repository;


import com.dungeonhunters.dungeonhunters.model.InventoryItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface InventoryItemsRepository extends JpaRepository<InventoryItems,Long> {


}
