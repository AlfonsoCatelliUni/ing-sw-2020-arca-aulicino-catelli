package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCellToMoveEvent implements ClientToServerEvent {


    private String playerNickname;


    private String actionID;


    private int pawnRow;

    private int pawnColumn;


    private int nextRow;

    private int nextColumn;


    // ======================================================================================


    public ChosenCellToMoveEvent(String playerNickname, int pawnRow, int pawnColumn, int nextRow, int nextColumn) {
        this.playerNickname = playerNickname;
        this.actionID = "move";

        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;

        this.nextRow = nextRow;
        this.nextColumn = nextColumn;

    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.receiveEvent(this);
    }


    // ======================================================================================


    public String getActionID() {
        return this.actionID;
    }


    public String getPlayerNickname() {
        return this.playerNickname;
    }


    public int getPawnRow() {
        return this.pawnRow;
    }


    public int getPawnColumn() {
        return this.pawnColumn;
    }


    public int getNextRow() {
        return this.nextRow;
    }


    public int getNextColumn() {
        return this.nextColumn;
    }




}
