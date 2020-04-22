package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;

public class ChosenFinishActionEvent extends ClientToServerEvent {


    private String playerNickname;


    private Action action;


    // ======================================================================================


    public ChosenFinishActionEvent(String playerNickname, Action action) {
        this.playerNickname = playerNickname;
        this.action = action;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.receiveEvent(this);
    }


    // ======================================================================================


    public Action getAction() {
        return this.action;
    }


    public String getPlayerNickname() {
        return this.playerNickname;
    }


}
