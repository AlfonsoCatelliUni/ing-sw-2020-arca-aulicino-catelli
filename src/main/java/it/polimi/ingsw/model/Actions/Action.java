package it.polimi.ingsw.model.Actions;

/**
 * this interface action is used to handler the possible actions of the game which players can choose
 */
public interface Action {

    /**
     * the identifier of the action, it's a string that characterises different actions
     * @return the string of the identifier
     */
    String getActionID();

}
