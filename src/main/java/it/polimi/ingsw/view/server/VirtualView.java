package it.polimi.ingsw.view.server;


import it.polimi.ingsw.events.GeneralEvent;
import it.polimi.ingsw.events.STCEvents.NotifyStatusEvent;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class VirtualView extends Observable implements Observer {


    // ======================================================================================





    // ======================================================================================


    @Override
    public void update(GeneralEvent event) {

        /* in here i check that the event is a NotifyStatusEvent directed to the ClientView,
        * if not so i threw an exception */
        if(event instanceof NotifyStatusEvent) {
            /* i know for sure that this event is a NotifyStatusEvent so i can cast it */
            String status = ((NotifyStatusEvent) event).getStatus();


        }
        else {
            /* threw a beautiful exception */
        }

    }


    // ======================================================================================


}
