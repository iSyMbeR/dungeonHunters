package com.dungeonhunters.dungeonhunters.model;

import com.dungeonhunters.dungeonhunters.Decorator.Equipment;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class Item implements Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private ItemBase itemBase;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable
    private List<Bonus> bonus;


    @Override
    public int getDmg() {
        return itemBase.getDmg();
    }



}
