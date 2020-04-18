package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildNotOnSameCellEffectTest {


    List<Action> expectedActions;
    List<Cell> availableCellsToBuild;
    List<Cell> expectedCellsToBuild;
    Board gameBoard;
    Player player;
    List<Building> buildings;


    @BeforeEach
    void setUp() {

        expectedCellsToBuild = new ArrayList<>();

        gameBoard = new Board();

        buildings = gameBoard.getBuildings();

        //Demeter
        player = new Player("test", Color.BLUE, new Card("Demeter", true, "Demeter"), new BuildNotOnSameCellEffect(new BasicEffect(), gameBoard.getCell(1,1)));
        player.initPawn(gameBoard, gameBoard.getCell(0,0));

        expectedActions = new ArrayList<>();

    }

    @Test
    void wherePawnCanBuild() {

        expectedCellsToBuild.add(gameBoard.getCell(0,1));
        expectedCellsToBuild.add(gameBoard.getCell(1,0));

        availableCellsToBuild = player.wherePawnCanBuild(gameBoard, player.getPawns().get(0));

        assertEquals(expectedCellsToBuild,availableCellsToBuild);

    }
}