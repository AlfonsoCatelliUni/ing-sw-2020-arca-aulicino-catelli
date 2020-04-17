package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.BlockConsequence;
import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

public class BlockOpponentEffect extends EffectDecorator {

    public BlockOpponentEffect(Effect e) {
        super(e);
    }

    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        Consequence resultAction = super.move(gameBoard, designatedPawn, nextPosition);

        if( !(resultAction instanceof VictoryConsequence) && nextPosition.getHeight() - oldHeight == 1) {
            return new BlockConsequence("Athena");
        }

        return resultAction;
    }


}