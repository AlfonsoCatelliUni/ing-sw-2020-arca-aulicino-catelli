package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;

public class NotMoveUpEffect extends EffectDecorator{

    public NotMoveUpEffect(Effect e) {
        super(e);
    }

    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);

        return availableCellsToMove;
    }

}
