package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
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

class CanForceEffectTest {

    int i;
    Board gameBoard;
    Player player;
    Player opponentPlayer;
    List<Action> possibleActions;
    List<Action> correctActions;
    List<Cell> possibleCells;
    List<Cell> correctCells;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        player = new Player("name", Color.BLUE, new Card("card", true, "effect"), new CanForceEffect(new BasicEffect()));
        opponentPlayer = new Player("name", Color.WHITE, new Card("card", true, "effect"), new BasicEffect());
        player.initPawn(gameBoard, gameBoard.getCell(1,1));
        opponentPlayer.initPawn(gameBoard, gameBoard.getCell(0,1));
        correctActions = new ArrayList<>();
        correctCells = new ArrayList<>();


    }

    @Test
    void wherePawnCanForce() {

        correctCells.add(gameBoard.getCell(0,1));

        possibleCells = player.getOpponentsNeighboring(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        assertEquals(correctCells.size(), possibleCells.size());
        for (i = 0; i < correctCells.size(); i++)
            assertEquals(correctCells.get(i).getClass(), possibleCells.get(i).getClass());

        //the symmetrical is occupied so you cannot force

        player.initPawn(gameBoard, gameBoard.getCell(2,1));

        possibleCells = player.getOpponentsNeighboring(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        assertEquals(0, possibleCells.size());

        //now there is also another opponent pawn
        opponentPlayer.initPawn(gameBoard,gameBoard.getCell(1,2));

        possibleCells = player.getOpponentsNeighboring(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        correctCells.clear();
        correctCells.add(gameBoard.getCell(1,2));

        assertEquals(correctCells.size(), possibleCells.size());
        for (i = 0; i < correctCells.size(); i++)
            assertEquals(correctCells.get(i).getClass(), possibleCells.get(i).getClass());

        // now there are symmetrical opponent pawn so you cannot force
        opponentPlayer.initPawn(gameBoard, gameBoard.getCell(0,1));
        opponentPlayer.move(gameBoard, gameBoard.getPawnByCoordinates(0,1), gameBoard.getCell(1,0));

        possibleCells = player.getOpponentsNeighboring(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        assertEquals(0, possibleCells.size());




    }
}