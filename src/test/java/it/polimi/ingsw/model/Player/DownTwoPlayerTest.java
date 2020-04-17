package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DownTwoPlayerTest {


    private Board gameBoard;

    private Player player;

    private List<Building> buildings;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        player = new DownTwoPlayer( new BasicPlayer("player", Color.BLUE, new Card("Apollo", true, "effetto_apollo")));

        buildings = gameBoard.getBuildings();

    }


    @Test
    void movePawn() {

        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));

        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(1));

        player.getPawns()[0].moveTo(gameBoard.getCell(1,1));

        Consequence retMove = player.move(gameBoard,player.getPawns()[0],gameBoard.getCell(0,0));

        assertEquals(VictoryConsequence.class, retMove.getClass());



    }


}