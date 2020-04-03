package it.polimi.ingsw.events;

import it.polimi.ingsw.controller.EventVisitor;

public interface ServerToClientEvent extends GeneralEvent {

    void accept(EventVisitor visitor);

}
