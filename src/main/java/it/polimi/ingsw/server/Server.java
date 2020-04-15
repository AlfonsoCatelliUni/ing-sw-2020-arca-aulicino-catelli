package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Server {

    private static final int SOCKET_PORT = 12345;


    private ServerSocket serverSocket;


    private ExecutorService executor;


    private List<Connection> connections;


    private Random randomGen;


    // ======================================================================================


    public Server() {

        this.executor = Executors.newFixedThreadPool(128);
        this.randomGen = new Random();

        this.connections = new ArrayList<>();

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

        System.out.println("Server listening on port : " + SOCKET_PORT);

        try {

            while (true) {

                /* non so se mettere la mappatura tra connessione e id numerico del player nel server o nel controller */
                //TODO : codice ripetuto per non chiarezza
                Socket socket = serverSocket.accept();
                System.out.println("Accepted new socket connection from " + socket.getRemoteSocketAddress());


                Integer id;
                do {
                    id = randomGen.nextInt(69420);
                } while(!VirtualView.isValidID(id));

                Connection connection = new Connection(id, socket);

                VirtualView.newTempConnection(id, connection);

                System.out.println("New socket bounded at the player with ID: " + id);

                //starts the new server proxy to receive the connection message
                //new Thread(connection).start();
                //TODO : controllare che questa riga sostituisca veramente quella sopra
                executor.submit(connection);

                //Sends the temporary id to the player
                //The player will send it back with all the necessary information (nickname)
                //TODO : mandare messaggio di assegnazione connectionID e richiesta del nickname
                //connection.sendEvent();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }




    }



}
