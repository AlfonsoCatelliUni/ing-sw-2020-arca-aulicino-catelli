package it.polimi.ingsw.client;

public class FormattedSimpleCell {


    private Integer row;

    private Integer column;


    public FormattedSimpleCell(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }


    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getNumber() {
        return (row * 5) + column;
    }



}
