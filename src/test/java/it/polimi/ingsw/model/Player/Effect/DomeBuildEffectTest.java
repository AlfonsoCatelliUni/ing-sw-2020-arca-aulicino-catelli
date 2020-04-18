package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomeBuildEffectTest {

    Board gameBoard;

    List<Action> test;

    Card card;

    Card card_1;

    List<Building> buildings;

    Player player;

    Player player_1;

    List<Building> expectedBuildings;

    List<Building> possibleBuildings;

    @BeforeEach
    void setUp() {
        gameBoard = new Board();
        test = new ArrayList<>();
        card = new Card("test", true, "test");
        card_1 = new Card("test_1", true, "test_1");
        buildings = gameBoard.getBuildings();

        player = new Player("Massi", Color.BLUE, card, new DomeBuildEffect(new BasicEffect()));
        player.initPawn(gameBoard, gameBoard.getCell(0, 0));
        player_1 = new Player("Massi", Color.WHITE, card, new BasicEffect());
        player_1.initPawn(gameBoard, gameBoard.getCell(2, 3));

        expectedBuildings = new ArrayList<>();

        possibleBuildings = new ArrayList<>();
    }

    @Test
    void getPossibleBuildingOnCell() {
        expectedBuildings.add(buildings.get(0));
        expectedBuildings.add(buildings.get(3));

        possibleBuildings = player.getPossibleBuildingOnCell(gameBoard, gameBoard.getCell(1, 1));
        assertEquals(expectedBuildings, possibleBuildings);


        //if the player can not build, he can not build also a dome
        gameBoard.getCell(0, 1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0, 1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0, 1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0, 1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1, 1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1, 1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1, 1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1, 1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1, 0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1, 0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1, 0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1, 0).buildOnThisCell(buildings.get(3));

        expectedBuildings.clear();
        possibleBuildings = player.getPossibleBuildingOnCell(gameBoard, gameBoard.getCell(1, 1));
        assertEquals(expectedBuildings, possibleBuildings);

        /* case when all the domes are placed in the board */

        gameBoard.getBuildings().get(3).setPlacedNumber(18);

        expectedBuildings.clear();
        expectedBuildings.add(buildings.get(0));

        assertEquals(expectedBuildings, player_1.getPossibleBuildingOnCell(gameBoard, gameBoard.getCell(2, 2)));

    }
}
