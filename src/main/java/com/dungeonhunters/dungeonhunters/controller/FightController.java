package com.dungeonhunters.dungeonhunters.controller;


import com.dungeonhunters.dungeonhunters.dto.Fight;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.CardService;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import com.dungeonhunters.dungeonhunters.service.PlayerService;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Controller;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.util.Map;



@Controller
public class FightController extends JFrame {
    public GameController gameController;
    public final EnemyService enemyService;
    private final DeckService deckService;
    private final CardService cardService;
    public final Fight fight;
    public int selected = 1;
    public JPanel mainPanel;
    public JPanel actionPanel;
    public JPanel cardPanel;
    public JPanel playerPanel;
    public JPanel enemyPanel;
    public JPanel logPanel;
    public JPanel turnPanel;

    FightController(EnemyService enemyService, DeckService deckService, Fight fight, CardService cardService) {
        this.enemyService = enemyService;
        this.deckService = deckService;
        this.cardService = cardService;
        this.fight = fight;
    }

    public void createView() {
        generateArena();
        generateEnemy();
        fight.nextTurn();
        logPanel = new JPanel();
        mainPanel = new JPanel();
        turnPanel = new JPanel();
        playerPanel = new JPanel();
        enemyPanel = new JPanel();
        actionPanel = new JPanel();
        cardPanel = new JPanel();
        setupPanelPlacement();
        buildPanels();
        gameController.setMainContent(mainPanel);
    }

    private void generateArena() {
        fight.generateArea();
    }

    private void buildPanels() {
        buildTurnPanel();
        buildPlayerPanel();
        buildEnemyPanel();
        buildActionPanel();
        buildCardPanel();
    }

    private void buildActionPanel() {
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER,25,25));
        actionPanel.setBackground(Color.lightGray);
        JButton attackButton = LogoController.getButtonWithIcon("attack-green");
        styleOptionButton(attackButton);
        attackButton.addActionListener(e -> attack());
        JButton defendButton = LogoController.getButtonWithIcon("defend-blue");
        styleOptionButton(defendButton);
        defendButton.addActionListener(e -> defend());
        JButton nextTurnButton = LogoController.getButtonWithIcon("next-turn-red");
        styleOptionButton(nextTurnButton);
        nextTurnButton.addActionListener(e -> endTurn());
        actionPanel.add(attackButton);
        actionPanel.add(defendButton);
        actionPanel.add(nextTurnButton);

    }
    private void styleOptionButton(JButton b){
        b.setPreferredSize(new Dimension(50,50));
        b.setBackground(null);
        b.setForeground(null);
        b.setBorder(null);
        b.setFocusPainted(false);
    }

    private void buildPlayerPanel() {
        playerPanel.removeAll();
        playerPanel.setBackground(Color.lightGray);
        JPanel playerIconContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        playerIconContainer.setPreferredSize(new Dimension(350, 350));
        playerIconContainer.setBackground(Color.lightGray);
        JLabel playerIcon = LogoController.getLogoPlayer(fight.player.getLogo());
        playerIcon.setBackground(Color.white);
        playerIcon.setPreferredSize(new Dimension(300, 300));
        playerIcon.setBackground(Color.orange);
        playerIconContainer.add(playerIcon);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT,25,5));
        JLabel playerName = new JLabel(fight.player.getName());
        JLabel playerHp = new JLabel("HP " + fight.player.getCurrentHp() + "/" + fight.player.getHp());
        JLabel playerDmg = new JLabel("Attack damage: " + fight.player.getDmg());
        JLabel playerDef = new JLabel("Defense: " + fight.player.getDef());

        JPanel playerStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        playerStatus.setBackground(Color.white);
        for (Map.Entry<Card, Integer> entry : fight.playerStatus.entrySet()) {
            JLabel l = new JLabel(entry.getKey().getType().toString() + ": " + entry.getValue());
            l.setPreferredSize(new Dimension(200, 20));
            l.setForeground(Color.BLUE);
            playerStatus.add(l);
        }

        stats.add(playerName);
        stats.add(playerHp);
        stats.add(playerDmg);
        stats.add(playerDef);
        stats.add(playerStatus);
        styleStatsPanel(stats);

        playerPanel.add(playerIconContainer);
        playerPanel.add(stats);
        playerPanel.revalidate();
        playerPanel.repaint();
    }
    private void styleStatsPanel(JPanel stats){
        stats.setPreferredSize(new Dimension(250, 300));
        stats.setBackground(Color.white);
        Component[] components = stats.getComponents();
        JLabel c = (JLabel)components[0];
        c.setPreferredSize(new Dimension(200,24));
        c.setFont(new Font("Arial",Font.BOLD,24));

        c = (JLabel) components[1];
        c.setPreferredSize(new Dimension(200,20));
        c.setForeground(Color.red);
        c.setFont(new Font("Arial",Font.BOLD,20));

        c = (JLabel) components[2];
        c.setPreferredSize(new Dimension(200,20));
        c.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        c = (JLabel) components[3];
        c.setPreferredSize(new Dimension(200,20));
        //c.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
    }

    private void buildEnemyPanel() {
        enemyPanel.removeAll();
        enemyPanel.setBackground(Color.lightGray);;
        JPanel enemyIconContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        enemyIconContainer.setPreferredSize(new Dimension(350, 350));
        enemyIconContainer.setBackground(Color.lightGray);
        JLabel enemyIcon = LogoController.getLogoEnemy(fight.enemy.getName());
        enemyIcon.setBackground(Color.white);
        enemyIcon.setPreferredSize(new Dimension(300, 300));
        enemyIcon.setBackground(Color.orange);
        enemyIconContainer.add(enemyIcon);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT,25,5));
        JLabel enemyName = new JLabel(fight.enemy.getName(),SwingConstants.RIGHT);
        JLabel enemyHp = new JLabel("HP " + fight.enemy.getHp() + "/" + fight.getEnemyMaxHp(),SwingConstants.RIGHT);
        JLabel enemyDmg = new JLabel("Attack damage: " + fight.enemy.getDmg(),SwingConstants.RIGHT);
        JLabel enemyDef = new JLabel("Defense: " + fight.enemy.getDefense(),SwingConstants.RIGHT);

        JPanel enemyStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        enemyStatus.setBackground(Color.white);
        for (Map.Entry<Card, Integer> entry : fight.enemyStatus.entrySet()) {
            JLabel l = new JLabel(entry.getKey().getType().toString() + ": " + entry.getValue());
            l.setPreferredSize(new Dimension(250, 25));
            l.setForeground(Color.green);
            enemyStatus.add(l);
        }

        stats.add(enemyName);
        stats.add(enemyHp);
        stats.add(enemyDmg);
        stats.add(enemyDef);
        stats.add(enemyStatus);
        styleStatsPanel(stats);

        enemyPanel.add(stats);
        enemyPanel.add(enemyIconContainer);
        enemyPanel.revalidate();
        enemyPanel.repaint();
    }

    private void buildTurnPanel() {
        turnPanel.removeAll();
        turnPanel.setBackground(Color.lightGray);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setPreferredSize(new Dimension(500, 50));
        JLabel locationInfo = new JLabel(fight.getArea().getName());
        locationInfo.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        locationInfo.setPreferredSize(new Dimension(200, 50));
        left.add(locationInfo);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setPreferredSize(new Dimension(500, 50));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        center.setPreferredSize(new Dimension(200, 50));
        JLabel turnCount = new JLabel("Turn: " + fight.getTurn());
        turnCount.setPreferredSize(new Dimension(200, 50));
        turnCount.setHorizontalAlignment(JLabel.CENTER);
        center.add(turnCount);

        turnPanel.add(left);
        turnPanel.add(center);
        turnPanel.add(right);
        turnPanel.revalidate();
        turnPanel.repaint();
    }

    private void setupPanelPlacement() {
        mainPanel.setPreferredSize(new Dimension(1200, 800));
        mainPanel.setBackground(Color.white);
        turnPanel.setPreferredSize(new Dimension(1200, 50));
        playerPanel.setPreferredSize(new Dimension(600, 350));
        enemyPanel.setPreferredSize(new Dimension(600, 350));
        actionPanel.setPreferredSize(new Dimension(100, 400));
        JScrollPane scrollableCards = new JScrollPane(cardPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableCards.setPreferredSize(new Dimension(800, 400));
        scrollableCards.setBorder(null);
        scrollableCards.getVerticalScrollBar().setUnitIncrement(16);
        logPanel.setPreferredSize(new Dimension(300,400));
        logPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,2));
        JScrollPane scrollableLogs = new JScrollPane(logPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableLogs.setPreferredSize(new Dimension(300, 400));
        scrollableLogs.setBorder(null);
        scrollableLogs.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(turnPanel);
        mainPanel.add(playerPanel);
        mainPanel.add(enemyPanel);
        mainPanel.add(actionPanel);
        removeLayoutGapsFromPanel();
        mainPanel.add(scrollableCards);
        mainPanel.add(scrollableLogs);

    }

    private void generateEnemy() {
        fight.createEnemy();
    }

    private void removeLayoutGapsFromPanel() {
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        Component[] components = mainPanel.getComponents();
        for (Component c : components) {
            JPanel p = (JPanel) c;
            p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.fight.setPlayer(player);
    }

    private void checkToEndBattle(int battleStatus) {
        if (battleStatus == -1) endGame();
        if (battleStatus == 1) winBattle();
    }

    private void winBattle() {
        fight.generateLootAndUpdatePlayer();
        showLootScreen();
    }

    private void endGame() {
        fight.looseBattleAndUpdatePlayer();
        showFailureScreen();
    }
    private void logInfo(String message){
        if((logPanel.getComponents().length+2)*14>logPanel.getHeight()) logPanel.setPreferredSize(new Dimension(300,(logPanel.getComponents().length+2)*14));
        JLabel m = new JLabel(message);
        m.setPreferredSize(new Dimension(250,12));
        m.setFont(new Font("Arail", Font.ITALIC,12));
        logPanel.add(m);
    }
    private void useCard(Card card) {
        String message = fight.useCard(card);
        logInfo(message);
        fight.refreshStatus();
        buildPlayerPanel();
        buildEnemyPanel();
        buildCardPanel();
        checkToEndBattle(fight.checkEndBattleConditions());
    }

    public void attack() {
        String message = fight.playerAttack();
        logInfo(message);
        buildEnemyPanel();
        checkToEndBattle(fight.checkEndBattleConditions());
    }


    public void defend(){
        String message = fight.playerDefend();
        logInfo(message);
        buildPlayerPanel();
    }

    public void endTurn() {
        String message;
        message = fight.enemyTurn();
        logInfo(message);
        message = fight.nextTurn();
        logInfo(message);
        buildPlayerPanel();
        buildEnemyPanel();
        buildTurnPanel();
        checkToEndBattle(fight.checkEndBattleConditions());
    }
    private void showLootScreen() {
        JPanel lootScreen = new JPanel();
        lootScreen.setLayout(new FlowLayout(FlowLayout.CENTER,100,0));
        JPanel lootContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,100,50));
        for(String lootName : fight.loot){
            JPanel lootItem = new JPanel();
            lootItem.setLayout(new BoxLayout(lootItem,BoxLayout.Y_AXIS));
            lootItem.setPreferredSize(new Dimension(200,300));
            lootItem.setBorder(BorderFactory.createLineBorder(Color.black,2));
            JLabel lootItemLabel = new JLabel(lootName);
            lootItem.add(lootItemLabel);
            lootContainer.add(lootItem);
        }
        JButton exitButton = new JButton("Go to town");
        exitButton.setPreferredSize(new Dimension(400,80));
        exitButton.setBackground(Color.CYAN);
        exitButton.addActionListener(e -> gameController.switchToProfileController());
        exitButton.setBorder(null);
        exitButton.setFocusPainted(false);
        lootScreen.add(lootContainer);
        lootScreen.add(exitButton);
        gameController.setMainContent(lootScreen);
        fight.clearBattle();
    }
    private void showFailureScreen() {
        JPanel cont = new JPanel();
        JPanel lostMessagePanel = new JPanel();
        JLabel lostMessage = new JLabel("You lost. Your character has been deleted");
        lostMessagePanel.add(lostMessage);
        cont.add(lostMessagePanel);
        gameController.setMainContent(cont);
    }

}
