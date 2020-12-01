package com.dungeonhunters.dungeonhunters.controller;

import com.dungeonhunters.dungeonhunters.Ansi;
import com.dungeonhunters.dungeonhunters.dto.Fight;
import com.dungeonhunters.dungeonhunters.model.Card;
import com.dungeonhunters.dungeonhunters.model.Player;
import com.dungeonhunters.dungeonhunters.service.DeckService;
import com.dungeonhunters.dungeonhunters.service.EnemyService;
import org.springframework.stereotype.Controller;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static com.dungeonhunters.dungeonhunters.Ansi.opponentFight;
import static com.dungeonhunters.dungeonhunters.Ansi.playerFight;

@Controller
public class FightController extends JFrame {
    public GameController gameController;
    public final EnemyService enemyService;
    private final DeckService deckService;
    public final Fight fight;
    public int selected = 1;
    public JPanel container;
    public JPanel optionsPanel;
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
        generateEnemy();
        //-------------------te dwa panele juz powinny miec obrazki-------------------//
        JPanel playerFightPanel = new JPanel();
        JPanel opponentFightPanel = new JPanel();
        playerFightPanel.setLayout(new BoxLayout(playerFightPanel, BoxLayout.Y_AXIS));
        opponentFightPanel.setLayout(new BoxLayout(opponentFightPanel, BoxLayout.Y_AXIS));
        generatePlayerAndEnemy(playerFightPanel, playerFight);
        generatePlayerAndEnemy(opponentFightPanel, opponentFight);
        //----------------------------------------------------------------------------//
        fight.nextTurn();
        logPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        getTurnPanel();
        getPlayerPanel();
        getEnemyPanel();
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel,BoxLayout.Y_AXIS));
        JLabel attackOption = new JLabel("Attack");
        JLabel defendOption = new JLabel("Defend");
        JLabel useCardOption = new JLabel("Use card");
        JLabel endTurnOption = new JLabel("End turn");
        optionsPanel.add(attackOption);
        optionsPanel.add(defendOption);
        optionsPanel.add(useCardOption);
        optionsPanel.add(endTurnOption);

        getCardPanel();
        createControls(optionsPanel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected == 1) attack();
                if(selected == 2) defend();
                if(selected == 3) switchControlToCardMenu();
                if(selected == 4) endTurn();
            }
        });
        optionsPanel.setFocusable(true);
        buildMainContainer();
    }

    public void getCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel,BoxLayout.Y_AXIS));
        List<Card> cardList = deckService.getDeckById(fight.player.getDeck().getId()).getCardSet();
        for(Card c : cardList){
            JLabel name = new JLabel(c.getName()+" dmg: "+c.getValue());
            cardPanel.add(name);
        }
        cardPanel.add(new JLabel("I choose nothing"));
        cardPanel.setFocusable(true);
        createControls(cardPanel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected!=cardPanel.getComponentCount())useCard(cardList.get(selected-1));
                switchControlToOptionsMenu();
            }
        });
    }

    public void getPlayerPanel() {
        playerPanel = new JPanel();
        JLabel playerName = new JLabel(fight.player.getName());
        JLabel playerStats = new JLabel(
                "HP: "+fight.player.getCurrentHp()+"/"+fight.player.getHp()+" DMG: "+
                        fight.player.getDmg()+" DEF: "+fight.player.getDef()
        );
        playerPanel.add(playerName);
        playerPanel.add(playerStats);
    }

    private void checkToEndBattle(int battleStatus) {
        if(battleStatus == - 1 ) endGame();
        if(battleStatus == 1 ) winBattle();
    }

    private void winBattle() {
        fight.generateLootAndUpdatePlayer();
        showLootScreen();
    }

    private void endGame() {
        fight.looseBattleAndUpdatePlayer();
        showFailureScreen();
    }

    private void showLootScreen() {
        JPanel cont = new JPanel();
        cont.setLayout(new BoxLayout(cont,BoxLayout.Y_AXIS));
        JPanel loot = new JPanel();
        JPanel exit = getBattleExitOptions(1);
        for(String lootItem : fight.loot){
            JLabel lootItemLabel = new JLabel(lootItem);
            loot.add(lootItemLabel);
        }
        loot.setLayout(new BoxLayout(loot,BoxLayout.Y_AXIS));
        cont.add(loot);
        cont.add(exit);
        gameController.setMainContent(cont);
        exit.requestFocusInWindow();
        fight.clearBattle();
    }

    private void showFailureScreen() {
        JPanel cont = new JPanel();
        JPanel lostMessagePanel = new JPanel();
        JLabel lostMessage = new JLabel("You lost. Your character has been deleted");
        lostMessagePanel.add(lostMessage);
        JPanel exit = getBattleExitOptions(0);
        cont.add(lostMessagePanel);
        cont.add(exit);
        gameController.setMainContent(cont);
        exit.requestFocusInWindow();
    }
    private JPanel getBattleExitOptions(int battleOutcome){
        JPanel p = new JPanel();
        JLabel exit = new JLabel("exit");
        p.add(exit);
        createControls(p, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(battleOutcome == 1) gameController.switchToProfileController();
                if(battleOutcome == 0) gameController.switchToMenuController();
            }
        });
        return p;
    }
    private void generateEnemy() {
        fight.createEnemy();
    }

    private void useCard(Card card) {
        String message = fight.useCard(card);
        logPanel.add(new JLabel(message));
        getPlayerPanel();
        getEnemyPanel();
        getCardPanel();
        buildMainContainer();
        checkToEndBattle(fight.checkEndBattleConditions());
    }

    public void attack(){
        String message = fight.playerAttack();
        logPanel.add(new JLabel(message));
        getEnemyPanel();
        getPlayerPanel();
        buildMainContainer();
        checkToEndBattle(fight.checkEndBattleConditions());
    }
    public void defend(){
        String message = fight.playerDefend();
        logPanel.add(new JLabel(message));
        buildMainContainer();
    }
    public void endTurn(){
        String message;
        message = fight.enemyTurn();
        logPanel.add(new JLabel(message));
        message = fight.nextTurn();
        logPanel.add(new JLabel(message));
        getTurnPanel();
        getPlayerPanel();
        getEnemyPanel();
        buildMainContainer();
        checkToEndBattle(fight.checkEndBattleConditions());
    }
    public void buildMainContainer(){
        container = new JPanel();
        container.setLayout(new GridLayout(2,3));
        container.add(playerPanel);
        container.add(turnPanel);
        container.add(enemyPanel);
        container.add(optionsPanel);
        container.add(logPanel);
        container.add(cardPanel);
        gameController.setMainContent(container);
        optionsPanel.requestFocusInWindow();
    }

    public void getTurnPanel(){
        turnPanel = new JPanel();
        JLabel turnLabel = new JLabel("Turn: "+fight.turn);
        turnPanel.add(turnLabel);
    }
    public void getEnemyPanel() {
        enemyPanel = new JPanel();
        JLabel enemyName = new JLabel(fight.enemy.getName());
        JLabel enemyLife = new JLabel("HP: "+fight.enemy.getHp()+"/"+fight.enemyMaxHp+" DMG: "+
                fight.enemy.getDmg()+" DEF: "+fight.enemy.getDefense());
        enemyPanel.add(enemyName);
        enemyPanel.add(enemyLife);
    }

    public void switchControlToCardMenu(){
        selected = 1;
        refreshColor(cardPanel);
        cardPanel.requestFocusInWindow();
    }
    public void switchControlToOptionsMenu(){
        selected = 1;
        refreshColor(optionsPanel);
        optionsPanel.requestFocusInWindow();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.fight.setPlayer(player);
    }
    public void refreshColor(JPanel p) {
        Component[] components = optionsPanel.getComponents();
        for (Component c : components) {
            c.setForeground(Color.BLACK);
        }
        components = cardPanel.getComponents();
        for (Component c : components) {
            c.setForeground(Color.BLACK);
        }
        p.getComponent(selected - 1).setForeground(Color.red);
    }

    public void createControls(JPanel p, AbstractAction action){
        selected = 1;
        refreshColor(p);
        Action incrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected < p.getComponentCount()) selected++;
                refreshColor(p);
            }
        };
        Action decrementSelection = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selected != 1) selected--;
                refreshColor(p);
            }
        };
        p.getInputMap().put(KeyStroke.getKeyStroke("UP"),"pressedUp");
        p.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"pressedDown");
        p.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressedEnter");
        p.getActionMap().put("pressedUp",decrementSelection);
        p.getActionMap().put("pressedDown",incrementSelection);
        p.getActionMap().put("pressedEnter",action);
    }

    public void generatePlayerAndEnemy(JPanel panel, String asciArt){
        StringBuilder tmp = new StringBuilder();
        for(int i=0; i<asciArt.length(); i++) {
            char ch = asciArt.charAt(i);
            if (ch == '\n') {
                JLabel lejbel= new JLabel(tmp+"");
                panel.add(lejbel);
                tmp.delete(0,i);
            }
            tmp.append(ch);
        }
    }
}