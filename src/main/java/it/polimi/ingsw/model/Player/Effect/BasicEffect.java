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
import it.polimi.ingsw.model.Player.State.StateEffectInterface;

import java.util.List;


/**
 * this is the basic effect class, with the standard method,
 * this class will be decorated according to the chosen {@link it.polimi.ingsw.model.Player.Card}
 */
public class BasicEffect implements Effect {


    private StateEffectInterface state;


    // MARK : Constructor Section ======================================================================================


    /**
     * base constructor
     */
    public BasicEffect() {
        this.state = new MoveState(this);
    }


    // MARK : Getter Section ======================================================================================


    @Override
    public StateEffectInterface getState() {
        return this.state;
    }


    @Override
    public Effect getEffect() {
        return this;
    }


    // MARK : Setter Section ======================================================================================


    /**
     * this method is used to change the state of the player based
     * the next move that he can do
     * @param state the new state that I want to set
     */
    @Override
    public void changeState(StateEffectInterface state) {
        this.state = state;
    }


    // MARK : Possibilities Control Section ======================================================================================


    /**
     * return which are the possible actions that i can do with the selected pawn
     * if i can't do any move with the pawn i lose the game, it's the basic possible action,
     * it will by decorated according with the selected God card
     * @param gameBoard the board of the game
     * @param designatedPawn the pawn selected to generate the possible actions
     * @return the list of possible actions
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        return state.GetPossibleActions(gameBoard, designatedPawn);
    }


    /**
     * the standard method that return the cells where a pawn can move
     * @param gameBoard the game board where the pawn have to move on
     * @param designatedPawn the pawn that's designated to move
     * @return the list of cells available to be move
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return gameBoard.getCellAvailableToMove( designatedPawn );
    }


    /**
     * the standard method that return the cells where a pawn can build
     * @param gameBoard the game board where the pawn have to build on
     * @param designatedPawn the pawn that's designated to build
     * @return the list of cells available to be built
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return gameBoard.getCellAvailableToBuild( designatedPawn );
    }


    /**
     * this method returns the list of possible building on the designatedCell
     * @param designatedCell the cell that i've chosen to build on
     * @return the list of possible building on the designated cell
     */
    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {
        return gameBoard.getPossibleBuildingOnCell( designatedCell );
    }


    /**
     * this method returns the list of the cell where there are opponents pawn against designatedPawn
     * @param gameBoard the game board where the pawn is
     * @param designatedPawn the pawn from which opponents must be found
     * @return the list of cell where the opponents pawn are onto
     */
    @Override
    public List<Cell> getOpponentsNeighboring(Board gameBoard, Pawn designatedPawn) {
        return gameBoard.getOpponentsNeighboring(designatedPawn);
    }


    /**
     * the standard method that return the cells where I can destroy a block (not a dome)
     * @param gameBoard the game board
     * @param designatedPawn the pawn that want to destroy a block
     * @return the list of cells
     */
    @Override
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {
        throw new RuntimeException("Invalid Request!");
    }


    // MARK : Real Actions Section ======================================================================================


    /**
     * the standard method that move designated pawn, it's the default move
     * @param gameBoard the game board where the pawn have to move to
     * @param designatedPawn the pawn that's designated to move
     * @return the Consequence created by the move
     */
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
        }

        return new NoConsequence();
    }


    /**
     * the standard method that build a building by the designated pawn, it's the default build
     * @param designatedPawn the pawn that's designated to move
     * @param designatedCell the game board where the pawn have to move to
     * @param chosenLevel the level of the building
     * @param buildings the list of the four buildings
     * @return the Consequence created by the build
     */
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
        }

        return new NoConsequence();
    }


    /**
     * this method forces the pawn and return void because there is no consequence (victory or block) if you force a pawn
     * @param designatedPawn the pawn forced
     * @param nextPosition the cell where the pawn is forced to
     */
    @Override
    public void force(Pawn designatedPawn, Cell nextPosition) {
        designatedPawn.forcePawn(nextPosition);
    }


    /**
     * this method is used only for the destroyBlockPlayer
     */
    @Override
    public void destroy(Cell designatedCell, List<Building> buildings) {
        throw new RuntimeException("Invalid Command!");
    }


    // MARK : Pawn Placing Section ======================================================================================


    /**
     * place the pawn on the board
     * @param gameBoard the board where to place the pawn
     * @param designatedPawn the pawn that we have to place
     * @param designatedCell the cell where place the pawn
     */
    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

        int rowPosition = designatedCell.getRowPosition();
        int columnPosition = designatedCell.getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).placePawnHere(designatedPawn);

    }


    /**
     * remove a pawn from the board
     * @param gameBoard we have to remove the pawn form this board
     * @param designatedPawn the pawn to remove
     */
    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {

        int rowPosition = designatedPawn.getPosition().getRowPosition();
        int columnPosition = designatedPawn.getPosition().getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).freeCell();

    }


    @Override
    public Object clone() {

        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;

    }
}
