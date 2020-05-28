package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.FormattedCellInfo;
import it.polimi.ingsw.client.FormattedPlayerInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Graphic Drawer CLI</h3>
 * <p>this class is used to compose and draw the graphic
 * of the textual part of the game</p>
 */
public class GraphicDrawerCLI {

    private final String RESET = "\u001B[0m";

    private final String RED_C = "\u001b[38;5;196m";
    private final String GREEN_C = "\u001b[38;5;46m";
    private final String CYAN_C = "\u001b[38;5;39m";
    private final String BLUE_GREEN_C = "\u001b[38;5;30m";
    private final String COPPER_C = "\u001b[38;5;94m";
    private final String ORANGE_C = "\u001b[38;5;202m";

    private final String BLUE_C = "\u001b[38;5;27m";
    private final String GREY_C = "\u001b[38;5;241m";
    private final String WHITE_C = "\u001b[38;5;231m";


    private final int ROWS = 62;

    private final int COLUMNS = 154;

    private String[][] screen;


    // ======================================================================================


    public GraphicDrawerCLI() {

        this.screen = new String[ROWS][COLUMNS];
        this.formattedBoardInfo = new FormattedCellInfo[5][5];

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


    // ======================================================================================


    public void drawChallengerCardsChoice() {
        clearScreen();
        clear();
        drawChallengerPanel();
    }


    public void showChallenger() {
        drawChallengerCardsChoice();

        for(int row = 0; row < ROWS; row++) {
            System.out.println();
            for (int column = 0; column < COLUMNS; column++) {
                System.out.print(screen[row][column]);
            }
        }

        System.out.println();
    }


    /**
     * this method modify the screen array calling the specific methods for each section
     */
    public void draw() {
        clearScreen();
        clear();
        drawPlayersPanel();
        drawChoicePanel();
        drawSantoriniLogo();
        drawBoardPanel();
    }


    /**
     * this method draw the new graphic and print it on screren
     */
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


    /**
     * clear the console
     */
    public void clearScreen() {
        System.out.print("\033\143");
        System.out.flush();
    }


    /**
     * this method draw an empty screen
     */
    private void clear() {

        for(int row = 0; row < ROWS; row++) {
            for(int column = 0; column < COLUMNS; column++) {

                if(row == 0 || row == ROWS-1) {
                    screen[row][column] = COPPER_C + "-" + RESET;
                }
                else if(column == 0 || column == COLUMNS-1) {
                    screen[row][column] = COPPER_C + "|" + RESET;
                }
                else {
                    screen[row][column] = " ";
                }

            }
        }

        screen[0][0] = COPPER_C + "+" + RESET;
        screen[0][153] = COPPER_C + "+" + RESET;
        screen[61][153] = COPPER_C + "+" + RESET;
        screen[61][0] = COPPER_C + "+" + RESET;

    }


    /**
     * this method write every single char of the passed string
     * into the screen table with the passed color
     * @param row the row where the string will be wrote
     * @param column the starting column where the string will be wrote
     * @param p the string
     * @param color the color that i want the string
     */
    private void print(int row, int column, String p, String color) {
        for (int off = 0; off < p.length(); off++) {
            screen[row][column + off] =  color + String.valueOf(p.charAt(off)) + RESET ;
        }
    }


    /**
     * this method only calls the print passing the standard color RESET
     * @param row the row where the string will be wrote
     * @param column the starting column where the string will be wrote
     * @param p the string
     */
    private void print(int row, int column, String p) {
        print(row, column, p, RESET);
    }


    // ======================================================================================


    /**
     * this method draw the Santorini logo in a specific position of the board
     */
    private void drawSantoriniLogo() {

        print(25, 10, "  _____             _             _       _ ", WHITE_C);
        print(26, 10, " / ____|           | |           (_)     (_)", WHITE_C);
        print(27, 10, "| (___   __ _ _ __ | |_ ___  _ __ _ _ __  _ ", WHITE_C);
        print(28,  10, " \\___ \\ / _` | '_ \\| __/ _ \\| '__| | '_ \\| |", WHITE_C);
        print(29, 10, " ____) | (_| | | | | || (_) | |  | | | | | |", WHITE_C);
        print(30, 10, "|_____/ \\__,_|_| |_|\\__\\___/|_|  |_|_| |_|_|", WHITE_C);

    }


    //MARK : Board Panel Section ======================================================================================

    /**
     * the information of the board
     */
    private FormattedCellInfo[][] formattedBoardInfo;


    /**
     * this method draws the structure of the board graphic
     * the fills it with the cells info
     */
    private void drawBoardPanel() {

        int startRow = 34;
        int coeff = 0;
        String divider = "+-------+-------+-------+-------+-------+-------+";
        String empty = "|       |       |       |       |       |       |";

        for(int row = startRow; row <= 58; row++) {

            coeff = (row + 2) % 4;

            if( coeff == 0) {
                print(row, 8, divider, COPPER_C);
            } else {
                print(row, 8, empty, COPPER_C);
            }

        }

        fillBoardPanel();
    }


    /**
     * this method deletes all the cells info saved
     */
    private void resetBoardCellsInfo() {

        for(int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                formattedBoardInfo[row][column] = new FormattedCellInfo(row, column, 0, 0, false);
            }
        }

    }


    /**
     * this method fills all the board with the info for the cell
     * or puts in the coordinates
     */
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
                        print(rowRef, colRef, "X", RED_C);
                    }
                    else {
                        print(rowRef, colRef, String.valueOf(col-1), RED_C);
                    }

                }
                else if(col == 0) {
                    print(rowRef, colRef, String.valueOf(row-1), RED_C);
                }
                else {
                    int selectedRow = row-1;
                    int selectedCol = col-1;

                    fillCellInfo(rowRef, colRef-1, selectedRow, selectedCol, formattedBoardInfo[selectedRow][selectedCol]);
                }

            }
        }

    }


    /**
     * this method is used to save new info for a specific cell
     * @param cellInfoList is a list of formatted object that contains all the useful info of the cell
     */
    public void saveBoardChanges(List<FormattedCellInfo> cellInfoList) {
        for (FormattedCellInfo c : cellInfoList ) {
            this.formattedBoardInfo[c.getRow()][c.getColumn()] = c;
        }

    }


    /**
     * this method draw the graphic of the single cell
     * @param printRow the row where I want to print
     * @param printCol the column where I want to print
     * @param row the row of the cell that I want to print
     * @param column the column of the cell that I want to print
     * @param cellInfo the formatted info of the cell that I want to print
     */
    private void fillCellInfo(int printRow, int printCol, int row, int column, FormattedCellInfo cellInfo) {

        Boolean isDome = cellInfo.getRoofInfo().getSecond();
        String color = cellInfo.getPawnInfo().getFirst();
        String sex = cellInfo.getPawnInfo().getSecond();

        String retString = String.valueOf(cellInfo.getHeight()) + " ";

        if (isDome) {
            retString += "X";
            print(printRow, printCol, retString, CYAN_C);
        }
        else if(color.equals("") && sex.equals("")) {
            retString += ".";
            print(printRow, printCol, retString, GREEN_C);
        }
        else {
            if (sex.equals("MALE")) {
                retString += "M";
            } else if (sex.equals("FEMALE")) {
                retString += "F";
            }
            switch (color) {
                case "BLUE":
                    print(printRow, printCol, retString, BLUE_C);
                    break;

                case "GREY":
                    print(printRow, printCol, retString, GREY_C);
                    break;

                case "WHITE":
                    print(printRow, printCol, retString, WHITE_C);
                    break;
            }
        }


    }


    //MARK : Player Panel Section ======================================================================================

    /**
     * the title of the players information panel
     */
    private String playerPanelTitle;

    /**
     * the list of information of the players
     */
    private List<FormattedPlayerInfo> playersInfo;

    /**
     * the list of names of the available gods
     */
    private List<String> cardsNameChoices;

    /**
     * the list of effect of the available gods
     */
    private List<String> cardsEffectChoices;


    /**
     * this method draw the skeleton of the players information panel
     */
    private void drawPlayersPanel() {

        int startRow = 4;
        String divider = "+------------------------------------------------------------------------------------------------------------------------------------------+";
        String empty = "|                                                                                                                                          |";

        for(int row = startRow; row <= 20; row++) {

            if(row == 4 || row == 8 || row == 20) {
                print( row, 8, divider, BLUE_GREEN_C);
            } else {
                print(row, 8, empty, BLUE_GREEN_C);
            }


        }

        fillPlayersPanel();
    }


    /**
     * this method fills the players information panel with the right type of information
     */
    private void fillPlayersPanel() {
        fillTitlePlayerPanel(playerPanelTitle);
        fillInfoPlayerPanel(playersInfo);

        if(cardsNameChoices.size() > 0) {
            fillPlayerCardChoice(cardsNameChoices, cardsEffectChoices);
        }
    }


    /**
     * this method saves the title of the players information panel
     */
    public void saveTitlePlayerPanel(String title) {
        this.playerPanelTitle = title.toUpperCase();
    }


    /**
     * this method saves the actual players information
     */
    public void saveInfoPlayerPanel(List<FormattedPlayerInfo> playerInfoList) {
        this.playersInfo = playerInfoList;

        //when i set the players info the cards are all being chosen so i can eliminate the value
        this.cardsNameChoices = new ArrayList<>();
        this.cardsEffectChoices = new ArrayList<>();
    }


    /**
     * this method saves the available cards information
     */
    public void savePlayerCardChoice(List<String> cardsNameList, List<String> cardsEffectList) {
        this.cardsNameChoices = cardsNameList;
        this.cardsEffectChoices = cardsEffectList;
    }


    /**
     * this method fills the players information panel with the available cards
     */
    private void fillPlayerCardChoice( List<String> cardsNameList, List<String> cardsEffectList) {

        int startRow = 11;
        int rowOffset = 3;
        int refRow;


        int numCards = cardsNameList.size();

        for(int card = 0; card < numCards; card++) {
            refRow = startRow + (card * rowOffset);

            print(refRow, 11, "["+String.valueOf(card)+"]", RED_C);
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


    /**
     * this method print the title of the players information panel
     */
    private void fillTitlePlayerPanel(String title) {

        if(title.length() > 132)
            throw new RuntimeException("Invalid Format for Player Panel Title !");

        String uppercaseTitle = title.toUpperCase();

        print(6, 12, uppercaseTitle, WHITE_C);

    }


    /**
     * this method fills the players information panel with the actual information
     */
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

    /**
     * the title of the choice panel
     */
    private String choicePanelTitle;

    /**
     * the available cells list
     */
    private List<Point> cellsChoicePanel;

    /**
     * the available actions list
     */
    private List<String> actionsChoicePanel;

    /**
     * the available buildings list
     */
    private List<Integer> buildingsChoicePanel;


    /**
     * this method draw the skeleton of the choice panel
     */
    private void drawChoicePanel() {

        int startRow = 23;
        String divider = "+-----------------------------------------------------------------------------+";
        String empty = "|                                                                             |";

        for(int row = startRow; row <= 58; row++) {

            if(row == 23 || row == 27 || row == 58) {
                print(row, 69, divider, BLUE_GREEN_C);
            } else {
                print(row, 69, empty, BLUE_GREEN_C);
            }
        }

        fillChoicePanel();
    }


    /**
     * this method print the right information in the choice panel
     */
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


    /**
     * this method print the title of the choice panel
     */
    private void fillTitleChoicePanel() {
        print(25, 72, choicePanelTitle);
    }


    /**
     * this method print the available cells in the choice panel
     */
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
            print(refRow, refColNum, choiceNumber, RED_C);

            choiceValue = String.valueOf(cellsChoicePanel.get(i).x) + " - " + String.valueOf(cellsChoicePanel.get(i).y);
            print(refRow, refColVal, choiceValue);


        }

        fillExit(cellsChoicePanel);
    }


    /**
     * this method print the available actions in the choice panel
     */
    private void fillActionsChoicesPanel() {

        int startRow = 31;
        int rowOffset = 3;
        int refRow;

        String choiceNumber;

        //print(29, 77, "Actions");

        for(int a = 0; a < actionsChoicePanel.size(); a++) {
            refRow = startRow + (a * rowOffset);

            choiceNumber = "[" + String.valueOf(a) + "]";
            print(refRow, 72, choiceNumber, RED_C);

            print(refRow, 77, actionsChoicePanel.get(a));
        }

        fillExit(actionsChoicePanel);
    }


    /**
     * this method print the available buildings in the choice panel
     */
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
            print(refRow, 72, choiceNumber, RED_C);

            choiceValue = "Level " + String.valueOf(buildingsChoicePanel.get(i));
            print(refRow, 77, choiceValue);
        }

        fillExit(buildingsChoicePanel);
    }


    /**
     * this method saves the title of the choice panel
     */
    public void saveTitleChoicePanel(String title) {

        if(title.length() > 72 )
            throw new RuntimeException("Invalid Format fot Choice Panel Title !");

        this.choicePanelTitle = title.toUpperCase();

    }


    /**
     * this method saves the available cells information
     */
    public void saveCellsChoicesValue(List<Point> cellsList) {
        clearChoicePanelValues();
        this.cellsChoicePanel = cellsList;
    }


    /**
     * this method saves the available actions information
     */
    public void saveActionsChoicesValue(List<String> actionsList) {
        clearChoicePanelValues();
        this.actionsChoicePanel = actionsList;
    }


    /**
     * this method saves the available buildings information
     */
    public void saveBuildingsChoicesValue(List<Integer> buildingsList) {
        clearChoicePanelValues();
        this.buildingsChoicePanel = buildingsList;
    }


    /**
     * this method clear all the saved information for the choice
     */
    public void clearChoicePanelValues() {
        this.cellsChoicePanel = new ArrayList<>();
        this.actionsChoicePanel = new ArrayList<>();
        this.buildingsChoicePanel = new ArrayList<>();
    }


    /**
     * this method print the exit value in the choice panel
     */
    private void fillExit(List<?> list) {

        String exitNumber = "[" + String.valueOf(list.size()) + "]";
        print(55, 124, exitNumber, RED_C);

        String exit = "Exit";
        print(55, 130, exit);

    }


    //MARK : Challenger Panel Section ======================================================================================

    /**
     * the title of the players information panel
     */
    private String challengerPanelTitle;

    /**
     * the list of names of the available gods
     */
    private List<String> cardsNameChallenger;

    /**
     * the list of effect of the available gods
     */
    private List<String> cardsEffectChallenger;


    /**
     * this method draw the skeleton of the challenger cards choice panel
     */
    private void drawChallengerPanel() {

        int startRow = 4;
        String divider = "+------------------------------------------------------------------------------------------------------------------------------------------+";
        String empty = "|                                                                                                                                          |";

        for(int row = startRow; row <= 56; row++) {

            if(row == 4 || row == 8 || row == 56) {
                print( row, 8, divider, BLUE_GREEN_C);
            } else {
                print(row, 8, empty, BLUE_GREEN_C);
            }


        }

        fillChallengerPanel();
    }


    /**
     * this method fills the players information panel with the right type of information
     */
    private void fillChallengerPanel() {
        fillTitleChallengerPanel(challengerPanelTitle);
        fillChallengerChoice(cardsNameChallenger, cardsEffectChallenger);
    }


    /**
     * this method fills the players information panel with the available cards
     */
    private void fillChallengerChoice( List<String> cardsNameList, List<String> cardsEffectList) {

        int startRow = 11;
        int rowOffset = 3;
        int refRow;


        int numCards = cardsNameList.size();

        for(int card = 0; card < numCards; card++) {
            refRow = startRow + (card * rowOffset);

            print(refRow, 11, "["+String.valueOf(card)+"]", RED_C);
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


    /**
     * this method print the title of the players information panel
     */
    private void fillTitleChallengerPanel(String title) {

        if(title.length() > 132)
            throw new RuntimeException("Invalid Format for Player Panel Title !");

        String uppercaseTitle = title.toUpperCase();

        print(6, 12, uppercaseTitle, WHITE_C);

    }


    public void saveTitleChallengerPanel(String title) {
        this.challengerPanelTitle = title;
    }


    /**
     * this method saves the available cards information
     */
    public void saveChallengerChoice(List<String> cardsNameList, List<String> cardsEffectList) {
        this.cardsNameChallenger = cardsNameList;
        this.cardsEffectChallenger = cardsEffectList;
    }



}
