package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;

import java.util.ArrayList;
import java.util.List;

public class DoubleMovePlayer extends PlayerDecorator {

    public Boolean hasMoved;
    public Cell startCell;

    // ======================================================================================

    public DoubleMovePlayer(BasicPlayer player) {
        super(player);
        this.hasMoved = false;
        this.startCell = null;
    }


    // ======================================================================================


    /**
     * This method is similar to basic, but the player can move twice
     * After the player moved, will be returned the action "build" and also "move"
     * For the end of the turn, the method will return the action "finish"
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of possible actions that the player can do in a specific moment in his turn
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn ) {

        List<Action> availableActions = new ArrayList<>();
        List<Cell> availableCellsToMove;

        /* if player moved, he can move secondly */
        if( player.getNumMove() == 1 && player.getNumBuild() == 0 && hasMoved ) {

            availableCellsToMove = player.wherePawnCanMove(gameBoard, designatedPawn);

            if(availableCellsToMove.size() > 0) {
                availableActions.add(new MoveAction());
            }

        }

        availableActions.addAll(super.player.getPossibleActions(gameBoard, designatedPawn));


        return availableActions;
    }

    /**
     * This method returns a list of cells where the pawn can move
     * This is different from the basic method because if the player moves for the second time,
     * he cannot move back
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of cells where the pawn can move in the current turn
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        if (hasMoved) {
            availableCellsToMove.remove(startCell);
        }

        if (!super.player.getCanMoveUp()) {
            availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);
        }

        return availableCellsToMove;
    }


        // this is the old version of the decorator
        /*
        List<Cell> availableCells = new ArrayList<>();


         for each available cell in where I can move with one movement I
         have to check if I can do another movement
        for (Cell c : availableCellsToMove ) {

            if (!availableCells.contains(c)) {
                availableCells.add(c);
            }

            List<Cell> secondNeighboringCells = gameBoard.getNeighboring(c);
            for (Cell cc : secondNeighboringCells ) {
                if ( cc.getHeight() - c.getHeight() <= 1 && cc.getIsFree() && !availableCells.contains(cc)){
                    availableCells.add(cc);
                }
            }

        }

        if( !super.player.getCanMoveUp() ) {
            availableCells.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);
        }

        return availableCells;  */

    /**
     * This method is different from the basic one because the player can move twice
     * so if it happens, we have to set some parameters in order to have a correct move after the first move, we have to save the start cell because the player can not
     * move back if he wants to move twice
     * @param gameBoard is the board where the player move the pawn
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @param nextPosition is the cell where pawn moves to
     */
    @Override
    public Consequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        /* if is the first move, save the pawn's position*/
        if (player.getNumMove() == 0)
            this.startCell = designatedPawn.getPosition();

        Consequence consequence = super.movePawn(gameBoard, designatedPawn, nextPosition);

        /* if is the first move, player hasMoved becomes true */
        if (player.getNumMove() == 1 && player.getNumBuild() == 0 && !hasMoved)
            this.hasMoved = true;

        return consequence;
    }


    @Override
    public void resetPlayerStatus() {

        super.resetPlayerStatus();

        this.hasMoved = false;
    }


    /**
     * USED ONLY FOR TESTING
     * @param hasMoved sets if the player has already moved
     */
    public void setHasMoved(Boolean hasMoved) {
        this.hasMoved = hasMoved;
    }


}
