package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.ChosenMoveActionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;

public interface EventVisitor {


    void receiveCTS( ClientToServerEvent event );


    void manageCTSEvent(ChosenMoveActionEvent event);



}
