package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.Iterator.*;

import com.dungeonhunters.dungeonhunters.dto.Fight;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.CardService;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import org.springframework.stereotype.Controller;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Map;

import static com.dungeonhunters.dungeonhunters.dto.MenuStrings.*;

@Controller
public class FightController extends JFrame {
    public GameController gameController;
    public final EnemyService enemyService;
    private final DeckService deckService;
    public final Fight fight;
    public int oldAdditonalDmg;
    public static int LOST = 0;
    public JPanel mainPanel;
    public JPanel actionPanel;
    public JPanel cardPanel;
    public JPanel playerPanel;
    public JPanel enemyPanel;
    public JPanel logPanel;
    public JPanel turnPanel;

    FightController(EnemyService enemyService, DeckService deckService, Fight fight) {
        this.enemyService = enemyService;
        this.deckService = deckService;
        this.fight = fight;
    }

    public void createView() {
        logPanel = new JPanel();
        mainPanel = new JPanel();
        turnPanel = new JPanel();
        playerPanel = new JPanel();
        enemyPanel = new JPanel();
        actionPanel = new JPanel();
        cardPanel = new JPanel();
        generateArena();
        generateEnemy();
        fight.nextTurn();
        String message = fight.getEnemyIntent();
        logInfo(message);
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
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
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

    private void styleOptionButton(JButton b) {
        b.setPreferredSize(new Dimension(50, 50));
        b.setBackground(null);
        b.setForeground(null);
        b.setBorder(null);
        b.setFocusPainted(false);
    }

    private void buildCardPanel() {
        cardPanel.removeAll();
        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        List<Card> cardList = deckService.getDeckById(fight.player.getDeck().getId()).getCardSet();
        int cardCount = cardList.size();
        int height = 25;
        while (cardCount > 0) {
            cardCount -= 4;
            height += 235;
        }
        cardPanel.setPreferredSize(new Dimension(800, height));
        for (Card c : cardList) {
            JPanel singleCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            singleCard.setPreferredSize(new Dimension(164, 210));
            singleCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            singleCard.setBackground(Color.white);

            JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            costPanel.setBackground(null);
            costPanel.setPreferredSize(new Dimension(160, 20));

            for (int i = 0; i < c.getCost(); i++) {
                JLabel crystal = LogoController.getLogoCard("Crystal-small");
                crystal.setPreferredSize(new Dimension(20, 20));
                costPanel.add(crystal);
            }
            singleCard.add(costPanel);

            JLabel cardName = new JLabel(c.getName(), SwingConstants.CENTER);
            cardName.setPreferredSize(new Dimension(160, 20));
            cardName.setFont(new Font("Arial", Font.BOLD, 12));

            JLabel cardImage = LogoController.getLogoCard(c.getType().toString());
            cardImage.setPreferredSize(new Dimension(160, 100));

            JLabel effect = new JLabel(c.getValue() + " " + c.getType().toString(), SwingConstants.CENTER);
            effect.setPreferredSize(new Dimension(160, 20));

            JButton useButton = new JButton(USE);
            useButton.setPreferredSize(new Dimension(80, 40));
            useButton.setAlignmentX(SwingConstants.CENTER);
            useButton.setFont(new Font("Arial", Font.BOLD, 20));
            useButton.setBorder(null);
            useButton.setBackground(Color.blue);
            useButton.setForeground(Color.white);
            useButton.setFocusPainted(false);
            useButton.addActionListener(e -> useCard(c));

            singleCard.add(costPanel);
            singleCard.add(cardName);
            singleCard.add(cardImage);
            singleCard.add(effect);
            singleCard.add(useButton);
            cardPanel.add(singleCard);
        }
        cardPanel.revalidate();
        cardPanel.repaint();
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

        int value;
        if (LOST == 2) {
            fight.player = gameController.profileController.player;
            oldAdditonalDmg = 0;
            LOST = 0;
        } else {
            if (gameController.profileController.additionalDmg > oldAdditonalDmg) {
                value = gameController.profileController.additionalDmg - oldAdditonalDmg;
                fight.player.setDmg(fight.player.getDmg() + value);
                oldAdditonalDmg = gameController.profileController.additionalDmg;
            } else if (gameController.profileController.additionalDmg < oldAdditonalDmg) {
                value = oldAdditonalDmg - gameController.profileController.additionalDmg;
                fight.player.setDmg(fight.player.getDmg() - value);
                oldAdditonalDmg = gameController.profileController.additionalDmg;
            }
        }
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 5));
        JLabel playerName = new JLabel(fight.player.getName());
        JLabel playerHp = new JLabel(HP + fight.player.getCurrentHp() + "/" + fight.player.getHp());
        HealthBar hb = new HealthBar(fight.player.getCurrentHp());
        Iterator<HealthBox> it = hb.createIterator();
        JPanel playerHealthBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        while(it.hasNext()){
            HealthBox box = it.next();
            playerHealthBar.add(new JLabel(String.valueOf(box.value)));
        }

        JLabel playerDmg = new JLabel(ATTACK_DMG + fight.player.getDmg());
        JLabel playerDef = new JLabel(DEFENSE + fight.player.getDef());


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
        stats.add(playerHealthBar);
        stats.add(playerDmg);
        stats.add(playerDef);
        stats.add(playerStatus);
        styleStatsPanel(stats);

        playerPanel.add(playerIconContainer);
        playerPanel.add(stats);
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private void styleStatsPanel(JPanel stats) {
        stats.setPreferredSize(new Dimension(250, 300));
        stats.setBackground(Color.white);
        Component[] components = stats.getComponents();
        JLabel c = (JLabel) components[0];
        c.setPreferredSize(new Dimension(200, 24));
        c.setFont(new Font("Arial", Font.BOLD, 24));

        c = (JLabel) components[1];
        c.setPreferredSize(new Dimension(200, 20));
        c.setForeground(Color.red);
        c.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel healthBar = (JPanel) components[2];
        int height = ((healthBar.getComponentCount())/10);
        int width =0;
        if(healthBar.getComponentCount()%10>0) height++;
        healthBar.setPreferredSize(new Dimension(200,20*height));
        healthBar.setBackground(Color.white);
        Component[] healthBoxes = healthBar.getComponents();
        for(Component box : healthBoxes){
            JLabel l = (JLabel) box;
            width = Integer.parseInt(l.getText())*2;
            l.setPreferredSize(new Dimension(width-2,18));
            l.setBorder(BorderFactory.createLineBorder(Color.blue,1));
            l.setOpaque(true);
            l.setBackground(Color.red);
            l.setForeground(Color.red);

        }

        c = (JLabel) components[3];
        c.setPreferredSize(new Dimension(200,20));
        c.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        c = (JLabel) components[4];
        c.setPreferredSize(new Dimension(200,20));
    }

    private void buildEnemyPanel() {
        enemyPanel.removeAll();
        enemyPanel.setBackground(Color.lightGray);
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
        JLabel enemyHp = new JLabel(HP + fight.enemy.getHp() + "/" + fight.getEnemyMaxHp(),SwingConstants.RIGHT);

        HealthBar hb = new HealthBar(fight.enemy.getHp());
        Iterator<HealthBox> it = hb.createIterator();
        JPanel enemyHealthBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        while(it.hasNext()){
            HealthBox box = it.next();
            enemyHealthBar.add(new JLabel(String.valueOf(box.value)));
        }

        JLabel enemyDmg = new JLabel(ATTACK_DMG + fight.enemy.getDmg(),SwingConstants.RIGHT);
        JLabel enemyDef = new JLabel(DEFENSE + fight.enemy.getDefense(),SwingConstants.RIGHT);


        JPanel enemyStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        enemyStatus.setPreferredSize(new Dimension(200, 100));
        enemyStatus.setBackground(Color.white);
        for (Map.Entry<Card, Integer> entry : fight.enemyStatus.entrySet()) {
            JLabel l = new JLabel(entry.getKey().getType().toString() + ": " + entry.getValue(), SwingConstants.RIGHT);
            l.setPreferredSize(new Dimension(200, 25));
            l.setForeground(Color.green);
            enemyStatus.add(l);
        }

        stats.add(enemyName);
        stats.add(enemyHp);
        stats.add(enemyHealthBar);
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
        JLabel turnCount = new JLabel(TURN + ": " + fight.getTurn());
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
        logPanel.setPreferredSize(new Dimension(298, 398));
        logPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
        JScrollPane scrollableLogs = new JScrollPane(logPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableLogs.setPreferredSize(new Dimension(300, 400));
        scrollableLogs.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
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
        LOST = 1;
        showFailureScreen();
    }

    private void logInfo(String message) {
        if ((logPanel.getComponents().length + 2) * 14 > logPanel.getHeight())
            logPanel.setPreferredSize(new Dimension(300, (logPanel.getComponents().length + 2) * 14));
        JLabel m = new JLabel(message);
        m.setPreferredSize(new Dimension(250, 12));
        m.setFont(new Font("Arail", Font.ITALIC, 12));
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


    public void defend() {
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
        message = fight.getEnemyIntent();
        logInfo(message);
        buildPlayerPanel();
        buildEnemyPanel();
        buildTurnPanel();
        checkToEndBattle(fight.checkEndBattleConditions());
    }

    private void showLootScreen() {
        JPanel lootScreen = new JPanel();
        lootScreen.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0));
        JPanel lootContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 50));
        for (Map.Entry<String, Integer> entry : fight.loot.entrySet()) {
            JPanel lootItem = new JPanel();
            lootItem.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            lootItem.setPreferredSize(new Dimension(120, 150));
//            lootItem.setBorder(BorderFactory.createLineBorder(Color.black,2));
            JLabel lootItemIcon = LogoController.getLogoItem(entry.getKey());
            lootItemIcon.setPreferredSize(new Dimension(120, 100));
            JLabel lootItemLabel = new JLabel(entry.getKey() + " x" + entry.getValue(), SwingConstants.CENTER);
            lootItemLabel.setPreferredSize(new Dimension(120, 50));
            lootItem.add(lootItemIcon);
            lootItem.add(lootItemLabel);
            lootContainer.add(lootItem);
        }
        JButton exitButton = new JButton(GO_TO_TOWN);
        exitButton.setPreferredSize(new Dimension(400, 80));
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
        cont.setLayout(new FlowLayout());
        cont.setPreferredSize(new Dimension(10, 200));
        cont.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel lostMessagePanel = new JPanel();
        JLabel lostMessage = new JLabel(YOU_LOST_CHARACTER);
        JButton mainMenuButton = new JButton(EXIT_MAIN_MENU);

        mainMenuButton.setForeground(Color.WHITE);
        mainMenuButton.setBackground(Color.BLACK);
        mainMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mainMenuButton.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mainMenuButton.setForeground(Color.WHITE);
            }
        });
        mainMenuButton.addActionListener(e -> gameController.switchToMenuController());
        lostMessagePanel.add(lostMessage);
        cont.add(lostMessagePanel);
        cont.add(mainMenuButton);
        gameController.setMainContent(cont);
    }

}
