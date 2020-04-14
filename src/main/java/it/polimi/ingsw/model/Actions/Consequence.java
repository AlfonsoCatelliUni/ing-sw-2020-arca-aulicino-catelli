package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;

/**
 * consequence used with Pattern Visitor
 */
public interface Consequence {


    void accept(GameConsequenceHandler game);


}
