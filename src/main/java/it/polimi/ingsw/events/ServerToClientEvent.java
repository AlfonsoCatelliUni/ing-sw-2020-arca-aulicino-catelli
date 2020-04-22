package it.polimi.ingsw.events;

import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.io.Serializable;

public abstract class ServerToClientEvent implements Serializable {

    public abstract void accept(ServerToClientManager visitor);

}
