package it.polimi.ingsw.observer;


import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

public interface Observer {


    void update(Object event);


    void update(ClientToServerEvent event);
    void update(ServerToClientEvent event);



}
