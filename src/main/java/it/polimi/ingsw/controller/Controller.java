package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.ChosenMoveActionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Game;

public class Controller implements GameObserver, EventVisitor {


    private Game game;


    // ======================================================================================


    public Controller( Game game ){

        this.game = game;

    }



    //TODO : maybe cambiare il tipo di parametro
    @Override
    public void update(ClientToServerEvent event) {
        event.accept(this);
    }


    // ======================================================================================


    @Override
    public void receiveCTS(ClientToServerEvent event) {
        event.accept(this);
    }


    @Override
    public void manageCTSEvent(ChosenMoveActionEvent event) {

    }



}
