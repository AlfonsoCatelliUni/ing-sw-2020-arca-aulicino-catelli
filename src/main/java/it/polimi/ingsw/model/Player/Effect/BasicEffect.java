package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.DestroyTowersConsequence;
import it.polimi.ingsw.model.Consequence.NoConsequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Player.State.MoveState;
import it.polimi.ingsw.model.Player.State.StateInterface;


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


    //TODO : controllare ?!
    @Override
    public Effect getEffect() {
        return this;
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
        return gameBoard.getPossibleBuildingOnCell( designatedCell );
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

        // save the old height of the pawn to compare it with the new height to declare if there is a winner
        int oldPawnHeight = designatedPawn.getPosition().getHeight();

        // change the position in the pawn and set the propriety of the pawn ( hasMove, hasGoneUp, ... )
        designatedPawn.moveTo(nextPosition);

        if(state.getClass().equals(MoveState.class)) {
            changeState(new BuildState(this));
        }


        placePawn( gameBoard, designatedPawn, nextPosition ); // place the pawn on the board in the new position

        /* this is the control for the victory moving from 2 to 3 level height,
         * only if it's not been forced to move in the position */
        if ( oldPawnHeight == 2 && nextPosition.getHeight() == 3
                && !designatedPawn.getForcedMove() && designatedPawn.getHasMoved() ) {
            return new VictoryConsequence();
            //TODO : mettere nome alla conseguenza
        }

        return new NoConsequence();
    }


    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        designatedPawn.pawnBuild();

        Building designatedBuilding = new Building(0,30);

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
            //TODO : mettere nome alla conseguenza
        }

        return new NoConsequence();
    }


    @Override
    public void force(Pawn designatedPawn, Cell nextPosition) {
        designatedPawn.forcePawn(nextPosition);
    }


    // ======================================================================================
    // MARK : Pawn Placing Section


    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {

        int rowPosition = designatedPawn.getPosition().getRowPosition();
        int columnPosition = designatedPawn.getPosition().getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).freeCell();

    }


    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

        int rowPosition = designatedCell.getRowPosition();
        int columnPosition = designatedCell.getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).placePawnHere(designatedPawn);

    }



}
