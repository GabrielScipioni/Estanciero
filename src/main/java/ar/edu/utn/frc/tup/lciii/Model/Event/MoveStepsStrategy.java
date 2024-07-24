package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;


public class MoveStepsStrategy implements EventStrategy {
    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {
        game.getBoard().moveSteps(game, player,amount);
    }
}

