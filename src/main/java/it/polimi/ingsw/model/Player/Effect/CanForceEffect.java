package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.ForceAndMoveState;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.ArrayList;
import java.util.List;

public class CanForceEffect extends EffectDecorator {


    public CanForceEffect(Effect e) {
        super(e);
        changeState(new ForceAndMoveState(this));
    }


    // ======================================================================================


    /**
     * this method checks if the designatedPawn can force an opponent pawn to the
     * space directly on the other side of the designatedPawn position if that space is unoccupied
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the pawn of the current player
     * @return a list of cells that are the positions of the opponent player's pawn that can be forced
     */
    @Override
    public List<Cell> getOpponentsNeighboring(Board gameBoard, Pawn designatedPawn) {

        List<Cell> opponentsCell = super.getOpponentsNeighboring(gameBoard, designatedPawn);
        List<Cell> opponentNeighboringCell = new ArrayList<>();

        for (Cell c : opponentsCell){
            if (gameBoard.getSymmetrical( designatedPawn.getPosition(), c ) != null && c.getPawnInThisCell().getColor() != designatedPawn.getColor())
                if (gameBoard.getSymmetrical( designatedPawn.getPosition(), c ).getIsFree())
                    opponentNeighboringCell.add(c);
        }
        return opponentNeighboringCell;
    }

    @Override
    public void force(Pawn designatedPawn, Cell nextPosition) {

        super.force(designatedPawn, nextPosition);

        if(this.effect.getState().getClass().equals(ForceAndMoveState.class)) {

            this.effect.changeState(new MoveState(this));

        }
    }

    @Override
    public Effect clone() {
        return new CanForceEffect(effect.clone());
    }

    @Override
    public Effect addEffect(Effect e) {
        return new CanForceEffect(e);
    }
}
