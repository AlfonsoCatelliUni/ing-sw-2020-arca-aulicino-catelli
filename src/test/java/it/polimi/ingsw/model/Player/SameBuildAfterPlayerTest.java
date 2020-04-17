package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.NoConsequence;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SameBuildAfterPlayerTest {

    Board gameBoard;
    List<Cell> availableCellsToBuild;
    List<Cell> expectedCellsToBuild;
    SameBuildAfterPlayer player;
    List<Action> possibleActions;
    List<Action> expectedActions;
    List<Building> buildings;
    Consequence consequence;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        expectedCellsToBuild = new ArrayList<>();
        player = new SameBuildAfterPlayer( new BasicPlayer("test", Color.GREY, new Card("Demeter", true, "Hephaestus")));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        expectedActions = new ArrayList<>();

        buildings = gameBoard.getBuildings();

    }

    @Test
    void wherePawnCanBuild() {

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


        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        Cell expectedCell = gameBoard.getCell(0,1);

        consequence = player.build(player.getPawns()[0], gameBoard.getCell(0,1), 1, buildings);

        Consequence consequence1 = new NoConsequence();

        assertEquals(consequence.getClass(), consequence1.getClass());

        assertEquals(expectedCell,player.getCellBefore());



    }
}