package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.view.client.ClientView;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class CLI implements Client, ServerToClientManager {

    private final String RESET = "\u001B[0m";
    private final String RED_C = "\u001b[38;5;196m";

    private String ipAddress;
    private int port;

    private final String nicknamePattern = "^[aA-zZ]\\w{5,29}$";


    private final Scanner input;

    private final GraphicDrawerCLI drawer;

    private ClientView clientView;


    private List<FormattedPlayerInfo> playersInfo;


    private String nickname;

    private int rowUsedPawn;

    private int columnUsedPawn;

    private int nextActionRow;

    private int nextActionColumn;


    // MARK : Constructor and Run ======================================================================================


    public CLI() {
        this.ipAddress = "127.0.0.1";

        this.input = new Scanner(System.in);
        this.drawer = new GraphicDrawerCLI();

        this.playersInfo = new ArrayList<>();

        this.nickname = "";

        this.rowUsedPawn = -1;
        this.columnUsedPawn = -1;

        this.nextActionRow = -1;
        this.nextActionColumn = -1;

    }


    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.run();
    }


    @Override
    public void run() {

        Boolean isConnected = false;
        Socket serverSocket = null;

        while( !isConnected ) {

            System.out.print("Insert the server IP: ");
            ipAddress = input.nextLine();
            if(ipAddress.equals("")) {
                ipAddress = "127.0.0.1";
            }

            while( !isValidIP(this.ipAddress) ) {
                System.out.print("Invalid IP, reinsert a new one: ");
                ipAddress = input.nextLine();
            }


            do {
                System.out.print("Insert the port number: ");

                try {
                    port = Integer.parseInt( input.nextLine() );
                } catch (NumberFormatException e) {
                    port = -1;
                }

                // control if the choice is available
                if( !(port >= 49152 && port < 65535) ) {
                    System.out.println("Unavailable Choice!");
                }

            } while( !(port >= 49152 && port < 65535) );



            try {
                //Connects with the server through socket
                serverSocket = new Socket();
                serverSocket.connect( new InetSocketAddress(ipAddress, port), 5000);
                isConnected = true;
            }
            catch (IOException e ) {
                System.out.println( RED_C + "Connection Timeout!" + RESET);
                isConnected = false;
            }

        }

        //Creates a new RemoteViewSocket object which is used to keep the connection open and read all new messages
        ClientView clientSocket = new ClientView(serverSocket, this);

        this.clientView = clientSocket;

        new Thread(clientSocket).start();
        System.out.println("Connection established!");

    }


    // MARK : Network Event Managers ======================================================================================


    @Override
    public void receiveEvent(ServerToClientEvent event) {
        event.accept(this);
    }


    @Override
    public void manageEvent(ConnectionEstablishedEvent event) {

        System.out.println("Insert your nickname (min. 6 chars, max. 30 chars, only letters, numbers and _ ): ");
        String nickname = input.nextLine();

        while( !(Pattern.matches(nicknamePattern, nickname)) ) {
            System.err.println("Invalid Nickname!");
            System.out.println("Insert a new one (min. 6 chars, max. 30 chars, only letters, numbers and _ ): ");
            nickname = input.nextLine();
        }

        clientView.sendCTSEvent(new NewConnectionEvent(event.ID, nickname));

    }


    @Override
    public void manageEvent(FirstConnectedEvent event) {

        this.nickname = event.nickname;
        int selectedNumberOfPlayer = 0;

        do {

            System.out.println("Do you want a 2 or 3 players game? ");

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number!");
                input.next();

                System.out.println("Do you want a 2 or 3 players game? ");
            }
            selectedNumberOfPlayer = input.nextInt();

        } while( !(selectedNumberOfPlayer == 2 || selectedNumberOfPlayer == 3) );

        System.out.println("Please wait until the lobby is full...");


        clientView.sendCTSEvent( new ChosenPlayerNumberEvent(nickname, selectedNumberOfPlayer));
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

        System.out.println("This nickname is already used, please insert a new nickname (min. 6 chars, max. 30 chars, only letters, numbers and _ ):");

        String nickname = input.nextLine();

        while( !(Pattern.matches(nicknamePattern, nickname)) ) {
            System.err.println("Invalid Nickname!");
            System.out.print("Insert a new one (min. 6 chars, max. 30 chars, only letters, numbers and _ ): ");
            nickname = input.nextLine();
        }

        clientView.sendCTSEvent(new NewConnectionEvent(event.ID, nickname));


    }


    @Override
    public void manageEvent(DisconnectionClientEvent event) {
        System.out.println("---YOU HAVE BEEN DISCONNECTED!---");
        this.clientView = null;
        System.exit(0);
    }


    @Override
    public void manageEvent(OneClientDisconnectedEvent event) {
        System.out.println(event.disconnected + " is disconnected");

        System.out.println("Players in lobby are now: ");
        for (String p : event.connectedPlayers){
            System.out.println(p);
        }

    }


    @Override
    public void manageEvent(UnableToEnterWaitingRoomEvent event) {
        System.out.println("---THE WAITING ROOM IS FILLED!---");
        System.out.println();
        System.out.println("---YOU HAVE BEEN DISCONNECTED!---");
        this.clientView = null;
        System.exit(0);
    }


    @Override
    public void manageEvent(RoomNotFilled event) {
        System.out.println(event.message);
        System.out.println();
        manageEvent(new DisconnectionClientEvent());
    }


    @Override
    public void manageEvent(PlainTextEvent event) {
        System.out.println(event.message);
    }


    // MARK : Game Event Managers ======================================================================================


    @Override
    public void manageEvent(NotifyStatusEvent event) {

        List<FormattedCellInfo> cellsInfoList = JsonHandler.generateCellsList(event.status);
        drawer.saveBoardChanges(cellsInfoList);

        drawer.show();
    }


    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {
        System.out.println("THE WAITING ROOM IS NOW CLOSED!");
        System.out.println("Players in game are:");

        for (String nickname : event.connectedPlayers){
            System.out.println(nickname);
        }


    }


    @Override
    public void manageEvent(AskInitPawnsEvent event) {


        List<Point> occupiedCells = event.info;
        List<Point> freeCells = new ArrayList<>();
        int selectedMale = -1;
        int selectedFemale = -1;

        if(!event.isValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Point np = new Point(i, j);
                if(!occupiedCells.contains(np)) {
                    freeCells.add(np);
                }
            }
        }


        drawer.saveTitleChoicePanel("select the cell for the male pawn");
        drawer.saveCellsChoicesValue(freeCells);

        selectedMale = userChoice( freeCells.size() );

        int maleRowPosition = freeCells.get(selectedMale).x;
        int maleColumnPosition = freeCells.get(selectedMale).y;
        freeCells.remove(selectedMale);

        String color = "";
        for (FormattedPlayerInfo info : playersInfo ) {
            if(info.getNickname().equals(nickname)) {
                color = info.getColor();
            }
        }
        List<FormattedCellInfo> cellInfoList = new ArrayList<>();
        cellInfoList.add(new FormattedCellInfo(maleRowPosition, maleColumnPosition, 0, color.toUpperCase(), "MALE", 0, false));
        drawer.saveBoardChanges(cellInfoList);


        drawer.saveTitleChoicePanel("select the cell for the female pawn");
        drawer.saveCellsChoicesValue(freeCells);

        selectedFemale = userChoice( freeCells.size() );

        int femaleRowPosition = freeCells.get(selectedFemale).x;
        int femaleColumnPosition = freeCells.get(selectedFemale).y;

        clientView.sendCTSEvent(new ChosenInitialPawnCellEvent(nickname, maleRowPosition, maleColumnPosition, femaleRowPosition, femaleColumnPosition));

        drawer.saveTitleChoicePanel("-- WAIT UNTIL YOUR NEXT TURN --");
        drawer.clearChoicePanelValues();

        drawer.show();
    }


    @Override
    public void manageEvent(AskWhichPawnsUseEvent event) {

        List<Point> availablePawns = event.info;
        int selectedPawn = -1;

        if (!event.isValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        drawer.saveTitleChoicePanel("select the pawn that you want to use in this turn");
        drawer.saveCellsChoicesValue(availablePawns);

        selectedPawn = userChoice( availablePawns.size() );

        this.rowUsedPawn = availablePawns.get(selectedPawn).x;
        this.columnUsedPawn = availablePawns.get(selectedPawn).y;

       clientView.sendCTSEvent(new ChosenPawnToUseEvent(nickname, rowUsedPawn, columnUsedPawn));

       //TODO : forse questo titolo non è necessario
       drawer.saveTitleChoicePanel("-- WAIT UNTIL YOUR NEXT TURN --");
       drawer.clearChoicePanelValues();

       drawer.show();

   }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

        List<String> cardsName = event.cardsName;
        List<String> cardsEffect = event.cardsEffect;
        int choiceNum = -1;

        if(!event.isValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        drawer.saveTitlePlayerPanel("choose your card");
        for (int i = 0; i < cardsName.size(); i++) {
            drawer.savePlayerCardChoice(cardsName, cardsEffect);
        }

        choiceNum = userChoice( cardsName.size() );

        clientView.sendCTSEvent(new ChosenCardEvent(nickname, cardsName.get(choiceNum)));

        //show only your information in Players Panel into the CLI graphic
        this.playersInfo = new ArrayList<>();
        this.playersInfo.add( new FormattedPlayerInfo(nickname, "", Couple.create(cardsName.get(choiceNum), cardsEffect.get(choiceNum))) );

        drawer.saveTitlePlayerPanel("players information");
        drawer.saveInfoPlayerPanel(playersInfo);

        drawer.show();
    }


    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

        List<String> possibleActions = event.actions;
        boolean isEventValid = event.isValid;
        int indexChosenAction = -1;

        if(!isEventValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        drawer.saveTitleChoicePanel("choose your next action");
        drawer.saveActionsChoicesValue(possibleActions);

        indexChosenAction = userChoice( possibleActions.size() );
//        do {
//            drawer.show();
//
//            while(!input.hasNextInt()) {
//                System.err.println("Insert a Number!");
//                drawer.show();
//
//                input.next();
//            }
//            indexChosenAction = input.nextInt();
//
//            if( !(indexChosenAction >= 0 && indexChosenAction < possibleActions.size()) ) {
//                System.err.println("Choice Unavailable!");
//            }
//
//        } while( !(indexChosenAction >= 0 && indexChosenAction < possibleActions.size()) );


        //in case there is only one possible action I directly send the possible action
        //indexChosenAction is initialized to 0 so automatically takes the first and only possible action

        switch ( possibleActions.get(indexChosenAction) ) {

            case "Move":
                clientView.sendCTSEvent(new ChosenMoveActionEvent(nickname, "Move", rowUsedPawn, columnUsedPawn));
                break;

            case "Build":
                clientView.sendCTSEvent(new ChosenBuildActionEvent(nickname, "Build", rowUsedPawn, columnUsedPawn));
                break;

            case "Destroy":
                clientView.sendCTSEvent(new ChosenDestroyActionEvent(nickname, "Destroy", rowUsedPawn, columnUsedPawn));
                break;

            case "Force":
                clientView.sendCTSEvent(new ChosenForceActionEvent(nickname, "Force", rowUsedPawn, columnUsedPawn));
                break;

            case "End turn":
                clientView.sendCTSEvent(new ChosenFinishActionEvent(nickname, "End turn"));

                drawer.saveTitleChoicePanel("-- WAIT UNTIL YOUR NEXT TURN --");
                drawer.clearChoicePanelValues();

                drawer.show();
                break;

            default:
                throw new RuntimeException("Error while selecting the next action!");

        }

    }


    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        List<Point> cellsAvailableToMove = event.cellsAvailableToMove;
        boolean isEventValid = event.isValid;
        int selectedCell = -1;

        if(!isEventValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        drawer.saveTitleChoicePanel("choose the cell where you want to move");
        drawer.saveCellsChoicesValue(cellsAvailableToMove);

        selectedCell = userChoice( cellsAvailableToMove.size() );
//        do {
//            drawer.show();
//
//            while(!input.hasNextInt()) {
//                System.err.println("Insert an Number!");
//                drawer.show();
//
//                input.next();
//            }
//            selectedCell = input.nextInt();
//
//            if( !(selectedCell >= 0 && selectedCell < cellsAvailableToMove.size()) ) {
//                System.err.println("Unavailable Choice!");
//            }
//
//        } while( !(selectedCell >= 0 && selectedCell < cellsAvailableToMove.size()) );
        nextActionRow = cellsAvailableToMove.get(selectedCell).x;
        nextActionColumn = cellsAvailableToMove.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToMoveEvent(nickname, rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));

        /*TODO : io lo salvo anche se non so ancora se il server riceverà in modo corretto l'evento
        *  è possibile quindi che mi torni un GivePossibleCellsToMoveEvent con isValid=false e queste
        * coordinate sarebbero sbagliate non consentendo di eseguire correttamente la mossa*/
        //save the new position of the pawn
        rowUsedPawn = cellsAvailableToMove.get(selectedCell).x;
        columnUsedPawn = cellsAvailableToMove.get(selectedCell).y;
    }


    @Override
    public void manageEvent(GivePossibleCellsToBuildEvent event) {

        List<Point> cellsAvailableToBuild = event.cellsAvailableToBuild;
        boolean isEventValid = event.isValid;
        int selectedCell = 0;

        if(!isEventValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        drawer.saveTitleChoicePanel("choose the cell where you want to build");
        drawer.saveCellsChoicesValue(cellsAvailableToBuild);

        selectedCell = userChoice( cellsAvailableToBuild.size() );
//        do {
//            drawer.show();
//
//            while(!input.hasNextInt()) {
//                System.err.println("Insert an Number!");
//                drawer.show();
//
//                input.next();
//            }
//            selectedCell = input.nextInt();
//
//            if( !(selectedCell >= 0 && selectedCell < cellsAvailableToBuild.size()) ) {
//                System.err.println("Unavailable Choice!");
//            }
//
//        } while( !(selectedCell >= 0 && selectedCell < cellsAvailableToBuild.size()) );
        nextActionRow = cellsAvailableToBuild.get(selectedCell).x;
        nextActionColumn = cellsAvailableToBuild.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToBuildEvent(nickname, rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));

    }


    @Override
    public void manageEvent(GivePossibleBuildingsEvent event) {

        List<Integer> buildingsLevel = event.buildings;
        boolean isEventValid = event.isValid;
        int selectedLevel = 0;

        if(!isEventValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        //if there are more than one single options then I
        //have to display them and give the possibility to choose
        if(buildingsLevel.size() > 1) {

            drawer.saveTitleChoicePanel("Choose the building to build on the selected cell");
            drawer.saveBuildingsChoicesValue(buildingsLevel);

            selectedLevel = userChoice( buildingsLevel.size() );
//            do {
//                drawer.show();
//
//                while(!input.hasNextInt()) {
//                    System.err.println("Insert an Number!");
//                    drawer.show();
//
//                    input.next();
//                }
//                selectedLevel = input.nextInt();
//
//                if( !(selectedLevel >= 0 && selectedLevel < buildingsLevel.size()) ) {
//                    System.err.println("Unavailable Choice!");
//                }
//
//            } while( !(selectedLevel >= 0 && selectedLevel < buildingsLevel.size()) );
        }

        clientView.sendCTSEvent(new ChosenBuildingEvent(nickname, buildingsLevel.get(selectedLevel), rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));
    }


    @Override
    public void manageEvent(GivePossibleCellsToDestroyEvent event) {

        List<Point> availableCellsToDestroy = event.cells;
        int selectedCell = -1;

        if (!event.isValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        drawer.saveTitleChoicePanel("choose the cell where you want to destroy the roof");
        drawer.saveCellsChoicesValue(availableCellsToDestroy);

        selectedCell = userChoice( availableCellsToDestroy.size() );
//        do {
//            drawer.show();
//
//            while(!input.hasNextInt()) {
//                System.err.println("Insert an Number!");
//                drawer.show();
//
//                input.next();
//            }
//            selectedCell = input.nextInt();
//
//            if( !(selectedCell >= 0 && selectedCell < availableCellsToDestroy.size()) ) {
//                System.out.println("Unavailable choice");
//            }
//
//        } while( !(selectedCell >= 0 && selectedCell < availableCellsToDestroy.size()) );
        int selectedRowToDestroy = event.cells.get(selectedCell).x;
        int selectedColumnToDestroy = event.cells.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToDestroyEvent(nickname, rowUsedPawn, columnUsedPawn, selectedRowToDestroy, selectedColumnToDestroy));

    }


    @Override
    public void manageEvent(GivePossibleCellsToForceEvent event) {

        List<Point> availableCellsToForce = event.cells;
        int selectedCell = -1;

        if (!event.isValid) {
            System.err.println("Apparently there was an error! Reselect...");
        }

        drawer.saveTitleChoicePanel("choose the cell where you want to force an opponent pawn");
        drawer.saveCellsChoicesValue(availableCellsToForce);

        selectedCell = userChoice( availableCellsToForce.size() );
//        do {
//            drawer.show();
//
//            while(!input.hasNextInt()) {
//                System.err.println("Insert an Number!");
//                drawer.show();
//
//                input.next();
//            }
//            selectedCell = input.nextInt();
//
//            if( !(selectedCell >= 0 && selectedCell < availableCellsToForce.size()) ) {
//                System.out.println("Unavailable choice");
//            }
//
//        } while( !(selectedCell >= 0 && selectedCell < availableCellsToForce.size()) );
        int selectedRowForcedPawn = availableCellsToForce.get(selectedCell).x;
        int selectedColumnForcedPawn = availableCellsToForce.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToForceEvent(nickname, rowUsedPawn, columnUsedPawn, selectedRowForcedPawn, selectedColumnForcedPawn));

    }


    @Override
    public void manageEvent(StartGameEvent event) {

        playersInfo = ClientJsonHandler.generatePlayersList(event.info);

        drawer.saveTitlePlayerPanel("players information");
        drawer.saveInfoPlayerPanel(playersInfo);

        drawer.show();
    }


    @Override
    public void manageEvent(LosingByNoActionEvent event) {

        String loser = event.nickname;
        List<String> actions = new ArrayList<>();

        if( this.nickname.equals(loser) ) {

            drawer.saveTitleChoicePanel("----------------------- you have lost the game! -----------------------");

            List<String> actionsAfterLosing = event.actionsAfterLosing;
            drawer.saveActionsChoicesValue(actionsAfterLosing);

            int indexChosenAction = -1;


            do {
                drawer.show();

                while(!input.hasNextInt()) {
                    System.err.println("Insert a Number!");
                    drawer.show();

                    input.next();
                }
                indexChosenAction = input.nextInt();

                if( !(indexChosenAction >= 0 && indexChosenAction < actionsAfterLosing.size()) ) {
                    System.err.println("Choice Unavailable !");
                }

            } while( !(indexChosenAction >= 0 && indexChosenAction < actionsAfterLosing.size()) );


            switch (actionsAfterLosing.get(indexChosenAction)) {

                case "Spectate Match":
                    break;

                case "Leave":
                    manageEvent(new DisconnectionClientEvent());
                    break;

            }

            actions.add("If you want to play again you have to reconnect to the server!");
            actions.add("PayPal email for donations: alfonsocatelli@gmail.com");

            drawer.saveActionsChoicesValue(actions);

        }
        // this client hasn't lost the game
        else {
            playersInfo.removeIf(p -> p.getNickname().equals(loser));
            playersInfo.add(new FormattedPlayerInfo(loser, "LOSER", Couple.create("loser", "---------------------------------------------------------------------------------------")));

            drawer.saveInfoPlayerPanel(playersInfo);
        }

        drawer.show();
    }


    @Override
    public void manageEvent(EndGameSTCEvent event) {

        String winner = event.winner;
        List<String> actions = new ArrayList<>();

        if( this.nickname.equals(winner) ) {
            drawer.saveTitleChoicePanel("----------------------- you are the winneeeer! -----------------------");
        }
        else {
            drawer.saveTitleChoicePanel("----- " + winner + " is the winner of the match! ------");
        }

        actions.add("If you want to play again you have to reconnect to the server!");
        actions.add("paypal email for donations : alfonsocatelli@gmail.com");

        drawer.saveActionsChoicesValue(actions);
        drawer.show();

        manageEvent(new DisconnectionClientEvent());

    }


    // MARK : Support Methods ======================================================================================


    /**
     * this method is used to reduce the duplication of code in this class,
     * because when the user have to select his choice this class control that the number
     * is in the range that goes from 0 to the number of elements in the list of choices
     * so this method only need the size of the list.
     * @param choicesListSize the upper limit of the range, that correspond to the size of the choices list
     * @return the selected index
     */
    private int userChoice( int choicesListSize ) {

        int selected = -1;

        do {
            /* in the manageEvent we set the title and the choices
             * then in here we show them every time the user enter an
             * invalid choice */
            drawer.show();

            // control if the user insert an number
            while(!input.hasNextInt()) {
                System.err.println("Insert an Number!");
                drawer.show();

                input.next();
            }
            selected = input.nextInt();

            // control if the choice is availabel
            if( !(selected >= 0 && selected < choicesListSize) ) {
                System.err.println("Unavailable Choice!");
            }

        } while( !(selected >= 0 && selected < choicesListSize) );

        // if the user insert an available choice then we return to the manageEvent
        return selected;
    }


    /**
     * this method control the validity of the ip address
     * @return true if the ip address is valid, false in case the ip is invalid
     */
    private boolean isValidIP(String ip) {
        // code taken from : https://stackoverflow.com/Questions/5667371/validate-ipv4-address-in-java
        String IP_PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(IP_PATTERN);

    }


}
