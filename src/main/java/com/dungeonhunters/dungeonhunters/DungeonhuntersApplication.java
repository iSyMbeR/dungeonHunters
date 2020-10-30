package com.dungeonhunters.dungeonhunters;





import com.dungeonhunters.dungeonhunters.model.Player;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
	public class DungeonhuntersApplication extends JFrame {
		private static PlayerService playerService;

		@Autowired
		DungeonhuntersApplication(PlayerService playerService) {
			this.playerService = playerService;
		}

		public static void main(String[] args) {


			SpringApplication.run(DungeonhuntersApplication.class, args);

			for (Player player:playerService.getPlayers()) {
				playerService.deletePlayerById(player.getId());
			}
			System.out.println("0");
		}





	}