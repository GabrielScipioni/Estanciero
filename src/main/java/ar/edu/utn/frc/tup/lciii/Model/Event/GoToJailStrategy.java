package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

// ex Go-To-Jail-Strategy.
//Marche preso directamente.
public class GoToJailStrategy implements EventStrategy {
    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {
        game.getBoard().sendToJail(game,player);
    }
}
