package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Consequence.NoConsequence;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class BuildUnderItselfEffect extends EffectDecorator {
    public BuildUnderItselfEffect(Effect e) {
        super(e);
        this.effect.changeState(new MoveState(this));
    }


    /**
     * this method actually build the block of the chosen level in the designated cell
     * but if the player has decided to build a block under his own pawn then I have to
     * force the pawn up one block (the player does not win by forcing from 2nd to 3hd level)
     * @param designatedPawn the pawn that build the block
     * @param designatedCell the cell where the pawn build the block
     * @param chosenLevel the level of the new block
     * @param buildings the list of all blocks
     * @return the consequence of the build action
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        super.build(designatedPawn, designatedCell, chosenLevel, buildings);

        /* se costruisco sotto di me devo solamente cambiare
         * la mia altezza e poi mi forzo la mossa */
        if(designatedCell.equals(designatedPawn.getPosition())) {
            force(designatedPawn, designatedCell);
        }
        return new NoConsequence();
    }

}
