package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.*;
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

class SwitchPlayerTest {

    Board gameBoard;

    private Player player;
    private Player opponentPlayer;

    List<Building> buildings;

    Consequence retMoveEncoded;



    @BeforeEach
    void setUp() {
        gameBoard = new Board();

        buildings = gameBoard.getBuildings();

        player = new SwitchPlayer(new BasicPlayer("playerTest", Color.BLUE, new Card("SwitchPlayer", true, "SwitchPlayer")));
        opponentPlayer = new BasicPlayer("opponentTest", Color.GREY, new Card("SwitchPlayer", true, "SwitchPlayer"));

        player.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE ,gameBoard.getCell(2,2));

        opponentPlayer.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,4));
        opponentPlayer.initPawn(gameBoard, Sex.FEMALE ,gameBoard.getCell(1,1));

    }

    @Test
    void wherePawnCanMove() {


        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));



        List<Cell> correctAvailableToMove = new ArrayList<>();
        List<Cell> availableCellsToMove;


        /* first test */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableToMove.add(gameBoard.getCell(1,0));
        correctAvailableToMove.add(gameBoard.getCell(1,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctAvailableToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctAvailableToMove, availableCellsToMove);
        correctAvailableToMove.clear();


        /* second test */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));
        player.setCanMoveUp(false);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableToMove.add(gameBoard.getCell(1,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctAvailableToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctAvailableToMove, availableCellsToMove);
        correctAvailableToMove.clear();


        /* third test */
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,0).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(3));
        player.setCanMoveUp(true);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(1, availableCellsToMove.size());

    }


    @Test
    void movePawn() {


        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));



        /* switch with an opponent player */
        retMoveEncoded = player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,1));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());
        assertEquals(gameBoard.getPawnByCoordinates(0,0), opponentPlayer.getPawns()[1] );
        assertEquals(true, gameBoard.getPawnByCoordinates(0,0).getForcedMove());
        assertEquals(2, opponentPlayer.getPawns()[1].getHeight());


        /* normal move */
        retMoveEncoded = player.move(gameBoard, gameBoard.getPawnByCoordinates(1,1), gameBoard.getCell(1,2));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());



    }


    @Test
    void getPossibleActions() {


        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        List<Action> availableActions;
        List<Action> correctAvailableActions = new ArrayList<>();


        /* move test */
        availableActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableActions.add(new MoveAction());

        for (int i = 0; i < availableActions.size(); i++ )
            assertEquals(correctAvailableActions.get(i).getClass(), availableActions.get(i).getClass());

        correctAvailableActions.clear();


        /* build test */
        player.setNumMove(1);
        player.setNumBuild(0);

        availableActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableActions.add(new BuildAction());

        for (int i = 0; i < availableActions.size(); i++ )
            assertEquals(correctAvailableActions.get(i).getClass(), availableActions.get(i).getClass());

        correctAvailableActions.clear();


        /* finish test */
        player.setNumMove(1);
        player.setNumBuild(1);

        availableActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableActions.add(new FinishAction());

        for (int i = 0; i < availableActions.size(); i++ )
            assertEquals(correctAvailableActions.get(i).getClass(), availableActions.get(i).getClass());

        correctAvailableActions.clear();


        /* no actions possible test */
        player.setNumMove(0);
        player.setNumBuild(1);

        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(2));


        availableActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        correctAvailableActions.add(new MoveAction());
        for (int i = 0; i < availableActions.size(); i++ )
            assertEquals(correctAvailableActions.get(i).getClass(), availableActions.get(i).getClass());

        correctAvailableActions.clear();

    }



}