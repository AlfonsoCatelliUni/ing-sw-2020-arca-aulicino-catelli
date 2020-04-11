package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.events.ClientToServerEvent;

public class ChosenMoveActionEvent implements ClientToServerEvent {


    private String playerNickname;


    private String actionID;


    private int pawnRow;


    private int pawnColumn;


    // ======================================================================================


    public ChosenMoveActionEvent(String playerNickname, int pawnRow, int pawnColumn) {
        this.playerNickname = playerNickname;
        this.actionID = "move";

        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;
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


}
