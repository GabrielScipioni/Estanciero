package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

// ex Go-To-Jail-Strategy.
//Con esta tarjeta sale usted de la Comisaría. Conservela o véndala.
public class GetOutOfJailStrategy implements EventStrategy {
    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {
        game.getDice().throwDice();
        game.getBoard().moveSteps(game,player,game.getDice().getTotalValue());
    }
}
