package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class CanSwitchOpponentEffect extends EffectDecorator {


    public CanSwitchOpponentEffect(Effect e) {
        super(e);
        this.effect.changeState(new MoveState(this));
    }


    // ======================================================================================


    /**
     * wherePawnCanMove returns also cells occupied by opponent pawns in order to switch
     * @param gameBoard the game board where the pawn can move on
     * @param designatedPawn the pawn that's designated to move
     * @return the list of cells available to be move
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        List<Cell> neighboringCells = super.getOpponentsNeighboring(gameBoard, designatedPawn );

        for (Cell c : neighboringCells ) {
            if ( !availableCellsToMove.contains(c) && c.getHeight() - designatedPawn.getHeight() <= 1 ) {
                availableCellsToMove.add(c);
            }
        }
        return availableCellsToMove;

    }


}
