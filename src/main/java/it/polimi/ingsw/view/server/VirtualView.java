package it.polimi.ingsw.view.server;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VirtualView extends Observable implements Observer {


    private static Map<Integer, Connection> connectionMap;

    private static Map<String, Integer> nicknameMap;


    // ======================================================================================


    public VirtualView() {
        connectionMap = new HashMap<>();
    }


    // ======================================================================================


    public static Boolean isValidID(Integer id) {
        return !connectionMap.containsKey(id);
    }


    public static void newConnection(Integer id, Connection connection) {
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
        Set<Integer> keys = connectionMap.keySet();

        for (Integer k : keys ) {
            connectionMap.get(k).sendEvent(event);
        }
    }


    public void sendMessageTo(Integer ID, ServerToClientEvent event) {
        connectionMap.get(ID).sendEvent(event);
    }


    public void sendMessageTo(String nickname, ServerToClientEvent event) {
        sendMessageTo(nicknameMap.get(nickname), event);
    }


    // ======================================================================================


    //TODO : maybe sync
    public boolean newNicknameID(String nickname, Integer ID) {
        try {
            nicknameMap.put(nickname, ID);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }




}
