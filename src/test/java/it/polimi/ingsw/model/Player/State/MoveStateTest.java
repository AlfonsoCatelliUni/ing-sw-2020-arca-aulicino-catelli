package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveStateTest {

    int i;
    Effect effect;
    List<Action> possibleActions = new ArrayList<>();
    Board gameBoard;
    Player player;
    Player opponentPlayer;
    List<Action> correctActions = new ArrayList<>();


    @BeforeEach
    void setUp() {
        gameBoard = new Board();
        effect = new BasicEffect();
        player = new Player("name", Color.BLUE, new Card("card", true, "effect" ), new CanSwitchOpponentEffect(new SwitchEffect(new BasicEffect())));
        opponentPlayer = new Player("name1", Color.WHITE, new Card("card", true, "effect" ), new BasicEffect());
    }

    @Test
    void checkPossibleActions() {

        //basic move
        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        correctActions.add(new MoveAction());

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        for(i = 0; i < possibleActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());

        // there is only opponent pawn, but you can switch
        opponentPlayer.initPawn(gameBoard, gameBoard.getCell(0,1));
        opponentPlayer.initPawn(gameBoard, gameBoard.getCell(1,0));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(1,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(1,22));

        possibleActions = player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        for(i = 0; i < possibleActions.size(); i++)
            assertEquals(correctActions.get(i).getClass(), possibleActions.get(i).getClass());

    }
}