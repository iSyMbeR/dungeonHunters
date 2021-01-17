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
public class Player implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int stage;
    private int experience;
    private int hp;
    private int dmg;
    private int def;
    private int gold;
    private int currentHp;
    private String logo;
    @OneToOne
    private Deck deck;
    @OneToOne
    private Inventory inventory;

    public static Player.PlayerBuilder builder() {
        return new Player.PlayerBuilder();
    }

    public static class PlayerBuilder {
        private Long id;
        private String name;
        private int stage;
        private int experience;
        private int hp;
        private int dmg;
        private int def;
        private int gold;
        private int currentHp;
        private String logo;
        private Deck deck;
        private Inventory inventory;

        PlayerBuilder() {
        }

        public Player.PlayerBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Player.PlayerBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Player.PlayerBuilder stage(final int stage) {
            this.stage = stage;
            return this;
        }

        public Player.PlayerBuilder experience(final int experience) {
            this.experience = experience;
            return this;
        }

        public Player.PlayerBuilder hp(final int hp) {
            this.hp = hp;
            return this;
        }

        public Player.PlayerBuilder dmg(final int dmg) {
            this.dmg = dmg;
            return this;
        }

        public Player.PlayerBuilder def(final int def) {
            this.def = def;
            return this;
        }

        public Player.PlayerBuilder gold(final int gold) {
            this.gold = gold;
            return this;
        }

        public Player.PlayerBuilder currentHp(final int currentHp) {
            this.currentHp = currentHp;
            return this;
        }

        public Player.PlayerBuilder logo(final String logo) {
            this.logo = logo;
            return this;
        }

        public Player.PlayerBuilder deck(final Deck deck) {
            this.deck = deck;
            return this;
        }

        public Player.PlayerBuilder inventory(final Inventory inventory) {
            this.inventory = inventory;
            return this;
        }

        public Player build() {
            return new Player(this.id, this.name, this.stage, this.experience, this.hp, this.dmg, this.def, this.gold, this.currentHp, this.logo, this.deck, this.inventory);
        }

        public String toString() {
            return "Player.PlayerBuilder(id=" + this.id + ", name=" + this.name + ", stage=" + this.stage + ", experience=" + this.experience + ", hp=" + this.hp + ", dmg=" + this.dmg + ", def=" + this.def + ", gold=" + this.gold + ", currentHp=" + this.currentHp + ", logo=" + this.logo + ", deck=" + this.deck + ", inventory=" + this.inventory + ")";
        }
    }
}
