package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class DisconnectionEvent extends ServerToClientEvent {


    private String playerNickname;


    // ======================================================================================


    public DisconnectionEvent(String playerNickname) {
        this.playerNickname = playerNickname;
    }


    public DisconnectionEvent() {
        this("");
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


}
