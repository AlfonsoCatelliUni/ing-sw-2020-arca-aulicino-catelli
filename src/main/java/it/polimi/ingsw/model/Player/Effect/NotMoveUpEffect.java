package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class NotMoveUpEffect extends EffectDecorator{


    public NotMoveUpEffect(Effect e) {
        super(e);
        this.effect.changeState(new MoveState(this));
    }


    // ======================================================================================


    /**
     * This method returns a list of cells where the pawn can move,
     * except the cells that are higher than the pawn's cell
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of cells where the pawn can move in the current turn
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);

        return availableCellsToMove;
    }


}
