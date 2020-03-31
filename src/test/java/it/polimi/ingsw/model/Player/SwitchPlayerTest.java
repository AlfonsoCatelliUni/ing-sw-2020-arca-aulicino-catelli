package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwitchPlayerTest {

    Board gameBoard;

    private Player player;
    private Player opponentPlayer;

    List<Building> buildings;


    @Before
    public void setup() {

        gameBoard = new Board();

        buildings.add(new Building(1, 22));
        buildings.add(new Building(2, 18));
        buildings.add(new Building(3, 14));
        buildings.add(new Building(4, 18));

        player = new SwitchPlayer(new BasicPlayer("playerTest", Color.BLUE, "playerGodTest"));
        opponentPlayer = new BasicPlayer("opponentTest", Color.GREY, "opponentGodTest");

        player.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,0));
        player.initPawn(gameBoard, Sex.FEMALE ,gameBoard.getCell(2,2));

        player.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(0,4));
        player.initPawn(gameBoard, Sex.MALE ,gameBoard.getCell(1,1));



    }


    @Test
    void wherePawnCanMove() {

    }


    @Test
    void movePawn() {
    }


    @Test
    void getPossibleAction() {
    }


}