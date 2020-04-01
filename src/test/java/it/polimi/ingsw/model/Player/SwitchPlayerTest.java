package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwitchPlayerTest {

    Board gameBoard;

    private Player player;
    private Player opponentPlayer;

    List<Building> buildings = new ArrayList<>();



    @Test
    void wherePawnCanMove() {

        gameBoard = new Board();

        buildings.add(new Building(1, 22));
        buildings.add(new Building(2, 18));
        buildings.add(new Building(3, 14));
        buildings.add(new Building(4, 18));

        player = new SwitchPlayer(new BasicPlayer("playerTest", Color.BLUE, "playerGodTest"));
        opponentPlayer = new BasicPlayer("opponentTest", Color.GREY, "opponentGodTest");

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));

        player.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE ,gameBoard.getCell(2,2));

        opponentPlayer.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,4));
        opponentPlayer.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(1,1));

        List<Cell> correctAvailableToMove = new ArrayList<>();
        List<Cell> availableCellsToMove;


        /* first test */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableToMove.add(gameBoard.getCell(1,0));
        correctAvailableToMove.add(gameBoard.getCell(1,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctAvailableToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctAvailableToMove, availableCellsToMove);
        correctAvailableToMove.clear();


        /* second test */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));
        player.setCanMoveUp(false);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableToMove.add(gameBoard.getCell(1,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctAvailableToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctAvailableToMove, availableCellsToMove);
        correctAvailableToMove.clear();


        /* third test */
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,0).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(3));
        player.setCanMoveUp(true);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(0, availableCellsToMove.size());

    }


    @Test
    void movePawn() {

        int retMoveEncoded = 0;

        gameBoard = new Board();

        buildings.add(new Building(1, 22));
        buildings.add(new Building(2, 18));
        buildings.add(new Building(3, 14));
        buildings.add(new Building(4, 18));

        player = new SwitchPlayer(new BasicPlayer("playerTest", Color.BLUE, "playerGodTest"));
        opponentPlayer = new BasicPlayer("opponentTest", Color.GREY, "opponentGodTest");

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));

        player.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE ,gameBoard.getCell(2,2));

        opponentPlayer.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,4));
        opponentPlayer.initPawn(gameBoard, Sex.FEMALE ,gameBoard.getCell(1,1));


        /* switch with an opponent player */
//        retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,1));

        assertEquals(0, retMoveEncoded);
        assertEquals(gameBoard.getPawnByCoordinates(0,0), opponentPlayer.getPawns()[1] );
        assertEquals(true, gameBoard.getPawnByCoordinates(0,0).getForcedMove());
        assertEquals(2, opponentPlayer.getPawns()[1].getHeight());


        /* normal move */
//        retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(1,1), gameBoard.getCell(1,2));

        assertEquals(0, retMoveEncoded);



    }


    @Test
    void getPossibleAction() {

        gameBoard = new Board();

        buildings.add(new Building(1, 22));
        buildings.add(new Building(2, 18));
        buildings.add(new Building(3, 14));
        buildings.add(new Building(4, 18));

        player = new SwitchPlayer(new BasicPlayer("playerTest", Color.BLUE, "playerGodTest"));
        opponentPlayer = new BasicPlayer("opponentTest", Color.GREY, "opponentGodTest");

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));

        player.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE ,gameBoard.getCell(2,2));

        opponentPlayer.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,4));
        opponentPlayer.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(1,1));

        List<String> availableActions;
        List<String> correctAvailableActions = new ArrayList<>();


        /* move test */
//        availableActions = player.getPossibleAction(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        availableActions = null;
        correctAvailableActions.add("move");

        assertEquals(correctAvailableActions, availableActions);
        correctAvailableActions.clear();


        /* build test */
        player.setNumMove(1);
        player.setNumBuild(0);

//        availableActions = player.getPossibleAction(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableActions.add("build");

        assertEquals(correctAvailableActions, availableActions);
        correctAvailableActions.clear();


        /* finish test */
        player.setNumMove(1);
        player.setNumBuild(1);

//        availableActions = player.getPossibleAction(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableActions.add("finish");

        assertEquals(correctAvailableActions, availableActions);
        correctAvailableActions.clear();


        /* no actions possible test */
        player.setNumMove(0);
        player.setNumBuild(1);

        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,0).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(3));

//        availableActions = player.getPossibleAction(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(correctAvailableActions, availableActions);
        correctAvailableActions.clear();







    }



}