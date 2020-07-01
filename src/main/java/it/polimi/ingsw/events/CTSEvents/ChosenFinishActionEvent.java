package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

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

}
