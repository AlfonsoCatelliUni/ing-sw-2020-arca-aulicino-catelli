package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.ClientToServerEvent;

public interface GameObserver {

    void update(ClientToServerEvent event);

}
