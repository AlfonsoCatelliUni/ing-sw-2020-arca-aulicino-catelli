package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.BasicPlayer;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Player.State.MoveState;
import it.polimi.ingsw.model.Player.State.StateInterface;
import it.polimi.ingsw.model.Sex;


import java.util.ArrayList;
import java.util.List;

public class BasicEffect implements Effect {


    private StateInterface state;


    // ======================================================================================
    // MARK : Constructor Section


    public BasicEffect() {
        this.state = new MoveState(this);
    }


    // ======================================================================================
    // MARK : Getter Section


    @Override
    public StateInterface getState() {
        return this.state;
    }


    @Override
    public List<Cell> getPawnsCoordinates(Board gameBoard) {
        return null;
    }


    // ======================================================================================
    // MARK : Setter Section


    @Override
    public void changeState(StateInterface state) {
        this.state = state;
    }


    // ======================================================================================
    // MARK : Possibilities Control Section


    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        //return state.checkPossibleActions(gameBoard, designatedPawn);

        return new ArrayList<>();
    }


    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return gameBoard.getCellAvailableToMove( designatedPawn );
    }


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return gameBoard.getCellAvailableToBuild( designatedPawn );
    }


    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {
        return null;
    }


    @Override
    public List<Cell> wherePawnCanForce(Board gameBoard, Pawn designatedPawn) {
        throw new RuntimeException("You're not enough strong to use the Force, Young Padawan!");
    }


    @Override
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {
        throw new RuntimeException("Invalid Request!");
    }


    // ======================================================================================
    // MARK : Real Actions Section


    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        removePawn( gameBoard,  designatedPawn ); // remove the pawn from the game board

        /* save the old height of the pawn to compare it with the new height to declare if there is a winner */
        int oldPawnHeight = designatedPawn.getPosition().getHeight();

        /* change the position in the pawn and set the propriety of the pawn ( hasMove, hasGoneUp, ... ) */
        designatedPawn.moveTo(nextPosition);

        if(state.getClass().equals(MoveState.class)) {
            changeState(new BuildState(this));
        }


        placePawn( gameBoard, designatedPawn, nextPosition ); // place the pawn on the board in the new position

        /* this is the control for the victory moving from 2 to 3 level height,
         * only if it's not been forced to move in the position */
        //TODO : modificare nome nella consequence
        if ( oldPawnHeight == 2 && nextPosition.getHeight() == 3
                && !designatedPawn.getForcedMove() && designatedPawn.getHasMoved() ) {
            return new VictoryConsequence("nome");
        }

        return new NoConsequence();
    }


    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        designatedPawn.pawnBuild();

        Building designatedBuilding = new Building(2,22);

        for (Building b : buildings) {
            if ( b.getLevel() == chosenLevel ) {
                designatedBuilding = b;
            }
        }

        if(state.getClass().equals(BuildState.class)) {
            changeState(new FinishState(this));
        }


        designatedCell.buildOnThisCell(designatedBuilding);


        if( !designatedBuilding.isAvailable()) {
            return new DestroyTowersConsequence();
        }

        return new NoConsequence();
    }


    @Override
    public void force(Pawn designatedPawn, Cell nextPosition) {
        //TODO : fare force
    }


    // ======================================================================================
    // MARK : Pawn Placing Section


    @Override
    public void initPawn(Board gameBoard, Sex sex, Cell cell) {
        //TODO : fare initPawn
    }


    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {
        //TODO : fare removePawn
    }


    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {
        //TODO : fare placePAwn
    }



}
