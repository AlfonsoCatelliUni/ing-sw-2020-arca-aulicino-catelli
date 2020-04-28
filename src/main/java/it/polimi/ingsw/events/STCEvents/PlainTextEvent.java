package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class PlainTextEvent extends ServerToClientEvent {

    public String message;

    public PlainTextEvent(String message) {
        this.message = message;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    @Override
    public String toString() {
        return "PlainTextEvent{" + "\n" +
                "message='" + message + '\'' + "\n" +
                '}';
    }
}
