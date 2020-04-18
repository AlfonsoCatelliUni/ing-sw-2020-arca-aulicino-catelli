package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

public class PushEffect extends EffectDecorator {


    public PushEffect(Effect e) {
        super(e);
        this.effect.changeState(new MoveState(this));
    }


    // ======================================================================================


    /**
     * this method move pawn on nextposition cell and if the cell is occupied by a opponent pawn, it pushes this pawn in the same direction if is unoccupied
     * @param gameBoard the board where we have to move the pawn
     * @param designatedPawn the pawn that's designated to move
     * @param nextPosition the cell where to move the pawn
     * @return int encoded by super.movepawn
     */
    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        Consequence resultAction;


        if( nextPosition.getBuilderHere() ) {

            int diffRow = nextPosition.getRowPosition() - designatedPawn.getPosition().getRowPosition();
            int diffColumn = nextPosition.getColumnPosition() - designatedPawn.getPosition().getColumnPosition();

            Pawn oppPawn = nextPosition.getPawnInThisCell();
            Cell oppCell = gameBoard.getCell( nextPosition.getRowPosition()+diffRow, nextPosition.getColumnPosition()+diffColumn );

            removePawn(gameBoard, nextPosition.getPawnInThisCell());

            resultAction = super.move(gameBoard, designatedPawn, nextPosition);

            super.force(oppPawn, oppCell);

            placePawn(gameBoard, oppPawn, oppCell);

        }
        else {
            resultAction = super.move(gameBoard, designatedPawn, nextPosition);
        }

        return resultAction;
    }


}
