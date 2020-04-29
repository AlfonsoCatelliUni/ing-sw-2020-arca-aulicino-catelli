package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Color;

import java.util.List;
import java.util.ListResourceBundle;

public class StartGameEvent extends ServerToClientEvent {

    public String info;


    public StartGameEvent(String info) {
        this.info = info;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    @Override
    public String toString() {
        return "StartGameEvent{" +  "\n" +
                "info='" + info + '\'' + "\n" +
                '}';
    }
}
