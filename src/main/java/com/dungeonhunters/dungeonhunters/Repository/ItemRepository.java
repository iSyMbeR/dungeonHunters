package com.dungeonhunters.dungeonhunters.Repository;

import com.dungeonhunters.dungeonhunters.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
