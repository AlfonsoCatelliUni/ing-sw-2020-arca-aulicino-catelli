package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Consequence.BlockConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class GameTest {

    private Game game;

    private Board gameBoard;

    private List<Player> players;

    private Effect alfoEffect;
    private Effect massiEffect;
    private Effect giammaEffect;

    private Card alfoCard;
    private Card massiCard;
    private Card giammaCard;

    private Player alfoPlayer;
    private Player massiPlayer;
    private Player giammaPlayer;

    private List<Building> buildings;


    @BeforeEach
    void setUp() {
        game = new Game("Alfonso", "Massi");
        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        alfoEffect = new CanPushOpponentEffect(new SwitchEffect(alfoEffect));
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));


        massiEffect = new BasicEffect();
        massiEffect = new CanPushOpponentEffect(new PushEffect(massiEffect));
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(0,4));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,1));

    }


    // ======================================================================================


    /**
     * this method calls only the wherePawnCanMove from Player class that's already tested
     */
    @Test
    void wherePawnCanMove() {
    }


    /**
     * this method calls only the wherePawnCanBuild from Player class that's already tested
     */
    @Test
    void wherePawnCanBuild() {
    }


    /**
     * this method calls only the getPossibleBuildingOnCell from Player class that's already tested
     */
    @Test
    void getPossibleBuildingOnCell() {
    }


    /**
     * this method calls only the getPossibleActions from Player class that's already tested
     */
    @Test
    void getPossibleActions() {
    }


    /**
     * this method calls only the getPawnsCoordinates from Player class that's already tested
     */
    @Test
    void getPawnsCoordinates() {
    }


    @Test
    void movePawn() {
    }


    /**
     * this method calls only the pawnBuild from Player class that's already tested
     */
    @Test
    void pawnBuild() {
    }


    @Test
    void newCurrentPlayer() {
    }


    @Test
    void removePlayer() {
    }


    @Test
    void receiveConsequence() {

        //TODO : dovrebbe andare ma causa test Decoratore Statico non funzionanti non sono riuscito a farlo runnare

        for(Effect e = alfoPlayer.getEffect(); !e.getClass().equals(BasicEffect.class); e = e.getEffect()) {
            assertNotEquals(NotMoveUpEffect.class, e.getClass());
        }

        game.receiveConsequence(new BlockConsequence("massi"));

        assertEquals(NotMoveUpEffect.class, alfoPlayer.getEffect().getClass());

        for(Effect e = massiPlayer.getEffect(); !e.getClass().equals(BasicEffect.class); e = e.getEffect()) {
            assertNotEquals(NotMoveUpEffect.class, e.getClass());
        }

    }


    @Test
    void doConsequence() {
    }

    @Test
    void testDoConsequence() {
    }


    @Test
    void testDoConsequence1() {
    }


    @Test
    void testNewCurrentPlayer() {
    }


    @Test
    void nextCurrentPlayer() {
    }


    @Test
    void getPlayerByName() {
    }


    @Test
    void generateStatusJson() {

        String provaJson;

        game.pawnBuild("Alfonso", 0,0,1,0,1);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

        game.pawnBuild("Alfonso", 0,0,1,0,2);
        game.pawnBuild("Alfonso", 0,0,1,0,3);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

        game.pawnBuild("Alfonso", 0,0,1,0,4);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

        game.pawnBuild("Alfonso", 0,0,1,1,4);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

    }


    /**
     * this method only calls the destroyBlock in player
     */
    @Test
    void destroyBlock() {
    }


    /**
     * this method only calls the wherePawnCanDestroy in player
     */
    @Test
    void wherePawnCanDestroy() {
    }

}