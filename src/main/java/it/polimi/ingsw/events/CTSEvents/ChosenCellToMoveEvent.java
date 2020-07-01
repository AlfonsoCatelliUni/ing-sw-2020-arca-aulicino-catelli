package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCellToMoveEvent extends ClientToServerEvent {


    public String playerNickname;


    public int pawnRow;

    public int pawnColumn;


    public int nextRow;

    public int nextColumn;


    // ======================================================================================


    public ChosenCellToMoveEvent(String playerNickname, int pawnRow, int pawnColumn, int nextRow, int nextColumn) {
        this.playerNickname = playerNickname;

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
