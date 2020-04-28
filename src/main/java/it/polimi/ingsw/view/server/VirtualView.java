package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
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


    public VirtualView(Controller controller) {
        //ID_Connection and Nickname_ID mapping, initial empty
        connectionMap = new HashMap<>();
        nicknameMap = new HashMap<>();

        this.addObserver(controller);

    }

    public VirtualView() {

        connectionMap = new HashMap<>();
        nicknameMap = new HashMap<>();

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
    public void update(ServerToClientEvent event) {
        sendMessage(event);
    }


    @Override
    public void update(ClientToServerEvent event) {
        updateAllObservers(event);
    }


    public void sendMessage(ServerToClientEvent event) {

        Set<Integer> keys = connectionMap.keySet();

        try {

            for (Integer k : keys ) {
                connectionMap.get(k).sendEvent(event);
            }
        }
        catch(NullPointerException e) {
            //USED ONLY FOR TESTING
//            System.out.println(event.toString() + "\n");
        }
    }


    public void sendMessageTo(Integer ID, ServerToClientEvent event) {
        connectionMap.get(ID).sendEvent(event);
    }


    public void sendMessageTo(String nickname, ServerToClientEvent event) {
        try {
            sendMessageTo(nicknameMap.get(nickname), event);
        }
        catch (NullPointerException e) {
            //USED ONLY FOR TESTING
//            System.out.println(event.toString() + "\n");
        }
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


    //TODO : maybe sync
    public String removeNicknameIDConnection(Integer ID) {

        String nickname = "";

        try{
            for(String key : nicknameMap.keySet()) {
                if(nicknameMap.get(key).equals(ID)){
                    nickname = key;
                    nicknameMap.remove(key);
                    connectionMap.remove(ID);
                    break;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return nickname;
    }




}
