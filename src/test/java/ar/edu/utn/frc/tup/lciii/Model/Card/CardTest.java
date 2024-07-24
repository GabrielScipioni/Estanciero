package ar.edu.utn.frc.tup.lciii.Model.Card;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CardTest {

    Card card;
    Event event = Mockito.spy(Event.class);

    @Test
    public void testNoArgsConstructor() {
        Card card = new Card();
        assertNotNull(card, "La card no puede ser null desp de crearlo.");
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        card = new Card(CardType.LUCK,null, null, null, null, false);
        Field cardEventField = Card.class.getDeclaredField("event");
        cardEventField.setAccessible(true);


        Mockito.doNothing().when(event).execute(any(), any(), any(), any(), any());

        cardEventField.set(card,event);


    }
    @Test
    void getCardType() {
        assertEquals(card.getCardType(), CardType.LUCK);
    }

    @Test
    void execute() {

        card.execute(new Game(new Board(),new User("test","test","test")),new HumanPlayer(null,false));

        verify(event,times(1)).execute(any(),any(),any(),any(),any());
    }

    @Test
    void isSavableByPlayer() {
        assertFalse(card.isSavableByPlayer());
    }
}