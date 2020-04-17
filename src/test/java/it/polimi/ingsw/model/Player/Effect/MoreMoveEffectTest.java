package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.NoConsequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;
import it.polimi.ingsw.model.Player.State.MoveState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoreMoveEffectTest {

    private Board gameBoard;

    private Player player;

    private Player opponentPlayer;

    private Player secondOpponentPlayer;

    private List<Building> buildings;

    @BeforeEach
    void setUp() {

        gameBoard = new Board();

        player = new Player(new MoreMoveEffect(new BasicEffect()));
        opponentPlayer = new Player(new MoreMoveEffect(new BasicEffect()));
        secondOpponentPlayer = new Player(new MoreMoveEffect(new BasicEffect()));

        buildings = gameBoard.getBuildings();

    }

    @Test
    void move() {

        Consequence retMoveEncoded;

        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        player.initPawn(gameBoard, gameBoard.getCell(2,2));

        // Level one
        gameBoard.getCell(0,1).buildOnThisCell(buildings.get(0));
        /* Level Two */
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,2).buildOnThisCell(buildings.get(1));
        /* Level Three */
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(0,3).buildOnThisCell(buildings.get(2));
        /* Dome Level */
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(0));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(1));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(2));
        gameBoard.getCell(1,3).buildOnThisCell(buildings.get(3));


        retMoveEncoded = player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(0,1));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());
        assertEquals(MoveAndBuildState.class,player.getEffect().getState().getClass());
        //assertEquals(NotMoveBackEffect.class, player.getEffect().getEffect().getClass());


        retMoveEncoded = player.move(gameBoard, gameBoard.getPawnByCoordinates(0,1), gameBoard.getCell(0,2));

        assertEquals(NoConsequence.class, retMoveEncoded.getClass());
        assertEquals(BuildState.class,player.getEffect().getState().getClass());



        player.getEffect().changeState(new MoveState(player.getEffect()));

        retMoveEncoded = player.move(gameBoard, gameBoard.getPawnByCoordinates(0,2), gameBoard.getCell(0,3));

        assertEquals(VictoryConsequence.class, retMoveEncoded.getClass());
        assertEquals(MoveAndBuildState.class,player.getEffect().getState().getClass());


    }
}