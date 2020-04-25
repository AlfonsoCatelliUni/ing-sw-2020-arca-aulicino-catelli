package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCellToBuildEvent extends ClientToServerEvent {


    public String playerNickname;


    public String actionID;


    public int pawnRow;

    public int pawnColumn;


    public int nextRow;

    public int nextColumn;


    // ======================================================================================


    public ChosenCellToBuildEvent(String playerNickname, int pawnRow, int pawnColumn, int nextRow, int nextColumn) {
        this.playerNickname = playerNickname;
        this.actionID = "build";

        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;

        this.nextRow = nextRow;
        this.nextColumn = nextColumn;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================

}
