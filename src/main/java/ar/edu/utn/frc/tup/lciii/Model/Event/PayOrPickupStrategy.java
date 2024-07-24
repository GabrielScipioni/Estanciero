package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.BotPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

import java.util.ArrayList;
import java.util.List;

/**
 * When a player has the option to pay a fine OR draw a card.
 * Bots will always choose to pay the fine.
 *
 * @see "Pay $200 or Pick up a Luck card!"
 */
public class PayOrPickupStrategy implements EventStrategy {
    UserInteraction ui = new UserInteraction();

    /**
     * Pay a fine or draw a card.
     * Bots always pay.
     *
     * @param game The current game instance.
     * @param player The player executing the event.
     * @param amount Fine to pay.
     * @param cardType The type of card to draw.
     *
     */
    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {
        if(player instanceof BotPlayer) {
            game.getBank().payToBank(game, amount, player);
            return;
        }
        String pay = "Pay " + amount;
        String pick = "Pick up a " + cardType.toString().toLowerCase() + " card";
        List<String> options = new ArrayList<>();
        options.add(pay);
        options.add(pick);
        Integer answer = ui.askValidSelection(options, "Choose one of the following!");
        answer += 1; //le sumo 1 porque retorna el indice.

        if(answer.equals(1)) {
            game.getBank().payToBank(game, amount, player);
        } else {
            game.getDeck().takeAndExecute(game, player, cardType);
        }
    }
}
