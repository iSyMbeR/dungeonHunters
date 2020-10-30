package com.dungeonhunters.dungeonhunters.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Inventory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Item item;
}
