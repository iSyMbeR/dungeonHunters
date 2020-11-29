package com.dungeonhunters.dungeonhunters.model;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int stage;
    private int experience;
    private int hp;
    private int gold;
    private int currentHp;
    @OneToOne
    private Deck deck;
    @OneToOne
    private Inventory inventory;
}
