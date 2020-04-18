package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.DestroyAndFinishState;
import it.polimi.ingsw.model.Player.State.FinishState;

import java.util.List;

public class DestroyEffect extends EffectDecorator {
    public DestroyEffect(Effect e) {
        super(e);
    }


    /**
     * this method destroys the roof of the designatedCell.
     * This method will not destroy a dome because the wherePawnCanDestroy do not return cells with a dome in them
     * Same for the cells with height == 0
     * The block is only removed, so it is reusable
     * @param buildings is the list of possible buildings
     * @param designatedCell is the cell where the block will be destroyed
     */
    public void destroyBlock(List<Building> buildings, Cell designatedCell) {

        for(Building building : buildings) {

            if(building.getLevel()+1 == designatedCell.getHeight()) {
                designatedCell.destroyRoof(building);
                break;
            }
        }
        if (super.effect.getState().getClass().equals(DestroyAndFinishState.class))
            changeState(new FinishState(this));
    }

    /**
     * this overrides the super method until the player has to finish the turn
     * With the finish action, the player could also have a destroy action to be used
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        Consequence consequence = super.build(designatedPawn, designatedCell, chosenLevel, buildings);
        if (super.effect.getState().getClass().equals(FinishState.class))
            changeState(new DestroyAndFinishState(this));
        return consequence;
    }
}
