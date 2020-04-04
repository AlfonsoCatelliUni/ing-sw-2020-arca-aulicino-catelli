package it.polimi.ingsw.observer;


import it.polimi.ingsw.events.GeneralEvent;

public interface Observer {

    void update(GeneralEvent event);

}
