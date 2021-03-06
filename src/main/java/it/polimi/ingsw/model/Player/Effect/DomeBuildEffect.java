package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;


/**
 * this decorator gives the possibility to build a dome even if the level isn't number 3
 */
public class DomeBuildEffect extends EffectDecorator {


    public DomeBuildEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * this is the same of basic method but it adds also the Dome building in order of apply Atlas Effects
     * @param gameBoard the game board where the pawn have to build on
     * @param designatedCell the cell where the pawn have to build on
     * @return the list of possible buildings
     */
    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {

        List<Building> possibleBuilding = super.getPossibleBuildingOnCell(gameBoard, designatedCell);

        if( designatedCell.getRoof().getLevel() < 3 && gameBoard.getBuildings().get(3).isAvailable() )
            possibleBuilding.add(gameBoard.getBuildings().get(3));

        return possibleBuilding;
    }

    @Override
    public Effect clone() {
        return new DomeBuildEffect(effect.clone());
    }

    @Override
    public Effect addEffect(Effect e) {
        return new DomeBuildEffect(e);
    }
}
