package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomeBuildPlayerTest {

    Board gameBoard;

    List<Action> test;

    Card card;

    List<Building> buildings;

    Player player;

    List<Building> expectedBuildings;

    List<Building> possibleBuildings;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        test = new ArrayList<>();
        card = new Card("test", true, true, "test");
        buildings = gameBoard.getBuildings();
        buildings.add(new Building(1,18));
        buildings.add(new Building(2,18));
        buildings.add(new Building(3,18));
        buildings.add(new Building(4,18));

        player = new DomeBuildPlayer(new BasicPlayer("test", Color.BLUE, card));
        player.initPawn(gameBoard, Sex.MALE,gameBoard.getCell(0,0));

        expectedBuildings = new ArrayList<>();

        possibleBuildings = new ArrayList<>();


    }

    @Test
    void getPossibleBuildingOnCell() {

        expectedBuildings.add(buildings.get(0));
        expectedBuildings.add(buildings.get(3));

        possibleBuildings = player.getPossibleBuildingOnCell(gameBoard,gameBoard.getCell(1,1));
        assertEquals(expectedBuildings,possibleBuildings);


        //if the player can not build, he can not build also a dome
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));

        expectedBuildings.clear();
        possibleBuildings = player.getPossibleBuildingOnCell(gameBoard,gameBoard.getCell(1,1));
        assertEquals(expectedBuildings,possibleBuildings);



    }
}