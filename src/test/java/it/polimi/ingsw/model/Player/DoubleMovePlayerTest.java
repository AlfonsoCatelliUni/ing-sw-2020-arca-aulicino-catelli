package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
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

    private DoubleMovePlayer player;

    private List<Cell> availableCellsToMove;

    private List<Cell> correctListAvailableCellsMove;

    private List <Action> availableAction;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        player = new DoubleMovePlayer(new BasicPlayer("player", Color.BLUE, new Card("Artemis", true, "effect_artemis")));

        availableCellsToMove = new ArrayList<>();
        correctListAvailableCellsMove = new ArrayList<>();
        availableAction = new ArrayList<>();

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
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(2));
        /* Dome Level */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));
        /* Dome Level */
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(3));


        /* in this case the pawn can move up, there's a ramp to a second level */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        correctListAvailableCellsMove.add(gameBoard.getCell(0,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(1,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctListAvailableCellsMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(availableCellsToMove, correctListAvailableCellsMove);
        correctListAvailableCellsMove.clear();


        /* in this case the pawn cannot move up, so it can move only to the same or lower level */
        player.setCanMoveUp(false);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        correctListAvailableCellsMove.add(gameBoard.getCell(1,1));

        assertEquals(correctListAvailableCellsMove, availableCellsToMove );
        correctListAvailableCellsMove.clear();

        /* in this case it's a second move, so player can not move back if he wants to move again */
        player.setCanMoveUp(true);

        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,1));

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        correctListAvailableCellsMove.add(gameBoard.getCell(2,0));
        correctListAvailableCellsMove.add(gameBoard.getCell(0,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctListAvailableCellsMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctListAvailableCellsMove, availableCellsToMove);


    }

    @Test
    void getPossibleActions() {
        int i;
        player.setNumMove(1);
        player.setNumBuild(0);
        player.setHasMoved(true);

        List <Action> expectedAction = new ArrayList<>();

        expectedAction.add(new MoveAction());
        expectedAction.add(new BuildAction());


        availableAction = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        for(i = 0; i < expectedAction.size(); i++)
            assertEquals(expectedAction.get(i).getClass(), availableAction.get(i).getClass());





    }
}