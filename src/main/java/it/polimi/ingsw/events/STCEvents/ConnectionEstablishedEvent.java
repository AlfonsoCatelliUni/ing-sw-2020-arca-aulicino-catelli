package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class ConnectionEstablishedEvent extends ServerToClientEvent {


    public Integer ID;


    // ======================================================================================


    public ConnectionEstablishedEvent(Integer ID) {
        this.ID = ID;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


    //USED IN TESTING
    @Override
    public String toString() {
        return "ConnectionEstablishedEvent{" + "\n" +
                "ID=" + ID + "\n" +
                '}';
    }
}
