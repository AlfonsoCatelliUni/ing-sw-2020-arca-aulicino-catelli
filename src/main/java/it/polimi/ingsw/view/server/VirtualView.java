package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Connection;

import java.util.*;
import java.util.concurrent.locks.Lock;

public class VirtualView extends Observable implements Observer {

    /**
     * the nicknames of the players connected to this particular VirtualView
     */
    private List<String> nicknames;


    /**
     * the ID-Connections map for all players
     */
    private static Map<Integer, Connection> connectionMap = new HashMap<>();


    /**
     * the Nickname-ID map for all players
     */
    private static Map<String, Integer> nicknameMap = new HashMap<>();

    private static Object lock = new Object();

    // ======================================================================================


    public VirtualView(Controller controller) {
        this.nicknames = new ArrayList<>();

        this.addObserver(controller);
    }


    public VirtualView() {

        this.nicknames = new ArrayList<>();

    }


    // ======================================================================================


    public static Boolean isValidID(Integer id) {
        synchronized (lock) {
            return !connectionMap.containsKey(id);
        }
    }


    public static boolean isValidNickname(String nickname) {

        for (String key : nicknameMap.keySet()) {
            if (key.equals(nickname)) {
                return false;
            }
        }

        return true;
    }


    public static void newConnection(Integer id, Connection connection) {
        synchronized (lock) {
            connectionMap.put(id, connection);
        }
    }


    public boolean newNicknameID(String nickname, Integer ID) {

        try {
            synchronized (lock) {
                nicknameMap.put(nickname, ID);
                nicknames.add(nickname);
            }
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }


    public String removeNicknameIDConnection(Integer ID) {

        String nickname = "";

        try{
            synchronized (lock) {
                for (String key : nicknameMap.keySet()) {
                    if (nicknameMap.get(key).equals(ID)) {

                        nickname = key;
                        nicknameMap.remove(key);

                        connectionMap.get(ID).closeConnection();
                        connectionMap.remove(ID);

                        nicknames.remove(nickname);
                        break;
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return nickname;
    }

    public void removeParticularNickname(String nickname){
        nicknames.remove(nickname);
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void addNickname(String nickname){
        nicknames.add(nickname);
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

        for (String nick : nicknames) {
            sendMessageTo(nick, event);
        }

        //USED ONLY FOR TESTING
        System.out.println(event.toString() + "\n");

    }


    public void sendMessageTo(Integer ID, ServerToClientEvent event) {

        synchronized (lock) {
            try {
                connectionMap.get(ID).sendEvent(event);
            } catch (NullPointerException e) {
                //USED ONLY FOR TESTING
                System.out.println("ID not found");
            }
        }


    }


    public void sendMessageTo(String nickname, ServerToClientEvent event) {
        try {
            synchronized (lock) {
                sendMessageTo(nicknameMap.get(nickname), event);
            }
        }
        catch (NullPointerException e) {
            //e.printStackTrace();
            //USED ONLY FOR TESTING
            System.out.println(event.toString() + "\n");
        }
    }


    // ======================================================================================


    public static Integer getIDFromNickname(String nickname) {
        return nicknameMap.get(nickname);
    }


    public static Connection getConnectionFromID(int ID){
        return connectionMap.get(ID);
    }


    public void setNicknames(List<String> connectedNicknames) {
        this.nicknames = connectedNicknames;
    }


    public Map<Integer, Connection> getConnectionMap() {
        return connectionMap;
    }


    //ONLY USED IN TESTING
    public static void clearVirtualView(){
        connectionMap.clear();
        nicknameMap.clear();
    }


}
