package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoubleMovePlayerTest {


    private Board gameBoard;

    private List<Building> buildings;

    private Player player;

    private List<Cell> availableCellsToMove;

    private List<Cell> correctListAvailableCellsMove;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        player = new DoubleMovePlayer(new BasicPlayer("player", Color.BLUE, new Card("Artemis", true, true, "effect_artemis")));

        availableCellsToMove = new ArrayList<>();
        correctListAvailableCellsMove = new ArrayList<>();

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));
    }

    @Test
    void wherePawnCanMove() {

        player.setCanMoveUp(true);


        /* Level One */
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));
        /* Level Two */
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(1));
        /* Level Three */
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(1));
        /* Dome Level */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));
        /* Dome Level */
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(3));


        /* in this case the pawn can move up, there's a ramp to a second level */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        correctListAvailableCellsMove.add(gameBoard.getCell(0,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(0,2));
        correctListAvailableCellsMove.add(gameBoard.getCell(2,0));
        correctListAvailableCellsMove.add(gameBoard.getCell(1,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(1,2));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctListAvailableCellsMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(availableCellsToMove, correctListAvailableCellsMove);
        correctListAvailableCellsMove.clear();


        /* in this case the pawn cannot move up, so it can move only to the same or lower level */
        player.setCanMoveUp(false);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        correctListAvailableCellsMove.add(gameBoard.getCell(1,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(2,0));

        assertEquals(availableCellsToMove, correctListAvailableCellsMove);
        correctListAvailableCellsMove.clear();


    }

}