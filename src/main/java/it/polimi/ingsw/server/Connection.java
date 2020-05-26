package it.polimi.ingsw.server;

import it.polimi.ingsw.TheLogger;
import it.polimi.ingsw.events.CTSEvents.ClientDisconnectionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.*;
import java.net.Socket;
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
            TheLogger.LOGGER.log(Level.SEVERE, "Error while creating a new Connection object");
            //e.printStackTrace();
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
        catch(IOException | ClassNotFoundException e ) {
            System.err.println("The socket with ID : " + connectionID + " has been disconnected!");
            //e.printStackTrace();
        }
        finally {
            close();
        }


    }


    private void close() {
        closeConnection();
        receiver.update(new ClientDisconnectionEvent(connectionID));
        System.out.println("The socket of ID : " + connectionID + " is closed!");
    }


    public synchronized void closeConnection() {

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
            //e.printStackTrace();
        }

    }


    public synchronized Boolean getActive() {
        return this.active;
    }







}
