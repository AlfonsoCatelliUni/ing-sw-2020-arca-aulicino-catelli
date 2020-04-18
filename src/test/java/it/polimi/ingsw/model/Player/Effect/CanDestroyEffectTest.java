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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CanDestroyEffectTest {


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

    private List<Building> buildings;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        alfoEffect = new CanDestroyEffect(new DestroyEffect(alfoEffect));
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
    void wherePawnCanDestroy() {

        List<Cell> availableCellsToDestroy;
        List<Cell> correctCellsToDestroy = new ArrayList<>();


        //The pawn is on a two level tower and is surrounded by an opponent pawn, a dome and a ground level
        //So he can't destroy anything
        availableCellsToDestroy = alfoPlayer.wherePawnCanDestroy(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));

        assertEquals(correctCellsToDestroy.size(), availableCellsToDestroy.size());


        //The pawn is in the same situation of before but instead of the ground level now there's a level one
        //and so the pawn can destroy only that block
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        availableCellsToDestroy = alfoPlayer.wherePawnCanDestroy(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));
        correctCellsToDestroy.add(gameBoard.getCell(1,0));

        assertEquals(correctCellsToDestroy.size(), availableCellsToDestroy.size());
        for (int i = 0; i < correctCellsToDestroy.size(); i++) {
            assertEquals(correctCellsToDestroy.get(i), availableCellsToDestroy.get(i));
        }


    }


}