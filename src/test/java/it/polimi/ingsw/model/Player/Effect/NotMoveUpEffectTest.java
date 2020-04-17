package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotMoveUpEffectTest {

    Board gameBoard;

    List<Cell> test;

    Card card;

    List<Building> buildings;


    Player player_1;

    Player player_2;

    Player player_3;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        test = new ArrayList<>();
        card = new Card("test", true, "test");
        buildings = gameBoard.getBuildings();

        player_1 = new Player(new NotMoveUpEffect(new BasicEffect()));
        player_2 = new Player(new NotMoveUpEffect(new BasicEffect()));
        player_3 = new Player(new NotMoveUpEffect(new BasicEffect()));

        player_1.initPawn(gameBoard, gameBoard.getCell(0,0));
        player_1.initPawn(gameBoard, gameBoard.getCell(0,1));
        player_2.initPawn(gameBoard, gameBoard.getCell(1,1));
        player_2.initPawn(gameBoard, gameBoard.getCell(1,2));
        player_3.initPawn(gameBoard, gameBoard.getCell(2,2));
        player_3.initPawn(gameBoard, gameBoard.getCell(2,3));

    }

    @Test
    void wherePawnCanMove() {

        /* case when the player can move only in one cell */

        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(0));

        test.add(gameBoard.getCell(3,3));

        assertEquals(test, player_3.wherePawnCanMove(gameBoard, player_3.getPawns().get(0)));

        /* case when the player can move in more than one cell */

        gameBoard.getCell(2,1).destroyRoof(new Building(0,25));
        gameBoard.getCell(3,1).destroyRoof(new Building(0,25));

        test.clear();

        test.add(gameBoard.getCell(2,1));
        test.add(gameBoard.getCell(3,1));
        test.add(gameBoard.getCell(3,3));

        assertEquals(test, player_3.wherePawnCanMove(gameBoard, player_3.getPawns().get(0)));

        /* case when the player cannot move in any cell because every neighbouring cell has an higher level */

        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,3).buildOnThisCell(buildings.get(0));

        test.clear();

        assertEquals(test, player_3.wherePawnCanMove(gameBoard, player_3.getPawns().get(0)));


    }
}