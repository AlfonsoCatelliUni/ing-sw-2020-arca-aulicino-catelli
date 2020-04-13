package it.polimi.ingsw.view.server;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class VirtualView extends Observable implements Observer {


    // ======================================================================================





    // ======================================================================================


    @Override
    public void update(Object event) {
        throw new RuntimeException("Unknown Event Type!");
    }


    @Override
    public void update(ServerToClientEvent event) {
        sendMessage(event);
    }


    @Override
    public void update(ClientToServerEvent event) {
        updateAllObservers(event);
    }


    public void sendMessage(ServerToClientEvent event) {

    }


    // ======================================================================================





}
