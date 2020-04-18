package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class MoveAndBuildStateTest {

    int i;
    Effect effect;
    List<Action> possibleActions = new ArrayList<>();
    Board gameBoard;
    Player player;
    Player opponentPlayer;
    List<Action> correctActions = new ArrayList<>();
    List<Building> buildings;


    @BeforeEach
    void setUp() {
        gameBoard = new Board();
        effect = new BasicEffect();
        player = new Player("name", Color.BLUE, new Card("card", true, "effect" ), new MoreMoveEffect(new BasicEffect()));
        opponentPlayer = new Player("name1", Color.WHITE, new Card("card", true, "effect" ), new MoreMoveEffect(new BasicEffect()));
        buildings = gameBoard.getBuildings();
    }


    @Test
    void checkPossibleActions() {

        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        player.getEffect().changeState(new MoveAndBuildState(player.getEffect()));

        correctActions.add(new MoveAction());
        correctActions.add(new BuildAction());

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(correctActions.size(), possibleActions.size());
        for(i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());

        //cannot move
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(2));

        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(2));

        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));


        correctActions.clear();
        correctActions.add(new BuildAction());

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(correctActions.size(), possibleActions.size());
        for(i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());


        gameBoard.getCell(0,1).destroyRoof(buildings.get(1));
        gameBoard.getCell(0,1).destroyRoof(buildings.get(0));
        gameBoard.getCell(0,1).destroyRoof(new Building(0, 25));

        gameBoard.getCell(1,1).destroyRoof(buildings.get(1));
        gameBoard.getCell(1,1).destroyRoof(buildings.get(0));
        gameBoard.getCell(1,1).destroyRoof(new Building(0, 25));

        gameBoard.getCell(1,0).destroyRoof(buildings.get(1));
        gameBoard.getCell(1,0).destroyRoof(buildings.get(0));
        gameBoard.getCell(1,0).destroyRoof(new Building(0, 25));


        opponentPlayer.initPawn(gameBoard, gameBoard.getCell(4,4));

        opponentPlayer.move(gameBoard, gameBoard.getPawnByCoordinates(4,4), gameBoard.getCell(3,3));

        /* now the opponentPlayer is in a MoveAndBuildState and he cannot move back to his start cell,
        * so if the only cell where he could move is his start cell, he only will be able to build */

        gameBoard.getCell(4,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(4,3).buildOnThisCell(buildings.get(1));

        gameBoard.getCell(4,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(4,2).buildOnThisCell(buildings.get(1));

        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(1));

        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(1));

        gameBoard.getCell(2,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,3).buildOnThisCell(buildings.get(1));

        gameBoard.getCell(2,4).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,4).buildOnThisCell(buildings.get(1));

        gameBoard.getCell(3,4).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,4).buildOnThisCell(buildings.get(1));

        correctActions.clear();
        correctActions.add(new BuildAction());

        possibleActions = opponentPlayer.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(3,3));

        assertEquals(correctActions.size(), possibleActions.size());
        for(i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());

    }
}