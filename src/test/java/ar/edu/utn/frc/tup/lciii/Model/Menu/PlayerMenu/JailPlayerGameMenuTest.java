package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Dice;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu.JailPlayerGameMenu;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class JailPlayerGameMenuTest {

    @Mock
    private Game game;
    @Mock
    private Player player;
    @Mock
    private Dice dice;
    @Mock
    private Board board;
    @Mock
    private Bank bank;
    @Mock
    private PlayerIterator playerIterator;

    private JailPlayerGameMenu jailPlayerGameMenu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(game.getDice()).thenReturn(dice);
        when(game.getBoard()).thenReturn(board);
        when(game.getBank()).thenReturn(bank);
        when(player.getName()).thenReturn("Player 1");
        when(playerIterator.getPlayersListTest()).thenReturn(new ArrayList<>(List.of(player)));
        when(game.getPlayersIterator()).thenReturn(playerIterator);
        jailPlayerGameMenu = new JailPlayerGameMenu(game, player);
    }



    @Test
    public void testPayFine() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = JailPlayerGameMenu.class.getDeclaredClasses()[0].getDeclaredConstructor(Integer.class, Game.class, Player.class);
        constructor.setAccessible(true);
        Object payFine = constructor.newInstance(1, game, player);

        when(player.getBankrupted()).thenReturn(false);

        Method executeMethod = payFine.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(payFine);

        verify(game).addEvent("Player 1 eligió: pagar multa");
        verify(game.getDice()).throwDice(game, player);
        verify(bank).chargePlayer(player, 1000);
        verify(board).moveSteps(game, player, 0);
    }

    @Test
    public void testUseCardWithoutCards() throws Exception {
        Constructor<?> constructor = JailPlayerGameMenu.class.getDeclaredClasses()[1].getDeclaredConstructor(Integer.class, Game.class, Player.class);
        constructor.setAccessible(true);
        Object useCard = constructor.newInstance(2, game, player);

        when(player.getCards()).thenReturn(new ArrayList<>());

        Method executeMethod = useCard.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(useCard);

        verify(player).getCards();
    }

    @Test
    public void testUseCardWithCards() throws Exception {
        Card card = mock(Card.class);
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        ByteArrayInputStream in = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(in);

        Constructor<?> constructor = JailPlayerGameMenu.class.getDeclaredClasses()[1].getDeclaredConstructor(Integer.class, Game.class, Player.class);
        constructor.setAccessible(true);
        Object useCard = constructor.newInstance(2, game, player);

        when(player.getCards()).thenReturn(cards);
        when(player.getName()).thenReturn("Player 1");
        when(card.getMessage()).thenReturn("Card 1");

        Method executeMethod = useCard.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(useCard);

        verify(player, times(9)).getCards();
        verify(game).addEvent("Player 1 eligió: usar carta");
    }
}
