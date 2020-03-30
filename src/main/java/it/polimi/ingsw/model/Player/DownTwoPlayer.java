package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Pawn;

public class DownTwoPlayer extends PlayerDecorator {

    public DownTwoPlayer(BasicPlayer player) { super(player); }


    @Override
    public int movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getzPosition();

        int moveRetEncoded = super.movePawn(gameBoard, designatedPawn, nextPosition);

        if( moveRetEncoded == 0 && oldHeight - nextPosition.getHeight() >= 2 ) {
            moveRetEncoded = 2;
        }

        return moveRetEncoded;
    }
}
