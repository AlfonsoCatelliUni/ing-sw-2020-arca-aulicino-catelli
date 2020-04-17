package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.BasicPlayer;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.NotSameBuildAfterPlayer;
import it.polimi.ingsw.model.Player.State.BuildAndFinishState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoreBuildNotOnSameEffectTest {

    List<Action> possibleActions;
    List<Action> expectedActions;
    List<Cell> availableCellsToBuild;
    List<Cell> expectedCellsToBuild;
    Board gameBoard;
    Player player;
    List<Building> buildings;
    Consequence consequence;

    @BeforeEach
    void setUp() {

        expectedCellsToBuild = new ArrayList<>();

        gameBoard = new Board();

        buildings = gameBoard.getBuildings();

        //Demeter
        player =  new Player("test", Color.BLUE, new Card("Demeter", true, "Demeter"), new MoreBuildNotOnSameEffect(new BasicEffect()));
        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, gameBoard.getCell(0,1));

        expectedActions = new ArrayList<>();


    }

    @Test
    void build() {

        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,0));
        player.build(gameBoard.getPawnByCoordinates(1,0), gameBoard.getCell(2,0), 1, buildings);

        assertEquals(BuildAndFinishState.class, player.getEffect().getState().getClass());


        player.build(gameBoard.getPawnByCoordinates(1,0), gameBoard.getCell(2,1), 1, buildings);

        assertEquals(FinishState.class, player.getEffect().getState().getClass());



    }
}