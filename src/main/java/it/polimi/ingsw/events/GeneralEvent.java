package it.polimi.ingsw.events;

import it.polimi.ingsw.events.manager.ClientToServerManager;

public interface GeneralEvent {

    void accept(ClientToServerManager visitor);

}
