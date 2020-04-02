package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoubleBuildPlayerTest {


    @Test
    void getPossibleActions() {

        List<Action> possibleActions;
        List<Action> expectedActions = new ArrayList<>();

        Board gameBoard = new Board();

        DoubleBuildPlayer player = new DoubleBuildPlayer( new BasicPlayer("test", Color.BLUE, "Demeter"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

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
        expectedActions.add(new BuildAction());
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
        List<Cell> availableCellsToBuild;
        List<Cell> expectedCellsToBuild = new ArrayList<>();

        Board gameBoard = new Board();
        
        DoubleBuildPlayer player = new DoubleBuildPlayer( new BasicPlayer("test", Color.BLUE, "Demeter"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

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

        //Hephaestus
        /*
        expectedCellsToBuild.clear();
        DoubleBuildPlayer player1 = new DoubleBuildPlayer( new BasicPlayer("test", Color.GREY, "Hephaestus"));
        player1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));

        gameBoard.getCell(2,3).buildOnThisCell(new Building(4,22));
        player1.setNumBuild(1);


        player1.setCellBefore(gameBoard.getCell(2,3));

        availableCellsToBuild = player1.wherePawnCanBuild(gameBoard,player1.getPawns()[0]);
        assertEquals(expectedCellsToBuild,availableCellsToBuild);

        gameBoard.getCell(2,3).buildOnThisCell(new Building(2,22));

        expectedCellsToBuild.add(gameBoard.getCell(2,3));


        availableCellsToBuild = player1.wherePawnCanBuild(gameBoard,player1.getPawns()[0]);
        assertEquals(expectedCellsToBuild,availableCellsToBuild);

        */

    }


    @Test
    void pawnBuild() {
        Board gameBoard = new Board();
        DoubleBuildPlayer player = new DoubleBuildPlayer( new BasicPlayer("test", Color.BLUE, "Demeter"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(1,18));
        buildings.add(new Building(2,18));
        buildings.add(new Building(3,18));
        buildings.add(new Building(4,18));
        Cell expectedCell = gameBoard.getCell(0,1);

        player.pawnBuild(player.getPawns()[0], gameBoard.getCell(0,1), 1, buildings);

        assertEquals(expectedCell,player.getCellBefore());





    }

}