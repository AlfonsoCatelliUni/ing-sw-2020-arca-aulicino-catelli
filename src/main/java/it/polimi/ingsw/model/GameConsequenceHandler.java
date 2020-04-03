package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.BlockConsequence;
import it.polimi.ingsw.model.Actions.NoConsequence;
import it.polimi.ingsw.model.Actions.VictoryConsequence;

public interface GameConsequenceHandler {


    void receiveConsequence(Consequence consequence);


    void doConsequence(VictoryConsequence consequence);


    void doConsequence(BlockConsequence consequence);

    void doConsequence(NoConsequence consequence);


}
