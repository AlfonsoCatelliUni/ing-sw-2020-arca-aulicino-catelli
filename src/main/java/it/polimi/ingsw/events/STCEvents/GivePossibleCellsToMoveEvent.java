package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

public class GivePossibleCellsToMoveEvent extends ServerToClientEvent {


    private String receiverNickname;


    private List<Cell> cellsAvailableToMove;


    private String actionID;


    // ======================================================================================


    public GivePossibleCellsToMoveEvent(String receiverNickname, List<Cell> cellsAvailableToMove) {
        this.receiverNickname = receiverNickname;
        this.actionID = "move";

        this.cellsAvailableToMove = cellsAvailableToMove;
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


    public List<Cell> getCellsAvailableToMove() {
        return this.cellsAvailableToMove;
    }


}
