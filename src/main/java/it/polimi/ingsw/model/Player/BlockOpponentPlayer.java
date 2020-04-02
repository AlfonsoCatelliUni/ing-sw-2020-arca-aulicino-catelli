package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.MoveConsequence;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;

public class BlockOpponentPlayer extends PlayerDecorator {


    public BlockOpponentPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    /**
     * This method is different from the basic one because the special effect for this player is that if a pawn moves up in his turn,
     * for the next opponent's turns, the other players will not be able to move up
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @param nextPosition is the cell where the designatedPawn will move
     * @return is an encoded value. 0 and 1 in described in the basic method, while the value 3 means that the pawn
     * moved up, so for the next opponent's turns, they will not be able to move up with their pawns
     */
    @Override
    public MoveConsequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int oldHeight = designatedPawn.getHeight();

        MoveConsequence resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);

        if( !resultAction.isCheckResult() && nextPosition.getHeight() - oldHeight == 1) {
            return new MoveConsequence(true,false, true);
        }

        return resultAction;
    }


}