package com.dungeonhunters.dungeonhunters.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Area implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
