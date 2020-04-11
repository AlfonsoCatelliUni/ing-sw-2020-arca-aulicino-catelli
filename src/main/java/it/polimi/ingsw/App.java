package it.polimi.ingsw;

import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;

public class App  {


    public static void main( String[] args ) {

        System.out.println( "Hello Bitch" );

    }


    /**
     * I want the information of this cell encoded in the following method "Height,Pawn" :
     *      - Height is the building level in this cell (0-1-2-3-4)
     *      - Pawn is the color's first letter, upper case if male, lower case if female (B-b, G-g, W-w)
     *      - Pawn is "x" if there is a dome in this cell
     *      - Pawn is "." if there is no builder in this cell
     * @param designatedCell cell
     * @return the encoded string
     */
    public String getStringCellInfo(Cell designatedCell) {


        String retString = String.valueOf(designatedCell.getHeight());

        if (designatedCell.getRoof().getIsDome()) {
            return retString + "x";
        }
        else if(!designatedCell.getBuilderHere()) {
            return retString + ".";
        }
        else {

            Color pawnColor = designatedCell.getPawnInThisCell().getColor();
            Sex pawnSex = designatedCell.getPawnInThisCell().getSex();

            if ( pawnColor == Color.BLUE ) {

                if ( pawnSex == Sex.MALE ) {
                    return retString + "B";
                }
                else if ( pawnSex == Sex.FEMALE ) {
                    return retString + "b";
                }

            }
            else if ( pawnColor == Color.GREY ) {

                if ( pawnSex == Sex.MALE ) {
                    return retString + "G";
                }
                else if ( pawnSex == Sex.FEMALE ) {
                    return retString + "g";
                }

            }
            else if ( pawnColor == Color.WHITE ){

                if ( pawnSex == Sex.MALE ) {
                    return retString + "W";
                }
                else if ( pawnSex == Sex.FEMALE ) {
                    return retString + "w";
                }

            }

        }

        return "error";
    }


    /*
    public String boardToString() {

        String retString = "";

        retString = ("╔═══╦════╦════╦════╦════╦════╗\n" +
                "║   ║ 1  ║ 2  ║ 3  ║ 4  ║ 5  ║\n" +
                "╠═══╬════╬════╬════╬════╬════╣\n" +
                "║ A ║ " + gameBoard.getStringCellInfo(0,0) + " ║ "
                + gameBoard.getStringCellInfo(0,1) + " ║ "
                + gameBoard.getStringCellInfo(0,2) + " ║ "
                + gameBoard.getStringCellInfo(0,3) + " ║ "
                + gameBoard.getStringCellInfo(0,4) + " ║\n" +
                "║ B ║ "+ gameBoard.getStringCellInfo(1,0) + " ║ "
                + gameBoard.getStringCellInfo(1,1) + " ║ "
                + gameBoard.getStringCellInfo(1,2) + " ║ "
                + gameBoard.getStringCellInfo(1,3) + " ║ "
                + gameBoard.getStringCellInfo(1,4) + " ║\n" +
                "║ C ║ " + gameBoard.getStringCellInfo(2,0) + " ║ "
                + gameBoard.getStringCellInfo(2,1) + " ║ "
                + gameBoard.getStringCellInfo(2,2) + " ║ "
                + gameBoard.getStringCellInfo(2,3) + " ║ "
                + gameBoard.getStringCellInfo(2,4) + " ║\n" +
                "║ D ║ " + gameBoard.getStringCellInfo(3,0) + " ║ "
                + gameBoard.getStringCellInfo(3,1) + " ║ "
                + gameBoard.getStringCellInfo(3,2) + " ║ "
                + gameBoard.getStringCellInfo(3,3) + " ║ "
                + gameBoard.getStringCellInfo(3,4) + " ║\n" +
                "║ E ║ " + gameBoard.getStringCellInfo(4,0) + " ║ "
                + gameBoard.getStringCellInfo(4,1) + " ║ "
                + gameBoard.getStringCellInfo(4,2) + " ║ "
                + gameBoard.getStringCellInfo(4,3) + " ║ "
                + gameBoard.getStringCellInfo(4,4) + " ║\n" +
                "╚═══╩════╩════╩════╩════╩════╝\n\n");


        return retString;
    }

     */


}
