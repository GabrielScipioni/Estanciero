package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MoneyStrategyTest {
    @Test
    public void testExecutePositiveAmount() {
        Game game = Mockito.mock(Game.class);
        Bank bank = Mockito.mock(Bank.class);
        Player player = Mockito.mock(Player.class);
        Square square = Mockito.mock(Square.class);
        when(game.getBank()).thenReturn(bank);
        CardType cardType = CardType.LUCK;
        int amount = 200;

        MoneyStrategy strategy = new MoneyStrategy();

        strategy.execute(game, player, square, amount, cardType);

        verify(game.getBank(), times(1)).payPlayer(player, amount);
        verify(game.getBank(), times(0)).payToBank(game, Math.abs(amount), player);
    }

    @Test
    public void testExecuteNegativeAmount() {
        Game game = Mockito.mock(Game.class);
        Bank bank = Mockito.mock(Bank.class);
        Player player = Mockito.mock(Player.class);
        Square square = Mockito.mock(Square.class);
        when(game.getBank()).thenReturn(bank);
        CardType cardType = CardType.LUCK;
        int amount = -200;

        MoneyStrategy strategy = new MoneyStrategy();

        strategy.execute(game, player, square, amount, cardType);

        verify(game.getBank(), times(0)).payPlayer(player, amount);
        verify(game.getBank(), times(1)).payToBank(game, Math.abs(amount), player);
    }
}
