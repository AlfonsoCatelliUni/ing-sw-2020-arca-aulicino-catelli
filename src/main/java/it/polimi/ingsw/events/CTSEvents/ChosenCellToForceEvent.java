package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCellToForceEvent extends ClientToServerEvent {

    public String playerNickname;

    public int rowForcedPawn;

    public int columnForcedPawn;


    // ======================================================================================


    public ChosenCellToForceEvent(String playerNickname, int rowForcedPawn, int columnForcedPawn) {
        this.playerNickname = playerNickname;
        this.rowForcedPawn = rowForcedPawn;
        this.columnForcedPawn = columnForcedPawn;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }
}
