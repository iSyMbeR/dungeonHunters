package com.dungeonhunters.dungeonhunters.model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class ItemBonus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Bonus bonus;
    @ManyToOne
    private Item item;
}
