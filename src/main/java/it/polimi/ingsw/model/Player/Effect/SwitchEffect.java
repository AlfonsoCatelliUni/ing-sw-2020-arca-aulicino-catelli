package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

public class SwitchEffect extends  EffectDecorator{

    public SwitchEffect(Effect e) {
        super(e);
    }

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

}
