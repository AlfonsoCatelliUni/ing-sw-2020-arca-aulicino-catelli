package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

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


    //USED IN TESTING
    @Override
    public String toString() {
        return "GivePossibleCellsToMoveEvent{" + "\n" +
                "receiverNickname='" + receiverNickname + '\'' + ",\n" +
                "cellsAvailableToMove=" + cellsAvailableToMove + ",\n" +
                "actionID='" + actionID + '\'' + ",\n" +
                "isValid=" + isValid + "\n" +
                '}';
    }
}
