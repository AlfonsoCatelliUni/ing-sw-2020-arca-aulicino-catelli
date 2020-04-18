package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoreBuildInsideEffectTest {

    int i;

    Board gameBoard;

    Player player;

    List<Cell> possibleCells;

    List<Action> possibleActions;

    List<Cell> correctCells;

    List<Action> correctActions;



    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        player = new Player("name", Color.BLUE, new Card("card", true, "effetto"), new MoreBuildInsideEffect(new BasicEffect()));
        player.initPawn(gameBoard, gameBoard.getCell(0,0));

    }

    @Test
    void build() {

        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,1));

        player.build(gameBoard.getPawnByCoordinates(1,1), gameBoard.getCell(0,1), 1, gameBoard.getBuildings());

        correctActions = new ArrayList<>();

        correctActions.add(new FinishAction());
        correctActions.add(new BuildAction());


        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        assertEquals(correctActions.size(), possibleActions.size());
        for (i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());

        correctCells = new ArrayList<>();

        correctCells.add(gameBoard.getCell(2,1));
        correctCells.add(gameBoard.getCell(2,2));
        correctCells.add(gameBoard.getCell(1,2));

        possibleCells = player.wherePawnCanBuild(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        assertEquals(correctCells.size(), possibleCells.size());
        for (i = 0; i < correctCells.size(); i++)
            assertEquals(correctCells.get(i).getClass(), possibleCells.get(i).getClass());

        //you cannot build for the second time
        gameBoard.getCell(2,1).buildOnThisCell(gameBoard.getBuildings().get(0));
        gameBoard.getCell(2,1).buildOnThisCell(gameBoard.getBuildings().get(1));
        gameBoard.getCell(2,1).buildOnThisCell(gameBoard.getBuildings().get(2));
        gameBoard.getCell(2,1).buildOnThisCell(gameBoard.getBuildings().get(3));

        gameBoard.getCell(2,2).buildOnThisCell(gameBoard.getBuildings().get(0));
        gameBoard.getCell(2,2).buildOnThisCell(gameBoard.getBuildings().get(1));
        gameBoard.getCell(2,2).buildOnThisCell(gameBoard.getBuildings().get(2));
        gameBoard.getCell(2,2).buildOnThisCell(gameBoard.getBuildings().get(3));

        gameBoard.getCell(1,2).buildOnThisCell(gameBoard.getBuildings().get(0));
        gameBoard.getCell(1,2).buildOnThisCell(gameBoard.getBuildings().get(1));
        gameBoard.getCell(1,2).buildOnThisCell(gameBoard.getBuildings().get(2));
        gameBoard.getCell(1,2).buildOnThisCell(gameBoard.getBuildings().get(3));

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,1));
        correctActions.clear();
        correctActions.add(new FinishAction());

        assertEquals(correctActions.size(), possibleActions.size());
        for (i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());

        //now you can build
        gameBoard.getCell(2,2).destroyRoof(gameBoard.getBuildings().get(3));

        player.build(gameBoard.getPawnByCoordinates(1,1), gameBoard.getCell(2,2), 4, gameBoard.getBuildings());

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,1));
        correctActions.clear();
        correctActions.add(new FinishAction());

        assertEquals(correctActions.size(), possibleActions.size());
        for (i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());










    }
}