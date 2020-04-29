package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

public class SwitchEffect extends  EffectDecorator {

    public SwitchEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * this is the method that actually move the pawn, in case of switching with
     * an opponent pawn this method force the opponent pawn in the designatedPawn
     * starting cell
     * @param gameBoard the gameBoard
     * @param designatedPawn the pawn that i want to move
     * @param nextPosition the new position of the pawn
     * @return an encoded value that indicates if the pawn moved on a third level
     */
    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        Consequence resultAction;

        if( nextPosition.getBuilderHere() ) {

            Pawn opponentPawn = nextPosition.getPawnInThisCell();
            Cell myPawnCell = designatedPawn.getPosition();

            removePawn(gameBoard, opponentPawn);

            resultAction = super.move(gameBoard, designatedPawn, nextPosition);

            super.force(opponentPawn, myPawnCell);

            placePawn(gameBoard, opponentPawn, myPawnCell);

        }
        else {
            resultAction = super.move(gameBoard, designatedPawn, nextPosition);
        }
        return resultAction;
    }


    @Override
    public Effect clone() {
        return new SwitchEffect(effect.clone());
    }


    @Override
    public Effect addEffect(Effect e) {
        return new SwitchEffect(e);
    }
}
