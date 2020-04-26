package it.polimi.ingsw.client;

import java.util.Objects;

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
        }
        else {
            this.isPawnHere = false;
        }
        this.pawnInfo = Couple.create(pawnColor, pawnSex);


        this.roofInfo = Couple.create(roofLevel, roofIsDome);
    }


    public FormattedCellInfo(Integer row, Integer column, Integer height, Integer roofLevel, Boolean roofIsDome) {
        this(row, column, height, "", "", roofLevel, roofIsDome);
    }


    // ======================================================================================


    public static FormattedCellInfo create(Integer row, Integer column, Integer height, String pawnColor, String pawnSex, Integer roofLevel, Boolean roofIsDome) {
        return new FormattedCellInfo(row, column, height, pawnColor, pawnSex, roofLevel, roofIsDome);
    }


    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getHeight() {
        return height;
    }

    public Boolean getPawnHere() {
        return isPawnHere;
    }

    public Couple<String, String> getPawnInfo() {
        return pawnInfo;
    }

    public Couple<Integer, Boolean> getRoofInfo() {
        return roofInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormattedCellInfo that = (FormattedCellInfo) o;
        return Objects.equals(row, that.row) &&
                Objects.equals(column, that.column) &&
                Objects.equals(height, that.height) &&
                Objects.equals(isPawnHere, that.isPawnHere) &&
                Objects.equals(pawnInfo, that.pawnInfo) &&
                Objects.equals(roofInfo, that.roofInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, height, isPawnHere, pawnInfo, roofInfo);
    }
}
