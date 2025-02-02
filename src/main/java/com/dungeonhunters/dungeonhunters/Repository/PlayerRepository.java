package com.dungeonhunters.dungeonhunters.Repository;

import com.dungeonhunters.dungeonhunters.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Player u set u.experience = :experience where u.id = :id")
    void updateExperience(@Param("id") Long id, @Param("experience") int experience);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Player u set u.hp = :hp where u.id = :id")
    void setHp(@Param("id") Long id, @Param("hp") int hp);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select c.id from Player c where c.name =:name")
    List<Long> getPlayerIdByName(@Param("name") String name);


}
