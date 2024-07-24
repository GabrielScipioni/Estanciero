package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

/**
 * Event where a player picks up a card from any deck.
 */
public class PickUpCardTypeStrategy implements EventStrategy {

    /**
     * Picks up a card and executes it.
     *
     * @param game The current game.
     * @param player Player who triggers the event.
     * @param cardType Either LUCK or DESTINY.
     */
    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {
        game.getDeck().takeAndExecute(game, player, cardType);
    }
}
