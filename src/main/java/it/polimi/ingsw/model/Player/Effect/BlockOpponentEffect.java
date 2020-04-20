package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.BlockConsequence;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.VictoryConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

/**
 * this decorator blocks the possibility of opponent player to move up during their turn
 */
public class BlockOpponentEffect extends EffectDecorator {


    public BlockOpponentEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
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