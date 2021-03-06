package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void moveTo() {
        Board gameBoard = new Board();

        //Goes straight
        Cell cell = gameBoard.getCell(0,0);
        cell.setHeight(1);
        Pawn pawn = new Pawn(Color.GREY, Sex.FEMALE, cell);
        Cell nextPosition = gameBoard.getCell(0,1);

        nextPosition.setHeight(1);

        Cell movedTo = pawn.moveTo(nextPosition);

        assertEquals(false, pawn.getGoneUp());
        assertEquals(Color.GREY, pawn.getColor());
        assertEquals(Sex.FEMALE, pawn.getSex());


        //Goes up
        Cell cell1 = gameBoard.getCell(2,2);
        cell1.setHeight(1);
        Pawn pawn1 = new Pawn(Color.GREY, Sex.MALE, cell1);
        Cell nextPosition1 = gameBoard.getCell(2,3);

        nextPosition1.setHeight(2);

        Cell movedTo1 = pawn1.moveTo(nextPosition1);

        assertEquals(true, pawn1.getGoneUp());


        //Goes down
        Cell cell2 = gameBoard.getCell(1,2);
        cell2.setHeight(2);
        Pawn pawn2 = new Pawn(Color.GREY, Sex.MALE, cell2);
        Cell nextPosition2 = gameBoard.getCell(1,3);

        nextPosition2.setHeight(1);

        Cell movedTo2 = pawn2.moveTo(nextPosition2);

        assertEquals(false, pawn2.getGoneUp());

    }

    @Test
    void pawnBuild() {
        Board gameBoard = new Board();
        Cell cell = gameBoard.getCell(0,0);
        Pawn pawn = new Pawn(Color.GREY, Sex.FEMALE, cell);
        pawn.pawnBuild();
        assertEquals (true, pawn.getHasBuilt());
    }

    @Test
    void forcePawn() {
        Board gameBoard = new Board();
        Cell cell = gameBoard.getCell(0,0);
        Pawn pawn = new Pawn(Color.BLUE, Sex.MALE, cell);
        Cell cell1 = gameBoard.getCell(0,1);
        pawn.forcePawn(cell1);

        assertEquals(false, pawn.getHasMoved());
        assertEquals(true, pawn.getForcedMove());
        assertEquals(cell1, pawn.getPosition());
        assertEquals(cell1.getHeight(), pawn.getHeight());
    }

    @Test
    void resetPawnStatus() {

        Board gameBoard = new Board();

        List<Building> buildings = gameBoard.getBuildings();

        Pawn pawn = new Pawn(Color.WHITE, Sex.MALE, gameBoard.getCell(0,0));

        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));

        pawn.moveTo(gameBoard.getCell(0,1));
        pawn.pawnBuild();

        assertEquals(true, pawn.getHasMoved());
        assertEquals(true, pawn.getGoneUp());
        assertEquals(true, pawn.getHasBuilt());

        pawn.forcePawn(gameBoard.getCell(0,2));

        assertEquals(true, pawn.getForcedMove());

        pawn.resetPawnStatus();

        assertEquals(false, pawn.getHasMoved());
        assertEquals(false, pawn.getGoneUp());
        assertEquals(false, pawn.getHasBuilt());
        assertEquals(false, pawn.getHasBuilt());

    }
}