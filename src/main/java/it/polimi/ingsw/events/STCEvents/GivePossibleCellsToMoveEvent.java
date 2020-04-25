package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Board.Cell;

import java.awt.*;
import java.util.List;

public class GivePossibleCellsToMoveEvent extends ServerToClientEvent {


    public String receiverNickname;


    public List<Point> cellsAvailableToMove;


    public String actionID;


    public boolean isValid;


    // ======================================================================================


    public GivePossibleCellsToMoveEvent(String receiverNickname, List<Point> cellsAvailableToMove, boolean isValid) {
        this.receiverNickname = receiverNickname;
        this.actionID = "move";

        this.cellsAvailableToMove = cellsAvailableToMove;

        this.isValid = isValid;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


}
