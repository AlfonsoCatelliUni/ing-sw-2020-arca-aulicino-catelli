package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
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

class CanPushOpponentEffectTest {

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
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));

        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(2,3).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(3));

        gameBoard.getCell(4,4).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(4,4).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(4,4).buildOnThisCell(buildings.get(2));

        alfoEffect = new BasicEffect();
        alfoEffect = new CanPushOpponentEffect(alfoEffect);
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,0));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(3,3));

    }


    @Test
    void wherePawnCanMove() {

        List<Cell> availableCellsToMove;
        List<Cell> correctCellsToMove = new ArrayList<>();

        //the pawn in the center of the board is surrounded by dome except for one cell
        //this cell is occupied by an enemy pawn that can be forced into a three level tower
        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, alfoPlayer.getPawnInCoordinates(2,2));
        correctCellsToMove.add(gameBoard.getCell(3,3));

        correctCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        availableCellsToMove.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));

        assertEquals(correctCellsToMove.size(), availableCellsToMove.size());
        for(int i = 0; i < correctCellsToMove.size(); i++)
            assertEquals(correctCellsToMove.get(i), availableCellsToMove.get(i));


        //now, the cell where previously I was able to force the opponent pawn into, is occupied by a dome
        //my pawn now can't move
        gameBoard.getCell(4,4).buildOnThisCell(buildings.get(3));
        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, alfoPlayer.getPawnInCoordinates(2,2));
        correctCellsToMove.clear();

        assertEquals(correctCellsToMove.size(), availableCellsToMove.size());


        //the pawn cannot move up and is surrounded all by dome except for a cell where there is a pawn in a three level tower
        alfoPlayer.setEffect(new NotMoveUpEffect(alfoPlayer.getEffect()));

        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctCellsToMove.clear();

        assertEquals(correctCellsToMove.size(), availableCellsToMove.size());



    }


}