package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

public class GivePossibleCellsToBuildEvent extends ServerToClientEvent {


    private String receiverNickname;


    private List<Cell> cellsAvailableToBuild;


    private String actionID;


    // ======================================================================================


    public GivePossibleCellsToBuildEvent(String receiverNickname, List<Cell> cellsAvailableToBuild) {
        this.receiverNickname = receiverNickname;
        this.actionID = "build";

        this.cellsAvailableToBuild = cellsAvailableToBuild;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


    public String getReceiverNickname() {
        return this.receiverNickname;
    }


    public List<Cell> getCellsAvailableToBuild() {
        return this.cellsAvailableToBuild;
    }

}
