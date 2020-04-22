package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;

import java.io.Serializable;

public class ChosenBuildActionEvent extends ClientToServerEvent {


    public String playerNickname;


    public Action action;


    public int pawnRow;


    public int pawnColumn;


    // ======================================================================================


    public ChosenBuildActionEvent(String playerNickname, Action action, int pawnRow, int pawnColumn) {
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


//    public Action getAction() {
//        return this.action;
//    }
//
//
//    public String getPlayerNickname() {
//        return this.playerNickname;
//    }
//
//
//    public int getPawnRow() {
//        return this.pawnRow;
//    }
//
//
//    public int getPawnColumn() {
//        return this.pawnColumn;
//    }


}
