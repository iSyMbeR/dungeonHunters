package com.dungeonhunters.dungeonhunters.Repository;

import com.dungeonhunters.dungeonhunters.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AreaRepository extends JpaRepository<Area,Long> {
}
