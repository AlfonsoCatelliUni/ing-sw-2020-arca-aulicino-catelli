package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.BasicEffect;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinishStateTest {

    Board gameBoard;

    Player player;


    @Test
    void getPossibleActions() {

        List<Action> correctAction = new ArrayList<>();
        List<Action> returnedActions;

        gameBoard = new Board();

        player = new Player("alfo", Color.WHITE, new Card("alfo's card", true, "alfo's effect"), new BasicEffect());
        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, gameBoard.getCell(0,1));

        correctAction.add(new MoveAction());
        returnedActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(1, returnedActions.size());
        assertEquals(correctAction.get(0).getClass(), returnedActions.get(0).getClass());

        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,0));

        returnedActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,0));
        correctAction.clear();
        correctAction.add(new BuildAction());

        assertEquals(1, returnedActions.size());
        assertEquals(correctAction.get(0).getClass(), returnedActions.get(0).getClass());

        player.build(gameBoard.getPawnByCoordinates(1,0), gameBoard.getCell(2,0), 1, gameBoard.getBuildings());

        returnedActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(1,0));
        correctAction.clear();
        correctAction.add(new FinishAction());

        assertEquals(1, returnedActions.size());
        assertEquals(correctAction.get(0).getClass(), returnedActions.get(0).getClass());

    }
}