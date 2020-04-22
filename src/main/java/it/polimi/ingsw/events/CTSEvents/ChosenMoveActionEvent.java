package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Actions.Action;

public class ChosenMoveActionEvent extends ClientToServerEvent {


    private String playerNickname;


    private Action action;


    private int pawnRow;


    private int pawnColumn;


    // ======================================================================================


    public ChosenMoveActionEvent(String playerNickname, Action action, int pawnRow, int pawnColumn) {
        this.playerNickname = playerNickname;
        this.action = action;

        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;
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


    public Action getAction() {
        return this.action;
    }


    public int getPawnRow() {
        return this.pawnRow;
    }


    public int getPawnColumn() {
        return this.pawnColumn;
    }


}
