package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;

public class CanPushOpponentEffect extends EffectDecorator {


    public CanPushOpponentEffect(Effect e){
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * wherePawnCanMove returns also cells occupied by opponent pawns in order to push it
     * @param gameBoard the game board where the pawn can move on
     * @param designatedPawn the pawn that's designated to move
     * @return the list of cells available to be move
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        List<Cell> opponentNeighboringCells = gameBoard.getOpponentsNeighboring( designatedPawn );

        int diffRow = 0;
        int diffColumn = 0;

        for ( Cell c : opponentNeighboringCells ) {
                diffRow = c.getRowPosition() - designatedPawn.getPosition().getRowPosition();
                diffColumn = c.getColumnPosition() - designatedPawn.getPosition().getColumnPosition();

                if( c.getRowPosition()+diffRow >= 0 && c.getRowPosition()+diffRow <= 4 &&
                        c.getColumnPosition()+diffColumn >= 0 && c.getColumnPosition()+diffColumn <= 4) {
                    if( gameBoard.getCell( c.getRowPosition()+diffRow, c.getColumnPosition()+diffColumn ).getIsFree() ) {
                        availableCellsToMove.add(c);
                    }
                }

            }

        return availableCellsToMove;
    }


    @Override
    public Effect clone() {
        return new CanPushOpponentEffect(effect.clone());
    }


    @Override
    public Effect addEffect(Effect e) {
        return new CanPushOpponentEffect(e);
    }
}
