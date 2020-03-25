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

        Board gameBoard = new Board();

        Cell cell_0 = gameBoard.getCell(0,0);
        cell_0.setHeight(0);

        Pawn designatedPawn = new Pawn(Color.BLUE, Sex.MALE, cell_0);

        cell_0.placePawnHere(designatedPawn);

        Cell cell_1 = gameBoard.getCell(0,1);
        Cell cell_2 = gameBoard.getCell(1,0);
        Cell cell_3 = gameBoard.getCell(1,1);

        cell_1.setHeight(0);
        cell_2.setHeight(1);
        cell_3.setHeight(0);

        ArrayList<Cell> cells = gameBoard.getCellAvailableToMove(designatedPawn);

        ArrayList<Cell> testCell = new ArrayList<>();
        testCell.add(cell_1);
        testCell.add(cell_2);
        testCell.add(cell_3);

        //case where the pawn can be moved in all the neighboring cells
        assertEquals(testCell, cells);

        cell_2.setHeight(2);
        testCell.remove(cell_2);

        cells = gameBoard.getCellAvailableToMove(designatedPawn);

        //case where the pawn can be moved in two of the neighboring cells [1,0]
        assertEquals(testCell, cells);

        Pawn addedPawn = new Pawn(Color.GREY, Sex.FEMALE, cell_1);

        cell_1.placePawnHere(addedPawn);

        cells = gameBoard.getCellAvailableToMove(designatedPawn);

        testCell.remove(cell_1);

        //case where there is a pawn next to the designatedPawn (in [0,1]), so the pawn can be moved only in [1,1]
        assertEquals(testCell, cells);

        cell_3.buildOnThisCell(new Building(4,18));

        cells = gameBoard.getCellAvailableToMove(designatedPawn);

        testCell.remove(cell_3);

        //case where a dome is built in a cell where the designatedPawn can be moved to + the pawn cannot move (empty list)
        assertEquals(testCell, cells);
    }

    @Test
    void getCellAvailableToBuild() {
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
}