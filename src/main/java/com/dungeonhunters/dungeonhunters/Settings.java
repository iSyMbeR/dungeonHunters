package com.dungeonhunters.dungeonhunters;

import com.dungeonhunters.dungeonhunters.model.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Settings {
    private static Settings instance = null;

    private Settings( ) {
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }
}
