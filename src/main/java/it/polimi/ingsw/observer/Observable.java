package it.polimi.ingsw.observer;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import java.util.ArrayList;
import java.util.List;

public class Observable {


    private List<Observer> observers;


    // ======================================================================================


    public Observable(){
        this.observers = new ArrayList<>();
    }


    // ======================================================================================


    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }


    public void updateAllObservers(ClientToServerEvent event){
        for(Observer o : observers) {
            o.update(event);
        }
    }


    public void updateAllObservers(ServerToClientEvent event){
        for(Observer o : observers) {
            o.update(event);
        }
    }


}
