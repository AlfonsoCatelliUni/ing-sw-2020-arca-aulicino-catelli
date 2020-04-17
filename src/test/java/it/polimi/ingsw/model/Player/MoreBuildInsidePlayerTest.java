package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Actions.FinishAction;
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

class MoreBuildInsidePlayerTest {

    Board gameBoard;
    List<Cell> availableCellsToBuild;
    List<Cell> expectedCellsToBuild;
    Player player;
    List<Action> possibleActions;
    List<Action> expectedActions;
    List<Building> buildings;
    Consequence consequence;

    @BeforeEach
    void setUp() {

        expectedCellsToBuild = new ArrayList<>();

        gameBoard = new Board();

        buildings = gameBoard.getBuildings();

        //Demeter
        player = new MoreBuildInsidePlayer( new BasicPlayer("test", Color.BLUE, new Card("Demeter", true, "Demeter")));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        expectedActions = new ArrayList<>();


    }

    @Test
    void getPossibleActions() {

        player.setNumMove(1);
        player.setNumBuild(1);


        expectedActions.add(new FinishAction());
        expectedActions.add(new BuildAction());

        possibleActions = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        for (int i = 0; i < possibleActions.size(); i++ )
            assertEquals(expectedActions.get(i).getClass(), possibleActions.get(i).getClass());


        // i built for the second time so i have to end turn
        player.setNumBuild(2);

        expectedActions.clear();
        expectedActions.add(new FinishAction());

        possibleActions = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        expectedActions.clear();
        expectedActions.add(new FinishAction());

        //i can build for the second time only in perimeter space, so i have to end turn
        gameBoard.getCell(1,1).buildOnThisCell(new Building(1,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(2,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(3,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(4,22));

        player.setNumMove(1);
        player.setNumBuild(1);

        expectedActions.clear();
        expectedActions.add(new FinishAction());

        expectedActions.clear();
        expectedActions.add(new FinishAction());




    }


    @Test
    void wherePawnCanBuild() {

        player.setNumBuild(0);

        expectedCellsToBuild.add(gameBoard.getCell(1,1));
        expectedCellsToBuild.add(gameBoard.getCell(0,1));
        expectedCellsToBuild.add(gameBoard.getCell(1,0));

        availableCellsToBuild = player.wherePawnCanBuild(gameBoard, player.getPawnInCoordinates(0,0));

        availableCellsToBuild.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        expectedCellsToBuild.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        for (int i = 0; i < availableCellsToBuild.size(); i++ )
            assertEquals(expectedCellsToBuild.get(i), availableCellsToBuild.get(i));

        expectedCellsToBuild.clear();


        player.setNumBuild(1);

        expectedCellsToBuild.add(gameBoard.getCell(1,1));

        availableCellsToBuild = player.wherePawnCanBuild(gameBoard, player.getPawnInCoordinates(0,0));

        for (int i = 0; i < availableCellsToBuild.size(); i++ )
            assertEquals(expectedCellsToBuild.get(i), availableCellsToBuild.get(i));

        //can build only in perimeter space, so wherepawncanbuild is empty
        gameBoard.getCell(1,1).buildOnThisCell(new Building(1,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(2,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(3,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(4,22));

        availableCellsToBuild = player.wherePawnCanBuild(gameBoard, player.getPawnInCoordinates(0,0));

        assertEquals(0, availableCellsToBuild.size());






    }


}