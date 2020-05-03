package it.polimi.ingsw.model;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.BlockConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class GameTest {

    private Game game;

    private List<Player> players;

    private Player alfoPlayer;
    private Player massiPlayer;
    private Player giammaPlayer;

    private List<Building> buildings;


    @BeforeEach
    void setUp() {

        Card alfoCard = JsonHandler.deserializeCardList().get(0);
        alfoPlayer = new Player("Alfonso", Color.BLUE, alfoCard, alfoCard.getBaseEffect());


        Card massiCard = JsonHandler.deserializeCardList().get(1);
        massiPlayer = new Player("massi", Color.GREY, massiCard, massiCard.getBaseEffect());


        Card giammaCard = JsonHandler.deserializeCardList().get(2);
        giammaPlayer = new Player("giammi", Color.WHITE, giammaCard, giammaCard.getBaseEffect());

        game = new Game(alfoPlayer, massiPlayer);

        buildings = game.getGameBoard().getBuildings();



    }


    // ======================================================================================


    @Test
    void removePlayer() {

        /* This method removes the pawns from the board too
        * Player 1 pawns are in [0,0] and [0,1] */

        game.initializePawn("Alfonso", 0,0);
        game.initializePawn("Alfonso", 0,1);

        assertNotEquals(null, game.getGameBoard().getCell(0,0).getPawnInThisCell());
        assertNotEquals(null, game.getGameBoard().getCell(0,1).getPawnInThisCell());

        game.removePlayer("Alfonso");

        assertNull(game.getGameBoard().getCell(0, 0).getPawnInThisCell());
        assertNull(game.getGameBoard().getCell(0, 1).getPawnInThisCell());

        /* there's only one player, so throws a new VictoryConsequence to the ReceiveConsequence method
        * Debugging, we saw it throws that */



    }


    @Test
    void receiveConsequence() {

        Game gameTest2 = new Game(alfoPlayer, massiPlayer);
        gameTest2.initializePawn(alfoPlayer.getName(), 0,0);
        gameTest2.initializePawn(alfoPlayer.getName(), 0,1);

        gameTest2.initializePawn(massiPlayer.getName(), 0,4);
        gameTest2.initializePawn(massiPlayer.getName(), 2,2);

        for(Effect e = alfoPlayer.getEffect(); !e.getClass().equals(BasicEffect.class); e = e.getEffect()) {
            assertNotEquals(NotMoveUpEffect.class, e.getClass());
        }


        Player player = gameTest2.getPlayers().get(1);
        player.setEffect(new BlockOpponentEffect(gameTest2.getPlayers().get(1).getEffect()));

        gameTest2.getGameBoard().getCell(0,3).buildOnThisCell(buildings.get(0));

        gameTest2.receiveConsequence(player.move(gameTest2.getGameBoard(), gameTest2.getGameBoard().getPawnByCoordinates(0,4), gameTest2.getGameBoard().getCell(0,3)));

        assertEquals(NotMoveUpEffect.class, gameTest2.getPlayers().get(0).getEffect().getClass());

        for(Effect e = player.getEffect(); !e.getClass().equals(BasicEffect.class); e = e.getEffect()) {
            assertNotEquals(NotMoveUpEffect.class, e.getClass());
        }

    }


    @Test
    void doConsequence() {

        Game gameTest = new Game(alfoPlayer, massiPlayer);

        gameTest.doConsequence(new BlockConsequence(massiPlayer.getName()));

        assertEquals(NotMoveUpEffect.class, gameTest.getPlayerByName(alfoPlayer.getName()).getEffect().getClass());
        assertNotEquals(NotMoveUpEffect.class, gameTest.getPlayerByName(massiPlayer.getName()).getEffect().getClass());


    }


    @Test
    void getPlayerByName() {

        Game gameTest = new Game(alfoPlayer, massiPlayer);

        assertEquals(alfoPlayer, gameTest.getPlayerByName(alfoPlayer.getName()));

        assertEquals(massiPlayer, gameTest.getPlayerByName(massiPlayer.getName()));

        assertNotEquals(alfoPlayer, gameTest.getPlayerByName(massiPlayer.getName()));

    }


    @Test
    void generateStatusJson() {

        String provaJson;
        game.initializePawn("Alfonso", 0,0);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

        game.pawnBuild("Alfonso", 0,0,1,0,2);
        game.pawnBuild("Alfonso", 0,0,1,0,3);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

        game.pawnBuild("Alfonso", 0,0,1,0,4);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

        game.pawnBuild("Alfonso", 0,0,1,1,4);

        provaJson = game.generateStatusJson();
        System.out.println(provaJson);

        assertNotEquals("",provaJson);

    }


    @Test
    void getAvailablePawns() {

        List<Cell> retPawnsCell;
        List<Cell> correctPawnsCell = new ArrayList<>();

        Game gameTest = new Game("player", "opponentPlayer");

        gameTest.initializePawn("player", 0,0);
        gameTest.initializePawn("player", 0,1);


        retPawnsCell = gameTest.getAvailablePawns("player");
        correctPawnsCell.add(gameTest.getGameBoard().getCell(0,0));
        correctPawnsCell.add(gameTest.getGameBoard().getCell(0,1));

        assertEquals(correctPawnsCell, retPawnsCell);

        //now one pawn cannot move
        gameTest.getGameBoard().getCell(1,1).buildOnThisCell(buildings.get(0));
        gameTest.getGameBoard().getCell(1,1).buildOnThisCell(buildings.get(1));

        gameTest.getGameBoard().getCell(1,0).buildOnThisCell(buildings.get(0));
        gameTest.getGameBoard().getCell(1,0).buildOnThisCell(buildings.get(1));

        correctPawnsCell.clear();
        correctPawnsCell.add(gameTest.getGameBoard().getCell(0,1));
        retPawnsCell = gameTest.getAvailablePawns("player");

        assertEquals(correctPawnsCell, retPawnsCell);

        gameTest.getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(0));
        gameTest.getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(1));

        gameTest.getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(0));
        gameTest.getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(1));

        retPawnsCell = gameTest.getAvailablePawns("player");
        assertEquals(0, retPawnsCell.size());
    }


    @Test
    void isValidCoordinate() {

        assertTrue(game.isValidCoordinate(0,0));
        assertTrue(game.isValidCoordinate(0,1));
        assertTrue(game.isValidCoordinate(0,2));
        assertTrue(game.isValidCoordinate(0,3));
        assertTrue(game.isValidCoordinate(0,4));
        assertTrue(game.isValidCoordinate(1,0));
        assertTrue(game.isValidCoordinate(1,1));
        assertTrue(game.isValidCoordinate(1,2));
        assertTrue(game.isValidCoordinate(1,3));
        assertTrue(game.isValidCoordinate(1,4));
        assertTrue(game.isValidCoordinate(4,0));
        assertTrue(game.isValidCoordinate(4,1));
        assertTrue(game.isValidCoordinate(4,2));
        assertTrue(game.isValidCoordinate(4,3));
        assertTrue(game.isValidCoordinate(4,4));

        assertFalse(game.isValidCoordinate(-1,0));
        assertFalse(game.isValidCoordinate(-1,-1));
        assertFalse(game.isValidCoordinate(-1,5));
        assertFalse(game.isValidCoordinate(2,5));
        assertFalse(game.isValidCoordinate(6,6));

    }


    @Test
    void isValidSpot() {

        game.initializePawn("Alfonso", 0,0);

        assertTrue(game.isValidSpot(3,1));

        assertFalse(game.isValidSpot(0,0));

        Game gameTest2 = new Game(giammaPlayer.getName(), "opponent");
        gameTest2.initializePawn(giammaPlayer.getName(), 4,4);

        assertFalse(gameTest2.isValidSpot(4,4));



    }


    @Test
    void isValid() {

        game.initializePawn("Alfonso", 0,0);

        game.wherePawnCanMove("Alfonso", 0,0);

        assertTrue(game.isValid(1,1));

        assertFalse(game.isValid(4,4));

        game.wherePawnCanBuild("Alfonso", 0,0);

        assertTrue(game.isValid(1,0));

        game.getPossibleBuildingOnCell("Alfonso", 1,0);

        assertTrue(game.isValid(1));

        assertFalse(game.isValid(2));

        game.getPossibleActions("Alfonso", 0,0 );

        assertTrue(game.isValid("Move"));

        assertFalse(game.isValid("End turn"));




    }



    /**
     * this method calls only the wherePawnCanMove from Player class that's already tested
     */
    @Test
    void wherePawnCanMove() {
    }


    /**
     * this method calls only the wherePawnCanBuild from Player class that's already tested
     */
    @Test
    void wherePawnCanBuild() {
    }


    /**
     * this method calls only the getPossibleBuildingOnCell from Player class that's already tested
     */
    @Test
    void getPossibleBuildingOnCell() {
    }


    /**
     * this method calls only the getPossibleActions from Player class that's already tested
     */
    @Test
    void getPossibleActions() {
    }


    /**
     * this method calls only the getPawnsCoordinates from Player class that's already tested
     */
    @Test
    void getPawnsCoordinates() {
    }


    /**
     * this method calls only the pawnBuild from Player class that's already tested
     */
    @Test
    void movePawn() {
    }


    /**
     * this method calls only the pawnBuild from Player class that's already tested
     */
    @Test
    void pawnBuild() {
    }

    /**
     * this method only calls the destroyBlock in player
     */
    @Test
    void destroyBlock() {
    }


    /**
     * this method only calls the wherePawnCanDestroy in player
     */
    @Test
    void wherePawnCanDestroy() {
    }


    /**
     * this method only calls the player.resetPlayerStatus method
     */
    @Test
    void resetPlayerStatus() {
    }
}