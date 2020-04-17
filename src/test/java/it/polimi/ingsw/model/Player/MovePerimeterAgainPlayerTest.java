package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovePerimeterAgainPlayerTest {

    private Board gameBoard;



    private MovePerimeterAgainPlayer player;



    private List <Action> availableAction;

    @BeforeEach
    void setUp() {
        gameBoard = new Board();

        player = new MovePerimeterAgainPlayer(new BasicPlayer("player", Color.BLUE, new Card("Triton", true, "effect_artemis")));

        availableAction = new ArrayList<>();

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));

    }

    @Test
    void getPossibleActions() {
        int i;

        // i move first and pawn is on perimeter, so i moved on perimeter
        player.setNumMove(1);
        player.setNumBuild(0);

        List <Action> expectedAction = new ArrayList<>();

        expectedAction.add(new BuildAction());
        expectedAction.add(new MoveAction());

        availableAction = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        for(i = 0; i < availableAction.size(); i++)
            assertEquals(expectedAction.get(i).getClass(), availableAction.get(i).getClass());

        player.move(gameBoard,player.getPawnInCoordinates(0,0), gameBoard.getCell(0,1));
        availableAction = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        for(i = 0; i < availableAction.size(); i++)
            assertEquals(expectedAction.get(i).getClass(), availableAction.get(i).getClass());

        player.move(gameBoard,player.getPawnInCoordinates(0,1),gameBoard.getCell(1,1));

        availableAction = player.getPossibleActions(gameBoard, player.getPawns()[0]);

        expectedAction.clear();
        expectedAction.add(new BuildAction());

        for(i = 0; i < availableAction.size(); i++)
            assertEquals(expectedAction.get(i).getClass(), availableAction.get(i).getClass());

        player.setNumMove(0);
        player.setNumBuild(1);

        player.move(gameBoard,player.getPawnInCoordinates(1,1),gameBoard.getCell(0,2));

        availableAction = player.getPossibleActions(gameBoard, player.getPawns()[0]);
        expectedAction.add(new MoveAction());

        for(i = 0; i < availableAction.size(); i++)
            assertEquals(expectedAction.get(i).getClass(), availableAction.get(i).getClass());


    }
}