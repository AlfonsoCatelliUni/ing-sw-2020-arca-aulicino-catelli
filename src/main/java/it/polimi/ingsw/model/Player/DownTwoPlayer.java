package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;

/**
 * this decorator gives the possibility to win also when player moves down two levels
 */
public class DownTwoPlayer extends PlayerDecorator {


    public DownTwoPlayer(BasicPlayer player) { super(player); }


    // ======================================================================================


    /**
     * this method is the same of basic player, but it return Victory Consequence also
     * if player moves down to two levels
     */
    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        Consequence resultAction = super.move(gameBoard, designatedPawn, nextPosition);

        if( oldHeight - nextPosition.getHeight() >= 2 ) {
            return new VictoryConsequence(super.player.getName());
        }

        return resultAction;
    }


}
