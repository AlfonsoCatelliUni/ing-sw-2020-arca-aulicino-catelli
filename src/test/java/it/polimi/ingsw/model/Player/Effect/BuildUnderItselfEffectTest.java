package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.NoConsequence;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildUnderItselfEffectTest {

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

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        alfoEffect = new BuildUnderItselfEffect(alfoEffect);
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,2));


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(0,4));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,1));

    }


    @Test
    void build() {

        Consequence consequence = new NoConsequence();

        //the pawn is on a one level tower and build under itself, forcing the pawn to go one level up
        consequence = alfoPlayer.build(gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(0,0), 2, buildings);

        assertEquals(2, alfoPlayer.getPawnInCoordinates(0,0).getHeight());
        assertEquals(true, alfoPlayer.getPawnInCoordinates(0,0).getForcedMove());
        assertEquals(NoConsequence.class, consequence.getClass());


        //the pawn is on a two level tower and build under itself, forcing the pawn to go third level up and not winning
        alfoPlayer.build(alfoPlayer.getPawnInCoordinates(2,2), gameBoard.getCell(2,2), 3, buildings);

        assertEquals(3, alfoPlayer.getPawnInCoordinates(2,2).getHeight());
        assertEquals(true, alfoPlayer.getPawnInCoordinates(2,2).getForcedMove());
        assertEquals(NoConsequence.class, consequence.getClass());


    }


}