package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Effect.BasicEffect;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Board gameBoard;

    private Effect alfoEffect;
    private Effect massiEffect;

    private Card alfoCard;
    private Card massiCard;

    private Player alfoPlayer;
    private Player massiPlayer;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();


        alfoEffect = new BasicEffect();
        alfoCard = new Card("alfo_card", true, "alfo_effect");
        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");
        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void resetPlayerStatus() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void getPossibleActions() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void wherePawnCanMove() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void wherePawnCanBuild() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void getPossibleBuildingOnCell() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void wherePawnCanForce() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void wherePawnCanDestroy() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void move() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void build() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void force() {
    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void destroy() {
    }

    @Test
    void initPawn() {

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));

        assertEquals(gameBoard.getCell(0,0), alfoPlayer.getPawns().get(0).getPosition());
        assertEquals(Sex.MALE, alfoPlayer.getPawns().get(0).getSex());
        assertEquals(Color.BLUE, alfoPlayer.getPawns().get(0).getColor());

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,1));

        assertEquals(gameBoard.getCell(0,1), alfoPlayer.getPawns().get(1).getPosition());
        assertEquals(Sex.FEMALE, alfoPlayer.getPawns().get(1).getSex());
        assertEquals(Color.BLUE, alfoPlayer.getPawns().get(1).getColor());

        /* once the pawns are initialized, a new initPawn must change nothing */

        Pawn oldPawnMale = alfoPlayer.getPawns().get(0);
        Pawn oldPawnFemale = alfoPlayer.getPawns().get(1);


        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,2));

        assertEquals(oldPawnMale, alfoPlayer.getPawns().get(0));

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,3));

        assertEquals(oldPawnFemale, alfoPlayer.getPawns().get(1));


    }

    /**
     * this method only calls the same method in player.effect
     */
    @Test
    void placePawn() {
    }

    @Test
    void removePawn() {
    }
}