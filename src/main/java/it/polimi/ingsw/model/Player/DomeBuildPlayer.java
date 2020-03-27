package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;

import java.util.ArrayList;

public class DomeBuildPlayer extends PlayerDecorator {


    public DomeBuildPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================

    /*
    @Override
    public ArrayList<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, ArrayList<Building> buildings) {

        ArrayList<Building> possibleBuilding = super.getPossibleBuildingOnCell(gameBoard, designatedCell, buildings);

        for (Building b : buildings) {
            if( b.getIsDome() && buildings.get(0).getLevel() != b.getLevel() ){
                possibleBuilding.add(b);
            }
        }

        return possibleBuilding;
    }

     */

    @Override
    public ArrayList<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, ArrayList<Building> buildings) {

        //TODO : we have to modify this method to allow the player to build a dome
        return super.getPossibleBuildingOnCell(gameBoard, designatedCell, buildings);
    }


}
