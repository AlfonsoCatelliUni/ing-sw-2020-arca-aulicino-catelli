package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.DestroyAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.BasicEffect;
import it.polimi.ingsw.model.Player.Effect.CanDestroyEffect;
import it.polimi.ingsw.model.Player.Effect.DestroyEffect;
import it.polimi.ingsw.model.Player.Effect.MoreBuildNotOnSameEffect;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class DestroyAndFinishStateTest {

    Board gameBoard;

    List<Action> rightActions;

    Card card;

    List<Building> buildings;

    Player player_1;

    Player player_2;

    Player player_3;

    int i;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        rightActions = new ArrayList<>();
        card = new Card("test", true, "test");
        buildings = gameBoard.getBuildings();

        player_1 = new Player("alfo", Color.WHITE, new Card("alfo's card", true, "alfo's effect"), new DestroyEffect(new CanDestroyEffect(new BasicEffect())));
        player_2 = new Player("massi", Color.GREY, new Card("massi's card", true, "massi's effect"), new DestroyEffect(new CanDestroyEffect(new BasicEffect())));
        player_3 = new Player("giammi", Color.BLUE, new Card("giammi's card", true, "giammi's effect"), new DestroyEffect(new CanDestroyEffect(new BasicEffect())));

        player_1.initPawn(gameBoard, gameBoard.getCell(0,0));
        player_1.initPawn(gameBoard, gameBoard.getCell(0,1));
        player_2.initPawn(gameBoard, gameBoard.getCell(1,1));
        player_2.initPawn(gameBoard, gameBoard.getCell(1,2));
        player_3.initPawn(gameBoard, gameBoard.getCell(2,2));
        player_3.initPawn(gameBoard, gameBoard.getCell(2,3));

    }

    @Test
    void checkPossibleActions() {

        /* case when the player can destroy a block */

        player_3.move(gameBoard, gameBoard.getPawnByCoordinates(2,2), gameBoard.getCell(3,1));

        player_3.build(gameBoard.getPawnByCoordinates(3,1), gameBoard.getCell(3,2), 1, buildings);

        /* now the player is on DestroyAndFinishState */

        gameBoard.getCell(3,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,4).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,4).buildOnThisCell(buildings.get(0));

        rightActions.add(new FinishAction());
        rightActions.add(new DestroyAction());

        assertEquals(rightActions.size(), player_3.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(3,1)).size());

        for(i = 0; i < rightActions.size(); i++)
            assertEquals(rightActions.get(i).getClass(), player_3.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(3,1)).get(i).getClass());

        /* case when the unmoved pawn cannot destroy, so the player can only finish */

        gameBoard.getCell(3,2).destroyRoof(new Building(0, 25));
        gameBoard.getCell(3,3).destroyRoof(new Building(0, 25));
        gameBoard.getCell(3,4).destroyRoof(new Building(0, 25));
        gameBoard.getCell(2,4).destroyRoof(new Building(0, 25));

        gameBoard.getCell(4,0).buildOnThisCell(buildings.get(0));

        rightActions.clear();
        rightActions.add(new FinishAction());

        assertEquals(rightActions.size(), player_3.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(3,1)).size());

        for(i = 0; i < rightActions.size(); i++)
            assertEquals(rightActions.get(i).getClass(), player_3.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(3,1)).get(i).getClass());



    }
}