package it.polimi.ingsw.view.server;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Connection;

import java.util.HashMap;
import java.util.Map;

public class VirtualView extends Observable implements Observer {


    private static Map<Integer, Connection> connectionMap;


    // ======================================================================================


    public VirtualView() {
        connectionMap = new HashMap<>();
    }


    // ======================================================================================


    public static Boolean isValidID(Integer id) {
        return !connectionMap.containsKey(id);
    }


    public static void newTempConnection(Integer id, Connection connection) {
        connectionMap.put(id, connection);
    }


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


    public void sendMessageTo(Integer id, ServerToClientEvent event) {

    }


    // ======================================================================================





}
