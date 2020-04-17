package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CanSwitchOpponentEffectTest {

    private Board gameBoard;

    private Effect alfoEffect;
    private Effect massiEffect;
    private Effect giammaEffect;

    private Card alfoCard;
    private Card massiCard;
    private Card giammaCard;

    private Player alfoPlayer;
    private Player massiPlayer;
    private Player giammaPlayer;

    List<Building> buildings;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        alfoEffect = new CanSwitchOpponentEffect(alfoEffect);
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(0,4));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,1));

    }


    @Test
    void wherePawnCanMove() {
        List<Cell> correctAvailableToMove = new ArrayList<>();
        List<Cell> availableCellsToMove;


        // Pawn on a second level, a dome near this pawn, there is a pawn near the pawn and the other cell is free
        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableToMove.add(gameBoard.getCell(1,0));
        correctAvailableToMove.add(gameBoard.getCell(1,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition)); //Sorting the list
        correctAvailableToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctAvailableToMove, availableCellsToMove);
        correctAvailableToMove.clear();


        // Pawn cannot move up and there's a new third level tower near the pawn
        alfoPlayer.setEffect(new NotMoveUpEffect(alfoPlayer.getEffect()));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));

        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctAvailableToMove.add(gameBoard.getCell(1,1));

        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition)); //Sorting the list
        correctAvailableToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctAvailableToMove, availableCellsToMove);
        correctAvailableToMove.clear();

    }


}