package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Actions.*;

public interface GameConsequenceHandler {


    void receiveConsequence(Consequence consequence);


    void doConsequence(VictoryConsequence consequence);

    void doConsequence(BlockConsequence consequence);

    void doConsequence(NoConsequence consequence);

    void doConsequence(DestroyTowersConsequence consequence);


}
