package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the possible three colors of the pawns, one color per players
 */
public enum Color {

    BLUE,
    GREY,
    WHITE,
    ;


    Color() {
    }

    public static List<Color> getRandomColors(int size) {

        Color[] colors = Color.values();

        return new ArrayList<>(Arrays.asList(colors).subList(0, size));
    }


}
