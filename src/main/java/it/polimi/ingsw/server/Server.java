package it.polimi.ingsw.server;

import it.polimi.ingsw.TheLogger;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.STCEvents.ConnectionEstablishedEvent;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Server {

    private static final int SOCKET_PORT = 64209;


    private static Controller controller =  new Controller();


    private static List<Controller> controllerList = new ArrayList<>();


    private ServerSocket serverSocket;


    private ExecutorService executor;


    private List<Connection> connections;


    private Random randomGen;


    // ======================================================================================


    public Server() {

        this.executor = Executors.newFixedThreadPool(128);
        this.randomGen = new Random();

        controllerList.add(controller);

        this.connections = new ArrayList<>();

        try {
            this.serverSocket = new ServerSocket(SOCKET_PORT);
        }
        catch (IOException e) {
            TheLogger.LOGGER.log(Level.SEVERE, "Error while opening server socket");
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

        System.out.println("Server listening on port : " + SOCKET_PORT + "\n");

        try {

            while (true) {

                Socket socket = serverSocket.accept();

                if (controller.isClosed()) {
                    controller = new Controller();
                    controllerList.add(controller);

                    System.out.println("------------------------------");
                    System.out.println("NEW CONTROLLER CREATED !");
                    System.out.println("------------------------------");
                    System.out.println("All Connection with the Server:");

                    Map<Integer, Connection> hash = controller.getVirtualView().getConnectionMap();

                    for(Integer i : hash.keySet() ) {
                        System.out.println(String.valueOf(i) + " - " + hash.get(i) );
                    }

                    System.out.println("------------------------------\n");

                }

                System.out.println("Accepted new socket connection from " + socket.getRemoteSocketAddress());

                Integer id;
                do {
                    id = randomGen.nextInt(69420);
                } while(!VirtualView.isValidID(id));

                Connection connection = new Connection(id, socket, controller.getVirtualView());

                VirtualView.newConnection(id, connection);

                System.out.println("New socket bounded at the player with ID: " + id + "\n");

                executor.submit(connection);
                //new Thread(connection).start();

                connection.sendEvent(new ConnectionEstablishedEvent(id));
            }
        }
        catch(IOException e) {
            //TheLogger.LOGGER.log(Level.SEVERE, "Error while accepting new connections"  );
            //e.printStackTrace();
        }




    }



}
