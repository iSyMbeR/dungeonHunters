package com.dungeonhunters.dungeonhunters.Repository;

import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Inventory;
import com.dungeonhunters.dungeonhunters.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    @Transactional
    @Modifying
    @Query("select i from Item i where i.id= :id")
    List<Item> getAllItems(@Param("id") Long id);

}
