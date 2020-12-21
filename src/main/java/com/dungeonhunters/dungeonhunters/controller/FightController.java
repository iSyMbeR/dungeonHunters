package com.dungeonhunters.dungeonhunters.controller;


import com.dungeonhunters.dungeonhunters.dto.Fight;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Controller;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;



@Controller
public class FightController extends JFrame {
    public GameController gameController;
    public final EnemyService enemyService;
    private final DeckService deckService;
    public final Fight fight;
    public int selected = 1;
    public JPanel mainPanel;
    public JPanel actionPanel;
    public JPanel cardPanel;
    public JPanel playerPanel;
    public JPanel enemyPanel;
    public JPanel logPanel;
    public JPanel turnPanel;

    FightController(EnemyService enemyService, DeckService deckService, Fight fight){
        this.enemyService = enemyService;
        this.deckService = deckService;
        this.fight = fight;
    }

    public void createView() {
        generateArena();
        generateEnemy();
        fight.nextTurn();
        mainPanel = new JPanel();
        turnPanel = new JPanel();
        playerPanel = new JPanel();
        enemyPanel = new JPanel();
        actionPanel = new JPanel();
        cardPanel = new JPanel();
        buildPanels();
        setupPanelPlacement();
        gameController.setMainContent(mainPanel);
    }

    private void generateArena() {
        fight.generateArea();
    }

    private void buildPanels() {
        buildTurnPanel();
        buildPlayerPanel();
        mainPanel.add(turnPanel);
        mainPanel.add(playerPanel);
        mainPanel.add(enemyPanel);
        mainPanel.add(actionPanel);
        mainPanel.add(cardPanel);
    }

    private void buildPlayerPanel() {
        JPanel playerIconContainer = new JPanel(new FlowLayout(FlowLayout.LEFT,25,25));
        playerIconContainer.setPreferredSize(new Dimension(350,350));
        playerIconContainer.setBackground(Color.GRAY);
        JLabel playerIcon = LogoController.getLogoPlayer(fight.player.getLogo());
        playerIcon.setBackground(Color.white);
        playerIcon.setPreferredSize(new Dimension(300,300));
        //playerIcon.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        playerIcon.setBackground(Color.orange);
        playerIconContainer.add(playerIcon);

//        playerName.setFont(new Font("Verdana", Font.BOLD, 15));
        JPanel stats = new JPanel();
        stats.setLayout(new BoxLayout(stats,BoxLayout.Y_AXIS));
        stats.setPreferredSize(new Dimension(250,300));
        stats.setBorder(BorderFactory.createEmptyBorder(25,25,25,0));
        JLabel playerName = new JLabel(fight.player.getName());
//        playerName.setPreferredSize(new Dimension(250,40));
        JLabel playerHp = new JLabel("Life "+fight.player.getCurrentHp()+"/"+fight.player.getHp());
//        playerHp.setPreferredSize(new Dimension(250,40));
        JLabel playerDmg = new JLabel("Attack damage: "+fight.player.getDmg());
//        playerDmg.setPreferredSize(new Dimension(250,40));
        JLabel playerDef = new JLabel("Defense: "+fight.player.getDef());
//        playerDef.setPreferredSize(new Dimension(250,40));
        stats.add(playerName);
        stats.add(playerHp);
        stats.add(playerDmg);
        stats.add(playerDef);

        playerPanel.add(playerIconContainer);
        playerPanel.add(stats);
    }

    private void buildTurnPanel() {
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        left.setPreferredSize(new Dimension(500,50));
        JLabel locationInfo = new JLabel(fight.getArea().getName());
        locationInfo.setBorder(BorderFactory.createEmptyBorder(0,25,0,0));
        locationInfo.setPreferredSize(new Dimension(200,50));
        left.add(locationInfo);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
        right.setPreferredSize(new Dimension(500,50));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        center.setPreferredSize(new Dimension(200,50));
        JLabel turnCount = new JLabel("Turn: "+fight.getTurn());
        turnCount.setPreferredSize(new Dimension(200,50));
        turnCount.setHorizontalAlignment(JLabel.CENTER);
        center.add(turnCount);

        turnPanel.add(left);
        turnPanel.add(center);
        turnPanel.add(right);
    }

    private void setupPanelPlacement() {
        removeLayoutGapsFromPanel();
        mainPanel.setPreferredSize(new Dimension(1200,800));
        turnPanel.setPreferredSize(new Dimension(1200,50));
        playerPanel.setPreferredSize(new Dimension(600,350));

        enemyPanel.setPreferredSize(new Dimension(600,350));
        enemyPanel.setBackground(Color.yellow);

        actionPanel.setPreferredSize(new Dimension(100,400));
        actionPanel.setBackground(Color.green);

        cardPanel.setPreferredSize(new Dimension(1100,400));
        cardPanel.setBackground(Color.BLACK);
    }
    private void generateEnemy() {
        fight.createEnemy();
    }
    private void removeLayoutGapsFromPanel() {
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        Component[] components = mainPanel.getComponents();
        for(Component c : components){
            JPanel p = (JPanel) c;
            p.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
    public void setPlayer(Player player) {
        this.fight.setPlayer(player);
    }
//    public void getCardPanel() {
//        cardPanel = new JPanel();
//        cardPanel.setLayout(new BoxLayout(cardPanel,BoxLayout.Y_AXIS));
//        List<Card> cardList = deckService.getDeckById(fight.player.getDeck().getId()).getCardSet();
//        for(Card c : cardList){
//
//            JLabel name = new JLabel(c.getName()+" "+c.getType()+": "+c.getValue()+" cost: "+c.getCost());
//            cardPanel.add(name);
//        }
//        cardPanel.add(new JLabel("Back"));
//        cardPanel.setFocusable(true);
//        createControls(cardPanel, new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(selected!=cardPanel.getComponentCount())useCard(cardList.get(selected-1));
//                switchControlToOptionsMenu();
//            }
//        });
//    }
//
//    public void getPlayerPanel() {
//        playerPanel = new JPanel();
//        playerPanel.setLayout(new BoxLayout(playerPanel,BoxLayout.Y_AXIS));
//        //SpringLayout layout = new SpringLayout();
//        //playerPanel.setLayout(new SpringLayout());
//        JLabel playerName = new JLabel(fight.player.getName());
//        playerName.setFont(new Font("Verdana", Font.BOLD, 15));
//        JLabel playerHp = new JLabel("Life "+fight.player.getCurrentHp()+"/"+fight.player.getHp());
//        playerHp.setFont(new Font("Verdana", Font.BOLD, 18));
//        playerHp.setForeground(new Color(173,10,2));
//        JLabel playerDmg = new JLabel("Attack damage: "+fight.player.getDmg());
//        JLabel playerDef = new JLabel("Defense: "+fight.player.getDef());
//        String playerStatusText="";
//        for(Map.Entry<Card, Integer> entry : fight.playerStatus.entrySet()){
//            playerStatusText += entry.getKey().getType().toString()+": "+entry.getValue()+" ";
//        }
//        JLabel playerStatus = new JLabel(playerStatusText);
//
//        JPanel playerIcon = new JPanel();
//        playerIcon.setLayout(new BoxLayout(playerIcon,BoxLayout.Y_AXIS));
//        String[] iconList = Ansi.playerFight.split("\n");
//        for(String s : iconList){
//            JLabel l = new JLabel(s);
//            playerIcon.add(l);
//        }
//
//
//        playerStatus.setForeground(new Color(24,45,181));
//        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
//        playerHp.setAlignmentX(Component.CENTER_ALIGNMENT);
//        playerDmg.setAlignmentX(Component.CENTER_ALIGNMENT);
//        playerDef.setAlignmentX(Component.CENTER_ALIGNMENT);
//        playerStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
//        playerIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
//        playerPanel.add(playerName);
//        playerPanel.add(playerHp);
//        playerPanel.add(playerDmg);
//        playerPanel.add(playerDef);
//        playerPanel.add(playerIcon);
//        playerPanel.add(playerStatus);
//

//    }

//    public void getEnemyPanel() {
//        enemyPanel = new JPanel();
//        enemyPanel.setLayout(new BoxLayout(enemyPanel,BoxLayout.Y_AXIS));
//        JLabel enemyName = new JLabel(fight.enemy.getName());
//        enemyName.setFont(new Font("Verdana", Font.BOLD, 15));
//        JLabel enemyHp = new JLabel("Life "+fight.enemy.getHp());
//        enemyHp.setFont(new Font("Verdana", Font.BOLD, 18));
//        enemyHp.setForeground(new Color(173,10,2));
//        JLabel enemyDmg = new JLabel("Attack damage: "+fight.enemy.getDmg());
//        JLabel enemyDef = new JLabel("Defense: "+fight.enemy.getDefense());
//        String enemyStatusText="";
//        for(Map.Entry<Card, Integer> entry : fight.enemyStatus.entrySet()){
//            enemyStatusText += entry.getKey().getType().toString()+": "+entry.getValue()+" ";
//        }
//        JPanel enemyIcon = new JPanel();
//        enemyIcon.setLayout(new BoxLayout(enemyIcon,BoxLayout.Y_AXIS));
//        String[] iconList = Ansi.opponentFight.split("\n");
//        for(String s : iconList){
//            JLabel l = new JLabel(s);
//            enemyIcon.add(l);
//        }
//
//        JLabel enemyStatus = new JLabel(enemyStatusText);
//        enemyStatus.setForeground(new Color(0,145,12));
//        enemyName.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enemyHp.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enemyDmg.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enemyDef.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enemyStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enemyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
//        enemyPanel.add(enemyName);
//        enemyPanel.add(enemyHp);
//        enemyPanel.add(enemyDmg);
//        enemyPanel.add(enemyDef);
//        enemyPanel.add(enemyIcon);
//        enemyPanel.add(enemyStatus);
//    }
//
//    private void checkToEndBattle(int battleStatus) {
//        if(battleStatus == - 1 ) endGame();
//        if(battleStatus == 1 ) winBattle();
//    }
//
//    private void winBattle() {
//        fight.generateLootAndUpdatePlayer();
//        showLootScreen();
//    }
//
//    private void endGame() {
//        fight.looseBattleAndUpdatePlayer();
//        showFailureScreen();
//    }
//
//    private void showLootScreen() {
//        JPanel cont = new JPanel();
//        cont.setLayout(new BoxLayout(cont,BoxLayout.Y_AXIS));
//        JPanel loot = new JPanel();
//        JPanel exit = getBattleExitOptions(1);
//        for(String lootItem : fight.loot){
//            JLabel lootItemLabel = new JLabel(lootItem);
//            loot.add(lootItemLabel);
//        }
//        loot.setLayout(new BoxLayout(loot,BoxLayout.Y_AXIS));
//        cont.add(loot);
//        cont.add(exit);
//        gameController.setMainContent(cont);
//        exit.requestFocusInWindow();
//        fight.clearBattle();
//    }
////    private void showFailureScreen() {
//        JPanel cont = new JPanel();
//        JPanel lostMessagePanel = new JPanel();
//        JLabel lostMessage = new JLabel("You lost. Your character has been deleted");
//        lostMessagePanel.add(lostMessage);
//        JPanel exit = getBattleExitOptions(0);
//        cont.add(lostMessagePanel);
//        cont.add(exit);
//        gameController.setMainContent(cont);
//        exit.requestFocusInWindow();
//    }
//    private JPanel getBattleExitOptions(int battleOutcome){
//        JPanel p = new JPanel();
//        JLabel exit = new JLabel("exit");
//        p.add(exit);
//        createControls(p, new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(battleOutcome == 1) gameController.switchToProfileController();
//                if(battleOutcome == 0) gameController.switchToMenuController();
//            }
//        });
//        return p;

//    }


//    private void useCard(Card card) {
//        String message = fight.useCard(card);
//        logPanel.add(new JLabel(message));
//        fight.refreshStatus();
//        getPlayerPanel();
//        getEnemyPanel();
//        getCardPanel();
//        buildMainContainer();
//        checkToEndBattle(fight.checkEndBattleConditions());
//    }
//    public void attack(){
//        String message = fight.playerAttack();
//        logPanel.add(new JLabel(message));
//        getEnemyPanel();
//        getPlayerPanel();
//        buildMainContainer();
//        checkToEndBattle(fight.checkEndBattleConditions());
//    }
//    public void endTurn(){
//        String message;
//        message = fight.enemyTurn();
//        logPanel.add(new JLabel(message));
//        message = fight.nextTurn();
//        logPanel.add(new JLabel(message));
//        getTurnPanel();
//        getPlayerPanel();
//        getEnemyPanel();
//        buildMainContainer();
//        checkToEndBattle(fight.checkEndBattleConditions());

//    }
//    public void buildMainContainer(){
//        container = new JPanel();
//        container.setLayout(new GridLayout(2,3));

//        container.add(playerPanel);
//        container.add(turnPanel);
//        container.add(enemyPanel);
//        container.add(optionsPanel);
//        container.add(logPanel);
//        container.add(cardPanel);
//    }
//    public void getTurnPanel(){
//        turnPanel = new JPanel();
//        JLabel turnLabel = new JLabel("Turn: "+fight.turn);
//        turnPanel.add(turnLabel);
//    }
//
//    public void switchControlToCardMenu(){
//        selected = 1;
//        refreshColor(cardPanel);
//        cardPanel.requestFocusInWindow();
//    }
//    public void switchControlToOptionsMenu(){
//        selected = 1;
//        refreshColor(optionsPanel);
//        optionsPanel.requestFocusInWindow();
//    }
//
//
//    public void refreshColor(JPanel p) {
//        Component[] components = optionsPanel.getComponents();
//        for (Component c : components) {
//            c.setForeground(Color.BLACK);
//        }
//        components = cardPanel.getComponents();
//        for (Component c : components) {
//            c.setForeground(Color.BLACK);
//        }
//        p.getComponent(selected - 1).setForeground(Color.red);
//    }

//    public void createControls(JPanel p, AbstractAction action){
//        selected = 1;
//        refreshColor(p);
//        Action incrementSelection = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(selected < p.getComponentCount()) selected++;
//                refreshColor(p);
//            }
//        };
//        Action decrementSelection = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(selected != 1) selected--;
//                refreshColor(p);
//            }
//        };
//        p.getInputMap().put(KeyStroke.getKeyStroke("UP"),"pressedUp");
//        p.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"pressedDown");
//        p.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressedEnter");
//        p.getActionMap().put("pressedUp",decrementSelection);
//        p.getActionMap().put("pressedDown",incrementSelection);
//        p.getActionMap().put("pressedEnter",action);
//    }

//    public void generatePlayerAndEnemy(JPanel panel, String asciArt){
//        StringBuilder tmp = new StringBuilder();
//        for(int i=0; i<asciArt.length(); i++) {
//            char ch = asciArt.charAt(i);
//            if (ch == '\n') {
//                JLabel lejbel= new JLabel(tmp+"");
//                panel.add(lejbel);
//                tmp.delete(0,i);
//            }
//            tmp.append(ch);
//        }
//    }
}
