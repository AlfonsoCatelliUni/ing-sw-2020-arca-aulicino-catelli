package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveConsequence;
import it.polimi.ingsw.model.Actions.VictoryAction;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;

public class DownTwoPlayer extends PlayerDecorator {


    public DownTwoPlayer(BasicPlayer player) { super(player); }


    // ======================================================================================


    @Override
    public MoveConsequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        MoveConsequence resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);

        if( !resultAction.isVictoryByMove() && oldHeight - nextPosition.getHeight() >= 2 ) {
            return new MoveConsequence(true,true,false);
        }

        return resultAction;
    }


}
