package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PushPlayerTest {

    @Test
    void wherePawnCanMove() {

        List<Cell> availableCellsToMove;
        List<Cell> expectedCellsToMove = new ArrayList<>();
        Board gameBoard = new Board();

        //the opposite cell is free so player can move there
        Player player = new PushPlayer(new BasicPlayer("test", Color.GREY,"Apollo"));
        Player player1 = new PushPlayer(new BasicPlayer("test1", Color.BLUE,"opponent"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,2));

        expectedCellsToMove.add(gameBoard.getCell(1,1));
        expectedCellsToMove.add(gameBoard.getCell(1,3));
        expectedCellsToMove.add(gameBoard.getCell(2,1));
        expectedCellsToMove.add(gameBoard.getCell(2,3));
        expectedCellsToMove.add(gameBoard.getCell(3,1));
        expectedCellsToMove.add(gameBoard.getCell(3,2));
        expectedCellsToMove.add(gameBoard.getCell(3,3));
        expectedCellsToMove.add(gameBoard.getCell(1,2));

        availableCellsToMove = player.wherePawnCanMove(gameBoard,player.getPawns()[0]);

        assertEquals(expectedCellsToMove,availableCellsToMove);

        expectedCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));



        //the opposite cell isn't free so player can't move there
        player1.initPawn(gameBoard, Sex.FEMALE,gameBoard.getCell(0,2));

        expectedCellsToMove.remove(gameBoard.getCell(1,2));

        availableCellsToMove = player.wherePawnCanMove(gameBoard,player.getPawns()[0]);

        assertEquals(expectedCellsToMove,availableCellsToMove);

        //statement coverage if canMoveUp is false
        player.setCanMoveUp(false);
        gameBoard.getCell(2,1).buildOnThisCell(new Building(1,18));

        expectedCellsToMove.remove(gameBoard.getCell(2,1));
        availableCellsToMove = player.wherePawnCanMove(gameBoard,player.getPawns()[0]);

        assertEquals(expectedCellsToMove,availableCellsToMove);

    }

    @Test
    void movePawn() {
        Board gameBoard = new Board();
        Player player = new PushPlayer(new BasicPlayer("test", Color.GREY,"Apollo"));
        Player player1 = new PushPlayer(new BasicPlayer("test1", Color.BLUE,"opponent"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,2));

        player.movePawn(gameBoard,player.getPawns()[0],gameBoard.getCell(1,2));

        assertEquals(true,gameBoard.getCell(0,2).getBuilderHere());
        assertEquals(true,gameBoard.getCell(1,2).getBuilderHere());
        assertEquals(false,gameBoard.getCell(2,2).getBuilderHere());

        //statement coverage: basic movePawn if there is no builder in designatedCell
        player.movePawn(gameBoard, player.getPawns()[0],gameBoard.getCell(1,3));
        assertEquals(false,gameBoard.getCell(1,2).getBuilderHere());
        assertEquals(true,gameBoard.getCell(1,3).getBuilderHere());







    }
}