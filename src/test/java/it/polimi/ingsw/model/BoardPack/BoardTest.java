package it.polimi.ingsw.model.BoardPack;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {


    @Test
    void getCellAvailableToMove() {
    }


    @Test
    void getCellAvailableToBuild() {
    }


    @Test
    void getNeighboring() {

        Board gameBoard = new Board();

        /* Testing a central cell */
        ArrayList<Cell> retNeighboring = gameBoard.getNeighboring(gameBoard.getCell(2,2));

        ArrayList<Cell> correctNeighboring = new ArrayList<>();
        correctNeighboring.add(gameBoard.getCell(1,1));
        correctNeighboring.add(gameBoard.getCell(1,2));
        correctNeighboring.add(gameBoard.getCell(1,3));
        correctNeighboring.add(gameBoard.getCell(2,1));
        correctNeighboring.add(gameBoard.getCell(2,3));
        correctNeighboring.add(gameBoard.getCell(3,1));
        correctNeighboring.add(gameBoard.getCell(3,2));
        correctNeighboring.add(gameBoard.getCell(3,3));

        assertEquals(correctNeighboring, retNeighboring);


        /* Testing a corner cell */
        retNeighboring = gameBoard.getNeighboring(gameBoard.getCell(0,0));

        correctNeighboring.clear();
        correctNeighboring.add(gameBoard.getCell(0,1));
        correctNeighboring.add(gameBoard.getCell(1,0));
        correctNeighboring.add(gameBoard.getCell(1,1));


        /* Testing a side cell */
        retNeighboring = gameBoard.getNeighboring(gameBoard.getCell(1,0));

        correctNeighboring.clear();
        correctNeighboring.add(gameBoard.getCell(0,0));
        correctNeighboring.add(gameBoard.getCell(0,1));
        correctNeighboring.add(gameBoard.getCell(1,1));
        correctNeighboring.add(gameBoard.getCell(2,0));
        correctNeighboring.add(gameBoard.getCell(2,1));


    }


    @Test
    void getPossibleBuildingOnCell() {

        Board gameBoard = new Board();
        ArrayList<Building> buildings = new ArrayList<>();

        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);

        buildings.add( levelOne );
        buildings.add( levelTwo );
        buildings.add( levelThree );
        buildings.add( levelFour );


        /* Tested that on a level 0 you can build only a level 1*/
        ArrayList<Building> ret = gameBoard.getPossibleBuildingOnCell(gameBoard.getCell(0,0), buildings);

        ArrayList<Building> correctRet = new ArrayList<>();
        correctRet.add(levelOne);

        assertEquals(correctRet, ret);


        /* Tested that on a dome you cannot build any type of building */
        gameBoard.getCell(0,0).buildOnThisCell(levelFour);

        ret = gameBoard.getPossibleBuildingOnCell(gameBoard.getCell(0,0), buildings);
        correctRet.clear();

        assertEquals(correctRet, ret);



    }


    @Test
    void getNumberOfDome() {

        Board gameBoard = new Board();

        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);

        int domeNum = gameBoard.getNumberOfDome();

        assertEquals(0, domeNum);

        /* an third level tower il built on the cell 0-0 */
        gameBoard.getCell(0,0).buildOnThisCell(levelOne);
        gameBoard.getCell(0,0).buildOnThisCell(levelTwo);
        gameBoard.getCell(0,0).buildOnThisCell(levelThree);

        domeNum = gameBoard.getNumberOfDome();
        assertEquals(0, domeNum);


        /* a complete tower il built on the cell 0-0 */
        gameBoard.getCell(0,0).buildOnThisCell(levelFour);

        domeNum = gameBoard.getNumberOfDome();
        assertEquals(1, domeNum);


        /* a dome is built on cell 3-3 */
        gameBoard.getCell(3,3).buildOnThisCell(levelFour);

        domeNum = gameBoard.getNumberOfDome();
        assertEquals(2, domeNum);


        /* an entire tower is built on the cell 0-4 */
        gameBoard.getCell(0,4).buildOnThisCell(levelOne);
        gameBoard.getCell(0,4).buildOnThisCell(levelTwo);
        gameBoard.getCell(0,4).buildOnThisCell(levelThree);
        gameBoard.getCell(0,4).buildOnThisCell(levelFour);

        domeNum = gameBoard.getNumberOfDome();
        assertEquals(3, domeNum);


    }


    @Test
    void getStringCellInfo() {

        Board gameBoard = new Board();

        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);

        String retString;


        /* Testing a dome on a ground level */
        gameBoard.getCell(1,0).buildOnThisCell(levelFour);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(1,0));

        assertEquals("1x", retString);


        /* Testing a complete tower */
        gameBoard.getCell(1,1).buildOnThisCell(levelOne);
        gameBoard.getCell(1,1).buildOnThisCell(levelTwo);
        gameBoard.getCell(1,1).buildOnThisCell(levelThree);
        gameBoard.getCell(1,1).buildOnThisCell(levelFour);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(1,1));

        assertEquals("4x", retString);


        /* Testing the blue male on a ground level */
        Pawn blueMale = new Pawn(Color.BLUE, Sex.MALE, gameBoard.getCell(0,0));
        gameBoard.getCell(0,0).placePawnHere(blueMale);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(0,0));

        assertEquals("0B", retString);


        /* Testing the blue female on a level one */
        gameBoard.getCell(0,1).buildOnThisCell(levelOne);
        Pawn blueFemale = new Pawn(Color.BLUE, Sex.FEMALE, gameBoard.getCell(0,1));
        gameBoard.getCell(0,1).placePawnHere(blueFemale);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(0,1));

        assertEquals("1b", retString);


        /* Testing the grey male on a level two */
        gameBoard.getCell(0,2).buildOnThisCell(levelOne);
        gameBoard.getCell(0,2).buildOnThisCell(levelTwo);
        Pawn greyMale = new Pawn(Color.GREY, Sex.MALE, gameBoard.getCell(0,2));
        gameBoard.getCell(0,2).placePawnHere(greyMale);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(0,2));

        assertEquals("2G", retString);


        /* Testing the grey female on a level three */
        gameBoard.getCell(0,3).buildOnThisCell(levelOne);
        gameBoard.getCell(0,3).buildOnThisCell(levelTwo);
        gameBoard.getCell(0,3).buildOnThisCell(levelThree);
        Pawn greyFemale = new Pawn(Color.GREY, Sex.FEMALE, gameBoard.getCell(0,3));
        gameBoard.getCell(0,3).placePawnHere(greyFemale);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(0,3));

        assertEquals("3g", retString);


        /* Testing the white male on a ground level */
        Pawn whiteMale = new Pawn(Color.WHITE, Sex.MALE, gameBoard.getCell(0,4));
        gameBoard.getCell(0,4).placePawnHere(whiteMale);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(0,4));

        assertEquals("0W", retString);

        /* Testing the white female on a level one */
        gameBoard.getCell(4,0).buildOnThisCell(levelOne);
        Pawn whiteFemale = new Pawn(Color.WHITE, Sex.FEMALE, gameBoard.getCell(4,0));
        gameBoard.getCell(4,0).placePawnHere(whiteFemale);
        retString = gameBoard.getStringCellInfo(gameBoard.getCell(4,0));

        assertEquals("1w", retString);




    }


}