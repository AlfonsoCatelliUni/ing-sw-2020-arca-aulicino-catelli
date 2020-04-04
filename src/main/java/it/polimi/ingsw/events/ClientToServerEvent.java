package it.polimi.ingsw.events;

import it.polimi.ingsw.events.manager.ClientToServerManager;

public interface ClientToServerEvent extends GeneralEvent {

    void accept(ClientToServerManager visitor);

}
