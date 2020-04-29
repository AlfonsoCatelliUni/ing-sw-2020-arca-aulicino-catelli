package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class MoreMoveEffect extends EffectDecorator {


    public MoreMoveEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * This method decorates the player's effect based on his move for the current turn and changes his state
     * For the first move the method will change the state to a MoveAndBuild, because after the first move the player
     * could be able to move again
     * @param gameBoard is the board where the player move the pawn
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @param nextPosition is the cell where pawn moves to
     */
    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        if(super.effect.getState().getClass().equals(MoveState.class)) {
            Cell startPosition = designatedPawn.getPosition();
            changeState(new MoveAndBuildState(this));
            effect = new NotMoveBackEffect(effect, startPosition);
        }
        else if(super.effect.getState().getClass().equals(MoveAndBuildState.class)) {
            changeState(new BuildState(this));
        }

        return super.move(gameBoard, designatedPawn, nextPosition);
    }


    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        if(super.effect.getState().getClass().equals(MoveAndBuildState.class)) {
            changeState(new FinishState(this));
        }

        return super.build(designatedPawn, designatedCell, chosenLevel, buildings);
    }


    @Override
    public Effect clone() {
        return new MoreMoveEffect(effect.clone());
    }

    @Override
    public Effect addEffect(Effect e) {
        return new MoreMoveEffect(e);
    }
}
