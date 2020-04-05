package it.polimi.ingsw.events;

import it.polimi.ingsw.events.manager.ClientToServerManager;


public interface ClientToServerEvent {

    void accept(ClientToServerManager visitor);

}
