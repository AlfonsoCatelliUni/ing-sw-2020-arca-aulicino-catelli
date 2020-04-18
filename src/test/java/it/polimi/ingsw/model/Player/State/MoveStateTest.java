package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;
import it.polimi.ingsw.model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class MoveStateTest {


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

    private List<Action> correctActionsList = new ArrayList<>();


    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        buildings = gameBoard.getBuildings();

        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,0).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(3));
        gameBoard.getCell(1,0).buildOnThisCell(buildings.get(3));


        alfoEffect = new BasicEffect();
        alfoEffect = new CanSwitchOpponentEffect(new SwitchEffect(alfoEffect));
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
    void checkPossibleActions() {
        int i = 0;
        List<Action> possibleActions;

        //In this case the pawn is on a second level building and have two domes built near himself
        //the other cell is occupied by a pawn that can be force to switch
        correctActionsList.add(new MoveAction());
        possibleActions = alfoPlayer.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        possibleActions = alfoPlayer.getEffect().getState().checkPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));

        assertEquals(correctActionsList.size(), possibleActions.size());
        for(i = 0; i < correctActionsList.size(); i++)
            assertEquals(correctActionsList.get(i).getClass(), possibleActions.get(i).getClass());


        //In this case the pawn is on a second level tower and is surrounded only by domes and CANNOT move
        massiPlayer.move(gameBoard, massiPlayer.getPawnInCoordinates(1,1), gameBoard.getCell(2,1));
        gameBoard.getCell(1,1).buildOnThisCell(buildings.get(3));

        possibleActions = alfoPlayer.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(0,0));
        correctActionsList.clear();

        assertEquals(correctActionsList.size(), possibleActions.size());
        //for(i = 0; i < possibleActions.size(); i++)
          //  assertEquals(correctActionsList.get(i).getClass(), possibleActions.get(i).getClass());

    }
}