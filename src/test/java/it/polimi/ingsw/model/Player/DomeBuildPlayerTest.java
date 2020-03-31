package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomeBuildPlayerTest {

    @Test
    void getPossibleBuildingOnCell() {
        List<Building> expectedBuildings = new ArrayList<>();
        List<Building> possibleBuildings;
        Board gameBoard = new Board();
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(1,18));
        buildings.add(new Building(2,18));
        buildings.add(new Building(3,18));
        buildings.add(new Building(4,18));

        Player player = new DomeBuildPlayer(new BasicPlayer("test", Color.BLUE, "Atlas"));
        player.initPawn(gameBoard, Sex.MALE,gameBoard.getCell(0,0));

        expectedBuildings.add(buildings.get(0));
        expectedBuildings.add(buildings.get(3));

        possibleBuildings = player.getPossibleBuildingOnCell(gameBoard,gameBoard.getCell(1,1),buildings);
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
        possibleBuildings = player.getPossibleBuildingOnCell(gameBoard,gameBoard.getCell(1,1),buildings);
        assertEquals(expectedBuildings,possibleBuildings);



    }
}