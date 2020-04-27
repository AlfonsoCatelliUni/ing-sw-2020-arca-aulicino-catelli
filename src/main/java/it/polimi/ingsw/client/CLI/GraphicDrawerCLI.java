package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.Couple;
import it.polimi.ingsw.client.FormattedPlayerInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicDrawerCLI {

    private final String RESET = "\u001B[0m";
    private final String BLACK = "\u001B[30m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";
    private final String PURPLE = "\u001B[35m";
    private final String CYAN = "\u001B[36m";
    private final String WHITE = "\u001B[37m";
    private final String BRIGHTBLACK = "\u001b[30;1m";

    private final int ROWS = 62;

    private final int COLUMNS = 154;



    private String[][] screen;

    private char[][] boardCellsInfo;
    private String[][] boardString;


    // ======================================================================================


    public GraphicDrawerCLI() {
        //this.screen = new char[ROWS][COLUMNS];
        this.screen = new String[ROWS][COLUMNS];
        this.boardCellsInfo = new char[5][10];
        this.boardString = new String[5][10];

        this.playerPanelTitle = "";
        this.playersInfo = new ArrayList<>();
        this.cardsNameChoices = new ArrayList<>();
        this.cardsEffectChoices = new ArrayList<>();

        this.choicePanelTitle = "";
        this.cellsChoicePanel = new ArrayList<>();
        actionsChoicePanel = new ArrayList<>();
        buildingsChoicePanel = new ArrayList<>();


        resetBoardCellsInfo();
    }


    /*
    public void fillTest() {
        clear();
        drawPlayersPanel();
        drawChoicePanel();
        drawSantoriniLogo();
        drawBoardPanel();

        List<FormattedPlayerInfo> players = new ArrayList<>();
        players.add(new FormattedPlayerInfo("Alfonsoooooooooooooooooooooo30", "WHITE", Couple.create("Apollo", "Your Move: Your Worker may move into an opponent Worker's space (using normal movement rules) and force their Worker to the space yours just left (swapping their positions).")));
        players.add(new FormattedPlayerInfo("Massi", "BLUE", Couple.create("Minotaur", "Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.")));

        setTitlePlayerPanel("players information");
        setInfoPlayerPanel(players);

        List<Couple<Integer, Integer>> cellsList = new ArrayList<>();
        cellsList.add(Couple.create(0,0));
        cellsList.add(Couple.create(0,1));
        cellsList.add(Couple.create(1,1));
        cellsList.add(Couple.create(1,0));
        setTitleChoicePanel("do your choice");
        setCellChoicesValue(cellsList);
    }

     */


    public static void main(String[] args) {
        GraphicDrawerCLI cli = new GraphicDrawerCLI();

        cli.show();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cli.clearScreen();

        //cli.fillTest();
        cli.show();
    }


    // ======================================================================================


    public void draw() {
        clear();
        drawPlayersPanel();
        drawChoicePanel();
        drawSantoriniLogo();
        drawBoardPanel();
    }


    public void show() {

        draw();

        for(int row = 0; row < ROWS; row++) {
            System.out.println();
            for (int column = 0; column < COLUMNS; column++) {
                System.out.print(screen[row][column]);
            }
        }

        System.out.println();
    }


    public void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    private void clear() {

        for(int row = 0; row < ROWS; row++) {
            for(int column = 0; column < COLUMNS; column++) {

                if(row == 0 || row == ROWS-1) {
                    screen[row][column] = "-";
                }
                else if(column == 0 || column == COLUMNS-1) {
                    screen[row][column] = "|";
                }
                else {
                    screen[row][column] = " ";
                }

            }
        }

        screen[0][0] = "+";
        screen[0][153] = "+";
        screen[61][153] = "+";
        screen[61][0] = "+";

    }


    private void print(int row, int column, String p, String color) {
        for (int off = 0; off < p.length(); off++) {
            screen[row][column + off] =  color + String.valueOf(p.charAt(off)) + RESET ;
        }
    }


    private void print(int row, int column, String p) {
        print(row, column, p, RESET);
    }


    // ======================================================================================


    private void drawSantoriniLogo() {

        print(25, 10, "  _____             _             _       _ ", YELLOW);
        print(26, 10, " / ____|           | |           (_)     (_)", YELLOW);
        print(27, 10, "| (___   __ _ _ __ | |_ ___  _ __ _ _ __  _ ", YELLOW);
        print(28,  10, " \\___ \\ / _` | '_ \\| __/ _ \\| '__| | '_ \\| |", YELLOW);
        print(29, 10, " ____) | (_| | | | | || (_) | |  | | | | | |", YELLOW);
        print(30, 10, "|_____/ \\__,_|_| |_|\\__\\___/|_|  |_|_| |_|_|", YELLOW);

    }


    //MARK : Board Panel Section ======================================================================================


    private void drawBoardPanel() {

        int startRow = 34;
        int coeff = 0;
        String divider = "+-------+-------+-------+-------+-------+-------+";
        String empty = "|       |       |       |       |       |       |";

        for(int row = startRow; row <= 58; row++) {

            coeff = (row + 2) % 4;

            if( coeff == 0) {
                print(row, 8, divider, CYAN);
            } else {
                print(row, 8, empty, CYAN);
            }

        }

        fillBoardPanel();
    }


    private void resetBoardCellsInfo() {

        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 10; column++) {
                if(column % 2 == 0) {
                    boardCellsInfo[row][column] = '0';
                    boardString[row][column] = "0";
                }
                else {
                    boardCellsInfo[row][column] = '.';
                    boardString[row][column] = ".";
                }
            }
        }

    }


    private void fillBoardPanel() {

        int startRow = 36;
        int rowOffset = 4;

        int startCol = 12;
        int colOffset = 8;

        int rowRef;
        int colRef;

        for(int row = 0; row < 6; row++) {
            for(int col = 0; col < 6; col++) {

                rowRef = startRow + (row * rowOffset);
                colRef = startCol + (col * colOffset);

                if(row == 0) {
                    if(col == 0) {
                        print(rowRef, colRef, "X", GREEN);
                    }
                    else {
                        print(rowRef, colRef, String.valueOf(col-1), GREEN);
                    }

                }
                else if(col == 0) {
                    print(rowRef, colRef, String.valueOf(row-1), GREEN);
                }
                else {
                    print(rowRef, colRef-1, getCellInfo(row, col));
                }

            }
        }

    }


    /**
     * this method set the information of the cell at the passed coordinates,
     * it takes a well-formatted string info.
     * the first char of this string contains the height of the tower (0 to 4).
     * the second char of this string contains the first letter of the color
     * of the pawn, the letter is uppercase for the male pawn (B G W) and
     * lowercase for the female pawn (b g w), if there's a dome in the cell
     * this char will be an uppercase X, if the cell is empty there will be a '.'
     * @param row the row coordinates of the cell in range 1 to 5
     * @param column the column coordinates of the cell in range 1 to 5
     * @param info a formatted string which contains the information
     */
    public void setCellInfo(int row, int column, String info) {

        int refCol = column * 2;

        if(info.length() != 2) {
            throw new RuntimeException("Wrong Formatting CellInfo !");
        }
        else {
            boardCellsInfo[row][refCol-2] = info.charAt(0);
            boardCellsInfo[row][refCol-1] = info.charAt(1);
        }

    }


    public void setCellInfo(int row, int column, String towerInfo, String cellInfo) {

        int refCol = column * 2;

        boardString[row][refCol-2] = towerInfo;
        boardString[row][refCol-1] = cellInfo;
    }


    /**
     * this method shows the information of the cell at the passed coordinates,
     * specifically shows the height of the tower in this cell (0 to 4) and the
     * first letter of the color of pawn, uppercase letter to indicates male
     * (B G W) and lowercase letter to indicates female (b g w)
     * @param row the row coordinates of the cell in range 1 to 5
     * @param column the column coordinates of the cell in range 1 to 5
     * @return a formatted string which contains the information
     */
    public String getCellInfo(int row, int column) {

        int refCol = column * 2;

        //return boardString[row-1][refCol-2]+" "+boardString[row-1][refCol-1];
        return boardCellsInfo[row-1][refCol-2]+" "+boardCellsInfo[row-1][refCol-1];
    }


    //MARK : Player Panel Section ======================================================================================

    private String playerPanelTitle;

    private List<FormattedPlayerInfo> playersInfo;

    private List<String> cardsNameChoices;

    private List<String> cardsEffectChoices;


    private void drawPlayersPanel() {

        int startRow = 4;
        String divider = "+------------------------------------------------------------------------------------------------------------------------------------------+";
        String empty = "|                                                                                                                                          |";

        for(int row = startRow; row <= 20; row++) {

            if(row == 4 || row == 8 || row == 20) {
                print( row, 8, divider, CYAN);
            } else {
                print(row, 8, empty, CYAN);
            }


        }

        fillPlayersPanel();
    }


    private void fillPlayersPanel() {
        fillTitlePlayerPanel(playerPanelTitle);
        fillInfoPlayerPanel(playersInfo);

        if(cardsNameChoices.size() > 0) {
            fillPlayerCardChoice(cardsNameChoices, cardsEffectChoices);
        }
    }


    public void saveTitlePlayerPanel(String title) {
        this.playerPanelTitle = title.toUpperCase();
    }


    public void saveInfoPlayerPanel(List<FormattedPlayerInfo> playerInfoList) {
        this.playersInfo = playerInfoList;

        //when i set the players info the cards are all being chosen so i can eliminate the value
        this.cardsNameChoices = new ArrayList<>();
        this.cardsEffectChoices = new ArrayList<>();
    }


    public void savePlayerCardChoice(List<String> cardsNameList, List<String> cardsEffectList) {
        this.cardsNameChoices = cardsNameList;
        this.cardsEffectChoices = cardsEffectList;
    }


    private void fillPlayerCardChoice( List<String> cardsNameList, List<String> cardsEffectList) {

        int startRow = 11;
        int rowOffset = 3;
        int refRow;


        int numCards = cardsNameList.size();

        for(int card = 0; card < numCards; card++) {
            refRow = startRow + (card * rowOffset);

            print(refRow, 11, "["+String.valueOf(card)+"]", RED);
            print(refRow, 16, cardsNameList.get(card));

            if(cardsEffectList.get(card).length() > 87) {
                print(refRow, 57, cardsEffectList.get(card).substring(0, 87));
                print(refRow+1, 57, cardsEffectList.get(card).substring(87));
            }
            else {
                print(refRow, 57, cardsEffectList.get(card));
            }
        }

    }


    private void fillTitlePlayerPanel(String title) {

        if(title.length() > 132)
            throw new RuntimeException("Invalid Format for Player Panel Title !");

        String uppercaseTitle = title.toUpperCase();

        print(6, 12, uppercaseTitle, BRIGHTBLACK);

    }


    private void fillInfoPlayerPanel( List<FormattedPlayerInfo> playerInfoList ) {

        //nickname length 30
        //color length 5
        //effect length 87 * 2 rows (174)

        int startRow = 11;
        int rowOffset = 3;
        int refRow;


        int numPlayer = playerInfoList.size();

        for(int player = 0; player < numPlayer; player++) {
            refRow = startRow + (player * rowOffset);

            print(refRow, 12, String.valueOf(player+1));
            print(refRow, 16, playerInfoList.get(player).getNickname());
            print(refRow, 49, playerInfoList.get(player).getColor());

            if(playerInfoList.get(player).getCard().getSecond().length() > 87) {
                print(refRow, 57, playerInfoList.get(player).getCard().getSecond().substring(0, 87));
                print(refRow+1, 57, playerInfoList.get(player).getCard().getSecond().substring(87));
            }
            else {
                print(refRow, 57, playerInfoList.get(player).getCard().getSecond());
            }

        }


    }


    //MARK : Choice Panel Section ======================================================================================

    private String choicePanelTitle;

    private List<Point> cellsChoicePanel;

    private List<String> actionsChoicePanel;

    private List<Integer> buildingsChoicePanel;


    private void drawChoicePanel() {

        int startRow = 23;
        String divider = "+-----------------------------------------------------------------------------+";
        String empty = "|                                                                             |";

        for(int row = startRow; row <= 58; row++) {

            if(row == 23 || row == 27 || row == 58) {
                print(row, 69, divider, CYAN);
            } else {
                print(row, 69, empty, CYAN);
            }
        }

        fillChoicePanel();
    }


    private void fillChoicePanel() {
        fillTitleChoicePanel();

        if(this.cellsChoicePanel.size() > 0) {
            fillCellsChoicePanel();
        }
        else if(this.actionsChoicePanel.size() > 0) {
            fillActionsChoicesPanel();
        }
        else if(this.buildingsChoicePanel.size() > 0) {
            fillBuildingsChoicePanel();
        }

    }


    private void fillTitleChoicePanel() {
        print(25, 72, choicePanelTitle);
    }


    private void fillCellsChoicePanel() {

        int startRow = 31;
        int rowOffset = 3;
        int refRow;

        int startColumn = 72;
        int startColumnValue = 79;
        int colOffset = 26;
        int refColNum;
        int refColVal;
        int numCol = 0;

        String choiceValue;
        String choiceNumber;

        print(29, 77, "Row - Column");

        refColNum = startColumn;
        refColVal = startColumnValue;
        for(int i = 0; i < cellsChoicePanel.size(); i++) {

            if(i == 9) {
                refRow = startRow;
                numCol = 9;
                refColNum += colOffset;
                refColVal += colOffset+1;
            }
            if(i == 18) {
                refRow = startRow;
                numCol = 18;
                refColNum += colOffset;
                refColVal += colOffset;
            }

            refRow = startRow + ((i - numCol) * rowOffset);

            choiceNumber = "[" + String.valueOf(i) + "]";
            print(refRow, refColNum, choiceNumber, RED);

            choiceValue = String.valueOf(cellsChoicePanel.get(i).x) + " - " + String.valueOf(cellsChoicePanel.get(i).y);
            print(refRow, refColVal, choiceValue);


        }

    }


    private void fillActionsChoicesPanel() {

        int startRow = 31;
        int rowOffset = 3;
        int refRow;

        String choiceNumber;

        print(29, 77, "Actions");

        for(int a = 0; a < actionsChoicePanel.size(); a++) {
            refRow = startRow + (a * rowOffset);

            choiceNumber = "[" + String.valueOf(a) + "]";
            print(refRow, 72, choiceNumber, RED);

            print(refRow, 77, actionsChoicePanel.get(a));
        }

    }


    private void fillBuildingsChoicePanel() {

        int startRow = 31;
        int rowOffset = 3;
        int refRow;

        String choiceNumber;
        String choiceValue;

        print(29, 77, "Building");

        for(int i = 0; i < buildingsChoicePanel.size(); i++) {
            refRow = startRow + (i * rowOffset);

            choiceNumber = "[" + String.valueOf(i) + "]";
            print(refRow, 72, choiceNumber, RED);

            choiceValue = "Level " + String.valueOf(buildingsChoicePanel.get(i));
            print(refRow, 77, choiceValue);
        }
    }


    public void saveTitleChoicePanel(String title) {

        if(title.length() > 72 )
            throw new RuntimeException("Invalid Format fot Choice Panel Title !");

        this.choicePanelTitle = title.toUpperCase();

    }


    public void saveCellsChoicesValue(List<Point> cellsList) {
        clearChoicePanelValues();
        this.cellsChoicePanel = cellsList;
    }


    public void saveActionsChoicesValue(List<String> actionsList) {
        clearChoicePanelValues();
        this.actionsChoicePanel = actionsList;
    }


    public void saveBuildingsChoicesValue(List<Integer> buildingsList) {
        clearChoicePanelValues();
        this.buildingsChoicePanel = buildingsList;
    }


    public void clearChoicePanelValues() {
        this.cellsChoicePanel = new ArrayList<>();
        this.actionsChoicePanel = new ArrayList<>();
        this.buildingsChoicePanel = new ArrayList<>();
    }




    /*
    private void setChoicesNumber() {

        int startRow = 31;
        int rowOffset = 3;

        int refRow;
        String value = "";

        for(int row = 0; row < 9; row++ ) {
            refRow = startRow + (row * rowOffset);
            value = "[" + String.valueOf(row+1) + "]";

            print(refRow, 73, value);
        }

    }
     */











}
