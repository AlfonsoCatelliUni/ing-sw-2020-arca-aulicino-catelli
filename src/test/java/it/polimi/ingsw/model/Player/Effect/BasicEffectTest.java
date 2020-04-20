package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.DestroyTowersConsequence;
import it.polimi.ingsw.model.Consequence.NoConsequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicEffectTest {

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

        // commento perchè se costruisco nel set up, rompe tutti i test sotto
        //gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        //gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        //gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        //alfoEffect = new SwitchEffect(alfoEffect);
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        // commento perchè se costruisco nel set up, rompe tutti i test sotto
        //massiPlayer.initPawn(gameBoard, gameBoard.getCell(0,4));
        //massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,1));

    }


    /**
     * this method is just a setter
     */
    @Test
    void changeState() {
    }


    /**
     * this method only calls the checkPossibleActions method of the state
     */
    @Test
    void getPossibleActions() {
    }


    @Test
    void wherePawnCanMove() {

        List<Pawn> pawns = alfoPlayer.getPawns();
        List<Cell> availableCellsToMove;
        List<Cell> correctCellsToMove = new ArrayList<>();


        /* simple corner case */
        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, pawns.get(0));
        correctCellsToMove.add(gameBoard.getCell(0,1));
        correctCellsToMove.add(gameBoard.getCell(1,0));
        correctCellsToMove.add(gameBoard.getCell(1,1));
        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* simple center case */
        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, pawns.get(1));
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


        /* corner case, cannot move up, there's a dome in a cell and a level one in another cell */
        alfoPlayer.setEffect(new NotMoveUpEffect(alfoEffect.getEffect()));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));

        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, pawns.get(0));
        correctCellsToMove.add(gameBoard.getCell(1,0));

        assertEquals(correctCellsToMove, availableCellsToMove);
        correctCellsToMove.clear();


        /* center case, surrounded by level one building and cannot move up */
        alfoPlayer.setEffect(new NotMoveUpEffect(new BasicEffect()));
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

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));

        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, pawns.get(1));
        correctCellsToMove.add(gameBoard.getCell(3,3));
        assertEquals(correctCellsToMove, availableCellsToMove);

        correctCellsToMove.clear();

        /* case when the player cannot move because is surrounded by cells with ah higher level and he cannot move up */

        gameBoard.getCell(3,3).buildOnThisCell(buildings.get(0));

        correctCellsToMove.clear();

        availableCellsToMove = alfoPlayer.wherePawnCanMove(gameBoard, pawns.get(1));


        assertEquals(correctCellsToMove, availableCellsToMove);

    }


    /**
     * this method only calls the gameboard.getCellsAvailableToBuild, already tested
     */
    @Test
    void wherePawnCanBuild() {
    }


    /**
     * this method only calls the gameboard.getPossibleBuildingOnCell, already tested
     */
    @Test
    void getPossibleBuildingOnCell() {
    }


    @Test
    void wherePawnCanForce() {
    }

    /**
     * shouldn't be called by a player with a basic effect
     */
    @Test
    void wherePawnCanDestroy() {
    }


    @Test
    void move() {
        Consequence retMoveEncoded;

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


        retMoveEncoded = alfoPlayer.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(0,1));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());
        assertEquals(BuildState.class, alfoPlayer.getEffect().getState().getClass());



        retMoveEncoded = alfoPlayer.move(gameBoard, gameBoard.getPawnByCoordinates(0,1), gameBoard.getCell(0,2));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());
        assertEquals(BuildState.class, alfoPlayer.getEffect().getState().getClass());



        retMoveEncoded = alfoPlayer.move(gameBoard, gameBoard.getPawnByCoordinates(0,2), gameBoard.getCell(0,3));

        assertEquals(VictoryConsequence.class, retMoveEncoded.getClass());
        assertEquals(BuildState.class, alfoPlayer.getEffect().getState().getClass());


    }



    @Test
    void build() {
        Consequence buildConsequence;

        Building building;


        alfoPlayer.move(gameBoard, alfoPlayer.getPawns().get(0), gameBoard.getCell(1,0));

        buildConsequence = alfoPlayer.build(alfoPlayer.getPawns().get(0), gameBoard.getCell(0,0), 1, buildings);

        assertEquals(FinishState.class, alfoPlayer.getEffect().getState().getClass());
        assertEquals(1, gameBoard.getCell(0,0).getHeight());
        assertEquals(NoConsequence.class, buildConsequence.getClass());


        building = buildings.get(0);

        while(building.isAvailable()) {
            building.increasePlacedQuantity();
        }

        building.decreasePlacedQuantity();

        assertEquals(21, building.getPlacedNumber());

        buildConsequence = alfoPlayer.build(alfoPlayer.getPawns().get(0), gameBoard.getCell(0,0), 1, buildings);

        assertEquals(DestroyTowersConsequence.class, buildConsequence.getClass());

    }


    /**
     * this method calls only the forcePawn from Pawn class that's already tested
     */
    @Test
    void force() {
    }


    /**
     * shouldn't be called by a player with a basic effect
     */
    @Test
    void destroy() {
    }


    /**
     * this method only calls the gameboard.PlacePawnHere
     */
    @Test
    void placePawn() {
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(4,4));

        massiPlayer.removePawn(gameBoard, gameBoard.getPawnByCoordinates(4,4));
        massiPlayer.placePawn(gameBoard, gameBoard.getPawnByCoordinates(4,4), gameBoard.getCell(3,4));

        assertEquals(false, gameBoard.getCell(4,4).getBuilderHere());
        assertEquals(true, gameBoard.getCell(4,4).getIsFree());
        assertNull(gameBoard.getCell(4, 4).getPawnInThisCell());

        assertEquals(true, gameBoard.getCell(3,4).getBuilderHere());
        assertEquals(false, gameBoard.getCell(3,4).getIsFree());

    }


    @Test
    void removePawn() {

        //remove a normal pawn
        alfoPlayer.removePawn(gameBoard, alfoPlayer.getPawnInCoordinates(0,0));

        assertEquals(false, gameBoard.getCell(0,0).getBuilderHere());
        assertEquals(true, gameBoard.getCell(0,0).getIsFree());
        assertNull(gameBoard.getCell(0, 0).getPawnInThisCell());


    }

}