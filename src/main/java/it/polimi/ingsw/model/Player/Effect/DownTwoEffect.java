package it.polimi.ingsw.model.Player.Effect;



import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

public class DownTwoEffect extends EffectDecorator {
    public DownTwoEffect(Effect e) {
        super(e);
    }


    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        Consequence resultAction = super.move(gameBoard, designatedPawn, nextPosition);

        if( oldHeight - nextPosition.getHeight() >= 2 ) {
            return new VictoryConsequence();
            //TODO : mettere il nome alla conseguenza
        }

        return resultAction;
    }

}

