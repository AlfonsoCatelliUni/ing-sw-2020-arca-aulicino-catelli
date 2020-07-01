package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.State.FinishState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovePerimeterAgainEffectTest {

    Board gameBoard;
    Player player;
    List<Action> possibleActions;
    List<Action> correctActions;

    List<Building> buildings;

    @BeforeEach
    void setUp() {


        gameBoard = new Board();
        player = new Player("name", Color.BLUE, new Card("card", true, "effect"), new MovePerimeterAgainEffect(new BasicEffect()));
        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        correctActions = new ArrayList<>();

        buildings = gameBoard.getBuildings();

    }

    @Test
    void move() {

        int i;
        correctActions.add(new MoveAction());

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(correctActions.size(), possibleActions.size());

        for (i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());


        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(0,1));


        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,1));

        correctActions.add(new BuildAction());

        assertEquals(correctActions.size(), possibleActions.size());

        for (i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());

        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,1), gameBoard.getCell(1,1));

        correctActions.clear();
        correctActions.add(new BuildAction());

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        assertEquals(correctActions.size(), possibleActions.size());

        for (i = 0; i < correctActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());
    }


    @Test
    void build() {

        player.move(gameBoard, gameBoard.getCell(0,0).getPawnInThisCell(), gameBoard.getCell(0,1));

        player.build(gameBoard.getCell(0,1).getPawnInThisCell(), gameBoard.getCell(1,1), 1, buildings);

        assertEquals(FinishState.class, player.getEffect().getState().getClass());


    }
}