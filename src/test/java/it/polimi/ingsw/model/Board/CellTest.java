package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CellTest {


    @Test
    void getRoof() {

        Cell designatedCell = new Cell(1,1);
        Building roof = designatedCell.getRoof();

        int levelTypeRoof = roof.getLevel();
        assertEquals(0, levelTypeRoof);

    }


    /**
     * testing if a only initialized cell is considered free and
     * if adding and removing a pawn a cell set it free
     */
    @Test
    void getIsFree() {

        Cell designatedCell = new Cell(1,1);

        assertEquals(true, designatedCell.getIsFree());

        Pawn designatedPawn = new Pawn(Color.BLUE, Sex.MALE, designatedCell);

        designatedCell.placePawnHere(designatedPawn);
        /* method placePawnHere has been tested before freeCell method */

        assertEquals(false, designatedCell.getIsFree());

        designatedCell.freeCell();

        assertEquals(true, designatedCell.getIsFree());

    }


    @Test
    void isPerimeter() {

        Board gameBoard = new Board();

        assertEquals(0, gameBoard.getCell(0,0).getRowPosition());
        assertEquals(0, gameBoard.getCell(0,0).getColumnPosition());


        /* case the cell is perimetral */

        for(int i = 0; i < 5; i++)
           assertEquals(true, gameBoard.getCell(i, 0).isPerimeter());

        for(int i = 0; i < 5; i++)
            assertEquals(true, gameBoard.getCell(i, 4).isPerimeter());

        for(int i = 0; i < 5; i++)
            assertEquals(true, gameBoard.getCell(0, i).isPerimeter());

        for(int i = 0; i < 5; i++)
            assertEquals(true, gameBoard.getCell(4, i).isPerimeter());

        /* case the cell is not perimetral */

        // internal coordinates for i
        for(int i = 1; i < 4; i++)
            assertEquals(false, gameBoard.getCell(i, 1).isPerimeter());

        // internal coordinates for i
        for(int i = 1; i < 4; i++)
            assertEquals(false, gameBoard.getCell(i, 2).isPerimeter());

        // internal coordinates for i
        for(int i = 1; i < 4; i++)
            assertEquals(false, gameBoard.getCell(i, 3).isPerimeter());



    }

    /**
     * testing if a only initialized cell is considered free and
     * if adding and removing a pawn a cell set it free
     */
    @Test
    void freeCell() {

        Cell designatedCell = new Cell(1,1);
        Pawn designatedPawn = new Pawn(Color.BLUE, Sex.MALE, designatedCell);

        designatedCell.placePawnHere(designatedPawn);
        /* method placePawnHere has been tested before freeCell method */

        designatedCell.freeCell();

        assertEquals(false, designatedCell.getBuilderHere());
        assertNull(designatedCell.getPawnInThisCell());


    }


    @Test
    void buildOnThisCell() {

        Cell designatedCell = new Cell(1,1);

        assertEquals(0, designatedCell.getHeight());

        Building newRoof = new Building(1,2);
        designatedCell.buildOnThisCell(newRoof);

        Building roof = designatedCell.getRoof();

        assertEquals(newRoof, roof);
        assertEquals(1, newRoof.getPlacedNumber());
        assertEquals(1, designatedCell.getHeight());


    }

    @Test
    void destroyRoof() {

        Board gameBoard = new Board();
        List<Building> buildings = gameBoard.getBuildings();

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(2));

        assertEquals(1, buildings.get(0).getPlacedNumber());
        assertEquals(1, buildings.get(1).getPlacedNumber());
        assertEquals(1, buildings.get(2).getPlacedNumber());


        gameBoard.getCell(0,0).destroyRoof(buildings.get(1));

        assertEquals(0, buildings.get(2).getPlacedNumber());
        assertEquals(2, gameBoard.getCell(0,0).getHeight());

        gameBoard.getCell(0,0).destroyRoof(buildings.get(0));

        assertEquals(0, buildings.get(1).getPlacedNumber());
        assertEquals(1, gameBoard.getCell(0,0).getHeight());

        gameBoard.getCell(0,0).destroyRoof(new Building(0,25));

        assertEquals(0, buildings.get(0).getPlacedNumber());
        assertEquals(0, gameBoard.getCell(0,0).getHeight());


    }

    @Test
    void placePawnHere() {

        Cell designatedCell = new Cell(1,1);
        Pawn designatedPawn = new Pawn(Color.BLUE, Sex.MALE, designatedCell);

        designatedCell.placePawnHere(designatedPawn);

        assertEquals(designatedPawn, designatedCell.getPawnInThisCell());
        assertEquals(true, designatedCell.getBuilderHere());

        Pawn newDesignatedPawn = new Pawn(Color.GREY, Sex.FEMALE, designatedCell);
        designatedCell.placePawnHere(newDesignatedPawn);

        assertEquals(designatedPawn, designatedCell.getPawnInThisCell());

    }

    @Test
    void setHeight() {

        Board gameBoard = new Board();

        gameBoard.getCell(0,0).setHeight(1);

        assertEquals(1, gameBoard.getCell(0,0).getHeight());

    }
}