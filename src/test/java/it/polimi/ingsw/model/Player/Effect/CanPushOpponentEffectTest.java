package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CanPushOpponentEffectTest {

    private Board gameBoard;

    private Player player;

    private Player opponentPlayer;

    private List<Building> buildings;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        player = new Player("Player", Color.WHITE, new Card("Minotauro",true, "effect"),new CanPushOpponentEffect(new BasicEffect()));

        opponentPlayer = new Player("Opponent", Color.BLUE, new Card("Minotauro",true, "effect"),new BasicEffect());

        buildings = gameBoard.getBuildings();

    }

    @Test
    void wherePawnCanMove() {

        List<Cell> availableCellsToMove;
        List<Cell> expectedCellsToMove = new ArrayList<>();

        //the opposite cell is free so player can move there
        player.initPawn(gameBoard, gameBoard.getCell(2,2));
        opponentPlayer.initPawn(gameBoard, gameBoard.getCell(1,2));

        expectedCellsToMove.add(gameBoard.getCell(1,1));
        expectedCellsToMove.add(gameBoard.getCell(1,3));
        expectedCellsToMove.add(gameBoard.getCell(2,1));
        expectedCellsToMove.add(gameBoard.getCell(2,3));
        expectedCellsToMove.add(gameBoard.getCell(3,1));
        expectedCellsToMove.add(gameBoard.getCell(3,2));
        expectedCellsToMove.add(gameBoard.getCell(3,3));
        expectedCellsToMove.add(gameBoard.getCell(1,2));

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(2,2));

        assertEquals(expectedCellsToMove,availableCellsToMove);

        expectedCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));



        //the opposite cell isn't free so player can't move there
        opponentPlayer.initPawn(gameBoard,gameBoard.getCell(0,2));

        expectedCellsToMove.remove(gameBoard.getCell(1,2));

        availableCellsToMove = player.wherePawnCanMove(gameBoard,gameBoard.getPawnByCoordinates(2,2));

        assertEquals(expectedCellsToMove,availableCellsToMove);

        //statement coverage if canMoveUp is false

        player.setEffect(new NotMoveUpEffect(player.getEffect()));

        gameBoard.getCell(2,1).buildOnThisCell(new Building(1,18));

        expectedCellsToMove.remove(gameBoard.getCell(2,1));
        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(2,2));

        assertEquals(expectedCellsToMove,availableCellsToMove);



    }


}