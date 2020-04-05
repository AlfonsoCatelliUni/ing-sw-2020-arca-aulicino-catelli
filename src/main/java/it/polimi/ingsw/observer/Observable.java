package it.polimi.ingsw.observer;

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


    public void updateAllObservers(Object event){
        for(Observer o : observers) {
            o.update(event);
        }
    }



}
