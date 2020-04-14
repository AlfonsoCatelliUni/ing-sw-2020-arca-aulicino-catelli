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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotSameBuildAfterPlayerTest {

    List<Action> possibleActions;
    List<Action> expectedActions;
    List<Cell> availableCellsToBuild;
    List<Cell> expectedCellsToBuild;
    Board gameBoard;
    NotSameBuildAfterPlayer player;
    List<Building> buildings;
    Consequence consequence;


    @BeforeEach
    void setUp() {

        expectedCellsToBuild = new ArrayList<>();

        gameBoard = new Board();

        buildings = gameBoard.getBuildings();

        //Demeter
        player = new NotSameBuildAfterPlayer( new BasicPlayer("test", Color.BLUE, new Card("Demeter", true, "Demeter")));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        expectedActions = new ArrayList<>();

    }

    @Test
    void getPossibleActions() {

        possibleActions = player.getPossibleActions(gameBoard,player.getPawns()[0]);
        expectedActions.add(new MoveAction());

        // numMove is 0 and NumBuild is 1
        for (int i = 0; i < expectedActions.size(); i++ )
            assertEquals(possibleActions.get(i).getClass(), expectedActions.get(i).getClass());

        player.setNumMove(1);
        player.setNumBuild(0);
        expectedActions.clear();
        expectedActions.add(new BuildAction());

        possibleActions = player.getPossibleActions(gameBoard,player.getPawns()[0]);

        for (int i = 0; i < expectedActions.size(); i++ )
            assertEquals(possibleActions.get(i).getClass(), expectedActions.get(i).getClass());

        player.setNumBuild(1);
        expectedActions.clear();
        expectedActions.add(new FinishAction());
        expectedActions.add(new BuildAction());

        possibleActions = player.getPossibleActions(gameBoard,player.getPawns()[0]);

        for (int i = 0; i < expectedActions.size(); i++ )
            assertEquals(possibleActions.get(i).getClass(), expectedActions.get(i).getClass());

        player.setNumBuild(2);

        expectedActions.clear();
        expectedActions.add(new FinishAction());

        possibleActions = player.getPossibleActions(gameBoard,player.getPawns()[0]);

        for (int i = 0; i < expectedActions.size(); i++ )
            assertEquals(possibleActions.get(i).getClass(), expectedActions.get(i).getClass());


        //non può costruire la seconda volta
        gameBoard.getCell(0,1).buildOnThisCell(new Building(1, 18));
        gameBoard.getCell(1,0).buildOnThisCell(new Building(4,18));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(4,18));

        player.setCellBefore(gameBoard.getCell(0,1));

        player.setNumMove(1);
        player.setNumBuild(1);

        expectedActions.clear();
        expectedActions.add(new FinishAction());

        possibleActions = player.getPossibleActions(gameBoard,player.getPawns()[0]);

        for (int i = 0; i < expectedActions.size(); i++ )
            assertEquals(possibleActions.get(i).getClass(), expectedActions.get(i).getClass());
    }


    @Test
    void wherePawnCanBuild() {


        player.setNumBuild(0);

        expectedCellsToBuild.add(gameBoard.getCell(0,1));
        expectedCellsToBuild.add(gameBoard.getCell(1,0));
        expectedCellsToBuild.add(gameBoard.getCell(1,1));

        availableCellsToBuild = player.wherePawnCanBuild(gameBoard, player.getPawns()[0]);
        assertEquals(expectedCellsToBuild,availableCellsToBuild);

        player.setNumBuild(1);
        player.setCellBefore(gameBoard.getCell(1,1));
        expectedCellsToBuild.remove(gameBoard.getCell(1,1));

        availableCellsToBuild = player.wherePawnCanBuild(gameBoard, player.getPawns()[0]);
        assertEquals(expectedCellsToBuild,availableCellsToBuild);


    }


    @Test
    void pawnBuild() {


        Cell expectedCell = gameBoard.getCell(0,1);

        consequence = player.pawnBuild(player.getPawns()[0], gameBoard.getCell(0,1), 1, buildings);

        assertEquals(expectedCell,player.getCellBefore());

        Consequence consequence1 = new NoConsequence();

        assertEquals(consequence.getClass(), consequence1.getClass());





    }

}