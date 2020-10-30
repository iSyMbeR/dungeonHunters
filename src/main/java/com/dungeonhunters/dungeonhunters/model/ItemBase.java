package com.dungeonhunters.dungeonhunters.model;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
public class ItemBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double dmg;
    private Double rarity;
    @OneToOne
    private Area area;

}
