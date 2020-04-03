package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.BlockConsequence;
import it.polimi.ingsw.model.Actions.VictoryConsequence;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockOpponentPlayerTest {

    @Test
    void movePawn() {

        Board gameBoard = new Board();
        List<Cell> test = new ArrayList<>();

        BasicPlayer player1 = new BasicPlayer("test1", Color.BLUE, "test1");
        BuildBeforePlayer player_1 = new BuildBeforePlayer(player1);
        BasicPlayer player2 = new BasicPlayer("test2", Color.GREY, "test2");
        BuildBeforePlayer player_2 = new BuildBeforePlayer(player2);
        BasicPlayer player3 = new BasicPlayer("test3", Color.WHITE, "test3");
        BlockOpponentPlayer player_3 = new BlockOpponentPlayer(player3);

        player_1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player_1.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));
        player_2.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,1));
        player_2.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(1,2));
        player_3.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player_3.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,3));

        /* case when the pawn moves up but don't win, so it has to return BlockConsequence */

        gameBoard.getCell(2,1).setHeight(1);

        assertEquals(BlockConsequence.class, player_3.movePawn(gameBoard, player_3.getPawns()[0], gameBoard.getCell(2,1)).getClass());

        /* case when the pawn moves up to level 3, so the player wins, so the method must not return BlockConsequence, but VictoryConsequence
        * P.S.: the pawn moved is now in cell [2,1]
        * */

        gameBoard.getCell(3,1).setHeight(2);
        gameBoard.getCell(3,2).setHeight(3);

        player_3.movePawn(gameBoard,player_3.getPawns()[0], gameBoard.getCell(3,1));

        assertEquals(VictoryConsequence.class, player_3.movePawn(gameBoard,player_3.getPawns()[0], gameBoard.getCell(3,2)).getClass());



    }
}