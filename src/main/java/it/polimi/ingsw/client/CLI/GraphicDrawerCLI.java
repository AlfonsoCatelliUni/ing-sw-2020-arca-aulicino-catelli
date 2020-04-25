package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.Couple;
import it.polimi.ingsw.client.FormattedPlayerInfo;

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

    private final int ROWS = 62;

    private final int COLUMNS = 154;



    private char[][] screen;

    private char[][] boardCellsInfo;


    // ======================================================================================


    public GraphicDrawerCLI() {
        screen = new char[ROWS][COLUMNS];
        boardCellsInfo = new char[5][10];
        resetBoardCellsInfo();
        draw();
    }


    public void fillTest() {
        clear();
        drawPlayersPanel();
        drawChoicePanel();
        drawSantoriniLogo();
        drawBoardPanel();
        fillBoardPanel();

        List<FormattedPlayerInfo> players = new ArrayList<>();
        players.add(new FormattedPlayerInfo("Alfonsoooooooooooooooooooooo30", "WHITE", Couple.create("Apollo", "Your Move: Your Worker may move into an opponent Worker's space (using normal movement rules) and force their Worker to the space yours just left (swapping their positions).")));
        players.add(new FormattedPlayerInfo("Massi", "BLUE", Couple.create("Minotaur", "Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.")));

        setTitlePlayerPanel("players information");
        setInfoPlayerPanel(players);

        setTitleChoicePanel("do your choice");
    }


    public static void main(String[] args) {
        GraphicDrawerCLI cli = new GraphicDrawerCLI();

        cli.show();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cli.clearScreen();

        cli.fillTest();
        cli.show();
    }


    // ======================================================================================


    public void draw() {
        clear();
        drawPlayersPanel();
        drawChoicePanel();
        drawSantoriniLogo();
        drawBoardPanel();
        fillBoardPanel();
    }


    public void show() {

        for(int row = 0; row < ROWS; row++) {
            System.out.println( String.valueOf(screen[row]) );
        }

    }


    public void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    // ======================================================================================


    private void clear() {

        for(int row = 0; row < ROWS; row++) {
            for(int column = 0; column < COLUMNS; column++) {

                if(row == 0 || row == ROWS-1) {
                    screen[row][column] = '-';
                }
                else if(column == 0 || column == COLUMNS-1) {
                    screen[row][column] = '|';
                }
                else {
                    screen[row][column] = ' ';
                }

            }
        }

        screen[0][0] = '+';
        screen[0][153] = '+';
        screen[61][153] = '+';
        screen[61][0] = '+';

    }


    private void print(int row, int column, String p) {
        for (int off = 0; off < p.length(); off++) {
            screen[row][column + off] = p.charAt(off);
        }
    }


    private void drawSantoriniLogo() {

        print(25, 10, "  _____             _             _       _ ");
        print(26, 10, " / ____|           | |           (_)     (_)");
        print(27, 10, "| (___   __ _ _ __ | |_ ___  _ __ _ _ __  _ ");
        print(28,  10, " \\___ \\ / _` | '_ \\| __/ _ \\| '__| | '_ \\| |");
        print(29, 10, " ____) | (_| | | | | || (_) | |  | | | | | |");
        print(30, 10, "|_____/ \\__,_|_| |_|\\__\\___/|_|  |_|_| |_|_|");

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
                print(row, 8, divider);
            } else {
                print(row, 8, empty);
            }

        }


    }


    private void resetBoardCellsInfo() {

        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 10; column++) {
                if(column % 2 == 0) {
                    boardCellsInfo[row][column] = '0';
                }
                else {
                    boardCellsInfo[row][column] = '.';
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
                    print(rowRef, colRef, String.valueOf(col));
                }
                else if(col == 0) {
                    print(rowRef, colRef, String.valueOf(row));
                }
                else {
                    print(rowRef, colRef-1, getCellInfo(row, col));
                }

            }
        }

    }


    public void setCellInfo(int row, int column, String info) {

        int refCol = column * 2;

        if(info.length() != 2) {
            throw new RuntimeException("Wrong Formatting CellInfo !");
        }
        else {
            boardCellsInfo[row-1][refCol-2] = info.charAt(0);
            boardCellsInfo[row-1][refCol-1] = info.charAt(1);
        }

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

        return boardCellsInfo[row-1][refCol-2]+" "+boardCellsInfo[row-1][refCol-1];
    }


    //MARK : Player Panel Section ======================================================================================


    private void drawPlayersPanel() {

        int startRow = 4;
        String divider = "+------------------------------------------------------------------------------------------------------------------------------------------+";
        String empty = "|                                                                                                                                          |";

        for(int row = startRow; row <= 20; row++) {

            if(row == 4 || row == 8 || row == 20) {
                print( row, 8, divider);
            } else {
                print(row, 8, empty);
            }


        }
        /*
        print( 5, 8, empty);
        print( 6, 8, empty);
        print( 7, 8, empty);
        print( 8, 8, divider);
        print( 9, 8, empty);
        print( 10,8, empty);
        print( 11,8, empty);
        print( 12,8, empty);
        print( 13,8, empty);
        print( 14,8, empty);
        print( 15,8, empty);
        print( 16,8, empty);
        print( 17,8, empty);
        print( 18,8, empty);
        print( 19,8, empty);
        print( 20,8, divider);

         */

    }


    public void setTitlePlayerPanel(String title) {

        if(title.length() > 132)
            throw new RuntimeException("Invalid Format for Player Panel Title !");

        String uppercaseTitle = title.toUpperCase();

        print(6, 12, uppercaseTitle);

    }


    public void setInfoPlayerPanel( List<FormattedPlayerInfo> playerInfoList ) {

        //nickname length 30
        //color length 5
        //effect length 87 * 2 rows (174)

        int startRow = 11;
        int rowOffset = 3;
        int refRow;


        int numPlayer = playerInfoList.size();

        if(numPlayer != 3 && numPlayer != 2) {
            throw new RuntimeException("Invalid Number of Player Passed to InfoPlayerPanel !");
        }
        else {

            for(int player = 0; player < numPlayer; player++) {
                refRow = startRow + (player * rowOffset);

                print(refRow, 12, String.valueOf(player+1));
                print(refRow, 16, playerInfoList.get(player).getNickname());
                print(refRow, 49, playerInfoList.get(player).getColor());
                print(refRow, 57, playerInfoList.get(player).getCard().getSecond().substring(0, 87));
                print(refRow+1, 57, playerInfoList.get(player).getCard().getSecond().substring(87));
            }
        }


    }


    //MARK : Player Panel Section ======================================================================================


    private void drawChoicePanel() {

        int startRow = 23;
        String divider = "+----------------------------------------------------------------------------+";
        String empty = "|                                                                            |";

        for(int row = startRow; row <= 58; row++) {

            if(row == 23 || row == 27 || row == 58) {
                print(row, 70, divider);
            } else {
                print(row, 70, empty);
            }
        }

        setChoicesNumber();
    }


    public void setTitleChoicePanel(String title) {

        if(title.length() > 72 )
            throw new RuntimeException("Invalid Format fot Choice Panel Title !");

        String uppercaseTitle = title.toUpperCase();

        print(25, 73, uppercaseTitle);

    }


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


    public void setChoicesValue(List<Couple<Integer, Integer>> cellsList) {

        int startRow = 31;
        int rowOffset = 3;
        int refRow;

        String choiceValue;

        print(29, 78, "Row - Column");

        for(int c = 0; c < cellsList.size(); c++) {
            refRow = startRow + (c * rowOffset);

            choiceValue = String.valueOf(cellsList.get(c).getFirst()) + " - " + String.valueOf(cellsList.get(c).getFirst());

            print(refRow, 80, choiceValue);
        }

    }

}
