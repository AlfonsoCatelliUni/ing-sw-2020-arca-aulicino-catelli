package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

import java.io.Serializable;

public class ClientDisconnectionEvent extends ClientToServerEvent {


    public String playerNickname;


    // ======================================================================================


    public ClientDisconnectionEvent(String playerNickname) {
        this.playerNickname = playerNickname;
    }


    public ClientDisconnectionEvent() {
        this("");
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.receiveEvent(this);
    }


}
