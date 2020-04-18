package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.BasicEffect;
import it.polimi.ingsw.model.Player.Effect.MoreBuildNotOnSameEffect;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class BuildAndFinishStateTest {

    Board gameBoard;

    Player player;

    List<Building> buildings;

    List<Action> rightActions;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        buildings = gameBoard.getBuildings();

        rightActions = new ArrayList<>();

        player = new Player("alfo", Color.WHITE, new Card("alfo's card", true, "alfo's effect"), new MoreBuildNotOnSameEffect(new BasicEffect()));
        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, gameBoard.getCell(0,1));


    }

    @Test
    void checkPossibleActions() {

        int i;

        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,0));

        player.build(gameBoard.getPawnByCoordinates(1,0), gameBoard.getCell(2,0), 1, buildings);

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(3));

        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(3));

        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(3));

        rightActions.add(new FinishAction());


        assertEquals(rightActions.size(), player.getEffect().getState().checkPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,0)).size());

        for(i = 0; i < rightActions.size(); i++)
            assertEquals(rightActions.get(i).getClass(), player.getEffect().getState().checkPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,0)).get(i).getClass());

    }
}