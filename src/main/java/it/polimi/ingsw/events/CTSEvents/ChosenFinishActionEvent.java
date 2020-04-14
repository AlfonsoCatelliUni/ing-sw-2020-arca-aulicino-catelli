package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenFinishActionEvent implements ClientToServerEvent {


    private String playerNickname;


    private String actionID;


    private int pawnRow;


    private int pawnColumn;


    // ======================================================================================


    public ChosenFinishActionEvent(String playerNickname, int pawnRow, int pawnColumn) {
        this.playerNickname = playerNickname;
        this.actionID = "finish";

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
