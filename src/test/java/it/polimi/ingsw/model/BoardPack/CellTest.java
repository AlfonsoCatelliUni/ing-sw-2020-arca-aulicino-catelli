package it.polimi.ingsw.model.BoardPack;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

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

        Building newRoof = new Building(1,2,0);
        designatedCell.buildOnThisCell(newRoof);

        Building roof = designatedCell.getRoof();

        assertEquals(newRoof, roof);
        assertEquals(1, newRoof.getPlacedNumber());
        assertEquals(1, designatedCell.getHeight());


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


}