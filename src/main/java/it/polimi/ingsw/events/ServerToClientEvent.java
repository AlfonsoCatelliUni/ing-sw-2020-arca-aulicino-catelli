package it.polimi.ingsw.events;

import it.polimi.ingsw.events.manager.ServerToClientManager;

public interface ServerToClientEvent extends GeneralEvent {

    void accept(ServerToClientManager visitor);

}
