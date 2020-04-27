package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.client.ClientJsonHandler;
import it.polimi.ingsw.client.FormattedCellInfo;
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



    private List<FormattedPlayerInfo> playersInfo;



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

        this.playersInfo = new ArrayList<>();

        this.nickname = "";

        this.rowUsedPawn = -1;
        this.columnUsedPawn = -1;

        this.nextActionRow = -1;
        this.nextActionColumn = -1;

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

        while( !(Pattern.matches(nicknamePattern, nickname)) ) {
            System.err.println("Invalid Nickname !");
            System.out.print("Insert a new one (min. 6 chars, max. 30 chars, only letters, numbers and _ ) : ");
            nickname = input.nextLine();
        }

        clientView.sendCTSEvent(new NewConnectionEvent(event.ID, nickname));

    }


    //TODO : no eccezione ip sbalgiato
    //TODO : riguardare print you can't place pawn here
    @Override
    public void manageEvent(FirstConnectedEvent event) {

        this.nickname = event.nickname;
        int selectedNumberOfPlayer = 0;

        do {

            System.out.println("Do you want a 2 or 3 players game ? ");

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number !");
                input.next();

                System.out.println("Do you want a 2 or 3 players game ? ");
            }
            selectedNumberOfPlayer = input.nextInt();

        } while( !(selectedNumberOfPlayer == 2 || selectedNumberOfPlayer == 3) );

        clientView.sendCTSEvent( new ChosenPlayerNumberEvent(nickname, selectedNumberOfPlayer));
    }


    @Override
    public void manageEvent(SuccessfullyConnectedEvent event) {

        this.nickname = event.nickname;

        System.out.println("The temporary players are : ");
        for (String nickname : event.connectedPlayers ) {
            System.out.println(nickname);
        }

    }


    @Override
    public void manageEvent(UnavailableNicknameEvent event) {

        System.out.println("This nickname is already used, please insert a new nickname (min. 6 chars, max. 30 chars, only letters, numbers and _ ) :");

        String nickname = input.nextLine();

        while( !(Pattern.matches(nicknamePattern, nickname)) ) {
            System.err.println("Invalid Nickname !");
            System.out.print("Insert a new one (min. 6 chars, max. 30 chars, only letters, numbers and _ ) : ");
            nickname = input.nextLine();
        }

        clientView.sendCTSEvent(new NewConnectionEvent(event.ID, nickname));


    }


    @Override
    public void manageEvent(NotifyStatusEvent event) {
        List<FormattedCellInfo> cellsInfoList = JsonHandler.generateCellsList(event.status);

        for (FormattedCellInfo c : cellsInfoList ) {
            String strInfo = getStringCellInfo(c.getHeight(), c.getRoofInfo().getSecond(), c.getPawnInfo().getFirst(), c.getPawnInfo().getSecond());

            //List<String> info = getStringCellInfoList(c.getHeight(), c.getRoofInfo().getSecond(), c.getPawnInfo().getFirst(), c.getPawnInfo().getSecond());
            //drawer.setCellInfo(c.getRow(), c.getColumn()+1, info.get(0), info.get(1));

            drawer.setCellInfo(c.getRow(), c.getColumn()+1, strInfo);
        }

        drawer.show();
    }


    @Override
    public void manageEvent(DisconnectionClientEvent event) {
        System.out.println("YOU HAVE BEEN DISCONNECTED!");
        this.clientView = null;
        System.exit(0);
    }


    @Override
    public void manageEvent(OneClientDisconnectedEvent event) {
        System.out.println(event.disconnected + " is disconnected");

        System.out.println("Players in lobby are now:");
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
    public void manageEvent(PlainTextEvent event) {
        System.out.println(event.message);
    }


    // ======================================================================================


    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {
        System.out.println("THE WAITING ROOM IS NOW CLOSED!");
    }


    @Override
    public void manageEvent(RoomNotFilled event) {
        System.out.println(event.message);
        System.out.println();
        System.out.println("---YOU HAVE BEEN DISCONNECTED!---");
        this.clientView = null;
        System.exit(0);
    }


    @Override
    public void manageEvent(AskInitPawnsEvent event) {

        List<Point> occupiedCells = event.info;
        List<Point> freeCells = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Point np = new Point(i, j);
                if(!occupiedCells.contains(np)) {
                    freeCells.add(np);
                }
            }
        }

        int selectedMale = -1;
        int selectedFemale = -1;


        if(!event.isValid) {
            System.out.println("An error has occurred, please reinsert the positions");
        }


        do {

            drawer.saveTitleChoicePanel("select the cell for the male pawn");
            drawer.saveCellsChoicesValue(freeCells);

            drawer.show();

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number !");
                input.next();
            }
            selectedMale = input.nextInt();

            if(!(selectedMale >= 0 && selectedMale < freeCells.size()))
                System.err.println("Choice Unavailable !");

        } while( !(selectedMale >= 0 && selectedMale < freeCells.size()) );

        int maleRowPosition = freeCells.get(selectedMale).x;
        int maleColumnPosition = freeCells.get(selectedMale).y;


        freeCells.remove(selectedMale);
        do {

            drawer.saveTitleChoicePanel("select the cell for the female pawn");
            drawer.saveCellsChoicesValue(freeCells);

            drawer.show();

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number !");
                input.next();
            }
            selectedFemale = input.nextInt();

            if(!(selectedFemale >= 0 && selectedFemale < freeCells.size()))
                System.err.println("Choice Unavailable !");

        } while( !(selectedFemale >= 0 && selectedFemale < freeCells.size()) );

        int femaleRowPosition = freeCells.get(selectedFemale).x;
        int femaleColumnPosition = freeCells.get(selectedFemale).y;

        clientView.sendCTSEvent(new ChosenInitialPawnCellEvent(nickname, maleRowPosition, maleColumnPosition, femaleRowPosition, femaleColumnPosition));

        drawer.saveTitleChoicePanel("-- WAIT UNTIL YOUR NEXT TURN --");
        drawer.clearChoicePanelValues();

        drawer.show();
    }


    @Override
    public void manageEvent(AskWhichPawnsUseEvent event) {

        if (!event.isValid) {
            System.out.println("An error has occurred, please reinsert the position");
        }

        List<Point> availablePawns = event.info;
        int selectedPawn = -1;


        do {

            drawer.saveTitleChoicePanel("select the pawn that you want to use in this turn");
            drawer.saveCellsChoicesValue(availablePawns);

            drawer.show();

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number !");
                drawer.show();

                input.next();
            }
            selectedPawn = input.nextInt();

            if( !(selectedPawn >= 0 && selectedPawn < availablePawns.size()) ) {
                System.err.println("Choice Unavailable !");
            }


        } while( !(selectedPawn >= 0 && selectedPawn < availablePawns.size()) );

        this.rowUsedPawn = availablePawns.get(selectedPawn).x;
        this.columnUsedPawn = availablePawns.get(selectedPawn).y;

       clientView.sendCTSEvent(new ChosenPawnToUseEvent(nickname, rowUsedPawn, columnUsedPawn));

       drawer.saveTitleChoicePanel("-- WAIT UNTIL YOUR NEXT TURN --");
       drawer.clearChoicePanelValues();

       drawer.show();

   }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

        List<String> cardsName = event.cardsName;
        List<String> cardsEffect = event.cardsEffect;

        int choiceNum;


        if(!event.isValid) {
            System.out.println("An error has occurred, please reinsert the your choice");
        }

        do {

            drawer.saveTitlePlayerPanel("CHOOSE YOUR CARD");
            for (int i = 0; i < cardsName.size(); i++) {
                drawer.savePlayerCardChoice(cardsName, cardsEffect);
            }
            drawer.show();


            while(!input.hasNextInt()) {
                System.err.println("Insert a Number !");
                drawer.show();

                input.next();
            }
            choiceNum = input.nextInt();

            if( !(choiceNum >= 0 && choiceNum < cardsName.size()) ) {
                System.err.println("Choice Unavailable !");
            }

        } while( !(choiceNum >= 0 && choiceNum < cardsName.size()) );


        clientView.sendCTSEvent(new ChosenCardEvent(nickname, cardsName.get(choiceNum)));

        //System.out.println("-- WAIT UNTIL YOUR NEXT TURN --");
        drawer.saveTitleChoicePanel("-- WAIT UNTIL YOUR NEXT TURN --");
        drawer.clearChoicePanelValues();

        drawer.show();
    }


    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

        List<String> possibleActions = event.actions;
        boolean isEventValid = event.isValid;

        int indexChosenAction = -1;

        if(!isEventValid) {
            System.err.println("Apparently there was an error! Reselect.");
        }

        drawer.saveTitleChoicePanel("choose your next action");
        drawer.saveActionsChoicesValue(possibleActions);

        do {

            drawer.show();

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number!");
                drawer.show();

                input.next();
            }
            indexChosenAction = input.nextInt();

            if( !(indexChosenAction >= 0 && indexChosenAction < possibleActions.size()) ) {
                System.err.println("Choice Unavailable !");
            }

        } while( !(indexChosenAction >= 0 && indexChosenAction < possibleActions.size()) );


        //in case there is only one possible action I directly send the possible action
        //indexChosenAction is initialized to 0 so automatically takes the first and only possible action
        switch (possibleActions.get(indexChosenAction)) {

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
                throw new RuntimeException("Error while selecting the next action !");

            }



    }


    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        List<Point> cellsAvailableToMove = event.cellsAvailableToMove;
        boolean isEventValid = event.isValid;

        int selectedCell = 0;


        if(!isEventValid) {
            System.err.println("Apparently there was an error! Reselect.");
        }

        drawer.saveTitleChoicePanel("choose the cell where you want to move");
        drawer.saveCellsChoicesValue(cellsAvailableToMove);

        do {
            drawer.show();

            while(!input.hasNextInt()) {
                System.err.println("Insert an Number !");
                drawer.show();

                input.next();
            }
            selectedCell = input.nextInt();

            if( !(selectedCell >= 0 && selectedCell < cellsAvailableToMove.size()) ) {
                System.err.println("Unavailable Choice !");
            }

        } while( !(selectedCell >= 0 && selectedCell < cellsAvailableToMove.size()) );


        nextActionRow = cellsAvailableToMove.get(selectedCell).x;
        nextActionColumn = cellsAvailableToMove.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToMoveEvent(nickname, rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));

        rowUsedPawn = cellsAvailableToMove.get(selectedCell).x;
        columnUsedPawn = cellsAvailableToMove.get(selectedCell).y;
    }


    //TODO : ottimizzare per ridurre le copie di codice
    @Override
    public void manageEvent(GivePossibleCellsToBuildEvent event) {

        List<Point> cellsAvailableToBuild = event.cellsAvailableToBuild;
        boolean isEventValid = event.isValid;
        int selectedCell = 0;


        if(!isEventValid) {
            System.err.println("Apparently there was an error! Reselect.");
        }

        drawer.saveTitleChoicePanel("choose the cell where you want to build");
        drawer.saveCellsChoicesValue(cellsAvailableToBuild);

        do {
            drawer.show();

            while(!input.hasNextInt()) {
                System.err.println("Insert an Number !");
                drawer.show();

                input.next();
            }
            selectedCell = input.nextInt();

            if( !(selectedCell >= 0 && selectedCell < cellsAvailableToBuild.size()) ) {
                System.err.println("Unavailable Choice !");
            }

        } while( !(selectedCell >= 0 && selectedCell < cellsAvailableToBuild.size()) );

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
            System.err.println("Apparently there was an error! Reselect.");
        }

        //if there are more than one single options then I
        //have to display them and give the possibility to choose
        if(buildingsLevel.size() > 1) {

            drawer.saveTitleChoicePanel("Choose the building to build on the selected cell");
            drawer.saveBuildingsChoicesValue(buildingsLevel);

            do {
                drawer.show();

                while(!input.hasNextInt()) {
                    System.err.println("Insert an Number!");
                    drawer.show();

                    input.next();
                }
                selectedLevel = input.nextInt();

                if( !(selectedLevel >= 0 && selectedLevel < buildingsLevel.size()) ) {
                    System.err.println("Unavailable Choice !");
                }

            } while( !(selectedLevel >= 0 && selectedLevel < buildingsLevel.size()) );

        }

        clientView.sendCTSEvent(new ChosenBuildingEvent(nickname, buildingsLevel.get(selectedLevel), rowUsedPawn, columnUsedPawn, nextActionRow, nextActionColumn));
    }


    @Override
    public void manageEvent(GivePossibleCellsToDestroyEvent event) {

        if (!event.isValid)
            System.out.println("An error has occurred, please reinsert your choice");


        System.out.println("You can destroy a building in the following cells:");

        for (int i = 0; i < event.cells.size(); i++) {

            System.out.println("[" + i + "]\t" + event.cells.get(i).x + " - " + event.cells.get(i).y + "\n");

        }

        int selectedCell;

        do {

            System.out.println("Choose the cell where you want to destroy:");

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number!");
                input.next();
            }
            selectedCell = input.nextInt();

            if(selectedCell < 0 || selectedCell >= event.cells.size())
                System.out.println("Unavailable choice");

        } while(selectedCell < 0 || selectedCell >= event.cells.size());

        int selectedRowToDestroy = event.cells.get(selectedCell).x;

        int selectedColumnToDestroy = event.cells.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToDestroyEvent(nickname, rowUsedPawn, columnUsedPawn, selectedRowToDestroy, selectedColumnToDestroy));

    }


    @Override
    public void manageEvent(GivePossibleCellsToForceEvent event) {

        if (!event.isValid)
            System.out.println("An error has occurred, please reinsert your choice");


        System.out.println("You can force to move the pawns in these cells:");

        for (int i = 0; i < event.cells.size(); i++) {

            System.out.println("[" + i + "]\t" + event.cells.get(i).x + " - " + event.cells.get(i).y + "\n");

        }

        int selectedCell;

        do {

            System.out.println("Choose the cell of the pawn you want to force to move:");

            while(!input.hasNextInt()) {
                System.err.println("Insert a Number!");
                input.next();
            }
            selectedCell = input.nextInt();

            if(selectedCell < 0 || selectedCell >= event.cells.size())
                System.out.println("Unavailable choice");

        } while(selectedCell < 0 || selectedCell >= event.cells.size());

        int selectedRowForcedPawn = event.cells.get(selectedCell).x;

        int selectedColumnForcedPawn = event.cells.get(selectedCell).y;

        clientView.sendCTSEvent(new ChosenCellToForceEvent(nickname, rowUsedPawn, columnUsedPawn, selectedRowForcedPawn, selectedColumnForcedPawn));

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

        playersInfo = ClientJsonHandler.generatePlayersList(event.info);

        drawer.saveTitlePlayerPanel("players information");
        drawer.saveInfoPlayerPanel(playersInfo);

        drawer.show();
    }


    @Override
    public void manageEvent(LosingByNoActionEvent event) {
        //TODO : da completare


        /* qua devo fare che stampo a video il fatto che
         hai perso la partita perchè non hia piu mosse disponibili,
         nella classe GraphicDrawer potrei fare che nella ChoicePanel gli stampo
         una piccola animazione con la scritta hai perso*/

        /*  */

    }


    @Override
    public void manageEvent(EndGameSTCEvent event) {
        /* devo comunicare al player che la partita è stat conclusa e devo dargli
        la possibilità di uscire dal gioco oppure di ricominciarne un'altra */

        /* anche qui posso fare un'animazione nella schermata */
    }


    // ======================================================================================


    /**
     * This method generate the format of the string that I have to pass to the GraphicDrawer
     * when I want to save and show the information of the board.
     * Generate a string of length two :
     * in the first char there is the height of the tower (0 to 4) in the particular cell
     * in the second char there is an uppercase X if there's a  dome in the cell, there is
     * a '.' is the cell is free and there is the first letter of the color of the pawn in
     * uppercase if the pawn is male (B G W) and lowercase if the pawn if female (b g w)
     * @param height the height of the tower in this cell, range 0 to 4
     * @param isDome indicate if in the cell there's a dome
     * @param color indicates the color of the pawn if present
     * @param sex indicates the sex of the pawn if present
     * @return return the formatted string
     */
    public String getStringCellInfo(Integer height, Boolean isDome, String color, String sex) {


        String retString = String.valueOf(height);

        if (isDome) {
            return retString + "x";
        }
        else if(color.equals("") && sex.equals("")) {
            return retString + ".";
        }
        else {

            switch (color) {
                case "BLUE":

                    if (sex.equals("MALE")) {
                        return retString + "B";
                    } else if (sex.equals("FEMALE")) {
                        return retString + "b";
                    }

                    break;
                case "GREY":

                    if (sex.equals("MALE")) {
                        return retString + "G";
                    } else if (sex.equals("FEMALE")) {
                        return retString + "g";
                    }

                    break;
                case "WHITE":

                    if (sex.equals("MALE")) {
                        return retString + "W";
                    } else if (sex.equals("FEMALE")) {
                        return retString + "w";
                    }

                    break;
            }

        }

        return "error";
    }


    /*
    public List<String> getStringCellInfoList(Integer height, Boolean isDome, String color, String sex) {

        String RESET = "\u001B[0m";
        String BLACK = "\u001B[30m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String BLUE = "\u001B[34m";
        String PURPLE = "\u001B[35m";
        String CYAN = "\u001B[36m";
        String WHITE = "\u001B[37m";
        String BRIGHTBLACK = "\u001b[30;1m";

        List<String> infoCell = new ArrayList<>();
        String towerInfo = String.valueOf(height);
        String cellInfo = "";

        if (isDome) {
            cellInfo = "X";
        }
        else if(color.equals("") && sex.equals("")) {
            cellInfo = ".";
        }
        else {

            switch (color) {
                case "BLUE":

                    if (sex.equals("MALE")) {
                        cellInfo = BLUE + "B" + RESET;
                    } else if (sex.equals("FEMALE")) {
                        cellInfo = BLUE + "b" + RESET;
                    }

                    break;
                case "GREY":

                    if (sex.equals("MALE")) {
                        cellInfo = BRIGHTBLACK + "G" + RESET;
                    } else if (sex.equals("FEMALE")) {
                        cellInfo = BRIGHTBLACK + "g" + RESET;
                    }

                    break;
                case "WHITE":

                    if (sex.equals("MALE")) {
                        cellInfo = WHITE + "W" + RESET;
                    } else if (sex.equals("FEMALE")) {
                        cellInfo = WHITE + "w" + RESET;
                    }

                    break;
            }

        }

        infoCell.add(towerInfo);
        infoCell.add(cellInfo);
        return infoCell;
    }

     */


}
