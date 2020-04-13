package it.polimi.ingsw.server;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;

public class Connection implements Runnable{

    private Socket socket;

    //private Scanner in;
    //private PrintWriter out;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Server server;

    private String name;

    private Boolean active;


    // ======================================================================================


    public Connection(Socket socket, Server server) {

        this.active = true;

        this.socket = socket;
        this.server = server;

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

                //TODO : set the receiver
                ClientToServerEvent event = (ClientToServerEvent)input.readObject();
                //receiver.receiveAnswer(event);
            }

        }
        catch(IOException | ClassNotFoundException e ) {
            System.err.println("SOCKET exception: disconnecting " + name);
            e.printStackTrace();
        }
        finally {
            close();
        }


    }


    private void close() {
        //TODO : change system.out.print
        closeConnection();
        System.out.println("Deregistering client...");
        server.removeConnection(this);
        System.out.println("Done!");
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


    /**
     * Sends a message through this socket
     * @param event event
     */
    public void sendEvent(ServerToClientEvent event) {

        try {
            output.writeObject(event);
            output.flush();
        } catch (IOException e) {
            System.err.println("SOCKET exception: disconnecting " + name);
            e.printStackTrace();
        }

    }


    public synchronized Boolean getActive() {
        return this.active;
    }





}
