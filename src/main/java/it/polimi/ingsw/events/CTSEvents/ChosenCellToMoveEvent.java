package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;

public class ChosenCellToMoveEvent implements ClientToServerEvent {


    private String playerNickname;


    private int pawnRow;

    private int pawnColumn;


    private int nextRow;

    private int nextColumn;


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
        visitor.receiveEvent(this);
    }


    // ======================================================================================


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
