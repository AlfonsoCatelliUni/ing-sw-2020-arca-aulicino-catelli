package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;

public class BlockOpponentPlayer extends PlayerDecorator {


    public BlockOpponentPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    @Override
    public int movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        int moveRetEncoded = super.movePawn(gameBoard, designatedPawn, nextPosition);

        if( moveRetEncoded == 0 && nextPosition.getHeight() - oldHeight == 1) {
            moveRetEncoded = 3;
        }

        return moveRetEncoded;
    }


}
