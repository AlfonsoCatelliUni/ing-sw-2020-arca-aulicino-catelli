package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

public class GivePossibleCellsToBuildEvent extends ServerToClientEvent {


    public String receiverNickname;


    public List<Cell> cellsAvailableToBuild;


    public String actionID;


    public boolean isValid;


    // ======================================================================================


    public GivePossibleCellsToBuildEvent(String receiverNickname, List<Cell> cellsAvailableToBuild, boolean isValid) {
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


//    public String getReceiverNickname() {
//        return this.receiverNickname;
//    }
//
//
//    public List<Cell> getCellsAvailableToBuild() {
//        return this.cellsAvailableToBuild;
//    }

}
