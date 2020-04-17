package it.polimi.ingsw.model.Player.Effect;

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
        changeState(new MoveAndBuildState(this));
    }




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

        Consequence buildConsequence = super.effect.build(designatedPawn, designatedCell, chosenLevel, buildings);

        if(super.effect.getState().getClass().equals(MoveAndBuildState.class)) {
            super.effect.changeState(new MoveState(this));
                this.effect = new NotMoveUpEffect(effect);
        }
        else if(super.effect.getState().getClass().equals(BuildState.class)) {
            super.effect.changeState(new FinishState(this));
            effect = new BasicEffect();
        }
        else {
            throw new RuntimeException("Invalid State!");
        }

        return buildConsequence;
    }

}
