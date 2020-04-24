package it.polimi.ingsw.client;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Board.Cell;
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

        System.out.print("Do you want a 2 or 3 players game? ");
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

        System.out.println("The temporary players are: ");
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
    public void manageEvent(RoomNotFilled event) {
        System.out.println(event.message);
    }

    @Override
    public void manageEvent(AskNewNicknameEvent event) {

    }


    @Override
    public void manageEvent(AskInitPawnsEvent event) {

        List<Couple<Integer, Integer>> occupiedCells = ClientJsonHandler.generateCellsList(event.info);

        if(!event.isValid)
            System.out.println("An error has occurred, please reinsert the positions");

        if(occupiedCells.size() != 0) {
            System.out.println("You can't place your pawns in this positions: ");
            for (Couple<Integer, Integer> occupiedCell : occupiedCells) {
                System.out.println("[" + occupiedCell.getFirst() + "," + occupiedCell.getSecond() + "]");
            }

            //male pawn choosing
            System.out.println("Chose the position of your male pawn");

            System.out.println("Row Position:");
            int maleRowPosition = Integer.parseInt(input.nextLine());

            while( maleRowPosition < 0 || maleRowPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Row Position:");
                maleRowPosition = Integer.parseInt(input.nextLine());
            }

            System.out.println("Column Position:");
            int maleColumnPosition = Integer.parseInt(input.nextLine());

            while( maleColumnPosition < 0 || maleColumnPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Column Position:");
                maleColumnPosition = Integer.parseInt(input.nextLine());
            }

            boolean isOccupied;

            do {

                isOccupied = false;

                for (Couple<Integer, Integer> occupiedCell : occupiedCells) {
                    //occupied position
                    if (maleRowPosition == occupiedCell.getFirst() &&
                            maleColumnPosition == occupiedCell.getSecond()) {
                        isOccupied = true;
                        break;
                    }
                }

                if(isOccupied) {
                    System.out.println("This Position is already occupied");
                    System.out.println("Chose the position of your male pawn");

                    System.out.println("Row Position:");
                    maleRowPosition = Integer.parseInt(input.nextLine());

                    while( maleRowPosition < 0 || maleRowPosition > 4) {

                        System.out.println("Invalid position");
                        System.out.println("Row Position:");
                        maleRowPosition = Integer.parseInt(input.nextLine());
                    }

                    System.out.println("Column Position:");
                    maleColumnPosition = Integer.parseInt(input.nextLine());

                    while( maleColumnPosition < 0 || maleColumnPosition > 4) {

                        System.out.println("Invalid position");
                        System.out.println("Column Position:");
                        maleColumnPosition = Integer.parseInt(input.nextLine());
                    }

                }

            } while(isOccupied);

            //female pawn choosing
            System.out.println("Chose the position of your female pawn");

            System.out.println("Row Position:");
            int femaleRowPosition = Integer.parseInt(input.nextLine());

            while( femaleRowPosition < 0 || femaleRowPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Row Position:");
                femaleRowPosition = Integer.parseInt(input.nextLine());
            }

            System.out.println("Column Position:");
            int femaleColumnPosition = Integer.parseInt(input.nextLine());

            while( femaleColumnPosition < 0 || femaleColumnPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Column Position:");
                femaleColumnPosition = Integer.parseInt(input.nextLine());
            }

            do {

                isOccupied = false;

                for (Couple<Integer, Integer> occupiedCell : occupiedCells) {

                    if (femaleRowPosition == occupiedCell.getFirst() &&
                            femaleColumnPosition == occupiedCell.getSecond() ||
                            femaleRowPosition == maleRowPosition &&
                            femaleColumnPosition == maleColumnPosition) {
                        isOccupied = true;
                        break;
                    }
                }

                if(isOccupied) {
                    System.out.println("This Position is already occupied");
                    System.out.println("Chose the position of your female pawn");

                    System.out.println("Row Position:");
                    femaleRowPosition = Integer.parseInt(input.nextLine());

                    while( femaleRowPosition < 0 || femaleRowPosition > 4) {

                        System.out.println("Invalid position");
                        System.out.println("Row Position:");
                        femaleRowPosition = Integer.parseInt(input.nextLine());
                    }

                    System.out.println("Column Position:");
                    femaleColumnPosition = Integer.parseInt(input.nextLine());

                    while( femaleColumnPosition < 0 || femaleColumnPosition > 4) {

                        System.out.println("Invalid position");
                        System.out.println("Column Position:");
                        femaleColumnPosition = Integer.parseInt(input.nextLine());
                    }

                }

            } while (isOccupied);

            clientView.sendCTSEvent(new ChosenInitialPawnCellEvent(nickname, maleRowPosition, maleColumnPosition, femaleRowPosition, femaleColumnPosition));

            System.out.println("Please wait until it's your turn");


        }
        else {

            //male pawn choosing
            System.out.println("Chose the position of your male pawn");

            System.out.println("Row Position:");
            int maleRowPosition = Integer.parseInt(input.nextLine());

            while( maleRowPosition < 0 || maleRowPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Row Position:");
                maleRowPosition = Integer.parseInt(input.nextLine());
            }

            System.out.println("Column Position:");
            int maleColumnPosition = Integer.parseInt(input.nextLine());

            while( maleColumnPosition < 0 || maleColumnPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Column Position:");
                maleColumnPosition = Integer.parseInt(input.nextLine());
            }

            //female pawn choosing
            System.out.println("Chose the position of your female pawn");

            System.out.println("Row Position:");
            int femaleRowPosition = Integer.parseInt(input.nextLine());

            while( femaleRowPosition < 0 || femaleRowPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Row Position:");
                femaleRowPosition = Integer.parseInt(input.nextLine());
            }

            System.out.println("Column Position:");
            int femaleColumnPosition = Integer.parseInt(input.nextLine());

            while( femaleColumnPosition < 0 || femaleColumnPosition > 4) {

                System.out.println("Invalid position");
                System.out.println("Column Position:");
                femaleColumnPosition = Integer.parseInt(input.nextLine());
            }

            boolean isOccupied;

            do {

                isOccupied = femaleRowPosition == maleRowPosition &&
                        femaleColumnPosition == maleColumnPosition;

                if(isOccupied) {
                    System.out.println("This Position is already occupied");
                    System.out.println("Chose the position of your female pawn");

                    System.out.println("Row Position:");
                    femaleRowPosition = Integer.parseInt(input.nextLine());

                    while( femaleRowPosition < 0 || femaleRowPosition > 4) {

                        System.out.println("Invalid position");
                        System.out.println("Row Position:");
                        femaleRowPosition = Integer.parseInt(input.nextLine());
                    }

                    System.out.println("Column Position:");
                    femaleColumnPosition = Integer.parseInt(input.nextLine());

                    while( femaleColumnPosition < 0 || femaleColumnPosition > 4) {

                        System.out.println("Invalid position");
                        System.out.println("Column Position:");
                        femaleColumnPosition = Integer.parseInt(input.nextLine());
                    }

                }

            } while(isOccupied);

            clientView.sendCTSEvent(new ChosenInitialPawnCellEvent(nickname, maleRowPosition, maleColumnPosition, femaleRowPosition, femaleColumnPosition));

            System.out.println("Please wait until it's your turn");


        }


    }

    @Override
    public void manageEvent(AskWhichPawnsUseEvent event) {

        if(!event.isValid) {
            System.out.println("An error has occurred, please reinsert the position");
        }

        List<Couple<Integer, Integer>> coordinatesAvailablePawns = ClientJsonHandler.generateCellsList(event.info);


        /* initialized to 0 because I'm sure the size will be 1 or 2, never 0 because the player would lose */
        int rowPosition = 0;

        int columnPosition = 0;

        //the player can choose witch pawn can use
        if(coordinatesAvailablePawns.size() == 1) {

            System.out.println("You can do your action only with the pawn with coordinates: " +
                    "[" + coordinatesAvailablePawns.get(0).getFirst() + "," + coordinatesAvailablePawns.get(0).getSecond() + "]");

            rowPosition = coordinatesAvailablePawns.get(0).getFirst();
            columnPosition = coordinatesAvailablePawns.get(0).getSecond();

        }
        if(coordinatesAvailablePawns.size() == 2) {

            System.out.println("Choose the coordinates of the pawn you want to move: ");

            for (Couple<Integer, Integer> coordinatesAvailablePawn : coordinatesAvailablePawns) {

                System.out.println("[" + coordinatesAvailablePawn.getFirst() + "," + coordinatesAvailablePawn.getSecond() + "]\n");

            }

            boolean isCorrect;

            do {

                isCorrect = false;

                System.out.println("Row position: ");
                rowPosition = Integer.parseInt(input.nextLine());

                while (rowPosition < 0 || rowPosition > 4) {

                    System.out.println("Invalid position");
                    System.out.println("Row Position:");
                    rowPosition = Integer.parseInt(input.nextLine());
                }

                System.out.println("Column position: ");
                columnPosition = Integer.parseInt(input.nextLine());

                while (columnPosition < 0 || columnPosition > 4) {

                    System.out.println("Invalid position");
                    System.out.println("Column Position:");
                    columnPosition = Integer.parseInt(input.nextLine());
                }

                for (Couple<Integer, Integer> coordinatesAvailablePawn : coordinatesAvailablePawns) {

                    if (rowPosition == coordinatesAvailablePawn.getFirst() &&
                            columnPosition == coordinatesAvailablePawn.getSecond()) {

                        isCorrect = true;
                        break;

                    }

                }

                if(!isCorrect) {
                    System.out.println("None of your pawns are in this cell, please reinsert the coordinates");
                }

            } while(!isCorrect);


        }

        clientView.sendCTSEvent(new ChosenPawnToUseEvent(nickname, rowPosition, columnPosition));

    }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

        if(!event.isValid) {
            System.out.println("An error has occurred, please reinsert the positions");

        }

        int choiceNum;

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

        clientView.sendCTSEvent(new ChosenCardEvent(nickname, cards.get(choiceNum).getFirst()));

        System.out.println("Please wait until it's your turn");

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

        System.out.println("The game starts right now!");
        System.out.println("   _____             _             _       _ \n" +
                "  / ____|           | |           (_)     (_)\n" +
                " | (___   __ _ _ __ | |_ ___  _ __ _ _ __  _ \n" +
                "  \\___ \\ / _` | '_ \\| __/ _ \\| '__| | '_ \\| |\n" +
                "  ____) | (_| | | | | || (_) | |  | | | | | |\n" +
                " |_____/ \\__,_|_| |_|\\__\\___/|_|  |_|_| |_|_|\n" +
                "                                             \n" +
                "                                             ");

        // <nameCard, effectCard>
        List<FormattedPlayerInfo> playersInfo = ClientJsonHandler.generatePlayersList(event.info);

        System.out.println("The players for this game are:\n");

        for (FormattedPlayerInfo formattedPlayerInfo : playersInfo) {
            System.out.println(formattedPlayerInfo.getNickname() +
                    "\tColor: " + formattedPlayerInfo.getColor() +
                    "\tCard: " + formattedPlayerInfo.getCard().getFirst() +
                    "\tCard effect: " + formattedPlayerInfo.getCard().getSecond() +
                    "\n");
        }

    }


    @Override
    public void manageEvent(LosingByNoActionEvent event) {

    }


}
