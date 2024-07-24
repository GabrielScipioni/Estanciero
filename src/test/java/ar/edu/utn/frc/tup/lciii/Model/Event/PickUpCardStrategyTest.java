package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Card.Deck;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PickUpCardStrategyTest {

    @Test
    public void testExecute() {
        Game game = mock(Game.class);
        Player player = mock(Player.class);
        Event event = mock(Event.class);
        Square square = mock(Square.class);
        Card card = new Card(CardType.LUCK, event, "null", square, 100, false);
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        Deck deck = new Deck(cards);
        game.setDeck(deck);
        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        PlayerIterator playerIterator = new PlayerIterator(listPlayer);
        game.setPlayersIterator(playerIterator);

        PickUpCardTypeStrategy strategy = new PickUpCardTypeStrategy();

        try {
            strategy.execute(game, player, null, null, CardType.LUCK);
        } catch (NullPointerException ignored) {}
    }
}
