package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int SOCKET_PORT = 12345;


    private ServerSocket serverSocket;


    private ExecutorService executor;


    private List<Connection> connections;


    private Map<String, Connection> waitingConnection;


//    private Map<Connection, Connection> playingConnection;


    // ======================================================================================


    public Server() {

        this.executor = Executors.newFixedThreadPool(128);

        this.connections = new ArrayList<>();
        this.waitingConnection = new HashMap<>();
//        this.playingConnection = new HashMap<>();

        try {
            this.serverSocket = new ServerSocket(SOCKET_PORT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    // ======================================================================================


    public static void main(String[] args) {

        // :)
        Server server = new Server();
        server.run();

    }


    public void run() {

        System.out.println("Server listening on port: " + SOCKET_PORT);

        while(true) {

            try {
                Socket socket = serverSocket.accept();

                Connection connection = new Connection(socket, this);

                registerConnection(connection);
                executor.submit(connection);

            } catch (IOException e){
                System.err.println("Connection error!");
            }

        }


    }


    public synchronized void registerConnection(Connection c) {
        connections.add(c);
    }


    public synchronized void removeConnection(Connection c) {
        connections.remove(c);

//        Connection opponent = playingConnection.get(c);

//        if(opponent != null) {
//            waitingConnection.keySet().removeIf(s -> waitingConnection.get(s) == c);
//        }

    }





}
