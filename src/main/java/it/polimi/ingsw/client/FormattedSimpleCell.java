package it.polimi.ingsw.client;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormattedSimpleCell {


    private Integer row;

    private Integer column;


    public FormattedSimpleCell(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }


    public static List<FormattedSimpleCell> generateFromPointList(List<Point> pointList) {
        List<FormattedSimpleCell> returnList = new ArrayList<>();

        for (Point p : pointList ) {
            returnList.add( new FormattedSimpleCell( p.x, p.y) );
        }

        return returnList;
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
