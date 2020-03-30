package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;

import java.util.ArrayList;
import java.util.List;

public class DomeBuildPlayer extends PlayerDecorator {


    public DomeBuildPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================

    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, List<Building> buildings) {

        List<Building> possibleBuilding = super.getPossibleBuildingOnCell(gameBoard, designatedCell, buildings);

        for (Building b : buildings) {
            if( b.getIsDome() && buildings.get(0).getLevel() != b.getLevel() ){
                possibleBuilding.add(b);
            }
        }

        return possibleBuilding;
    }




}
