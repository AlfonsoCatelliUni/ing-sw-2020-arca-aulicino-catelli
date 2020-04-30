package it.polimi.ingsw.view.client;

import it.polimi.ingsw.events.STCEvents.DisconnectionClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import java.io.*;
import java.net.Socket;

public class ClientView implements Runnable {


    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private final Socket socket;

    private Boolean isActive;

    private final ServerToClientManager userManager;



    // ======================================================================================


    public ClientView(Socket socket, ServerToClientManager userManager) {
        this.isActive = true;

        this.socket = socket;
        this.userManager = userManager;

        ObjectInputStream tempIn = null;
        ObjectOutputStream tempOut = null;

        try {
            tempOut = new ObjectOutputStream(socket.getOutputStream());
            tempIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch (IOException e){
            System.err.println("Error while creating ClientView");
        }

        this.inputStream = tempIn;
        this.outputStream = tempOut;


    }


    // ======================================================================================


    @Override
    public void run() {

        try {

            while(getActive()) {
                ServerToClientEvent event = (ServerToClientEvent)inputStream.readObject();

                userManager.receiveEvent(event);
            }

        }
        catch (ClassNotFoundException e) {
            System.err.println("The class ServerToClientEvent has not been found!");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("There's an error while receiving a ServerToClientEvent, please fix me!");
            e.printStackTrace();
        }
        finally {
            close();
        }

    }


    public void sendCTSEvent(ClientToServerEvent event) {

        try {
            outputStream.writeObject(event);
            outputStream.flush();
        }
        catch (IOException e) {
            System.err.println("Error while trying writeObject on client side");
            e.printStackTrace();
        }

    }


    private void close() {
        closeConnection();
        userManager.receiveEvent(new DisconnectionClientEvent());
    }


    public synchronized void closeConnection() {

        try {
            socket.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        this.isActive = false;
    }


    public synchronized Boolean getActive() {
        return this.isActive;
    }


    public synchronized void setActive(Boolean isActive) {
        this.isActive = isActive;
    }


}
