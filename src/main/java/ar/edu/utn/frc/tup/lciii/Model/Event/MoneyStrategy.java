package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

/**
 * Strategy where a player either receives or pays money. The amount determines
 * if the player receives money (positive) or pays (negative).
 *
 * @see "You won the beauty contest! collect $150!"
 */
public class MoneyStrategy implements EventStrategy {

    /**
     * Executes the money-related event. If is positive, the player receives
     * money, negative will make him pay to the bank.
     *
     * @param game The current game.
     * @param player The player triggering the event.
     * @param amount The money involved in the event, can be positive or negative.
     */
    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {
        if(amount > 0) {
            game.getBank().payPlayer(player, amount);
        } else {
            game.getBank().payToBank(game, Math.abs(amount), player); //valor absoluto de ammount para numeros negativos.
        }
    }
}
