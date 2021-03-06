package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class RoomNotFilledEvent extends ServerToClientEvent {


    public String message;


    // ======================================================================================


    public RoomNotFilledEvent(String message) {
        this.message = message;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    //USED IN TESTING
    @Override
    public String toString() {
        return "RoomNotFilled{" + "\n" +
                "message='" + message + '\'' + "\n" +
                '}';
    }
}
