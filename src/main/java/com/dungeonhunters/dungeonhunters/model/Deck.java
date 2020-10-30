package com.dungeonhunters.dungeonhunters.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Deck implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Card card;
}
