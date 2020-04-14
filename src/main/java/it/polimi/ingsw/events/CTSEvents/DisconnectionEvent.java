package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class DisconnectionEvent implements ClientToServerEvent {


    private String playerNickname;


    // ======================================================================================


    public DisconnectionEvent(String playerNickname) {
        this.playerNickname = playerNickname;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.receiveEvent(this);
    }


}
