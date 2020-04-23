package it.polimi.ingsw.client;

import it.polimi.ingsw.events.CTSEvents.ChosenCardEvent;
import it.polimi.ingsw.events.CTSEvents.ChosenInitialPawnCellEvent;
import it.polimi.ingsw.events.CTSEvents.ChosenPlayerNumberEvent;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.view.client.ClientView;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI implements ServerToClientManager {


    private String ipAddress;
    private final int port;

    private final Scanner input;


    private ClientView clientView;

    private String nickname;


    // ======================================================================================


    private CLI() {
        this.ipAddress = "127.0.0.1";
        this.port = 6969;

        this.input = new Scanner(System.in);

        this.nickname = "";

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

        clientView.sendCTSEvent( new NewConnectionEvent(event.ID, nickname));

    }


    @Override
    public void manageEvent(FirstConnectedEvent event) {

        this.nickname = event.nickname;

        System.out.print("Do you want a 2 or 3 players game ? ");
        int playersNumber = Integer.parseInt(input.nextLine());

        if(playersNumber == 420) {
            System.out.println("nice try, but shady things are done in the back room!");
            System.out.println(
                    "      #~~\n" +
                    "     )#(\n" +
                    "    ( # )\n" +
                    "     ___\n" +
                    "    |   |\n" +
                    "    |   |\n" +
                    "    |   |\n" +
                    "    |   |\n" +
                    "    |   |\n" +
                    "___ |   |\n" +
                    "\\  \\|   |\n" +
                    " \\  |   |\n" +
                    " /-------\\\n" +
                    "(_________)\n" +
                    " \\_______/");
        }

        while(playersNumber != 2 && playersNumber != 3) {
            System.out.print("Game is available only in 2 or 3 players, choose one of this options : ");
            playersNumber = Integer.parseInt(input.nextLine());
        }

        clientView.sendCTSEvent( new ChosenPlayerNumberEvent(nickname, playersNumber));
    }


    @Override
    public void manageEvent(SuccessfullyConnectedEvent event) {

        this.nickname = event.nickname;

        for (String nickname : event.connectedPlayers ) {
            System.out.println(nickname);
        }

    }


    @Override
    public void manageEvent(UnavailableNicknameEvent event) {

    }


    @Override
    public void manageEvent(NotifyStatusEvent event) {

    }


    @Override
    public void manageEvent(DisconnectionEvent event) {
        System.out.println("YOU HAVE BEEN DISCONNECTED!");
        this.clientView = null;
        System.exit(0);
    }


    @Override
    public void manageEvent(UnableToEnterWaitingRoomEvent event) {
        System.out.println("---THE WAITING ROOM IS FILLED!---");
        System.out.println();
        System.out.println("---YOU HAVE BEEN DISCONNECTED!---");
        this.clientView = null;
        System.exit(0);
    }



    // ======================================================================================


    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {
        System.out.println("THE WAITING ROOM IS NOW CLOSED!");
    }


    @Override
    public void manageEvent(AskNewNicknameEvent event) {

    }


    @Override
    public void manageEvent(AskInitPawnsEvent event) {

        List<Couple<Integer, Integer>> occupiedCells = ClientJsonHandler.generateCellsList(event.info);

        if(occupiedCells.size() != 0) {
            System.out.println("You can't place your pawns in this positions: ");
            for (Couple<Integer, Integer> occupiedCell : occupiedCells) {
                System.out.println("[" + occupiedCell.getFirst() + "]" + " " + "[" + occupiedCell.getSecond() + "]");
            }
        }

        System.out.println("Chose the position of your male pawn");

        System.out.println("Row Position:");
        int maleRowPosition = Integer.parseInt(input.nextLine());

        System.out.println("Column Position:");
        int maleColumnPosition = Integer.parseInt(input.nextLine());

        boolean correctPosition = false;

        while(!correctPosition) {

            for (Couple<Integer, Integer> occupiedCell : occupiedCells) {

                if (maleRowPosition != occupiedCell.getFirst() &&
                        maleColumnPosition != occupiedCell.getSecond()) {
                    correctPosition = true;
                    break;
                }
            }

            if(!correctPosition) {
                System.out.println("This Position is already occupied");
                System.out.println("Chose the position of your male pawn");

                System.out.println("Row Position:");
                maleRowPosition = Integer.parseInt(input.nextLine());

                System.out.println("Column Position:");
                maleColumnPosition = Integer.parseInt(input.nextLine());

            }

        }

        System.out.println("Chose the position of your female pawn");

        System.out.println("Row Position:");
        int femaleRowPosition = Integer.parseInt(input.nextLine());

        System.out.println("Column Position:");
        int femaleColumnPosition = Integer.parseInt(input.nextLine());

        correctPosition = false;

        while(!correctPosition) {

            for (Couple<Integer, Integer> occupiedCell : occupiedCells) {

                if (femaleRowPosition != occupiedCell.getFirst() &&
                        femaleColumnPosition != occupiedCell.getSecond() &&
                        femaleRowPosition != maleRowPosition &&
                        femaleColumnPosition != maleColumnPosition) {
                    correctPosition = true;
                    break;
                }
            }

            if(!correctPosition) {
                System.out.println("This Position is already occupied");
                System.out.println("Chose the position of your female pawn");

                System.out.println("Row Position:");
                femaleRowPosition = Integer.parseInt(input.nextLine());

                System.out.println("Column Position:");
                femaleColumnPosition = Integer.parseInt(input.nextLine());

            }

            clientView.sendCTSEvent(new ChosenInitialPawnCellEvent(nickname, maleRowPosition, maleColumnPosition, femaleRowPosition, femaleColumnPosition));

        }
    }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

        if(event.isValid) {
            int choiceNum;

            //System.out.println(event.receiverNickname);
            List<Couple<String, String>> cards = ClientJsonHandler.generateCardsList(event.info);

            for (int i = 0; i < cards.size(); i++) {
                System.out.println(i + ")\tGod Name : " + cards.get(i).getFirst() + "\n\tGod Effect : " + cards.get(i).getSecond());
            }

            System.out.print("\nInsert the number to choose your God : ");
            choiceNum = Integer.parseInt(input.nextLine());

            while (choiceNum > cards.size() || choiceNum < 0) {
                System.out.println("Invalid choice!");
                System.out.print("Insert the number to choose your God : ");
                choiceNum = Integer.parseInt(input.nextLine());
            }

            //TODO : da un errore OEFException
            clientView.sendCTSEvent(new ChosenCardEvent(nickname, cards.get(choiceNum).getFirst()));

        }
        else {
            System.out.println("non dovrebbe essere qui");
        }
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
    public void manageEvent(GivePossibleBuildingsEvent event) {

    }

    @Override
    public void manageEvent(StartGameEvent event) {

    }


    @Override
    public void manageEvent(LosingByNoActionEvent event) {

    }





}
