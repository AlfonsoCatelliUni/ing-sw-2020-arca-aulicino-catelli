package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;

public class ChosenFinishActionEvent extends ClientToServerEvent {


    public String playerNickname;


    public String action;


    // ======================================================================================


    public ChosenFinishActionEvent(String playerNickname, String action) {
        this.playerNickname = playerNickname;
        this.action = action;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
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


}
