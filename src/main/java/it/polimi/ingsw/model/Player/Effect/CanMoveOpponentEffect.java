package it.polimi.ingsw.model.Player.Effect;


import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;

public class CanMoveOpponentEffect extends EffectDecorator {

    public CanMoveOpponentEffect(Effect e){
        super(e);
    }


    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        List<Cell> neighboringCells = gameBoard.getNeighboring( designatedPawn.getPosition() );

        for (Cell c : neighboringCells ) {
            if ( !availableCellsToMove.contains(c) && c.getBuilderHere() &&
                    c.getPawnInThisCell().getColor() != designatedPawn.getColor() &&
                    c.getHeight() - designatedPawn.getHeight() <= 1 ) {
                availableCellsToMove.add(c);
            }
        }

        return availableCellsToMove;
    }
}
