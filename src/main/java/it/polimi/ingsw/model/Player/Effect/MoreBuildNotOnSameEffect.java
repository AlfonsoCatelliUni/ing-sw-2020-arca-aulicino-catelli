package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildAndFinishState;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;


import java.util.ArrayList;
import java.util.List;

public class MoreBuildNotOnSameEffect extends EffectDecorator {

    public MoreBuildNotOnSameEffect(Effect e) {
        super(e);
    }

    /**
     * This is the same of basic method but it stores the value of the cell of the first build
     * @param designatedPawn the pawn that's designated to build
     * @param designatedCell the cell where to build
     * @param chosenLevel the level of the building to build
     * @param buildings list of possible buildings to build
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        if (super.effect.getState().getClass().equals(BuildState.class)) {
                changeState(new BuildAndFinishState(this));
                effect = new BuildNotOnSameCellEffect(effect, designatedCell);

        }

        else if(super.effect.getState().getClass().equals(BuildAndFinishState.class)) {
                changeState(new FinishState(this));
            effect = new BasicEffect();
        }

        return super.build(designatedPawn, designatedCell, chosenLevel, buildings);
    }

}


