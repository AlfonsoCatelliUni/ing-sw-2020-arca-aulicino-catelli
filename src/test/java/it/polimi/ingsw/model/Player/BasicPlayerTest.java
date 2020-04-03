package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicPlayerTest {


    private Board gameBoard;


    private Player player;


    private Player opponentPlayer;


    /**
     * the cell startCell is not occupied by any pawn before the call of the method,
     * this is checked in the controller section
     */
    @Test
    void initPawn() {

        gameBoard = new Board();
        player = new BasicPlayer("test", Color.BLUE, "test");

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        assertEquals(gameBoard.getCell(0,0), player.getPawns()[0].getPosition());
        assertEquals(Sex.MALE, player.getPawns()[0].getSex());
        assertEquals(Color.BLUE, player.getPawns()[0].getColor());

        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));

        assertEquals(gameBoard.getCell(0,1), player.getPawns()[1].getPosition());
        assertEquals(Sex.FEMALE, player.getPawns()[1].getSex());
        assertEquals(Color.BLUE, player.getPawns()[1].getColor());

        /* once the pawns are initialized, a new initPawn must change nothing */

        Pawn oldPawnMale = player.getPawns()[0];
        Pawn oldPawnFemale = player.getPawns()[1];

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,2));

        assertEquals(oldPawnMale, player.getPawns()[0]);

        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,3));

        assertEquals(oldPawnFemale, player.getPawns()[1]);

    }


    // TODO : da rifare una volta implementata la move giusta
    @Test
    void movePawn() {

        gameBoard = new Board();
        player = new BasicPlayer("test", Color.BLUE, "test");
        opponentPlayer = new BasicPlayer("opp", Color.WHITE, "opp");
        int retMoveEncoded = 0;

        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));

        // Level one
        gameBoard.getCell(0,1).buildOnThisCell(levelOne);
        /* Level Two */
        gameBoard.getCell(0,2).buildOnThisCell(levelOne);
        gameBoard.getCell(0,2).buildOnThisCell(levelTwo);
        /* Level Three */
        gameBoard.getCell(0,3).buildOnThisCell(levelOne);
        gameBoard.getCell(0,3).buildOnThisCell(levelTwo);
        gameBoard.getCell(0,3).buildOnThisCell(levelThree);
        /* Dome Level */
        gameBoard.getCell(1,3).buildOnThisCell(levelOne);
        gameBoard.getCell(1,3).buildOnThisCell(levelTwo);
        gameBoard.getCell(1,3).buildOnThisCell(levelThree);
        gameBoard.getCell(1,3).buildOnThisCell(levelFour);


        //retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(0,1));

        assertEquals(0, retMoveEncoded);
        assertEquals(1, player.getNumMove());
        assertEquals(0, player.getNumBuild());

        player.setNumBuild(1);
        player.setNumMove(0);


        //retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(0,1), gameBoard.getCell(0,2));

        assertEquals(0, retMoveEncoded);
        assertEquals(1, player.getNumMove());
        assertEquals(0, player.getNumBuild());

        player.setNumBuild(1);
        player.setNumMove(0);


        //retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(0,2), gameBoard.getCell(0,3));

        assertEquals(1, retMoveEncoded);
        assertEquals(1, player.getNumMove());
        assertEquals(0, player.getNumBuild());

        player.setNumBuild(1);
        player.setNumMove(0);



    }

    //TODO : da fare una volta implementata la build giusta
    @Test
    void pawnBuild() {
    }


    /**
     * this method calls only the forcePawn from Pawn class that's already tested
     */
    @Test
    void forcePawn() {
    }


    @Test
    void wherePawnCanMove() {

        gameBoard = new Board();
        Player player = new BasicPlayer("test", Color.BLUE, "test");

        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));

        Pawn[] pawns = player.getPawns();
        List<Cell> availableCellsToMove;
        List<Cell> correctCellsToMove = new ArrayList<>();


        /* simple corner case */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[0]);
        correctCellsToMove.add(gameBoard.getCell(0,1));
        correctCellsToMove.add(gameBoard.getCell(1,0));
        correctCellsToMove.add(gameBoard.getCell(1,1));
        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* simple center case */
        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[1]);
        correctCellsToMove.add(gameBoard.getCell(1,1));
        correctCellsToMove.add(gameBoard.getCell(1,2));
        correctCellsToMove.add(gameBoard.getCell(1,3));
        correctCellsToMove.add(gameBoard.getCell(2,1));
        correctCellsToMove.add(gameBoard.getCell(2,3));
        correctCellsToMove.add(gameBoard.getCell(3,1));
        correctCellsToMove.add(gameBoard.getCell(3,2));
        correctCellsToMove.add(gameBoard.getCell(3,3));

        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* corner case, cannot move up, there's a dome in a cell and a level one in another cell*/
        player.setCanMoveUp(false);
        gameBoard.getCell(1,1).buildOnThisCell(levelFour);
        gameBoard.getCell(0,1).buildOnThisCell(levelOne);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[0]);
        correctCellsToMove.add(gameBoard.getCell(1,0));

        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* center case, surrounded by level one building and cannot move up */
        player.setCanMoveUp(false);
        gameBoard = new Board();
        gameBoard.getCell(1,1).buildOnThisCell(levelOne);
        gameBoard.getCell(1,2).buildOnThisCell(levelOne);
        gameBoard.getCell(1,3).buildOnThisCell(levelOne);
        gameBoard.getCell(2,1).buildOnThisCell(levelOne);
        gameBoard.getCell(2,3).buildOnThisCell(levelOne);
        gameBoard.getCell(3,1).buildOnThisCell(levelOne);
        gameBoard.getCell(3,1).buildOnThisCell(levelTwo);
        gameBoard.getCell(3,1).buildOnThisCell(levelThree);
        gameBoard.getCell(3,2).buildOnThisCell(levelOne);
        gameBoard.getCell(3,2).buildOnThisCell(levelTwo);
        gameBoard.getCell(3,2).buildOnThisCell(levelThree);

        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[1]);
        correctCellsToMove.add(gameBoard.getCell(3,3));
        assertEquals(correctCellsToMove, availableCellsToMove);

        correctCellsToMove.clear();



    }


    /**
     * this method calls only the getCellAvailableToBuild from Board class that's already tested
     */
    @Test
    void wherePawnCanBuild() {
    }


    /**
     * this method calls only getPossibleBuildingOnCell method from Board class that's already tested
     */
    @Test
    void getPossibleBuildingOnCell() {


    }


    @Test
    void removePawn() {

        gameBoard = new Board();
        player = new BasicPlayer("test", Color.BLUE, "test");
        Cell cell = gameBoard.getCell(0,0);

        player.initPawn(gameBoard, Sex.MALE, cell);

        //pawn is in the cell [0,0]
        assertEquals(gameBoard.getCell(0,0).getPawnInThisCell(), player.getPawns()[0]);

        //remove pawn from [0,0]
        player.removePawn(gameBoard, player.getPawns()[0]);

        //now the pawn is not in the cell [0,0]
        assertNotEquals(gameBoard.getCell(0,0).getPawnInThisCell(), player.getPawns()[0]);


    }


    /**
     * the attribute position in the pawn is not modified in this method, but in the moveTo
     * method of class Pawn
     * this method only modifies the designatedCell
     */
    @Test
    void placePawn() {

        gameBoard = new Board();
        player = new BasicPlayer("test", Color.BLUE, "test");
        Cell cell = gameBoard.getCell(0,0);

        player.initPawn(gameBoard, Sex.MALE, cell);

        player.removePawn(gameBoard, player.getPawns()[0]);

        player.placePawn(gameBoard, player.getPawns()[0], gameBoard.getCell(1,1));

        assertEquals(gameBoard.getCell(1,1).getPawnInThisCell(), player.getPawns()[0]);

    }


    @Test
    void getPossibleActions() {

        Board gameBoard = new Board();
        List<Action> test = new ArrayList<>();

        BasicPlayer player1 = new BasicPlayer("test1", Color.BLUE, "test1");
        BasicPlayer player2 = new BasicPlayer("test2", Color.GREY, "test2");
        BasicPlayer player3 = new BasicPlayer("test3", Color.WHITE, "test3");

        List<Building> buildings = new ArrayList<>();
        Building levelOne = new Building(1,22);
        Building levelTwo = new Building(2,18);
        Building levelThree = new Building(3,14);
        Building levelFour = new Building(4,18);
        buildings.add(levelOne);
        buildings.add(levelTwo);
        buildings.add(levelThree);
        buildings.add(levelFour);



        player1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player1.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));
        player2.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,1));
        player2.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(1,2));
        player3.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player3.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,3));

        int i;

        /* case when the pawn cannot move */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));

        assertEquals(test, player1.getPossibleActions(gameBoard, player1.getPawns()[0]));

        // TODO : mettere il metodo per rimuovere le celle
        gameBoard.getCell(1,0).setHeight(0);


        /* case when the pawn can move */
        test.add(new MoveAction());

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player1.getPossibleActions(gameBoard, player1.getPawns()[0]).get(i).getClass());

        test.clear();

        /* case when the pawn cannot build */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(3));

        assertEquals(test, player1.getPossibleActions(gameBoard, player1.getPawns()[0]));

        // TODO : mettere il metodo per rimuovere le celle
        gameBoard.getCell(0,0).setHeight(0);

        /* case when the pawn can build */
        test.add(new BuildAction());

        player1.movePawn(gameBoard, player1.getPawns()[0], gameBoard.getCell(1,0));

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player1.getPossibleActions(gameBoard, player1.getPawns()[0]).get(i).getClass());

        player1.pawnBuild(player1.getPawns()[0],gameBoard.getCell(0,0), 1, buildings);

        test.clear();
        test.add(new FinishAction());

        for(i = 0; i < test.size(); i++)
            assertEquals(test.get(i).getClass(), player1.getPossibleActions(gameBoard, player1.getPawns()[0]).get(i).getClass());


    }


}