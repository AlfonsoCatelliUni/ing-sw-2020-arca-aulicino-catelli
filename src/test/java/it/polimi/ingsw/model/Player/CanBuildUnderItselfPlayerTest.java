package it.polimi.ingsw.model.Player;

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

class CanBuildUnderItselfPlayerTest {


    private Board gameBoard;

    private Player alfoPlayer;

    private List<Building> buildings;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        alfoPlayer = new CanBuildUnderItselfPlayer( new BasicPlayer("alfonso", Color.BLUE, new Card("Zeus", true, "effetto_zeus")));
        alfoPlayer.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(1));
        alfoPlayer.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));

    }


    @Test
    void wherePawnCanBuild() {
        List<Cell> cellsAvailableToBuild = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));

        List<Cell> correctCells = new ArrayList<>();
        correctCells.add(gameBoard.getCell(0,1));
        correctCells.add(gameBoard.getCell(1,0));
        correctCells.add(gameBoard.getCell(1,1));
        correctCells.add(gameBoard.getCell(0,0));

        assertEquals(correctCells, cellsAvailableToBuild);


        cellsAvailableToBuild = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(2,2));
        correctCells.clear();
        correctCells.add(gameBoard.getCell(1,1));
        correctCells.add(gameBoard.getCell(1,2));
        correctCells.add(gameBoard.getCell(1,3));
        correctCells.add(gameBoard.getCell(2,1));
        correctCells.add(gameBoard.getCell(2,3));
        correctCells.add(gameBoard.getCell(3,1));
        correctCells.add(gameBoard.getCell(3,2));
        correctCells.add(gameBoard.getCell(3,3));
        correctCells.add(gameBoard.getCell(2,2));

        assertEquals(correctCells, cellsAvailableToBuild);


        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(2));
        alfoPlayer.getPawnInCoordinates(2,2).setHeight(3);

        cellsAvailableToBuild = alfoPlayer.wherePawnCanBuild(gameBoard, alfoPlayer.getPawnInCoordinates(2,2));
        correctCells.remove(8);

        assertEquals(correctCells, cellsAvailableToBuild);


    }


    @Test
    void pawnBuild() {

        alfoPlayer.pawnBuild(alfoPlayer.getPawnInCoordinates(2,2), gameBoard.getCell(2,2), 3, buildings);

        Building roofAfterBuild = gameBoard.getCell(2,2).getRoof();
        Pawn alfoPawn = alfoPlayer.getPawnInCoordinates(2,2);

        assertEquals(3, roofAfterBuild.getLevel());
        assertEquals(3, alfoPawn.getHeight());
        assertEquals(gameBoard.getCell(2,2), alfoPawn.getPosition());
        assertEquals(true, alfoPawn.getForcedMove());

    }


}