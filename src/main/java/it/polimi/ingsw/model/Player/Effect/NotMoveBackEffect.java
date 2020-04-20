package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class NotMoveBackEffect extends EffectDecorator {


    private Cell startCell;


    // ======================================================================================


    public NotMoveBackEffect(Effect e, Cell startCell) {
        super(e);
        this.startCell = startCell;
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * This method returns a list of cells where the pawn can move,
     * except the starting cell of the turn of the designatedPawn
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of cells where the pawn can move in the current turn
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        List<Cell> cells;

        cells = super.effect.wherePawnCanMove(gameBoard, designatedPawn);

        cells.removeIf(cell -> cell.equals(startCell));

        return cells;

    }


}
