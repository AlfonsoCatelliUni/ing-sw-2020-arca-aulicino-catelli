package it.polimi.ingsw.model.Consequence;

import it.polimi.ingsw.model.GameConsequenceHandler;

/**
 * consequence used with Pattern Visitor, some actions generate different consequence managed in Game using the pattern
 */
public interface Consequence {


    void accept(GameConsequenceHandler game);


    String getNickname();

    void setNickname(String nickname);


}
