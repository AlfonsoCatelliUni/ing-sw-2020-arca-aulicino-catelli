package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.io.Serializable;

public class UnavailableNicknameEvent extends ServerToClientEvent {


    public int ID;

    public UnavailableNicknameEvent(int ID) {
        this.ID = ID;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


}
