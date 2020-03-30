package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildBeforePlayerTest {

    @Test
    void getPossibleAction() {

        Board gameBoard = new Board();
        ArrayList<String> test = new ArrayList<>();

        BasicPlayer player1 = new BasicPlayer("test1", Color.BLUE, "test1");
        BuildBeforePlayer player_1 = new BuildBeforePlayer(player1);
        BasicPlayer player2 = new BasicPlayer("test2", Color.GREY, "test2");
        BuildBeforePlayer player_2 = new BuildBeforePlayer(player2);
        BasicPlayer player3 = new BasicPlayer("test3", Color.WHITE, "test3");
        BuildBeforePlayer player_3 = new BuildBeforePlayer(player3);

        player_1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player_1.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));
        player_2.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,1));
        player_2.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(1,2));
        player_3.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player_3.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,3));

        /* case when the pawn can move and build only in the same cell and cell.getHeight is >= pawn.getzPosition */

        test.add("move");

        assertEquals(test, player_1.getPossibleAction(gameBoard, player_1.getPawns()[0]));

        /* case when the pawn can build in more than one cell with the same height of the pawn */

        test.clear();
        test.add("build");
        test.add("move");

        assertEquals(test, player_1.getPossibleAction(gameBoard, player_1.getPawns()[1]));

        /* case when the pawn can move and build only in the same cell and cell.getHeight is < pawn.getzPosition */

        gameBoard.getCell(0,0).setHeight(1);
        player_1.getPawns()[0].setzPosition(1);

        assertEquals(test, player_1.getPossibleAction(gameBoard, player_1.getPawns()[0]));

        /* case when the player has built the first time, so he can only move */

        player_1.setHasBuiltBefore(true);

        test.clear();
        test.add("move");

        assertEquals(test, player_1.getPossibleAction(gameBoard, player_1.getPawns()[1]));

        /* case when the pawn moved, so he can only build */

        player_1.setNumMove(1);
        player_1.setNumBuild(0);

        test.clear();
        test.add("build");

        assertEquals(test, player_1.getPossibleAction(gameBoard, player_1.getPawns()[1]));

        /* case when the player has build for the second time, so he has to finish his turn */

        player_1.setNumBuild(1);

        test.clear();
        test.add("Finish");

        assertEquals(test, player_1.getPossibleAction(gameBoard, player_1.getPawns()[1]));




    }

    @Test
    void wherePawnCanBuild() {
    }

    @Test
    void wherePawnCanMove() {
    }

    @Test
    void pawnBuild() {
    }
}