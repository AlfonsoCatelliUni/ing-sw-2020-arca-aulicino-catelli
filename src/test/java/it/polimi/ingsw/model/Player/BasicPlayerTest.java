package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BasicPlayerTest {


    /**
     * the cell startCell is not occupied by any pawn before the call of the method,
     * this is checked in the controller section
     */
    @Test
    void initPawn() {

        Board gameBoard = new Board();
        Player player = new BasicPlayer("test", Color.BLUE, "test");

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        assertEquals(gameBoard.getCell(0,0), player.getPawns()[0].getPosition());
        assertEquals(Sex.MALE, player.getPawns()[0].getSex());
        assertEquals(Color.BLUE, player.getPawns()[0].getColor());

        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));

        assertEquals(gameBoard.getCell(0,1), player.getPawns()[1].getPosition());
        assertEquals(Sex.FEMALE, player.getPawns()[1].getSex());
        assertEquals(Color.BLUE, player.getPawns()[1].getColor());

        /* once the pawns are initialized, a new initPawn must change nothing */

        Pawn oldPawnMale = player.getPawns()[0];
        Pawn oldPawnFemale = player.getPawns()[1];

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,2));

        assertEquals(oldPawnMale, player.getPawns()[0]);

        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,3));

        assertEquals(oldPawnFemale, player.getPawns()[1]);

    }


    @Test
    void movePawn() {

    }


    @Test
    void pawnBuild() {
    }


    @Test
    void forcePawn() {
    }


    @Test
    void wherePawnCanMove() {

        Board gameBoard = new Board();
        Player player = new BasicPlayer("test", Color.BLUE, "test");

        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));

        Pawn[] pawns = player.getPawns();
        ArrayList<Cell> availableCellsToMove;
        ArrayList<Cell> correctCellsToMove = new ArrayList<>();


        /* simple corner case */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[0]);
        correctCellsToMove.add(gameBoard.getCell(0,1));
        correctCellsToMove.add(gameBoard.getCell(1,0));
        correctCellsToMove.add(gameBoard.getCell(1,1));
        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* simple center case */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[1]);
        correctCellsToMove.add(gameBoard.getCell(1,1));
        correctCellsToMove.add(gameBoard.getCell(1,2));
        correctCellsToMove.add(gameBoard.getCell(1,3));
        correctCellsToMove.add(gameBoard.getCell(2,1));
        correctCellsToMove.add(gameBoard.getCell(2,3));
        correctCellsToMove.add(gameBoard.getCell(3,1));
        correctCellsToMove.add(gameBoard.getCell(3,2));
        correctCellsToMove.add(gameBoard.getCell(3,3));

        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* corner case, cannot move up, there's a dome in a cell and a level one in another cell*/
        player.setCanMoveUp(false);
        gameBoard.getCell(1,1).buildOnThisCell(levelFour);
        gameBoard.getCell(0,1).buildOnThisCell(levelOne);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[0]);
        correctCellsToMove.add(gameBoard.getCell(1,0));

        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* center case, surrounded by level one building and cannot move up */
        player.setCanMoveUp(false);
        gameBoard = new Board();
        gameBoard.getCell(1,1).buildOnThisCell(levelOne);
        gameBoard.getCell(1,2).buildOnThisCell(levelOne);
        gameBoard.getCell(1,3).buildOnThisCell(levelOne);
        gameBoard.getCell(2,1).buildOnThisCell(levelOne);
        gameBoard.getCell(2,3).buildOnThisCell(levelOne);
        gameBoard.getCell(3,1).buildOnThisCell(levelOne);
        gameBoard.getCell(3,1).buildOnThisCell(levelTwo);
        gameBoard.getCell(3,1).buildOnThisCell(levelThree);
        gameBoard.getCell(3,2).buildOnThisCell(levelOne);
        gameBoard.getCell(3,2).buildOnThisCell(levelTwo);
        gameBoard.getCell(3,2).buildOnThisCell(levelThree);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[1]);
        correctCellsToMove.add(gameBoard.getCell(3,3));
        assertEquals(correctCellsToMove, availableCellsToMove);

        correctCellsToMove.clear();



    }


    @Test
    void wherePawnCanBuild() {
    }


    @Test
    void getPossibleBuildingOnCell() {
    }


    @Test
    void removePawn() {

        Board gameBoard = new Board();
        Player player = new BasicPlayer("test", Color.BLUE, "test");
        Cell cell = gameBoard.getCell(0,0);

        player.initPawn(gameBoard, Sex.MALE, cell);

        //pawn is in the cell [0,0]
        assertEquals(gameBoard.getCell(0,0).getPawnInThisCell(), player.getPawns()[0]);

        //remove pawn from [0,0]
        player.removePawn(gameBoard, player.getPawns()[0]);

        //now the pawn is not in the cell [0,0]
        assertNotEquals(gameBoard.getCell(0,0).getPawnInThisCell(), player.getPawns()[0]);


    }


    /**
     * the attribute position in the pawn is not modified in this method, but in the moveTo
     * method of class Pawn
     * this method only modifies the designatedCell
     */
    @Test
    void placePawn() {

        Board gameBoard = new Board();
        Player player = new BasicPlayer("test", Color.BLUE, "test");
        Cell cell = gameBoard.getCell(0,0);

        player.initPawn(gameBoard, Sex.MALE, cell);

        player.removePawn(gameBoard, player.getPawns()[0]);

        player.placePawn(gameBoard, player.getPawns()[0], gameBoard.getCell(1,1));

        assertEquals(gameBoard.getCell(1,1).getPawnInThisCell(), player.getPawns()[0]);

    }


    @Test
    void getPossibleAction() {
    }


}