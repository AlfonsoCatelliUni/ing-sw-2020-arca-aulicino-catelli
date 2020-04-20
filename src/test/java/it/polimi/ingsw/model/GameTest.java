package it.polimi.ingsw.model;

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

    private Board gameBoard;

    private List<Player> players;

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
        game = new Game("Alfonso", "Massi");
        gameBoard = new Board();
        buildings = gameBoard.getBuildings();


        alfoEffect = new BasicEffect();
        alfoEffect = new CanPushOpponentEffect(new SwitchEffect(alfoEffect));
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));


        massiEffect = new BasicEffect();
        massiEffect = new CanPushOpponentEffect(new PushEffect(massiEffect));
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(0,4));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,1));

        giammaEffect = new BasicEffect();
        giammaEffect = new BlockOpponentEffect(giammaEffect);

        giammaCard = new Card("giammi_card", true, "giammi_effect");

        giammaPlayer = new Player("giammi", Color.WHITE, giammaCard,giammaEffect);

    }


    // ======================================================================================


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


    @Test
    void newCurrentPlayer() {

        Player currentPlayer = game.getPlayers().get(0);

        assertEquals(currentPlayer, game.getCurrentPlayer());

        Player newCurrentPlayer = game.getPlayers().get(1);

        game.newCurrentPlayer();

        assertEquals(newCurrentPlayer, game.getCurrentPlayer());

        /* after the turn of both the players, the rotation restarts */

        game.newCurrentPlayer();

        assertEquals(currentPlayer, game.getCurrentPlayer());

    }



    @Test
    void removePlayer() {

        /* This method removes the pawns from the board too
        * Player 1 pawns are in [0,0] and [0,1] */

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

        for(Effect e = alfoPlayer.getEffect(); !e.getClass().equals(BasicEffect.class); e = e.getEffect()) {
            assertNotEquals(NotMoveUpEffect.class, e.getClass());
        }

        game.newCurrentPlayer();

        Player player = game.getPlayers().get(1);
        player.setEffect(new BlockOpponentEffect(game.getPlayers().get(1).getEffect()));

        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(0));

        game.receiveConsequence(player.move(gameBoard, gameBoard.getPawnByCoordinates(0,4), gameBoard.getCell(0,3)));

        assertEquals(NotMoveUpEffect.class, game.getPlayers().get(0).getEffect().getClass());

        for(Effect e = player.getEffect(); !e.getClass().equals(BasicEffect.class); e = e.getEffect()) {
            assertNotEquals(NotMoveUpEffect.class, e.getClass());
        }

    }


    @Test
    void doConsequence() {
    }


    @Test
    void nextCurrentPlayer() {

        assertEquals(0, game.getIndexCurrentPlayer());

        game.newCurrentPlayer();

        assertEquals(1, game.getIndexCurrentPlayer());

        /* after the turn of both the players, the rotation restarts */

        game.newCurrentPlayer();

        assertEquals(0, game.getIndexCurrentPlayer());

    }


    @Test
    void getPlayerByName() {
    }


    @Test
    void generateStatusJson() {

        String provaJson;

        game.pawnBuild("Alfonso", 0,0,1,0,1);

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

    @Test
    void getAvailablePawns() {

        List<Cell> retPawnsCell;
        List<Cell> correctPawnsCell = new ArrayList<>();

        Game gameTest = new Game("player", "opponentPlayer");

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
}