package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

public class MoreMoveEffect extends EffectDecorator {

    public MoreMoveEffect(Effect e) {
        super(e);
    }

   /* @Override
   public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        if(super.effect.getState().getClass().equals(MoveStateTest.class)) {
            Cell startPosition = designatedPawn.getPosition();
            changeState(new MoveAndBuildStateTest(this));
            effect = new NotMoveBackEffect(effect, startPosition);
        }
        else if(super.effect.getState().getClass().equals(MoveAndBuildStateTest.class)) {
            changeState(new BuildStateTest(this));
            effect = new BasicEffect();
        }

        Consequence consequence = super.movePawn(gameBoard, designatedPawn, nextPosition);

        return consequence;
    }
    */
}
