package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;

import java.util.List;

public class DomeBuildPlayer extends PlayerDecorator {


    public DomeBuildPlayer(BasicPlayer player) {
        super(player);
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




}
