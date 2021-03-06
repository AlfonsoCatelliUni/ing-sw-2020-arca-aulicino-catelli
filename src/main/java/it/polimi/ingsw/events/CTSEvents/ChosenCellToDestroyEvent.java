package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCellToDestroyEvent extends ClientToServerEvent {

    public String playerNickname;

    public int pawnRow;

    public int pawnColumn;

    public int rowToDestroy;

    public int columnToDestroy;


    // ======================================================================================


    public ChosenCellToDestroyEvent(String playerNickname, int pawnRow, int pawnColumn, int rowToDestroy, int columnToDestroy) {
        this.playerNickname = playerNickname;
        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;
        this.rowToDestroy = rowToDestroy;
        this.columnToDestroy = columnToDestroy;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }
}
