package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board gameBoard;

    private List<Building> buildings;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        buildings = gameBoard.getBuildings();

    }


    @Test
    void getCellAvailableToMove() {

        Cell cell_0 = gameBoard.getCell(0, 0);
        cell_0.setHeight(0);

        Pawn designatedPawn = new Pawn(Color.BLUE, Sex.MALE, cell_0);

        cell_0.placePawnHere(designatedPawn);

        Cell cell_1 = gameBoard.getCell(0, 1);
        Cell cell_2 = gameBoard.getCell(1, 0);
        Cell cell_3 = gameBoard.getCell(1, 1);

        cell_1.setHeight(0);
        cell_2.setHeight(1);
        cell_3.setHeight(0);

        List<Cell> cells = gameBoard.getCellAvailableToMove(designatedPawn);

        ArrayList<Cell> testCell = new ArrayList<>();
        testCell.add(cell_1);
        testCell.add(cell_2);
        testCell.add(cell_3);

        //case where the pawn can be moved in all the neighboring cells
        assertEquals(testCell, cells);

        cell_2.setHeight(2);
        testCell.remove(cell_2);

        cells = gameBoard.getCellAvailableToMove(designatedPawn);

        //case where the pawn can be moved in two of the neighboring cells [1,0]
        assertEquals(testCell, cells);

        Pawn addedPawn = new Pawn(Color.GREY, Sex.FEMALE, cell_1);

        cell_1.placePawnHere(addedPawn);

        cells = gameBoard.getCellAvailableToMove(designatedPawn);

        testCell.remove(cell_1);

        //case where there is a pawn next to the designatedPawn (in [0,1]), so the pawn can be moved only in [1,1]
        assertEquals(testCell, cells);

        cell_3.buildOnThisCell(new Building(4, 18));

        cells = gameBoard.getCellAvailableToMove(designatedPawn);

        testCell.remove(cell_3);

        //case where a dome is built in a cell where the designatedPawn can be moved to + the pawn cannot move (empty list)
        assertEquals(testCell, cells);
    }


    @Test
    void getCellAvailableToBuild() {

        Pawn pawn = new Pawn(Color.BLUE, Sex.MALE, gameBoard.getCell(0, 0));
        gameBoard.getCell(0, 0).placePawnHere(pawn);

        List<Cell> cells = gameBoard.getCellAvailableToBuild(pawn);
        List<Cell> expectedCells = new ArrayList<>();

        expectedCells.add(gameBoard.getCell(0, 1));
        expectedCells.add(gameBoard.getCell(1, 0));
        expectedCells.add(gameBoard.getCell(1, 1));

        assertEquals(cells, expectedCells);

        /* pawn in a center cell with a pawn near it and a dome */
        Pawn pawn1 = new Pawn(Color.GREY, Sex.FEMALE, gameBoard.getCell(1, 2));
        gameBoard.getCell(1, 2).placePawnHere(pawn1);
        gameBoard.getCell(2, 3).buildOnThisCell(new Building(4, 18));
        gameBoard.getCell(3, 3).buildOnThisCell(new Building(3, 22));

        pawn.moveTo(gameBoard.getCell(2, 2));
        gameBoard.getCell(2, 2).placePawnHere(pawn);
        cells = gameBoard.getCellAvailableToBuild(pawn);

        ArrayList<Cell> expectedCells2 = new ArrayList<>();
        expectedCells2.add(gameBoard.getCell(1, 1));
        expectedCells2.add(gameBoard.getCell(1, 3));
        expectedCells2.add(gameBoard.getCell(2, 1));
        expectedCells2.add(gameBoard.getCell(3, 1));
        expectedCells2.add(gameBoard.getCell(3, 2));
        expectedCells2.add(gameBoard.getCell(3, 3));
        assertEquals(cells, expectedCells2);

        /* empty list */
        gameBoard.getCell(3, 1).buildOnThisCell(new Building(4, 18));
        gameBoard.getCell(1, 1).buildOnThisCell(new Building(4, 18));
        gameBoard.getCell(3, 3).buildOnThisCell(new Building(4, 18));
        Pawn pawn2 = new Pawn(Color.GREY, Sex.FEMALE, gameBoard.getCell(1, 3));
        gameBoard.getCell(1, 3).placePawnHere(pawn2);
        Pawn pawn3 = new Pawn(Color.GREY, Sex.FEMALE, gameBoard.getCell(2, 1));
        gameBoard.getCell(2, 1).placePawnHere(pawn3);
        gameBoard.getCell(3, 2).buildOnThisCell(new Building(4, 18));

        cells = gameBoard.getCellAvailableToBuild(pawn);
        List<Cell> expectedCells3 = new ArrayList<>();

        assertEquals(cells, expectedCells3);


        //test if there is no pieces to build

        gameBoard.getBuildings().get(0).setPlacedNumber(22);
        gameBoard.getBuildings().get(1).setPlacedNumber(18);
        gameBoard.getBuildings().get(2).setPlacedNumber(14);
        gameBoard.getBuildings().get(3).setPlacedNumber(18);

        cells = gameBoard.getCellAvailableToBuild(pawn);

        assertEquals(cells, expectedCells3);


    }


    @Test
    void getNeighboring() {

        /* Testing a central cell */
        List<Cell> retNeighboring = gameBoard.getNeighboring(gameBoard.getCell(2, 2));

        List<Cell> correctNeighboring = new ArrayList<>();
        correctNeighboring.add(gameBoard.getCell(1, 1));
        correctNeighboring.add(gameBoard.getCell(1, 2));
        correctNeighboring.add(gameBoard.getCell(1, 3));
        correctNeighboring.add(gameBoard.getCell(2, 1));
        correctNeighboring.add(gameBoard.getCell(2, 3));
        correctNeighboring.add(gameBoard.getCell(3, 1));
        correctNeighboring.add(gameBoard.getCell(3, 2));
        correctNeighboring.add(gameBoard.getCell(3, 3));

        assertEquals(correctNeighboring, retNeighboring);


        /* Testing a corner cell */
        retNeighboring = gameBoard.getNeighboring(gameBoard.getCell(0, 0));

        correctNeighboring.clear();
        correctNeighboring.add(gameBoard.getCell(0, 1));
        correctNeighboring.add(gameBoard.getCell(1, 0));
        correctNeighboring.add(gameBoard.getCell(1, 1));


        /* Testing a side cell */
        retNeighboring = gameBoard.getNeighboring(gameBoard.getCell(1, 0));

        correctNeighboring.clear();
        correctNeighboring.add(gameBoard.getCell(0, 0));
        correctNeighboring.add(gameBoard.getCell(0, 1));
        correctNeighboring.add(gameBoard.getCell(1, 1));
        correctNeighboring.add(gameBoard.getCell(2, 0));
        correctNeighboring.add(gameBoard.getCell(2, 1));


    }


    @Test
    void getPossibleBuildingOnCell() {


        /* Tested that on a level 0 you can build only a level 1*/
        List<Building> ret = gameBoard.getPossibleBuildingOnCell(gameBoard.getCell(0, 0));

        List<Building> correctRet = new ArrayList<>();
        correctRet.add(buildings.get(0));

        assertEquals(correctRet, ret);


        /* Tested that on a dome you cannot build any type of building */
        gameBoard.getCell(0, 0).buildOnThisCell(buildings.get(3));

        ret = gameBoard.getPossibleBuildingOnCell(gameBoard.getCell(0, 0));
        correctRet.clear();

        assertEquals(correctRet, ret);

        //test if there is no pieces of building to build
        gameBoard.getBuildings().get(0).setPlacedNumber(22);
        gameBoard.getBuildings().get(1).setPlacedNumber(18);
        gameBoard.getBuildings().get(2).setPlacedNumber(14);
        gameBoard.getBuildings().get(3).setPlacedNumber(18);

        ret = gameBoard.getPossibleBuildingOnCell(gameBoard.getCell(0, 0));

        assertEquals(0, ret.size());

    }


    @Test
    void destroyTowers() {

        //build complete tower
        gameBoard.getCell(0, 0).buildOnThisCell(gameBoard.getBuildings().get(0));
        gameBoard.getCell(0, 0).buildOnThisCell(gameBoard.getBuildings().get(1));
        gameBoard.getCell(0, 0).buildOnThisCell(gameBoard.getBuildings().get(2));
        gameBoard.getCell(0, 0).buildOnThisCell(gameBoard.getBuildings().get(3));

        gameBoard.getCell(0, 1).buildOnThisCell(gameBoard.getBuildings().get(0));
        gameBoard.getCell(0, 1).buildOnThisCell(gameBoard.getBuildings().get(1));
        gameBoard.getCell(0, 1).buildOnThisCell(gameBoard.getBuildings().get(2));
        gameBoard.getCell(0, 1).buildOnThisCell(gameBoard.getBuildings().get(3));

        //no complete tower with dome
        gameBoard.getCell(1, 0).buildOnThisCell(gameBoard.getBuildings().get(0));
        gameBoard.getCell(1, 0).buildOnThisCell(gameBoard.getBuildings().get(1));
        gameBoard.getCell(1, 0).buildOnThisCell(gameBoard.getBuildings().get(3));

        //normal construction
        gameBoard.getCell(2, 2).buildOnThisCell(gameBoard.getBuildings().get(0));
        gameBoard.getCell(2, 2).buildOnThisCell(gameBoard.getBuildings().get(1));
        gameBoard.getCell(2, 2).buildOnThisCell(gameBoard.getBuildings().get(2));


        gameBoard.destroyTowers();


        assertEquals(2, gameBoard.getBuildings().get(0).getPlacedNumber());
        assertEquals(2, gameBoard.getBuildings().get(1).getPlacedNumber());
        assertEquals(1, gameBoard.getBuildings().get(2).getPlacedNumber());
        assertEquals(3, gameBoard.getBuildings().get(3).getPlacedNumber());

        assertEquals(1, gameBoard.getCell(0, 0).getHeight());
        assertEquals(gameBoard.getBuildings().get(3), gameBoard.getCell(0, 0).getRoof());

        assertEquals(1, gameBoard.getCell(0, 1).getHeight());
        assertEquals(gameBoard.getBuildings().get(3), gameBoard.getCell(0, 1).getRoof());

        assertEquals(3, gameBoard.getCell(1, 0).getHeight());
        assertEquals(gameBoard.getBuildings().get(3), gameBoard.getCell(1, 0).getRoof());

        assertEquals(3, gameBoard.getCell(2, 2).getHeight());
        assertEquals(gameBoard.getBuildings().get(2), gameBoard.getCell(2, 2).getRoof());


    }


    @Test
    void getSymmetrical() {

        Cell correctCell;
        Cell returnedCell;

        returnedCell = gameBoard.getSymmetrical(gameBoard.getCell(2, 2), gameBoard.getCell(2, 3));
        correctCell = gameBoard.getCell(2, 1);
        assertEquals(correctCell, returnedCell);

        returnedCell = gameBoard.getSymmetrical(gameBoard.getCell(0, 0), gameBoard.getCell(0, 1));
        assertNull(returnedCell);

        returnedCell = gameBoard.getSymmetrical(gameBoard.getCell(0, 4), gameBoard.getCell(0, 3));
        assertNull(returnedCell);

        returnedCell = gameBoard.getSymmetrical(gameBoard.getCell(4, 4), gameBoard.getCell(4, 3));
        assertNull(returnedCell);

        returnedCell = gameBoard.getSymmetrical(gameBoard.getCell(4, 0), gameBoard.getCell(4, 1));
        assertNull(returnedCell);

        returnedCell = gameBoard.getSymmetrical(gameBoard.getCell(4, 3), gameBoard.getCell(4, 2));
        correctCell = gameBoard.getCell(4, 4);
        assertEquals(correctCell, returnedCell);


    }


    @Test
    void getOpponentsNeighboring() {
        Pawn pawn = new Pawn(Color.WHITE, Sex.MALE, gameBoard.getCell(0, 0));

        gameBoard.getCell(0, 0).placePawnHere(pawn);
        gameBoard.getCell(1, 0).placePawnHere(new Pawn(Color.WHITE, Sex.FEMALE, gameBoard.getCell(1, 0)));

        gameBoard.getCell(0, 1).placePawnHere(new Pawn(Color.BLUE, Sex.FEMALE, gameBoard.getCell(0, 1)));


        List<Cell> returnedCell = gameBoard.getOpponentsNeighboring(pawn);
        List<Cell> correctNeighboring = new ArrayList<>();
        correctNeighboring.add(gameBoard.getCell(0, 1));

        assertEquals(correctNeighboring, returnedCell);

        gameBoard.getCell(0, 1).freeCell();

        returnedCell = gameBoard.getOpponentsNeighboring(pawn);

        assertEquals(0, returnedCell.size());


    }

    @Test
    void matrix() {

        int i;
        List<Cell> cellOfBoard = gameBoard.matrix();
        cellOfBoard.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        for (i = 0; i < 25; ) {
            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 5; c++, i++) {
                    assertEquals(gameBoard.getMatrixBoard()[r][c], cellOfBoard.get(i));
                }
            }
        }

    }


    @Test
    void getMatrixBoard() {
        Cell[][] matrixBoardRet = gameBoard.getMatrixBoard();

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                assertEquals(gameBoard.getCell(r, c), matrixBoardRet[r][c]);


            }
        }
    }


    @Test
    void getPawnByCoordinates() {

        Pawn pawn = new Pawn(Color.WHITE, Sex.FEMALE, gameBoard.getCell(0,0));
        gameBoard.getCell(0,0).placePawnHere(pawn);

        assertEquals(pawn, gameBoard.getPawnByCoordinates(0,0));
        assertNull(gameBoard.getPawnByCoordinates(1,1));


    }

    @Test
    void getAllPawns() {

        Pawn pawn1 = new Pawn(Color.WHITE, Sex.FEMALE, gameBoard.getCell(0,0));
        Pawn pawn2 = new Pawn(Color.WHITE, Sex.MALE, gameBoard.getCell(1,0));
        Pawn pawn3 = new Pawn(Color.BLUE, Sex.FEMALE, gameBoard.getCell(2,2));
        Pawn pawn4 = new Pawn(Color.BLUE, Sex.MALE, gameBoard.getCell(3,3));
        Pawn pawn5 = new Pawn(Color.GREY, Sex.FEMALE, gameBoard.getCell(4,0));
        Pawn pawn6 = new Pawn(Color.GREY, Sex.MALE, gameBoard.getCell(4,4));

        gameBoard.getCell(0,0).placePawnHere(pawn1);
        gameBoard.getCell(1,0).placePawnHere(pawn2);
        gameBoard.getCell(2,2).placePawnHere(pawn3);
        gameBoard.getCell(3,3).placePawnHere(pawn4);
        gameBoard.getCell(4,0).placePawnHere(pawn5);
        gameBoard.getCell(4,4).placePawnHere(pawn6);

        List<Pawn> pawns = gameBoard.getAllPawns();

        assertEquals(pawn1, pawns.get(0));
        assertEquals(pawn2, pawns.get(1));
        assertEquals(pawn3, pawns.get(2));
        assertEquals(pawn4, pawns.get(3));
        assertEquals(pawn5, pawns.get(4));

    }

    @Test
    void getPawnsByColor() {
        Pawn pawn1 = new Pawn(Color.WHITE, Sex.FEMALE, gameBoard.getCell(0,0));
        Pawn pawn2 = new Pawn(Color.WHITE, Sex.MALE, gameBoard.getCell(1,0));
        Pawn pawn3 = new Pawn(Color.BLUE, Sex.FEMALE, gameBoard.getCell(2,2));
        Pawn pawn4 = new Pawn(Color.BLUE, Sex.MALE, gameBoard.getCell(3,3));
        Pawn pawn5 = new Pawn(Color.GREY, Sex.FEMALE, gameBoard.getCell(4,0));
        Pawn pawn6 = new Pawn(Color.GREY, Sex.MALE, gameBoard.getCell(4,4));

        gameBoard.getCell(0,0).placePawnHere(pawn1);
        gameBoard.getCell(1,0).placePawnHere(pawn2);
        gameBoard.getCell(2,2).placePawnHere(pawn3);
        gameBoard.getCell(3,3).placePawnHere(pawn4);
        gameBoard.getCell(4,0).placePawnHere(pawn5);
        gameBoard.getCell(4,4).placePawnHere(pawn6);


        List<Pawn> pawns = gameBoard.getPawnsByColor(Color.WHITE);

        assertEquals(pawn1, pawns.get(0));
        assertEquals(pawn2, pawns.get(1));


        List<Pawn> pawns1 = gameBoard.getPawnsByColor(Color.BLUE);

        assertEquals(pawn3, pawns1.get(0));
        assertEquals(pawn4, pawns1.get(1));


        List<Pawn> pawns2 = gameBoard.getPawnsByColor(Color.GREY);

        assertEquals(pawn5, pawns2.get(0));
        assertEquals(pawn6, pawns2.get(1));


    }

    @Test
    void destroyRoofInThisCell() {

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(3));

        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));

        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(1));

        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(2));

        gameBoard.destroyRoofInThisCell(0,0);
        gameBoard.destroyRoofInThisCell(0,1);
        gameBoard.destroyRoofInThisCell(0,2);
        gameBoard.destroyRoofInThisCell(0,3);

        assertEquals(buildings.get(2), gameBoard.getCell(0,0).getRoof());
        assertEquals(0, gameBoard.getCell(0,1).getHeight());
        assertEquals(buildings.get(0), gameBoard.getCell(0,2).getRoof());
        assertEquals(buildings.get(1), gameBoard.getCell(0,3).getRoof());


    }

    @Test
    void getNumberOfCompleteTowers() {

        assertEquals(0, gameBoard.getNumberOfCompleteTowers());

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(3));


        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));

        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));

        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(3));

        //not a complete tower
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(3));

        assertEquals(4, gameBoard.getNumberOfCompleteTowers());

    }
}