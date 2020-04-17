package it.polimi.ingsw.model.Player.Effect;


import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

public class DomeBuildEffect extends EffectDecorator {

    public DomeBuildEffect(Effect e) {
        super(e);
    }

    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {

        List<Building> possibleBuilding = super.getPossibleBuildingOnCell(gameBoard, designatedCell);

        if( designatedCell.getRoof().getLevel() < 3 && gameBoard.getBuildings().get(3).isAvailable() )
            possibleBuilding.add(gameBoard.getBuildings().get(3));

        return possibleBuilding;
    }

}
