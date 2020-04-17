package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

public class PushEffect extends EffectDecorator {

    public PushEffect(Effect e) {
        super(e);
    }

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
