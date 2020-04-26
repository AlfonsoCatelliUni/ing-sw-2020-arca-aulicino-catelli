package it.polimi.ingsw.client;

public class FormattedCellInfo {

    private Integer row;

    private Integer column;

    private Integer height;

    private Boolean isPawnHere;

    private Couple<String, String> pawnInfo;

    private Couple<Integer, Boolean> roofInfo;


    // ======================================================================================


    public FormattedCellInfo(Integer row, Integer column, Integer height, String pawnColor, String pawnSex, Integer roofLevel, Boolean roofIsDome) {
        this.row = row;
        this.column = column;
        this.height = height;

        if( !pawnColor.equals("") && !pawnSex.equals("") ) {
            this.isPawnHere = true;
            this.pawnInfo = Couple.create(pawnColor, pawnSex);
        }

        this.roofInfo = Couple.create(roofLevel, roofIsDome);
    }


    public FormattedCellInfo(Integer row, Integer column, Integer height, Integer roofLevel, Boolean roofIsDome) {
        this(row, column, height, "", "", roofLevel, roofIsDome);
    }


    // ======================================================================================


    public static FormattedCellInfo create(Integer row, Integer column, Integer height, String pawnColor, String pawnSex, Integer roofLevel, Boolean roofIsDome) {
        return new FormattedCellInfo(row, column, height, pawnColor, pawnSex, roofLevel, roofIsDome);
    }


}
