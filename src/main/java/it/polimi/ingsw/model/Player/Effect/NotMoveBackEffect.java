package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;

public class NotMoveBackEffect extends EffectDecorator {

    Cell startCell;

    public NotMoveBackEffect(Effect e, Cell startCell) {
        super(e);
        this.startCell = startCell;
    }

    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        List<Cell> cells;

        cells = super.effect.wherePawnCanMove(gameBoard, designatedPawn);

        cells.removeIf(cell -> cell.equals(startCell));

        return cells;

    }

}
