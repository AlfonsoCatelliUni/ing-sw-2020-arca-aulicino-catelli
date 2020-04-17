package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.NoConsequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DownTwoEffectTest {

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

    List<Building> buildings;


    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(1));


        alfoEffect = new BasicEffect();
        alfoEffect = new DownTwoEffect(alfoEffect);
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(4,4));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,1));

    }


    @Test
    void move() {

        //Goes from second level to third level and Win
        Consequence returnConsequence = alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(0,0), gameBoard.getCell(0,1));
        assertEquals(VictoryConsequence.class, returnConsequence.getClass());

        //Goes from third level to first level and Win
        returnConsequence = alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(0,1), gameBoard.getCell(0,2));
        assertEquals(VictoryConsequence.class, returnConsequence.getClass());

        //Goes from first level to second level and doesn't win
        returnConsequence = alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(0,2), gameBoard.getCell(0,3));
        assertEquals(NoConsequence.class, returnConsequence.getClass());

        //Goes from second level to ground level and Win
        returnConsequence = alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(0,3), gameBoard.getCell(0,4));
        assertEquals(VictoryConsequence.class, returnConsequence.getClass());

        //Goes from ground level to third level
        returnConsequence = alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(0,4), gameBoard.getCell(0,1));
        assertEquals(NoConsequence.class, returnConsequence.getClass());

        //Goes from third level to ground level and Win
        returnConsequence = alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(0,1), gameBoard.getCell(1,2));
        assertEquals(VictoryConsequence.class, returnConsequence.getClass());


    }


}