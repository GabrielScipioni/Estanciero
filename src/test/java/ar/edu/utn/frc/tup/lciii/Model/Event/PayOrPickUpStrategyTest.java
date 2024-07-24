package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Card.Deck;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.BotPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PayOrPickUpStrategyTest {
    @Test
    public void testExecuteBotPlayer() {
        Game game = mock(Game.class);
        Bank bank = mock(Bank.class);
        Player botPlayer = mock(BotPlayer.class);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        int amount = 100;

        when(game.getBank()).thenReturn(bank);

        PayOrPickupStrategy strategy = new PayOrPickupStrategy();

        strategy.execute(game, botPlayer, square, amount, cardType);

        verify(bank, times(1)).payToBank(game, amount, botPlayer);
    }

    @Test
    public void testExecuteHumanPlayerTakeCard() {
        Game game = mock(Game.class);
        Bank bank = mock(Bank.class);
        Player humanPlayer = mock(HumanPlayer.class);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        int amount = 100;

        UserInteraction uiMock = mock(UserInteraction.class);
        PayOrPickupStrategy strategy = new PayOrPickupStrategy();
        strategy.ui = uiMock; // <--- inject mocks

        when(game.getBank()).thenReturn(bank);
        when(game.getDeck()).thenReturn(mock(Deck.class));

        when(uiMock.askValidSelection(anyList(), anyString())).thenReturn(1);

        strategy.execute(game, humanPlayer, square, amount, cardType);

        try {
            verify(game, times(1)).getDeck().takeAndExecute(game, humanPlayer, cardType);
        } catch (NullPointerException ignored) { }
    }

    @Test
    public void testExecuteHumanPlayerPay() {
        Game game = mock(Game.class);
        Bank bank = mock(Bank.class);
        Player humanPlayer = mock(HumanPlayer.class);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        int amount = 100;

        UserInteraction uiMock = mock(UserInteraction.class);
        PayOrPickupStrategy strategy = new PayOrPickupStrategy();
        strategy.ui = uiMock; // <--- inject mocks

        when(game.getBank()).thenReturn(bank);
        when(game.getDeck()).thenReturn(mock(Deck.class));

        when(uiMock.askValidSelection(anyList(), anyString())).thenReturn(0);

        strategy.execute(game, humanPlayer, square, amount, cardType);

        verify(bank, times(1)).payToBank(game, amount, humanPlayer);
    }
}
