package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

import java.io.Serializable;

public class ClientDisconnectionEvent extends ClientToServerEvent {


    public Integer ID;

    public String playerNickname;


    // ======================================================================================


    public ClientDisconnectionEvent(Integer ID, String playerNickname) {
        this.ID = ID;
        this.playerNickname = playerNickname;
    }


    public ClientDisconnectionEvent(Integer ID) {
        this(ID,"");
    }

    public ClientDisconnectionEvent(String playerNickname) {
        this(-1, playerNickname);
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.receiveEvent(this);
    }


}
