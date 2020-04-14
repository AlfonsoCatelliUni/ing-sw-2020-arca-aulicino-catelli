package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
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

class BuildBeforePlayerTest {

    Board gameBoard;

    List<Action> test;

    List<Cell> test_1;

    Card card;

    List<Building> buildings;

    BasicPlayer player1;

    BasicPlayer player2;

    BasicPlayer player3;

    BuildBeforePlayer player_1;

    BuildBeforePlayer player_2;

    BuildBeforePlayer player_3;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        test = new ArrayList<>();
        test_1 = new ArrayList<>();
        card = new Card("test", true, "test");
        buildings = gameBoard.getBuildings();

        player1 = new BasicPlayer("test1", Color.BLUE, card);
        player_1 = new BuildBeforePlayer(player1);
        player2 = new BasicPlayer("test2", Color.GREY, card);
        player_2 = new BuildBeforePlayer(player2);
        player3 = new BasicPlayer("test3", Color.WHITE, card);
        player_3 = new BuildBeforePlayer(player3);

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


        /* case when the pawn can build in at least one cell */

        test.add(new BuildAction());
        test.add(new MoveAction());

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_1.getPossibleActions(gameBoard, player_1.getPawns()[1]).get(i).getClass());


        /* case when the player has built the first time, so he can only move */

        player_1.setHasBuiltBefore(true);

        test.clear();
        test.add(new MoveAction());

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_1.getPossibleActions(gameBoard, player_1.getPawns()[1]).get(i).getClass());

        /* case when the pawn moved, so he can only build */

        player_1.setNumMove(1);
        player_1.setNumBuild(0);

        test.clear();
        test.add(new BuildAction());

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_1.getPossibleActions(gameBoard, player_1.getPawns()[1]).get(i).getClass());

        /* case when the player has build for the second time, so he has to finish his turn */

        player_1.setNumBuild(1);

        test.clear();
        test.add(new FinishAction());

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_1.getPossibleActions(gameBoard, player_1.getPawns()[1]).get(i).getClass());

        /* case when are the domes are placed on the board */

        gameBoard.getBuildings().get(3).setPlacedNumber(18);

        test.clear();
        test.add(new MoveAction());

        gameBoard.getCell(2,2).setHeight(2);
        player_3.getPawns()[0].setHeight(2);

        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(3,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,3).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(3,3).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(2));

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player_3.getPossibleActions(gameBoard, player_3.getPawns()[0]).get(i).getClass());




    }


    @Test
    void wherePawnCanMove() {


        /* case when the player has built before, so the pawn cannot move up */

        player_3.setHasBuiltBefore(true);

        gameBoard.getCell(2,1).setHeight(1);
        gameBoard.getCell(3,1).setHeight(1);
        gameBoard.getCell(3,2).setHeight(1);
        gameBoard.getCell(1,3).setHeight(1);

        test_1.add(gameBoard.getCell(3,3));

        assertEquals(test_1, player_3.wherePawnCanMove(gameBoard, player_3.getPawns()[0]));

        /* same case, but the pawn can move in more than one cell */

        gameBoard.getCell(2,1).setHeight(0);
        gameBoard.getCell(3,1).setHeight(0);

        test_1.clear();

        test_1.add(gameBoard.getCell(2,1));
        test_1.add(gameBoard.getCell(3,1));
        test_1.add(gameBoard.getCell(3,3));

        assertEquals(test_1, player_3.wherePawnCanMove(gameBoard, player_3.getPawns()[0]));

        /* case when the player cannot move up because of the BlockOpponentPlayer effect */

        player_2.setCanMoveUp(false);
        player_2.setHasBuiltBefore(false);

        test_1.clear();
        test_1.add(gameBoard.getCell(0,2));
        test_1.add(gameBoard.getCell(1,0));
        test_1.add(gameBoard.getCell(2,0));
        test_1.add(gameBoard.getCell(2,1));


        assertEquals(test_1, player_2.wherePawnCanMove(gameBoard,player_2.getPawns()[0]));

    }


    @Test
    void pawnBuild() {

        /* case when the player can build before moving, so NumBuild increases by 1, and I have to reset it to 1,
        after that I set the flag hasBuiltBefore */

        player_3.setHasBuiltBefore(false);

        player_3.pawnBuild(player_3.getPawns()[0], gameBoard.getCell(3,3), 1, buildings);

        assertEquals(1, player_3.getNumBuild());
        assertTrue(player_3.getHasBuiltBefore());

    }


}