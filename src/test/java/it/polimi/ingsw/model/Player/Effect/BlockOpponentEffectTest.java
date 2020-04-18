package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.BlockConsequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockOpponentEffectTest {

    Board gameBoard;

    List<Action> test;

    Card card;

    Player player1;

    Player player2;

    Player player3;



    @BeforeEach
    void setUp() {
        gameBoard = new Board();
        test = new ArrayList<>();
        card = new Card("test", true, "test");

        player1 = new Player("nome", Color.BLUE, card, new BlockOpponentEffect(new BasicEffect()));

        player2 = new Player("nome", Color.WHITE, card, new BlockOpponentEffect(new BasicEffect()));

        player3 = new Player("nome", Color.GREY, card, new BlockOpponentEffect(new BasicEffect()));


        player1.initPawn(gameBoard, gameBoard.getCell(0,0));
        player1.initPawn(gameBoard, gameBoard.getCell(0,1));
        player2.initPawn(gameBoard, gameBoard.getCell(1,1));
        player2.initPawn(gameBoard, gameBoard.getCell(1,2));
        player3.initPawn(gameBoard, gameBoard.getCell(2,2));
        player3.initPawn(gameBoard, gameBoard.getCell(2,3));
    }

    @Test
    void move() {
        /* case when the pawn moves up but don't win, so it has to return BlockConsequence */

        gameBoard.getCell(2,1).setHeight(1);

        assertEquals(BlockConsequence.class, player3.move(gameBoard, gameBoard.getPawnByCoordinates(2,2), gameBoard.getCell(2,1)).getClass());

        /* case when the pawn moves up to level 3, so the player wins, so the method must not return BlockConsequence, but VictoryConsequence
         * P.S.: the pawn moved is now in cell [2,1]
         * */

        gameBoard.getCell(3,1).setHeight(2);
        gameBoard.getCell(3,2).setHeight(3);

        player3.move(gameBoard,gameBoard.getPawnByCoordinates(2,1), gameBoard.getCell(3,1));

        assertEquals(VictoryConsequence.class, player3.move(gameBoard,player3.getPawns().get(0), gameBoard.getCell(3,2)).getClass());



    }
}