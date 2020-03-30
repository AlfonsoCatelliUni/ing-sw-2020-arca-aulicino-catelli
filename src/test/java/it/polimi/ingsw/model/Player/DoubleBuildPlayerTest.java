package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DoubleBuildPlayerTest {

    @Test
    void getPossibleAction() {

        ArrayList<String> possibleActions;
        ArrayList<String> expectedActions = new ArrayList<>();

        Board gameBoard = new Board();
        //Demeter
        DoubleBuildPlayer player = new DoubleBuildPlayer( new BasicPlayer("test", Color.BLUE, "Demeter"),0);
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        possibleActions = player.getPossibleAction(gameBoard,player.getPawns()[0]);
        expectedActions.add("move");

        // numMove is 0 and NumBuild is 1
        assertEquals(possibleActions, expectedActions);

        player.setNumMove(1);
        player.setNumBuild(0);
        expectedActions.remove("move");
        expectedActions.add("build");

        possibleActions = player.getPossibleAction(gameBoard,player.getPawns()[0]);
        assertEquals(expectedActions, possibleActions);

        player.setNumBuild(1);
        expectedActions.add("finish");

        possibleActions = player.getPossibleAction(gameBoard,player.getPawns()[0]);
        assertEquals(expectedActions, possibleActions);

        player.setNumBuild(2);
        expectedActions.remove("build");

        possibleActions = player.getPossibleAction(gameBoard,player.getPawns()[0]);
        assertEquals(expectedActions, possibleActions);

        //non può costruire la seconda volta
        gameBoard.getCell(0,1).buildOnThisCell(new Building(1, 18));
        gameBoard.getCell(1,0).buildOnThisCell(new Building(4,18));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(4,18));

        player.setCellBefore(gameBoard.getCell(0,1));

        player.setNumMove(1);
        player.setNumBuild(1);

        possibleActions = player.getPossibleAction(gameBoard,player.getPawns()[0]);
        assertEquals(expectedActions, possibleActions);

        //Hephaestus
        DoubleBuildPlayer player1 = new DoubleBuildPlayer( new BasicPlayer("test", Color.GREY, "Hephaestus"),1);
        player1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player1.setNumMove(1);
        player1.setNumBuild(1);
        player1.setCellBefore(gameBoard.getCell(2,1));

        possibleActions = player1.getPossibleAction(gameBoard,player1.getPawns()[0]);
        expectedActions.clear();
        expectedActions.add("build");
        expectedActions.add("finish");
        assertEquals(expectedActions, possibleActions);

        gameBoard.getCell(2,1).buildOnThisCell(new Building(4,18));
        player1.setCellBefore(gameBoard.getCell(2,1));

        expectedActions.remove("build");
        possibleActions = player1.getPossibleAction(gameBoard,player1.getPawns()[0]);
        assertEquals(expectedActions, possibleActions);

    }

    @Test
    void wherePawnCanBuild() {
    }

    @Test
    void pawnBuild() {
    }
}