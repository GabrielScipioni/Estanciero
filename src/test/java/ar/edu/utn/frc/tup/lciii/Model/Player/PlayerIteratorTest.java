package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Dice;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PlayerIteratorTest {
    List<Player> players = new ArrayList<>();
    Game game;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        players.add(new HumanPlayer(new ArrayList<>(), false));
        players.add(new AggressiveBot(new ArrayList<>(), true));
        players.add(new ModerateBot(new ArrayList<>(), false));


        game = new Game(new Board(),new User("test","test","test"));

        Field gameDiceField = Game.class.getDeclaredField("dice");
        gameDiceField.setAccessible(true);

        Dice dice = new Dice();
        Dice diceMock = Mockito.spy(dice);
        diceMock.throwDice();


        when(diceMock.getTotalValue()).thenReturn(4).thenReturn(4).thenReturn(3).thenReturn(5);

        gameDiceField.set(game,diceMock);
    }
    @Test
    void next() {
        PlayerIterator playerIterator = new PlayerIterator(null);

        assertNull(playerIterator.next());
        playerIterator = new PlayerIterator(new ArrayList<>());
        assertNull(playerIterator.next());

        playerIterator = new PlayerIterator(players);

        playerIterator.initializeTurns(game);

        Assertions.assertEquals(playerIterator.next(), players.get(2));
        Assertions.assertEquals(playerIterator.next(), players.get(0));
        Assertions.assertNotEquals(playerIterator.next(), players.get(1));


    }


    @Test
    void initializeTurns() {
        PlayerIterator iterator = new PlayerIterator(players);
        iterator.initializeTurns(game);

        List<Player> sortedPlayers = iterator.getPlayersListTest();
        Assertions.assertEquals(3, sortedPlayers.size());
        Assertions.assertEquals(sortedPlayers.get(0), players.get(2));
        Assertions.assertEquals(sortedPlayers.get(1), players.get(0));
        Assertions.assertEquals(sortedPlayers.get(2), players.get(1));
    }

    @Test
    public void initializeTurnsTest() {
        List<Player> emptyPlayerList = null;
        PlayerIterator iterator = new PlayerIterator(emptyPlayerList);
        Game game = new Game();
        iterator.initializeTurns(game);

        assertNull(emptyPlayerList);
    }

    @Test
    public void nextTestNull() {
        List<Player> playerList = new ArrayList<>();
        PlayerIterator iterator = new PlayerIterator(playerList);
        iterator.setPlayers(Collections.emptyList());

        assertNull(iterator.next());
    }
    @Test
    public void getPlayers() {
        List<Player> playerList = new ArrayList<>();
        PlayerIterator iterator = new PlayerIterator(playerList);
        assertEquals(iterator.getPlayersListTest(), playerList);

    }
}