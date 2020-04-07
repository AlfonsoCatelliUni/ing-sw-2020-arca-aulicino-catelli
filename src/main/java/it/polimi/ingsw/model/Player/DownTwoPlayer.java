package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;

public class DownTwoPlayer extends PlayerDecorator {


    public DownTwoPlayer(BasicPlayer player) { super(player); }


    // ======================================================================================


    @Override
    public Consequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        Consequence resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);

        if( oldHeight - nextPosition.getHeight() >= 2 ) {
            return new VictoryConsequence(super.player.getName());
        }

        return resultAction;
    }


}
