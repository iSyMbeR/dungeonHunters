package com.dungeonhunters.dungeonhunters;


import com.dungeonhunters.dungeonhunters.controller.GameController;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;

@SpringBootApplication
	public class DungeonhuntersApplication extends JFrame{
		public static void main(String[] args) {
			new SpringApplicationBuilder(DungeonhuntersApplication.class)
					.headless(false)
					.web(WebApplicationType.NONE)
					.run(args);
		}
	}