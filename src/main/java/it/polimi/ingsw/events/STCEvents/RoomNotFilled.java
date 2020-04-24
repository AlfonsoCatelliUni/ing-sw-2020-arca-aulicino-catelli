package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class RoomNotFilled extends ServerToClientEvent {


    public String message;


    // ======================================================================================


    public RoomNotFilled(String message) {
        this.message = message;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


}
