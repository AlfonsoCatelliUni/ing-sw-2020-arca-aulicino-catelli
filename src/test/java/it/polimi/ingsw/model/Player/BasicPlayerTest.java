package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.*;
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

class BasicPlayerTest {


    private Board gameBoard;

    private Player player;

    private Player opponentPlayer;

    private Player secondOpponentPlayer;

    private List<Building> buildings;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        player = new BasicPlayer("player", Color.BLUE, new Card("Apollo", true, true, "effetto_apollo"));
        opponentPlayer = new BasicPlayer("opponent", Color.WHITE, new Card("Atlas", true, true, "effetto_atlas"));
        secondOpponentPlayer = new BasicPlayer("second_opponent", Color.WHITE, new Card("Artemis", true, true, "effetto_artemis"));

        buildings = gameBoard.getBuildings();

    }


    /**
     * the cell startCell is not occupied by any pawn before the call of the method,
     * this is checked in the controller section
     */
    @Test
    void initPawn() {

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


    @Test
    void movePawn() {

        Consequence retMoveEncoded;

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));

        // Level one
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));
        /* Level Two */
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(1));
        /* Level Three */
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(2));
        /* Dome Level */
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(3));


        retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(0,1));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());
        assertEquals(1, player.getNumMove());
        assertEquals(0, player.getNumBuild());

        player.setNumBuild(1);
        player.setNumMove(0);


        retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(0,1), gameBoard.getCell(0,2));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());
        assertEquals(1, player.getNumMove());
        assertEquals(0, player.getNumBuild());

        player.setNumBuild(1);
        player.setNumMove(0);


        retMoveEncoded = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(0,2), gameBoard.getCell(0,3));

        assertEquals(VictoryConsequence.class, retMoveEncoded.getClass());
        assertEquals(1, player.getNumMove());
        assertEquals(0, player.getNumBuild());


    }


    @Test
    void pawnBuild() {

        Consequence buildConsequence;

        Building building;

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,2));

        player.movePawn(gameBoard, player.getPawns()[0], gameBoard.getCell(1,0));

        buildConsequence = player.pawnBuild(player.getPawns()[0], gameBoard.getCell(0,0), 1, buildings);

        assertEquals(1, player.getNumBuild());
        assertEquals(1, gameBoard.getCell(0,0).getHeight());
        assertEquals(NoConsequence.class, buildConsequence.getClass());


        building = buildings.get(0);

        while(building.isAvailable()) {
            building.increasePlacedQuantity();
        }

        building.decreasePlacedQuantity();

        assertEquals(21, building.getPlacedNumber());

        buildConsequence = player.pawnBuild(player.getPawns()[0], gameBoard.getCell(0,0), 1, buildings);

        assertEquals(DestroyTowersConsequence.class, buildConsequence.getClass());

    }


    /**
     * this method calls only the forcePawn from Pawn class that's already tested
     */
    @Test
    void forcePawn() {

    }


    @Test
    void wherePawnCanMove() {

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
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));

        availableCellsToMove = player.wherePawnCanMove(gameBoard, pawns[0]);
        correctCellsToMove.add(gameBoard.getCell(1,0));

        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* center case, surrounded by level one building and cannot move up */
        player.setCanMoveUp(false);
        gameBoard = new Board();
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(3,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(3,2).buildOnThisCell(buildings.get(2));

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

        Cell cell = gameBoard.getCell(0,0);

        player.initPawn(gameBoard, Sex.MALE, cell);

        //pawn is in the cell [0,0]
        assertEquals(cell.getPawnInThisCell(), player.getPawns()[0]);

        //remove pawn from [0,0]
        player.removePawn(gameBoard, player.getPawns()[0]);

        //now the pawn is not in the cell [0,0]
        assertNotEquals(cell.getPawnInThisCell(), player.getPawns()[0]);


    }


    /**
     * the attribute position in the pawn is not modified in this method, but in the moveTo
     * method of class Pawn
     * this method only modifies the designatedCell
     */
    @Test
    void placePawn() {

        Cell cell = gameBoard.getCell(0,0);

        player.initPawn(gameBoard, Sex.MALE, cell);

        player.removePawn(gameBoard, player.getPawns()[0]);

        player.placePawn(gameBoard, player.getPawns()[0], gameBoard.getCell(1,1));

        assertEquals(gameBoard.getCell(1,1).getPawnInThisCell(), player.getPawns()[0]);

    }


    @Test
    void getPossibleActions() {

        List<Action> possibleActions = new ArrayList<>();

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));
        opponentPlayer.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,1));
        opponentPlayer.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(1,2));
        secondOpponentPlayer.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        secondOpponentPlayer.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,3));

        int i;

        /* case when the pawn cannot move */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));

        assertEquals(possibleActions, player.getPossibleActions(gameBoard, player.getPawns()[0]));


        gameBoard.destroyRoofInThisCell(1,0);
        gameBoard.destroyRoofInThisCell(1,0);


        /* case when the pawn can move */
        possibleActions.add(new MoveAction());

        for(i = 0; i < possibleActions.size(); i++)
            assertEquals(possibleActions.get(i).getClass(), player.getPossibleActions(gameBoard, player.getPawns()[0]).get(i).getClass());

        possibleActions.clear();

        /* case when the pawn cannot build */
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(3));

        assertEquals(possibleActions, player.getPossibleActions(gameBoard, player.getPawns()[0]));


        gameBoard.destroyRoofInThisCell(0,0);

        /* case when the pawn can build */
        possibleActions.add(new BuildAction());

        player.movePawn(gameBoard, player.getPawns()[0], gameBoard.getCell(1,0));

        for(i = 0; i < possibleActions.size(); i++)
            assertEquals(possibleActions.get(i).getClass(), player.getPossibleActions(gameBoard, player.getPawns()[0]).get(i).getClass());

        player.pawnBuild(player.getPawns()[0],gameBoard.getCell(0,0), 1, buildings);

        possibleActions.clear();
        possibleActions.add(new FinishAction());

        for(i = 0; i < possibleActions.size(); i++)
            assertEquals(possibleActions.get(i).getClass(), player.getPossibleActions(gameBoard, player.getPawns()[0]).get(i).getClass());


    }


}