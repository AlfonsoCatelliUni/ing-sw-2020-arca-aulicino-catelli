package it.polimi.ingsw.events;

import it.polimi.ingsw.controller.EventVisitor;

public interface ClientToServerEvent extends GeneralEvent {

    void accept(EventVisitor visitor);

}
