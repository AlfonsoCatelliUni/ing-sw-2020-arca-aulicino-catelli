package it.polimi.ingsw.view.client;


import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientView implements Runnable{

    private final ObjectInputStream inputStream;

    private final ObjectOutputStream outputStream;

    private final ServerToClientManager userManager;

    //istanziata nella start connection della cli, viene creato un thread per ogni giocatore
    public ClientView(Socket socket, ServerToClientManager userManager) {

        this.userManager = userManager;

        ObjectInputStream tempIn = null;
        ObjectOutputStream tempOut = null;

        try {
            tempOut = new ObjectOutputStream(socket.getOutputStream());
            tempIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch (IOException e){
            System.err.println("Error while creating RemoteViewSocket");
        }

        this.inputStream = tempIn;
        this.outputStream = tempOut;
    }

    @Override
    public void run() {

        try {

            while (true) {

                ServerToClientEvent event = (ServerToClientEvent) inputStream.readObject();

                userManager.receiveEvent(event);
            }

        }
        catch (IOException | ClassNotFoundException e){
            System.err.println("Error while receiving new Question object through SOCKET");
            e.printStackTrace();
            //viene disconnesso il client
        }

    }

    public void sendCTSEvent(ClientToServerEvent event){

        try {
            outputStream.writeObject(event);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error while trying writeObject on client side");
            e.printStackTrace();
            //viene disconnesso il client
        }

    }

}
