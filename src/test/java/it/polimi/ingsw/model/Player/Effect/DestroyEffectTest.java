package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.State.FinishState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DestroyEffectTest {


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
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        alfoEffect = new CanDestroyEffect(alfoEffect);
        alfoEffect = new DestroyEffect(alfoEffect);
        alfoCard = new Card("destroy_card", true, "destroy_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);

        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(0,0));
        alfoPlayer.initPawn(gameBoard, gameBoard.getCell(2,3));


        massiEffect = new BasicEffect();
        massiCard = new Card("massi_card", true, "massi_effect");

        massiPlayer = new Player("massi", Color.GREY, massiCard, massiEffect);

        massiPlayer.initPawn(gameBoard, gameBoard.getCell(0,4));
        massiPlayer.initPawn(gameBoard, gameBoard.getCell(1,1));

    }


    @Test
    void destroy() {

        alfoPlayer.move(gameBoard, alfoPlayer.getPawnInCoordinates(2,3), gameBoard.getCell(3,3));

        alfoPlayer.build(gameBoard.getPawnByCoordinates(3,3), gameBoard.getCell(3,4), 1, buildings);

        alfoPlayer.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(3,3));

        //A pawn wants to destroy the third level of a tower
        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(2,2).buildOnThisCell(buildings.get(1));
        alfoPlayer.destroy(gameBoard.getCell(2,2), buildings);
        assertEquals(FinishState.class, alfoPlayer.getEffect().getState().getClass());



    }


    @Test
    void build() {



    }


}