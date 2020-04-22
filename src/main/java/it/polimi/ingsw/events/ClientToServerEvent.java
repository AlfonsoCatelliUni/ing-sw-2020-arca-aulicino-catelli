package it.polimi.ingsw.events;

import it.polimi.ingsw.events.manager.ClientToServerManager;

import java.io.Serializable;


public abstract class ClientToServerEvent implements Serializable {

    public abstract void accept(ClientToServerManager visitor);

}
