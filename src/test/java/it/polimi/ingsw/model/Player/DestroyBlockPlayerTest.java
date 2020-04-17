package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.DestroyAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DestroyBlockPlayerTest {

    Board gameBoard;

    List<Action> test;

    List<Cell> test_1;

    Card card;

    List<Building> buildings;

    BasicPlayer player1;

    BasicPlayer player2;

    BasicPlayer player3;

    DestroyBlockPlayer player_1;

    DestroyBlockPlayer player_2;

    DestroyBlockPlayer player_3;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        test = new ArrayList<>();
        test_1 = new ArrayList<>();
        card = new Card("test", true, "test");
        buildings = gameBoard.getBuildings();

        player1 = new BasicPlayer("test1", Color.BLUE, card);
        player_1 = new DestroyBlockPlayer(player1);
        player2 = new BasicPlayer("test2", Color.GREY, card);
        player_2 = new DestroyBlockPlayer(player2);
        player3 = new BasicPlayer("test3", Color.WHITE, card);
        player_3 = new DestroyBlockPlayer(player3);

        player_1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player_1.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));
        player_2.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,1));
        player_2.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(1,2));
        player_3.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player_3.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,3));
    }


    @Test
    void getPossibleActions() {

        int i;

        player_1.move(gameBoard, player_1.getPawns()[0], gameBoard.getCell(1,0));
        player_1.build(player_1.getPawns()[0], gameBoard.getCell(2,0), 1, buildings);

        /* case when the pawn cannot destroy any block because there are only level 0 cells around him */
        test.add(new FinishAction());

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_1.getPossibleActions(gameBoard, player_1.getPawns()[0]).get(i).getClass());

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));

        test.add(new DestroyAction());

        /* case when the pawn can destroy a block */
        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_1.getPossibleActions(gameBoard, player_1.getPawns()[0]).get(i).getClass());

        test.clear();
        test.add(new FinishAction());

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(3));


        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(3));

        /* case when thew pawn cannot destroy any block because has only dome blocks around him */
        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_1.getPossibleActions(gameBoard, player_1.getPawns()[0]).get(i).getClass());

    }


    @Test
    void destroyBlock() {

        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));

        player_1.destroyBlock(buildings, gameBoard.getCell(1,0));

        assertEquals(0, gameBoard.getCell(0,0).getHeight());

    }


    @Test
    void wherePawnCanDestroy() {

        assertEquals(test_1, player_1.wherePawnCanDestroy(gameBoard, player_1.getPawns()[1]));

        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));

        test_1.add(gameBoard.getCell(1,0));

        assertEquals(test_1, player_1.wherePawnCanDestroy(gameBoard, player_1.getPawns()[0]));


    }
}