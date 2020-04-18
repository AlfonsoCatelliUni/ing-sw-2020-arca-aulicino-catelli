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

class CanBuildUnderItselfEffectTest {

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
        alfoEffect = new CanBuildUnderItselfEffect(new BuildUnderItselfEffect(alfoEffect));
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
    void wherePawnCanBuild() {

        List<Cell> availableCellsToBuild;
        List<Cell> correctCellsToBuild = new ArrayList<>();


        //the pawn is on a two level tower and can build under itself and on a neighboring cell
        availableCellsToBuild = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));
        correctCellsToBuild.add(gameBoard.getCell(1,0));
        correctCellsToBuild.add(gameBoard.getCell(0,0));

        assertEquals(correctCellsToBuild.size(), availableCellsToBuild.size());
        for (int i = 0; i < correctCellsToBuild.size(); i++) {
            assertEquals(correctCellsToBuild.get(i), availableCellsToBuild.get(i));
        }


        //the pawn is on a two level tower and can build only under itself
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));

        availableCellsToBuild = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));
        correctCellsToBuild.clear();
        correctCellsToBuild.add(gameBoard.getCell(0,0));

        assertEquals(correctCellsToBuild.size(), availableCellsToBuild.size());
        for (int i = 0; i < correctCellsToBuild.size(); i++) {
            assertEquals(correctCellsToBuild.get(i), availableCellsToBuild.get(i));
        }


        //the pawn has built under itself and now is on a three level tower and cannot build
        alfoPlayer.build(alfoPlayer.getPawnInCoordinates(0,0), gameBoard.getCell(0,0), 3, buildings);

        availableCellsToBuild = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));
        correctCellsToBuild.clear();

        assertEquals(correctCellsToBuild.size(), availableCellsToBuild.size());
        for (int i = 0; i < correctCellsToBuild.size(); i++) {
            assertEquals(correctCellsToBuild.get(i), availableCellsToBuild.get(i));
        }

    }


}