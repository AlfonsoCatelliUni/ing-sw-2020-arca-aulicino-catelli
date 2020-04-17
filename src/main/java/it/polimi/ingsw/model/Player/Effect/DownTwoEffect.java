package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

public class DownTwoEffect extends EffectDecorator {
    public DownTwoEffect(Effect e) {
        super(e);
    }


    /**
     * this method is the same of basic player, but it return Victory Consequence also
     * if player moves down to two levels
     */
    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        Consequence resultAction = super.move(gameBoard, designatedPawn, nextPosition);

        if( oldHeight - nextPosition.getHeight() >= 2 ) {
            return new VictoryConsequence("Winner");
        }

        return resultAction;
    }

}

