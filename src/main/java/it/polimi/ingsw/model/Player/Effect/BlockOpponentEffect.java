package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.BlockConsequence;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

public class BlockOpponentEffect extends EffectDecorator {


    public BlockOpponentEffect(Effect e) {
        super(e);
    }


    // ======================================================================================


    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        Consequence resultAction = super.move(gameBoard, designatedPawn, nextPosition);

        if( !(resultAction instanceof VictoryConsequence) && nextPosition.getHeight() - oldHeight == 1) {
            return new BlockConsequence();
            //TODO : mettere il nome nella conseguenza
        }

        return resultAction;
    }



}