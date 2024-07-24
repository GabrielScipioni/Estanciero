package ar.edu.utn.frc.tup.lciii.Model.Square;

import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Event.EventType;
import ar.edu.utn.frc.tup.lciii.Model.Event.GoToJailStrategy;
import ar.edu.utn.frc.tup.lciii.Model.Event.MoneyStrategy;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NormalSquareEntityTest {
@Mock
Event event;
    @Test
    public void testGetAndSetMovementType() {
        Event event = new Event(EventType.MONEY, new MoneyStrategy());
        NormalSquare normalSquare = new NormalSquare(1L,event, "test", null, 100);
        assertEquals(event, normalSquare.getEvent());
        Event newEvent = new Event(EventType.GO_TO_JAIL, new GoToJailStrategy());
        normalSquare.setEvent(newEvent);
        assertEquals(newEvent, normalSquare.getEvent());
    }



    @Test
    public void testGetAndSetSquare() {
        Square square = new PropertySquare(null);
        NormalSquare normalSquare = new NormalSquare(1L,null, "test", square, 100);
        assertEquals(square, normalSquare.getSquare());
        Square newSquare = new PropertySquare(null);
        normalSquare.setSquare(newSquare);
        assertEquals(newSquare, normalSquare.getSquare());

    }

    @Test
    public void testGetAndSetAmount() {
        NormalSquare normalSquare = new NormalSquare(1L,null, "test", null, 100);
        assertEquals(100, normalSquare.getAmount());
        normalSquare.setAmount(200);
        assertEquals(200, normalSquare.getAmount());
    }

    @Test
    public void testGetName() {
        NormalSquare normalSquare = new NormalSquare(1L,null, "test", null, 100);
        assertEquals("test", normalSquare.getName());
    }

    /*
    @Test
    public void testExecute() {

        Event event = new Event(EventType.MONEY, new MoneyStrategy());
        NormalSquare normalSquare = new NormalSquare(event, "test", null, 100);
        normalSquare.setAmount(10);
        Game game = new Game(null, new User("test","test","test"));
        Player player = new HumanPlayer(new ArrayList<Property>(), false);
        player.setBalance(800);

            normalSquare.execute(game, player);


    }

    @Test
    public void testExecuteWhenPassing() {

        Event event = mock(Event.class);

        NormalSquare normalSquare = new NormalSquare(event, "test", null, 100);
        normalSquare.setAmount(10);
        normalSquare.setEventWhenPassing(event);
        Game game = new Game(null, new User("test","test","test"));
        Player player = new HumanPlayer(new ArrayList<Property>(), false);
        player.setBalance(800);

        normalSquare.executeWhenPassing(game, player);

        verify(event, times(1)).execute(any(),any(),any(),any(),any());


    }*/
}
