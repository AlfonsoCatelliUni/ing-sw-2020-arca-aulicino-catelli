package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;
import it.polimi.ingsw.model.Player.State.MoveState;


import java.util.List;


/**
 * this decorator gives the possibility to build before every move action, if the player uses this god's effect, he cannot move up in the same turn
 */
public class BuildBeforeEffect extends EffectDecorator {


    public BuildBeforeEffect(Effect e) {
        super(e);
        this.effect.changeState(new MoveAndBuildState(this));
    }


    // ======================================================================================


    /**
     * This method is different from the basic one because the player can build before the move,
     * so if it happens, we have to set some parameters in order to have a correct move after the first built
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @param designatedCell is the cell where the pawn will build
     * @param chosenLevel is the level that the designatedCell will have after the built
     * @param buildings is a list of every type of building based on the level * not used here *
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        if(super.effect.getState().getClass().equals(MoveAndBuildState.class)) {
            super.effect.changeState(new MoveState(this));
            this.effect = new NotMoveUpEffect(effect);
        }
        else if(super.effect.getState().getClass().equals(BuildState.class)) {
            super.effect.changeState(new FinishState(this));
        }

        return super.effect.build(designatedPawn, designatedCell, chosenLevel, buildings);
    }

    /**
     * this method changes the state of the player after he does a basic move action
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the pawn used for the move action
     * @param designatedCell is the position where the designatedPawn will be moved
     * @return a consequence of the move action
     */
    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

        Consequence moveConsequence = super.effect.move(gameBoard, designatedPawn, designatedCell);

        if(super.effect.getState().getClass().equals(MoveAndBuildState.class))
            super.effect.changeState(new BuildState(this));


        return moveConsequence;
    }


    @Override
    public Effect clone() {
        return new BuildBeforeEffect(effect.clone());
    }


    @Override
    public Effect addEffect(Effect e) {
        return new BuildBeforeEffect(e);
    }
}

