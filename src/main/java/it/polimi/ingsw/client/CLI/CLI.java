package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.ClientJsonHandler;
import it.polimi.ingsw.client.FormattedPlayerInfo;
import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.view.client.ClientView;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class CLI implements ServerToClientManager {


    private final String nicknamePattern = "^[aA-zZ]\\w{5,29}$";

    private String ipAddress;
    private final int port;


    private final Scanner input;

    private final GraphicDrawerCLI drawer;


    private ClientView clientView;


    private String nickname;

    private int rowUsedPawn;

    private int columnUsedPawn;

    private int nextActionRow;

    private int nextActionColumn;

    // ======================================================================================


    private CLI() {
        this.ipAddress = "127.0.0.1";
        this.port = 6969;

        this.input = new Scanner(System.in);
        this.drawer = new GraphicDrawerCLI();

        this.nickname = "";

        this.rowUsedPawn = -1;

        this.columnUsedPawn = -1;

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

        System.out.print("Insert your nickname (min. 6 chars, max. 30 chars, only letters, numbers and _ ) : ");
        String nickname = input.nextLine();

        if (nickname.equals("exit"))
            clientView.sendCTSEvent(new ClientDisconnectionEvent(event.ID));

        else {
            while (!(Pattern.matches(nicknamePattern, nickname))) {
                System.out.print("Invalid nickname! Reinsert a new one (min. 6 chars, max. 30 chars, only letters, numbers and _ ) : ");
                nickname = input.nextLine();
            }

            clientView.sendCTSEvent(new NewConnectionEvent(event.ID, nickname));

        }

    }


    //TODO : no eccezione ip sbalgiato
    //TODO : no eccezione carta sbagliata
    //TODO : riguardare print you can't place pawn here
    @Override
    public void manageEvent(FirstConnectedEvent event) {

        this.nickname = event.nickname;

        System.out.print("Do you want a 2 or 3 players game? ");
        int playersNumber = Integer.parseInt(input.nextLine());

        if (playersNumber == 4)
            clientView.sendCTSEvent(new ClientDisconnectionEvent(nickname));


        else {
            while (playersNumber != 2 && playersNumber != 3) {
                System.out.print("Game is available only in 2 or 3 players, choose one of this options : ");
                playersNumber = Integer.parseInt(input.nextLine());
            }

            clientView.sendCTSEvent(new ChosenPlayerNumberEvent(nickname, playersNumber));
        }
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

        System.out.println("This nickname is already used, please insert a new nickname:");

        String nickname = input.nextLine();

        while( !(Pattern.matches(nicknamePattern, nickname)) ) {
            System.out.print("Invalid nickname! Reinsert a new one (min. 6 chars, max. 30 chars, only letters, numbers and _ ) : ");
            nickname = input.nextLine();
        }

        clientView.sendCTSEvent(new NewConnectionEvent(event.ID, nickname));


    }


    @Override
    public void manageEvent(NotifyStatusEvent event) {
        System.out.println(event.status);
    }

    @Override
    public void manageEvent(OneClientDisconnectedEvent event) {

        System.out.println(event.disconnected + " is DISCONNECTED");

        System.out.println("The players are now:");

        for (String nickname : event.playersInside ) {
            System.out.println(nickname);
        }

        }



    @Override
    public void manageEvent(DisconnectionClientEvent event) {
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
    public void manageEvent(AskInitPawnsEvent event) {

        List<Point> occupiedCells = event.info;

        if(!event.isValid)
            System.out.println("An error has occurred, please reinsert the positions");

        if(occupiedCells.size() != 0) {
            System.out.println("You can't place your pawns in this positions: ");
            for (Point occupiedCell : occupiedCells) {
                System.out.println("[" + occupiedCell.getX() + "," + occupiedCell.getY() + "]");
            }

            //male pawn choosing
            System.out.println("Choose the position of your male pawn");

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

                for (Point occupiedCell : occupiedCells) {
                    //occupied position
                    if (maleRowPosition == occupiedCell.getX() &&
                            maleColumnPosition == occupiedCell.getY()) {
                        isOccupied = true;
                        break;
                    }
                }

                if(isOccupied) {
                    System.out.println("This Position is already occupied");
                    System.out.println("Choose the position of your male pawn");

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
            System.out.println("Choose the position of your female pawn");

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

                for (Point occupiedCell : occupiedCells) {

                    if (femaleRowPosition == occupiedCell.getX() &&
                            femaleColumnPosition == occupiedCell.getY() ||
                            femaleRowPosition == maleRowPosition &&
                            femaleColumnPosition == maleColumnPosition) {
                        isOccupied = true;
                        break;
                    }
                }

                if(isOccupied) {
                    System.out.println("This Position is already occupied");
                    System.out.println("Choose the position of your female pawn");

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
            System.out.println("Choose the position of your male pawn");

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
            System.out.println("Choose the position of your female pawn");

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
                    System.out.println("Choose the position of your female pawn");

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

        if (!event.isValid) {
            System.out.println("An error has occurred, please reinsert the position");
        }

        List<Point> coordinatesAvailablePawns = event.info;


       /* initialized to 0 because I'm sure the size will be 1 or 2, never 0 because the player would lose */
       int rowPosition = 0;

       int columnPosition = 0;

       //the player can choose witch pawn can use
       if(coordinatesAvailablePawns.size() == 1) {

           System.out.println("You can do your action only with the pawn with coordinates: " +
                   "[" + coordinatesAvailablePawns.get(0).x + "," + coordinatesAvailablePawns.get(0).y + "]");

           rowPosition = coordinatesAvailablePawns.get(0).x;
           columnPosition = coordinatesAvailablePawns.get(0).y;

       }
       else if(coordinatesAvailablePawns.size() == 2) {

           System.out.println("Choose the coordinates of the pawn you want to move: ");

           for (Point coordinatesAvailablePawn : coordinatesAvailablePawns) {

               System.out.println("[" + coordinatesAvailablePawn.x + "," + coordinatesAvailablePawn.y + "]\n");

           }

           boolean isCorrect;

           do {

               isCorrect = false;

               System.out.println("Row position: ");
               rowPosition = input.nextInt();

               while (rowPosition < 0 || rowPosition > 4) {

                   System.out.println("Invalid position");
                   System.out.println("Row Position:");
                   rowPosition = input.nextInt();
               }

               System.out.println("Column position: ");
               columnPosition = input.nextInt();

               while (columnPosition < 0 || columnPosition > 4) {

                   System.out.println("Invalid position");
                   System.out.println("Column Position:");
                   columnPosition = input.nextInt();
               }

               for (Point coordinatesAvailablePawn : coordinatesAvailablePawns) {

                   if (rowPosition == coordinatesAvailablePawn.x &&
                           columnPosition == coordinatesAvailablePawn.y) {

                       isCorrect = true;
                       break;

                   }

               }

               if(!isCorrect) {
                   System.out.println("None of your pawns are in this cell, please reinsert the coordinates");
               }

           } while(!isCorrect);


       }

       this.rowUsedPawn = rowPosition;

       this.columnUsedPawn = columnPosition;

       clientView.sendCTSEvent(new ChosenPawnToUseEvent(nickname, rowPosition, columnPosition));

   }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

        if(!event.isValid) {
            System.out.println("An error has occurred, please reinsert the positions");

        }

        int choiceNum;

        List<String> cardsName = event.cardsName;
        List<String> cardsEffect = event.cardsEffect;


        for (int i = 0; i < cardsName.size(); i++) {
                System.out.println(i + ")\tGod Name : " + cardsName.get(i) + "\n\tGod Effect : " + cardsEffect.get(i));
            }

        System.out.print("\nInsert the number to choose your God : ");
        choiceNum = Integer.parseInt(input.nextLine());

        while (choiceNum >= cardsName.size() || choiceNum < 0) {
            System.out.println("Invalid choice!");
            System.out.print("Insert the number to choose your God : ");
            choiceNum = Integer.parseInt(input.nextLine());
        }

        clientView.sendCTSEvent(new ChosenCardEvent(nickname, cardsName.get(choiceNum)));

        System.out.println("Please wait until it's your turn");

    }


    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

        List<String> possibleActions = event.actions;
        boolean isEventValid = event.isValid;

        int indexChosenAction = 0;

        //if there are more then one single possible actions
        //then I let the user to choose
        if(possibleActions.size() > 1) {

            if(!isEventValid) {
                System.out.println("Apparently there was an error! Reselect.");
            }

            do {
                System.out.println("Choose your next action :");
                for (int index = 0; index < possibleActions.size(); index++) {
                    System.out.println("[" + index + "]" + "\t" + possibleActions.get(index) + "\n");
                }

                indexChosenAction = input.nextInt();
                if( !(indexChosenAction >= 0 && indexChosenAction < possibleActions.size()) ) {
                    System.err.println("Action Unavailable !");
                }

            } while( !(indexChosenAction >= 0 && indexChosenAction < possibleActions.size()) );

        }

        System.out.println("Puoi solo fare  : " + possibleActions.get(indexChosenAction));
        //in case there is only one possible action I directly send the possible aciton
        //indexChosenAction is initialized to 0 so automatically takes the first and only possible action
        switch (possibleActions.get(indexChosenAction)) {

            case "Move":
                clientView.sendCTSEvent(new ChosenMoveActionEvent(nickname, "Move", rowUsedPawn, columnUsedPawn));
                break;

            case "Build":
                clientView.sendCTSEvent(new ChosenBuildActionEvent(nickname, "Build", rowUsedPawn, columnUsedPawn));
                break;

            case "End turn":
                clientView.sendCTSEvent(new ChosenFinishActionEvent(nickname, "End turn"));
                break;

            case "Destroy":
                clientView.sendCTSEvent(new ChosenDestroyActionEvent(nickname, "Destroy", rowUsedPawn, columnUsedPawn));
                break;

            case "Force":
                clientView.sendCTSEvent(new ChosenForceActionEvent(nickname, "Force", rowUsedPawn, columnUsedPawn));
                break;

            default:
                throw new RuntimeException("Error while selecting the next action !");

            }



    }


    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        List<Point> cellsAvailableToMove = event.cellsAvailableToMove;
        String actionID = event.actionID;
        boolean isEventValid = event.isValid;
        int selectedCell = 0;

        if(cellsAvailableToMove.size() > 1) {

            if(!isEventValid) {
                System.out.println("Apparently there was an error! Reselect.");
            }

            do {
                System.out.println("Choose the cell where you want to move :");
                for(int c = 0; c < cellsAvailableToMove.size(); c++) {
                    System.out.println("["+String.valueOf(c)+"]\t"+String.valueOf(cellsAvailableToMove.get(c).x)+" - "+String.valueOf(cellsAvailableToMove.get(c).y) + "\n");
                }

                selectedCell = input.nextInt();
                if( !(selectedCell >= 0 && selectedCell < cellsAvailableToMove.size()) ) {
                    System.err.println("Unavailable Choice !");
                }


            } while( !(selectedCell >= 0 && selectedCell < cellsAvailableToMove.size()) );

        }

        nextActionRow = cellsAvailableToMove.get(selectedCell).x;
        nextActionColumn = cellsAvailableToMove.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToMoveEvent(nickname, rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));

        rowUsedPawn = cellsAvailableToMove.get(selectedCell).x;
        columnUsedPawn = cellsAvailableToMove.get(selectedCell).y;
    }


    @Override
    public void manageEvent(GivePossibleCellsToBuildEvent event) {

        List<Point> cellsAvailableToBuild = event.cellsAvailableToBuild;
        String actionID = event.actionID;
        boolean isEventValid = event.isValid;
        int selectedCell = 0;

        System.out.println("evento ricevuto Build!");
        //control if there's only one single cell available to build
        //if there are more cells I let the user choose
        if(cellsAvailableToBuild.size() > 1) {

            if (!isEventValid) {
                System.out.println("Apparently there was an error! Reselect.");
            }

            do {
                System.out.println("Choose the cell where you want to build :");
                for (int c = 0; c < cellsAvailableToBuild.size(); c++) {
                    System.out.println("[" + String.valueOf(c) + "]\t" + String.valueOf(cellsAvailableToBuild.get(c).x) + " - " + String.valueOf(cellsAvailableToBuild.get(c).y) + "\n");
                }

                selectedCell = input.nextInt();
                if (!(selectedCell >= 0 && selectedCell < cellsAvailableToBuild.size())) {
                    System.err.println("Unavailable Choice !");
                }


            } while (!(selectedCell >= 0 && selectedCell < cellsAvailableToBuild.size()));
        }

        nextActionRow = cellsAvailableToBuild.get(selectedCell).x;
        nextActionColumn = cellsAvailableToBuild.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToBuildEvent(nickname, rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));

    }


    @Override
    public void manageEvent(GivePossibleBuildingsEvent event) {

        List<Integer> buildingsLevel = event.buildings;
        boolean isEventValid = event.isValid;
        int selectedLevel;

        //if there are more than one single options then I
        //have to display them and give the possibility to choose
        if(buildingsLevel.size() > 1) {

            if(!isEventValid) {
                System.out.println("Apparently there was an error! Reselect.");
            }

            do {

                System.out.println("Choose the building to build on the selected cell :");
                for(int b = 0; b < buildingsLevel.size(); b++) {
                    System.out.println("["+String.valueOf(b)+"]\t"+String.valueOf(buildingsLevel.get(b))+"\n");
                }

                selectedLevel = input.nextInt();
                if( !(selectedLevel >= 0 && selectedLevel < buildingsLevel.size()) ) {
                    System.err.println("Unavailable Choice !");
                }

            } while( !(selectedLevel >= 0 && selectedLevel < buildingsLevel.size()) );

            //MARK : QUA HO USATO GLI ATTRIBUTI CHE TI DICEVO, PERCHE L'EVENTO GivePossibleBuildingsEvent NON HA DENTRO LA CELLA DOVE DEVE COSTRUIRE QUINDI ME LA SALVO QUANDO LA SELEZIONO NEL manageEvent QUA SOPRA
            clientView.sendCTSEvent(new ChosenBuildingEvent(nickname, buildingsLevel.get(selectedLevel), rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));

        }
        //in case there is only one option I automatically select
        //that for the player
        else {
            clientView.sendCTSEvent(new ChosenBuildingEvent(nickname, buildingsLevel.get(0), rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));
        }

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


    @Override
    public void manageEvent(EndGameSTCEvent event) {

    }


}
