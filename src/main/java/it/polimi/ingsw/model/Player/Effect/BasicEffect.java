package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.StateInterface;
import it.polimi.ingsw.model.Sex;


import java.util.ArrayList;
import java.util.List;

public class BasicEffect implements Effect {

    private StateInterface state;

    public BasicEffect() {
        //this.state = new MoveState();

    }


    @Override
    public StateInterface getState() {
        return this.state;
    }

    @Override
    public void changeState(StateInterface state) {
        this.state = state;
    }

    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        removePawn( gameBoard,  designatedPawn ); // remove the pawn from the game board

        /* save the old height of the pawn to compare it with the new height to declare if there is a winner */
        int oldPawnHeight = designatedPawn.getPosition().getHeight();

        /* change the position in the pawn and set the propriety of the pawn ( hasMove, hasGoneUp, ... ) */
        designatedPawn.moveTo(nextPosition);

       // if(state.getClass().equals(MoveStateTest.class)) {
          //  changeState(new BuildStateTest(this));
      //  }



        placePawn( gameBoard, designatedPawn, nextPosition ); // place the pawn on the board in the new position

        /* this is the control for the victory moving from 2 to 3 level height,
         * only if it's not been forced to move in the position */
        if ( oldPawnHeight == 2 && nextPosition.getHeight() == 3
                && !designatedPawn.getForcedMove() && designatedPawn.getHasMoved() ) {
            return new VictoryConsequence("nome");
        }

        return new NoConsequence();
    }

    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return gameBoard.getCellAvailableToMove( designatedPawn );
    }

    @Override
    public void force(Pawn designatedPawn, Cell nextPosition) {

    }

    @Override
    public Consequence pawn(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        designatedPawn.pawnBuild();

        Building designatedBuilding = new Building(2,22);

        for (Building b : buildings) {
            if ( b.getLevel() == chosenLevel ) {
                designatedBuilding = b;
            }
        }

        if(state.getClass().equals(BuildState.class)) {
          //  changeState(new FinishState());
        }


        designatedCell.buildOnThisCell(designatedBuilding);


        if( !designatedBuilding.isAvailable()) {
            return new DestroyTowersConsequence();
        }

        return new NoConsequence();
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
    public void initPawn(Board gameBoard, Sex sex, Cell cell) {

    }

    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        //return state.checkPossibleActions(gameBoard, designatedPawn);

        return new ArrayList<>();
    }

    @Override
    public List<Cell> getPawnsCoordinates(Board gameBoard) {
        return null;
    }

    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {

    }

    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

    }

}
