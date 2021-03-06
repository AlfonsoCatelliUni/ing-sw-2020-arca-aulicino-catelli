package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCellToForceEvent extends ClientToServerEvent {

    public String playerNickname;

    public int pawnRow;

    public int pawnColumn;

    public int rowForcedPawn;

    public int columnForcedPawn;


    // ======================================================================================


    public ChosenCellToForceEvent(String playerNickname, int pawnRow, int pawnColumn, int rowForcedPawn, int columnForcedPawn) {
        this.playerNickname = playerNickname;
        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;
        this.rowForcedPawn = rowForcedPawn;
        this.columnForcedPawn = columnForcedPawn;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }
}
