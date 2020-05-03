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


    /**
     * this method changes the state of the player after he does a basic build action
     * @param designatedPawn is the pawn used for the move action
     * @param designatedCell is the position where the designatedPawn will be moved
     * @param chosenLevel is the level of the building that will be built
     * @param buildings is the list of possible buildings that can be built
     * @return a consequence of the build action
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        Consequence consequence = super.build(designatedPawn, designatedCell, chosenLevel, buildings);

        if(super.effect.getState().getClass().equals(MoveAndBuildState.class)) {
            changeState(new FinishState(this));
        }

        return consequence;
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
