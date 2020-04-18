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

class SwitchEffectTest {

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
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        alfoEffect = new SwitchEffect(alfoEffect);
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
    void move() {

        /* switch with an opponent player */
        Consequence returnConsequence = alfoPlayer.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,1));

        assertEquals(NoConsequence.class, returnConsequence.getClass());
        assertEquals(alfoPlayer.getPawnInCoordinates(0,0), massiPlayer.getPawnInCoordinates(2,2) );
        assertEquals(true, massiPlayer.getPawnInCoordinates(0,0).getForcedMove());
        assertEquals(2, massiPlayer.getPawnInCoordinates(0,0).getHeight());


        /* normal move */
        returnConsequence = alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(1,1), gameBoard.getCell(1,2));

        assertEquals(NoConsequence.class, returnConsequence.getClass());

    }


}