package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildAndFinishState;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;


import java.util.List;

public class MoreBuildNotOnSameEffect extends EffectDecorator {


    public MoreBuildNotOnSameEffect(Effect e) {
        super(e);
    }


    // ======================================================================================


    /**
     * This method decorates the player's effect based on his build for the current turn and changes his state
     * If this is the first build, than the player could build again, so the next state will be BuildAndFinish state,
     * but he will not be able to build on the same cell, so his effect is decorated with a BuildNotOnSameCellEffect
     * @param designatedPawn is the pawn used in the current turn
     * @param designatedCell is the cell where the pawn will build a block
     * @param chosenLevel is the level of the block that will be built
     * @param buildings is the list of possible buildings to be built
     * @return a consequence of this build action that could affect the game
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        if (super.effect.getState().getClass().equals(BuildState.class)) {
                changeState(new BuildAndFinishState(this));
                effect = new BuildNotOnSameCellEffect(effect, designatedCell);

        }

        else if(super.effect.getState().getClass().equals(BuildAndFinishState.class)) {
                changeState(new FinishState(this));
        }

        return super.build(designatedPawn, designatedCell, chosenLevel, buildings);
    }


}


