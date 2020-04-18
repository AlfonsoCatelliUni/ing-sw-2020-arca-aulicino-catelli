package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotMoveBackEffectTest {

    private Board gameBoard;

    private Player player;

    private List<Cell> availableCellsToMove;

    private List<Cell> correctListAvailableCellsMove;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        player = new Player("alfo", Color.WHITE, new Card("alfo's card", true, "alfo's effect"),new MoreMoveEffect(new BasicEffect()));

        availableCellsToMove = new ArrayList<>();
        correctListAvailableCellsMove = new ArrayList<>();

        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, gameBoard.getCell(2,2));

    }

    @Test
    void wherePawnCanMove() {

        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,1));

        availableCellsToMove = player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(1,1));

        correctListAvailableCellsMove.add(gameBoard.getCell(2,0));
        correctListAvailableCellsMove.add(gameBoard.getCell(0,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(2,1));
        correctListAvailableCellsMove.add(gameBoard.getCell(1,2));
        correctListAvailableCellsMove.add(gameBoard.getCell(0,2));
        correctListAvailableCellsMove.add(gameBoard.getCell(1,0));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        correctListAvailableCellsMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctListAvailableCellsMove, availableCellsToMove);

    }


}