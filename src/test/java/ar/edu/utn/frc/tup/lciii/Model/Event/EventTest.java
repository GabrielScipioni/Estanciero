package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testEvent(){
        EventStrategy moneyStrategy = new MoneyStrategy();
        Event event = new Event(EventType.MONEY, moneyStrategy);

        assertEquals(event.getStrategy(), moneyStrategy);
        EventStrategy newStrategy = new GoToJailStrategy();
        event.setStrategy(newStrategy);
        assertEquals(event.getStrategy(), newStrategy);

        Game game = new Game(new Board(), new User("test","test","test"));
        Player player = new HumanPlayer(null, false);

        try{
            event.execute(game,player,null, 10,null);

        }catch (Exception ignore){
            fail();
        }


    }
}
