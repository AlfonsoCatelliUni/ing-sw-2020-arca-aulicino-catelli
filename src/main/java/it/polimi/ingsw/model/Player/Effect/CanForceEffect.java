package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.ForceAndMoveState;

import java.util.ArrayList;
import java.util.List;

public class CanForceEffect extends EffectDecorator {
    public CanForceEffect(Effect e) {
        super(e);
        changeState(new ForceAndMoveState(this));
    }

    @Override
    public List<Cell> wherePawnCanForce (Board gameBoard, Pawn designatedPawn){

        List<Cell> neighboringCell = gameBoard.getNeighboring( designatedPawn.getPosition() );
        List<Cell> opponentNeighboringCell = new ArrayList<>();

        for (Cell c : neighboringCell){
            if (c.getPawnInThisCell() != null && gameBoard.getSymmetrical( designatedPawn.getPosition(), c ) != null)
                opponentNeighboringCell.add(c);
        }
        return opponentNeighboringCell;
    }


}
