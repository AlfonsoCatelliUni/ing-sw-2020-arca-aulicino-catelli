package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.awt.*;
import java.util.List;

public class GivePossibleCellsToBuildEvent extends ServerToClientEvent {


    public String receiverNickname;


    public List<Point> cellsAvailableToBuild;


    public String actionID;


    public boolean isValid;


    // ======================================================================================


    public GivePossibleCellsToBuildEvent(String receiverNickname, List<Point> cellsAvailableToBuild, boolean isValid) {
        this.receiverNickname = receiverNickname;
        this.actionID = "build";

        this.cellsAvailableToBuild = cellsAvailableToBuild;

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
        return "GivePossibleCellsToBuildEvent{" + "\n" +
                "receiverNickname='" + receiverNickname + '\'' + ",\n" +
                "cellsAvailableToBuild=" + cellsAvailableToBuild + ",\n" +
                "actionID='" + actionID + '\'' + ",\n" +
                "isValid=" + isValid + "\n" +
                '}';
    }
}
