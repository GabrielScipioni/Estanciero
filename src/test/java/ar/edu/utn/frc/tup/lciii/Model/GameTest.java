package ar.edu.utn.frc.tup.lciii.Model;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Deck;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class GameTest {

    @Test
    public void testNoArgsConstructor() {
        Game game = new Game();
        assertNotNull(game, "El Game no puede ser null desp de crearlo.");
    }

    @Test
    public void testGameConstructorAndGetters() {

        Board board = new Board();
        User owner = new User("test", "test", "test");


        Game game = new Game(board, owner);

        assertNotNull(game);
        assertEquals(board, game.getBoard());
        assertEquals(owner, game.getUserOwner());

//        Board newBoard = new Board();
//        game.setBoard(newBoard);
//        assertEquals(newBoard, game.getBoard());
//
//        User newOwner = new User("test", "test", "test");
//        game.setUserOwner(newOwner);
//        assertEquals(newOwner, game.getUserOwner());


    }

    @Test
    public void getSetBankTest() {
        Game game = new Game();
        Bank bank = new Bank();
        game.setBank(bank);

        assertNotNull(game.getBank());
        assertEquals(bank, game.getBank());
    }

    @Test
    public void getSetDeckTest() {
        Game game = new Game();
        Deck deck = mock(Deck.class);
        game.setDeck(deck);

        assertNotNull(game.getDeck());
        assertEquals(deck, game.getDeck());
    }

    @Test
    public void getDiceTest() {
        Game game = new Game();

        assertNotNull(game.getDice());
    }

    @Test
    public void getPlayersIteratorTest() {
        Game game = new Game();
        PlayerIterator players = mock(PlayerIterator.class);

        game.setPlayersIterator(players);

        assertNotNull(game.getPlayersIterator().getPlayersListTest());
    }

    @Test
    public void eventsTest() {
        Game game = new Game();
        for (int i = 0 ; i < 100 ; i++) {
            game.addEvent(new String("x"));
        }
        assertNotNull(game.getEvents());
        assertEquals(100, game.getEvents().size());
        game.addEvent(new String("a"));
        assertEquals(100, game.getEvents().size());
    }
}
