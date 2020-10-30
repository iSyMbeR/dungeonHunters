package com.dungeonhunters.dungeonhunters.model;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double stage;
    private Double experience;
    @OneToOne
    private Deck deck;
    @OneToOne
    private Inventory inventory;

    public Player(String name, Double stage, Double experience) {
        this.name = name;
        this.stage = stage;
        this.experience = experience;
    }
}
