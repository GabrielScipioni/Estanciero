package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Services.Bank;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ChargeAllPlayersStrategyTest {
    @Test
    public void testExecute() {
        Game game = mock(Game.class);
        Bank bank = mock(Bank.class);
        Player player = mock(Player.class);
        Square square = mock(Square.class);
        PlayerIterator playerIterator = mock(PlayerIterator.class);
        CardType cardType = CardType.LUCK;
        game.setPlayersIterator(playerIterator);
        int amount = 50;
        List<Player> players = new ArrayList<>();


        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        players.add(player);
        players.add(player1);
        players.add(player2);
        when(game.getBank()).thenReturn(bank);
        when(game.getPlayersIterator()).thenReturn(playerIterator);
        when(playerIterator.getPlayersListTest()).thenReturn(players);
        Mockito.when(game.getPlayersIterator().getPlayersListTest()).thenReturn(players);

        ChargeAllPlayersStrategy strategy = new ChargeAllPlayersStrategy();

        strategy.execute(game, player, square, amount, cardType);

        verify(game.getBank(), times(1)).makePlayerPayToOtherPlayer(game, player, amount, player1);
        verify(game.getBank(), times(1)).makePlayerPayToOtherPlayer(game, player, amount, player2);
        verify(game.getBank(), times(0)).makePlayerPayToOtherPlayer(game, player, amount, player);  // No debería cobrarse a sí mismo
    }
}
