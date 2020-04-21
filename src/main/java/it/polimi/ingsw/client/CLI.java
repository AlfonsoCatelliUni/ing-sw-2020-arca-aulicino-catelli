package it.polimi.ingsw.client;

import it.polimi.ingsw.events.CTSEvents.ChosenPlayerNumberEvent;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.view.client.ClientView;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class CLI implements ServerToClientManager {


    private String ipAddress;
    private final int port;


    private final Scanner input;


    private ClientView clientView;


    // ======================================================================================


    private CLI() {
        this.ipAddress = "127.0.0.1";
        this.port = 6969;

        input = new Scanner(System.in);

    }


    // ======================================================================================


    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.run();
    }



    public void run() {

        System.out.print("Insert the server IP : ");
        ipAddress = input.nextLine();

        while(isValidIP()) {
            System.out.print("Invalid IP, reinsert a new one : ");
            ipAddress = input.nextLine();
        }

        Socket serverSocket = null;
        try {
            //Connects with the server through socket
            serverSocket = new Socket(ipAddress, port);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }

        //Creates a new RemoteViewSocket object which is used to keep the connection open and read all new messages
        assert serverSocket != null;
        ClientView clientSocket = new ClientView(serverSocket, this);

        this.clientView = clientSocket;

        new Thread(clientSocket).start();
        System.out.println("Connection established!");

    }


    /**
     * this method control the validity of the ip address
     * @return true if the ip address is valid, false in case the ip is invalid
     */
    public boolean isValidIP() {
        String[] groups = ipAddress.split("\\.");

        if (groups.length != 4)
            return false;

        try {
            return Arrays.stream(groups)
                    .filter(s -> s.length() > 1 && s.startsWith("0"))
                    .map(Integer::parseInt)
                    .filter(i -> (i >= 0 && i <= 255))
                    .count() == 4;
        } catch(NumberFormatException e) {
            return false;
        }
    }


    // ======================================================================================


    @Override
    public void receiveEvent(ServerToClientEvent event) {
        event.accept(this);
    }


    @Override
    public void manageEvent(ConnectionEstablishedEvent event) {

        System.out.print("Insert your nickname (max. 45 characters) : ");
        String nickname = input.nextLine();

        while(nickname.length() > 45) {
            System.out.print("I said less than 45 characters! Reinsert a shorter one : ");
            nickname = input.nextLine();
        }

        clientView.sendCTSEvent( new NewConnectionEvent(event.getID(),nickname));
    }



    @Override
    public void manageEvent(FirstConnectedEvent event) {

        System.out.print("Quanti giocatori deve avere la partita (te compreso) : 2 o 3 ?");
        int playersNumber = Integer.parseInt(input.nextLine());

        while(playersNumber != 2 && playersNumber != 3) {
            System.out.print("Only 2 or 3 players : ");
            playersNumber = Integer.parseInt(input.nextLine());
        }

        clientView.sendCTSEvent( new ChosenPlayerNumberEvent(playersNumber));
    }


    @Override
    public void manageEvent(SuccessfullyConnectedEvent event) {

    }


    @Override
    public void manageEvent(NotifyStatusEvent event) {

    }


    @Override
    public void manageEvent(DisconnectionEvent event) {

    }


    // ======================================================================================


    @Override
    public void manageEvent(AskNewNicknameEvent event) {

    }


    @Override
    public void manageEvent(UnavailableNicknameEvent event) {

    }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

    }


    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

    }


    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {

    }


    @Override
    public void manageEvent(GivePossibleCellsToBuildEvent event) {

    }


    @Override
    public void manageEvent(InvalidChosenActionEvent event) {

    }


    @Override
    public void manageEvent(InvalidChosenCellEvent event) {

    }


    @Override
    public void manageEvent(LosingByNoActionEvent event) {

    }





}
