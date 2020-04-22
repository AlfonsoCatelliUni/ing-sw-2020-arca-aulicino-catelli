package it.polimi.ingsw.server;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.logging.Level;

public class Connection implements Runnable {

    private Socket socket;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Integer connectionID;

    private Boolean active;

    private VirtualView receiver;


    // ======================================================================================


    public Connection(Integer connectionID, Socket socket, VirtualView receiver) {
        this.active = true;

        this.connectionID = connectionID;
        this.socket = socket;
        this.receiver = receiver;

        try {
            input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    // ======================================================================================


    @Override
    public void run() {

        try {

            while(getActive()) {
                ClientToServerEvent event = (ClientToServerEvent)input.readObject();
                receiver.update(event);
            }

        }
        catch (EOFException e) {
            System.err.println("The socket with ID : " + connectionID + " has been disconnected!");
        }
        catch(IOException | ClassNotFoundException e ) {
            System.err.println("SOCKET exception: disconnecting " + connectionID);
            e.printStackTrace();
        }
        finally {
            close();
        }


    }


    private void close() {
        //TODO : change system.out.print
        closeConnection();
        System.out.println("The socket of ID : " + connectionID + " is closed!");
    }


    public synchronized void closeConnection() {

        //TODO : sendEvent( DisconnectionEvent )
        //send("Connection closed from the server side");

        try {
            socket.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        this.active = false;
    }


    public void sendEvent(ServerToClientEvent event) {

        try {
            output.writeObject(event);
            output.flush();
        }
        catch (IOException e) {
            System.err.println("SOCKET exception: disconnecting " + connectionID);
            e.printStackTrace();
        }

    }


    public synchronized Boolean getActive() {
        return this.active;
    }







}
