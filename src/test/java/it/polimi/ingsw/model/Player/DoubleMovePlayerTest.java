package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoubleMovePlayerTest {


    Board gameBoard;
    Player player;


    @Test
    void wherePawnCanMove() {

        gameBoard = new Board();
        player = new DoubleMovePlayer(new BasicPlayer("playerTest", Color.BLUE, "playerGodTest"));
        List<Cell> availableCellsToMove = new ArrayList<>();
        List<Cell> correctListAvailableCellsMove = new ArrayList<>();

        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);

        player.setCanMoveUp(true);
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));


        /* Level One */
        gameBoard.getCell(0,1).buildOnThisCell(levelOne);
        /* Level Two */
        gameBoard.getCell(0,2).buildOnThisCell(levelOne);
        gameBoard.getCell(0,2).buildOnThisCell(levelTwo);
        /* Level Three */
        gameBoard.getCell(1,2).buildOnThisCell(levelOne);
        gameBoard.getCell(1,2).buildOnThisCell(levelTwo);
        /* Dome Level */
        gameBoard.getCell(1,0).buildOnThisCell(levelFour);
        /* Dome Level */
        gameBoard.getCell(2,1).buildOnThisCell(levelFour);


        /* in this case the pawn can move up, there's a ramp to a second level */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctListAvailableCellsMove.add(gameBoard.getCell(0,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(0,2));
        correctListAvailableCellsMove.add(gameBoard.getCell(2,0));
        correctListAvailableCellsMove.add(gameBoard.getCell(1,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(1,2));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctListAvailableCellsMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(availableCellsToMove, correctListAvailableCellsMove);
        correctListAvailableCellsMove.clear();


        /* in this case the pawn cannot move up, so it can move only to the same or lower level */
        player.setCanMoveUp(false);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        correctListAvailableCellsMove.add(gameBoard.getCell(1,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(2,0));

        assertEquals(availableCellsToMove, correctListAvailableCellsMove);
        correctListAvailableCellsMove.clear();


    }

}