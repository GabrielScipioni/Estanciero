package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

import java.util.List;

/**
 * Event when a player collects money from all other players. This could represent
 *
 * @see "It's your birthday! Collect $200 from all players!"
 */
public class ChargeAllPlayersStrategy implements EventStrategy {

    /**
     * Collects a specified amount of money from all other players in the game.
     *
     * @param game The current game.
     * @param player The player collecting the money.
     * @param amount The money each player has to pay.
     */
    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {

        List<Player> players = game.getPlayersIterator().getPlayersListTest();

        for (Player p : players) {
            if (!p.equals(player)) { // No le cobras al q ejecuta el evento
                game.getBank().makePlayerPayToOtherPlayer(game, player, amount, p);
            }
        }
    }
}

