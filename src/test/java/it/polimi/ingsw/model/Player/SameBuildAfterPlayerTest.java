package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SameBuildAfterPlayerTest {

    @Test
    void wherePawnCanBuild() {
        Board gameBoard = new Board();

        List<Cell> availableCellsToBuild;
        List<Cell> expectedCellsToBuild = new ArrayList<>();

        SameBuildAfterPlayer player = new SameBuildAfterPlayer( new BasicPlayer("test", Color.GREY, "Hephaestus"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));

        gameBoard.getCell(2,3).buildOnThisCell(new Building(4,22));
        player.setNumBuild(1);


        player.setCellBefore(gameBoard.getCell(2,3));

        availableCellsToBuild = player.wherePawnCanBuild(gameBoard,player.getPawns()[0]);
        assertEquals(expectedCellsToBuild,availableCellsToBuild);

        gameBoard.getCell(2,3).buildOnThisCell(new Building(2,22));

        expectedCellsToBuild.add(gameBoard.getCell(2,3));


        availableCellsToBuild = player.wherePawnCanBuild(gameBoard,player.getPawns()[0]);
        assertEquals(expectedCellsToBuild,availableCellsToBuild);


    }

    @Test
    void getPossibleActions() {

        List<Action> possibleActions;
        List<Action> expectedActions = new ArrayList<>();

        Board gameBoard = new Board();

        SameBuildAfterPlayer player = new SameBuildAfterPlayer( new BasicPlayer("test", Color.GREY, "Hephaestus"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));

        player.setNumMove(1);
        player.setNumBuild(1);

        player.setCellBefore(gameBoard.getCell(2,1));

        expectedActions.add(new FinishAction());
        expectedActions.add(new BuildAction());

        possibleActions = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        for (int i = 0; i < possibleActions.size(); i++ )
            assertEquals(expectedActions.get(i).getClass(), possibleActions.get(i).getClass());

        //ha costruito un livello 4 nella prima costruzione quindi non può più costruire
        gameBoard.getCell(2,1).buildOnThisCell(new Building(4, 22));

        expectedActions.clear();
        expectedActions.add(new FinishAction());

        possibleActions = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        for (int i = 0; i < possibleActions.size(); i++ )
            assertEquals(expectedActions.get(i).getClass(), possibleActions.get(i).getClass());

        // ha costruito la seconda volta quindi ora passa il turno
        player.setNumBuild(2);

        expectedActions.clear();
        expectedActions.add(new FinishAction());

        possibleActions = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        for (int i = 0; i < possibleActions.size(); i++ )
            assertEquals(expectedActions.get(i).getClass(), possibleActions.get(i).getClass());


    }

    @Test
    void pawnBuild() {
        Board gameBoard = new Board();
        SameBuildAfterPlayer player = new SameBuildAfterPlayer( new BasicPlayer("test", Color.BLUE, "Demeter"));
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