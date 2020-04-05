package it.polimi.ingsw.model;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Player.BasicPlayer;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GameTest {

    Game game;

    Board board;

    List<Building> buildings;

    Player player;


    // ======================================================================================


    @BeforeEach
    void setUp() {

        game = new Game("Alfonso", "Massi");

        board = new Board();

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


    @Test
    void wherePawnCanMove() {
    }


    @Test
    void wherePawnCanBuild() {
    }


    @Test
    void getPossibleBuildingOnCell() {
    }


    @Test
    void getPossibleActions() {
    }


    @Test
    void getPawnsCoordinates() {
    }


    @Test
    void movePawn() {
    }


    @Test
    void pawnBuild() {
    }


    @Test
    void moveConsequence() {

    }


    @Test
    void testMoveConsequence() {
    }


    @Test
    void testMoveConsequence1() {
    }


    @Test
    void getBuildings() {
    }


    @Test
    void getAllNames() {
    }


    @Test
    void getMasters() {
    }


    @Test
    void getCards() {
    }


    @Test
    void newCurrentPlayer() {
    }


    @Test
    void boardToString() {
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


}