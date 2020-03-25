package it.polimi.ingsw.model.BoardPack;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getCellAvailableToMove() {

    }

    @Test
    void getCellAvailableToBuild() {

        Board gameBoard = new Board();
        Cell cell1 = gameBoard.getCell(0,0);
        Pawn pawn = new Pawn(Color.BLUE, Sex.MALE, gameBoard.getCell(0,0));
        cell1.placePawnHere(pawn);
        ArrayList<Cell> cells = gameBoard.getCellAvailableToBuild(pawn);
        ArrayList<Cell> expectedCells = new ArrayList<>();

        expectedCells.add(gameBoard.getCell(0,1));
        expectedCells.add(gameBoard.getCell(1,0));
        expectedCells.add(gameBoard.getCell(1,1));
        assertEquals(cells, expectedCells);

        // pedina al centro con pedina vicino e cupola
        Pawn pawn1 = new Pawn(Color.GREY,Sex.FEMALE, gameBoard.getCell(1,2));
        gameBoard.getCell(1,2).placePawnHere(pawn1);
        gameBoard.getCell(2,3).buildOnThisCell(new Building(4,18));
        gameBoard.getCell(3,3).buildOnThisCell(new Building(3,22));

        pawn.moveTo(gameBoard.getCell(2,2));
        gameBoard.getCell(2,2).placePawnHere(pawn);
        cells = gameBoard.getCellAvailableToBuild(pawn);

        ArrayList<Cell> expectedCells2 = new ArrayList<>();
        expectedCells2.add(gameBoard.getCell(1,1));
        expectedCells2.add(gameBoard.getCell(1,3));
        expectedCells2.add(gameBoard.getCell(2,1));
        expectedCells2.add(gameBoard.getCell(3,1));
        expectedCells2.add(gameBoard.getCell(3,2));
        expectedCells2.add(gameBoard.getCell(3,3));
        assertEquals(cells, expectedCells2);

        //lista vuota
        gameBoard.getCell(3,1).buildOnThisCell(new Building(4,18));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(4,18));
        gameBoard.getCell(3,3).buildOnThisCell(new Building(4,18));
        Pawn pawn2 = new Pawn(Color.GREY,Sex.FEMALE, gameBoard.getCell(1,3));
        gameBoard.getCell(1,3).placePawnHere(pawn2);
        Pawn pawn3 = new Pawn(Color.GREY,Sex.FEMALE, gameBoard.getCell(2,1));
        gameBoard.getCell(2,1).placePawnHere(pawn3);
        gameBoard.getCell(3,2).buildOnThisCell(new Building(4,18));

        cells = gameBoard.getCellAvailableToBuild(pawn);
        ArrayList<Cell> expectedCells3 = new ArrayList<>();
        assertEquals(cells, expectedCells3);







    }

    @Test
    void getNeighboring() {
    }

    @Test
    void getPossibleBuildingOnCell() {
    }

    @Test
    void getNumberOfDome() {
    }

    @Test
    void getStringCellInfo() {
    }
}