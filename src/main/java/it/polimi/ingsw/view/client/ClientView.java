package it.polimi.ingsw.view.client;


import it.polimi.ingsw.events.STCEvents.DisconnectionEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import java.io.*;
import java.net.Socket;

public class ClientView implements Runnable {


    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private final ServerToClientManager userManager;


    // ======================================================================================


    public ClientView(Socket socket, ServerToClientManager userManager) {

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

            while(true) {
                ServerToClientEvent event = (ServerToClientEvent)inputStream.readObject();

                userManager.receiveEvent(event);
            }

        }
        catch (Exception e){
            System.err.println("Error while receiving new Question object through SOCKET");
            e.printStackTrace();
            userManager.receiveEvent(new DisconnectionEvent());
        }

    }


    public void sendCTSEvent(ClientToServerEvent event) {

        try {
            outputStream.writeObject(event);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error while trying writeObject on client side");
            e.printStackTrace();
            userManager.receiveEvent(new DisconnectionEvent());
        }

    }


}
