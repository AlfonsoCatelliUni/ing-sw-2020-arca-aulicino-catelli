package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Consequence.BlockConsequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockOpponentPlayerTest {

    Board gameBoard;

    List<Action> test;

    Card card;

    BasicPlayer player1;

    BasicPlayer player2;

    BasicPlayer player3;

    BlockOpponentPlayer player_1;

    BlockOpponentPlayer player_2;

    BlockOpponentPlayer player_3;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        test = new ArrayList<>();
        card = new Card("test", true, "test");

        player1 = new BasicPlayer("test1", Color.BLUE, card);
        player_1 = new BlockOpponentPlayer(player1);
        player2 = new BasicPlayer("test2", Color.GREY, card);
        player_2 = new BlockOpponentPlayer(player2);
        player3 = new BasicPlayer("test3", Color.WHITE, card);
        player_3 = new BlockOpponentPlayer(player3);

        player_1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player_1.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));
        player_2.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,1));
        player_2.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(1,2));
        player_3.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player_3.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,3));

    }

    @Test
    void movePawn() {

        /* case when the pawn moves up but don't win, so it has to return BlockConsequence */

        gameBoard.getCell(2,1).setHeight(1);

        assertEquals(BlockConsequence.class, player_3.move(gameBoard, player_3.getPawns()[0], gameBoard.getCell(2,1)).getClass());

        /* case when the pawn moves up to level 3, so the player wins, so the method must not return BlockConsequence, but VictoryConsequence
        * P.S.: the pawn moved is now in cell [2,1]
        * */

        gameBoard.getCell(3,1).setHeight(2);
        gameBoard.getCell(3,2).setHeight(3);

        player_3.move(gameBoard,player_3.getPawns()[0], gameBoard.getCell(3,1));

        assertEquals(VictoryConsequence.class, player_3.move(gameBoard,player_3.getPawns()[0], gameBoard.getCell(3,2)).getClass());



    }
}