package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Consequence.BlockConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Player.BasicPlayer;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    Board board;

    List<Building> buildings;

    private List<Player> players;


    // ======================================================================================


    @BeforeEach
    void setUp() {

        game = new Game("Alfonso", "Massi");

        board = new Board();

        players = new ArrayList<>();

        buildings = new ArrayList<>();

        buildings.add(new Building(1,22));
        buildings.add(new Building(2,18));
        buildings.add(new Building(3,14));
        buildings.add(new Building(4,18));

        //player = new BasicPlayer("Alfonso", Color.BLUE, "God_Alfonso");

        //player.initPawn(board, Sex.MALE, board.getCell(0,0));
        //player.initPawn(board, Sex.FEMALE, board.getCell(0,1));

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
    }


    @Test
    void removePlayer() {
    }


    @Test
    void receiveConsequence() {
        Player player1 = new BasicPlayer("Alfonso", Color.BLUE, new Card("Name", true,"effect"));
        Player player2 = new BasicPlayer("Massi", Color.GREY, new Card("Name", true,"effect"));
        players.add(player1);
        players.add(player2);

        assertEquals(true,game.getPlayerByName("Massi").getCanMoveUp());

        game.receiveConsequence(new BlockConsequence("Alfonso"));

        assertEquals(false,game.getPlayerByName("Massi").getCanMoveUp());
        assertEquals(true,game.getPlayerByName("Alfonso").getCanMoveUp());

    }


    @Test
    void doConsequence() {
    }

    @Test
    void testDoConsequence() {
    }


    @Test
    void testDoConsequence1() {
    }


    @Test
    void testNewCurrentPlayer() {
    }


    @Test
    void nextCurrentPlayer() {
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
}