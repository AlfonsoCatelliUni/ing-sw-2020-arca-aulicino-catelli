package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DestroyAndFinishStateTest {

    Board gameBoard;

    List<Action> test;

    List<Cell> test_1;

    Card card;

    List<Building> buildings;

    Player player_1;

    Player player_2;

    Player player_3;

    @BeforeEach
    void setUp() {

        /*
        gameBoard = new Board();
        test = new ArrayList<>();
        test_1 = new ArrayList<>();
        card = new Card("test", true, "test");
        buildings = gameBoard.getBuildings();

        player_1 = new DestroyBlockPlayer(player1);
        player_2 = new DestroyBlockPlayer(player2);
        player_3 = new DestroyBlockPlayer(player3);

        player_1.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        player_1.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));
        player_2.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(1,1));
        player_2.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(1,2));
        player_3.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(2,2));
        player_3.initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(2,3));
        */
    }

    @Test
    void checkPossibleActions() {



    }
}