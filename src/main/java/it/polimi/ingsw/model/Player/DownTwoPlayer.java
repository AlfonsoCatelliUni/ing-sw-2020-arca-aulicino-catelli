package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.VictoryAction;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;

public class DownTwoPlayer extends PlayerDecorator {


    public DownTwoPlayer(BasicPlayer player) { super(player); }


    // ======================================================================================


    @Override
    public Action movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        Action resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);

        if( !resultAction.getClass().equals(VictoryAction.class) && oldHeight - nextPosition.getHeight() >= 2 ) {
            return new VictoryAction();
        }

        return resultAction;
    }


}
