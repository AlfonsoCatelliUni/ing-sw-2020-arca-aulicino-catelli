package it.polimi.ingsw.events;

import it.polimi.ingsw.events.manager.ServerToClientManager;

public interface ServerToClientEvent {

    void accept(ServerToClientManager visitor);

}
