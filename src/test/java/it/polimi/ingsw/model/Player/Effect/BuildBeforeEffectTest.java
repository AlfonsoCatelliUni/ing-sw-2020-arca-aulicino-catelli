package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;
import it.polimi.ingsw.model.Player.State.MoveState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildBeforeEffectTest {

    Board gameBoard;

    List<Action> test;

    List<Cell> test_1;

    Card card;

    List<Building> buildings;

    Player player1;

    Player player2;

    Player player3;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();
        test = new ArrayList<>();
        test_1 = new ArrayList<>();
        card = new Card("test", true, "test");
        buildings = gameBoard.getBuildings();

        player1 = new Player("name", Color.BLUE, card, new BuildBeforeEffect(new BasicEffect()));

        player2 = new Player("name", Color.GREY, card, new BuildBeforeEffect(new BasicEffect()));

        player3 = new Player("name", Color.WHITE, card, new BuildBeforeEffect(new BasicEffect()));


        player1.initPawn(gameBoard, gameBoard.getCell(0,0));
        player1.initPawn(gameBoard, gameBoard.getCell(0,1));
        player2.initPawn(gameBoard, gameBoard.getCell(1,1));
        player2.initPawn(gameBoard, gameBoard.getCell(1,2));
        player3.initPawn(gameBoard, gameBoard.getCell(2,2));
        player3.initPawn(gameBoard, gameBoard.getCell(2,3));

    }

    @Test
    void build() {
        /* case when the player can build before moving, so NumBuild increases by 1, and I have to reset it to 1,
        after that I set the flag hasBuiltBefore */

        assertEquals(MoveAndBuildState.class, player3.getEffect().getState().getClass());

        player3.build(player3.getPawns().get(0), gameBoard.getCell(3,3), 1, buildings);

        assertEquals(MoveState.class, player3.getEffect().getState().getClass());

        player3.getEffect().changeState(new BuildState(player3.getEffect()));

        player3.build(player3.getPawns().get(0), gameBoard.getCell(3,3), 2, buildings);

        assertEquals(FinishState.class, player3.getEffect().getState().getClass());

    }
}