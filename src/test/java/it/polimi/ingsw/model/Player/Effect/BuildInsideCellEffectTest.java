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

class BuildInsideCellEffectTest {

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
        alfoEffect = new BuildInsideCellEffect(alfoEffect);
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(3,3));


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(3,4));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,0));

    }


    @Test
    void wherePawnCanBuild() {

        List<Cell> availableCellsToBuildInside;
        List<Cell> correctCellsToBuildInside = new ArrayList<>();

        //Pawn is in the upper left corner and it's on a second level tower,
        //near him there's a dome, an enemy pawn and a free block, this last
        //one is an internal cell
        availableCellsToBuildInside = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));
        correctCellsToBuildInside.add(gameBoard.getCell(1,1));

        assertEquals(correctCellsToBuildInside.size(), availableCellsToBuildInside.size());
        for(int i = 0; i < correctCellsToBuildInside.size(); i++)
            assertEquals(correctCellsToBuildInside.get(i), availableCellsToBuildInside.get(i));


        //Pawn is in the lowe right internal corner, and have all cells free except for one perimetral
        availableCellsToBuildInside = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(3,3));
        correctCellsToBuildInside.clear();
        correctCellsToBuildInside.add(gameBoard.getCell(2,2));
        correctCellsToBuildInside.add(gameBoard.getCell(2,3));
        correctCellsToBuildInside.add(gameBoard.getCell(3,2));

        assertEquals(correctCellsToBuildInside.size(), availableCellsToBuildInside.size());
        for(int i = 0; i < correctCellsToBuildInside.size(); i++)
            assertEquals(correctCellsToBuildInside.get(i), availableCellsToBuildInside.get(i));


        //Same position of the previous test, but this time there's a dome in one of the internal cells
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(3));
        availableCellsToBuildInside = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(3,3));
        correctCellsToBuildInside.clear();
        correctCellsToBuildInside.add(gameBoard.getCell(2,2));
        correctCellsToBuildInside.add(gameBoard.getCell(2,3));

        assertEquals(correctCellsToBuildInside.size(), availableCellsToBuildInside.size());
        for(int i = 0; i < correctCellsToBuildInside.size(); i++)
            assertEquals(correctCellsToBuildInside.get(i), availableCellsToBuildInside.get(i));

    }



}