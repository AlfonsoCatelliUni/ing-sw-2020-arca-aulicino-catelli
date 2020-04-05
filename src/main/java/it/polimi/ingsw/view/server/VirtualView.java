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

        /* json status of the game */
        if(event instanceof ServerToClientEvent) {
            sendMessage((ServerToClientEvent) event);
        }
        /* this is needed only to manage the VictoryEvent */
        else if(event instanceof ClientToServerEvent) {
            updateAllObservers(event);
        }
        else {
            throw new RuntimeException("Unknown Event Type!");
        }

    }


    public void sendMessage(ServerToClientEvent event) {

    }


    // ======================================================================================


}
