package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushEffectTest {

    private Board gameBoard;

    private Player player;

    private Player opponentPlayer;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        player = new Player("Player", Color.BLUE, new Card("Minotauro",true, "effect"),new PushEffect(new BasicEffect()));

        opponentPlayer = new Player();

    }


    @Test
    void move() {

        player.initPawn(gameBoard, gameBoard.getCell(2,2));
        opponentPlayer.initPawn(gameBoard, gameBoard.getCell(1,2));

        player.move(gameBoard, gameBoard.getPawnByCoordinates(2,2), gameBoard.getCell(1,2));

        assertEquals(true, gameBoard.getCell(0,2).isPawnHere());
        assertEquals(true, gameBoard.getCell(1,2).isPawnHere());
        assertEquals(false, gameBoard.getCell(2,2).isPawnHere());
        assertEquals(true, opponentPlayer.getPawnInCoordinates(0,2).getForcedMove());

        //statement coverage: basic movePawn if there is no builder in designatedCell
        player.move(gameBoard, gameBoard.getPawnByCoordinates(1,2), gameBoard.getCell(1,3));
        assertEquals(false, gameBoard.getCell(1,2).isPawnHere());
        assertEquals(true, gameBoard.getCell(1,3).isPawnHere());
    }


}