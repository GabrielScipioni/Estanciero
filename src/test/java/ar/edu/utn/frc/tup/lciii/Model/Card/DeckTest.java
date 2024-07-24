package ar.edu.utn.frc.tup.lciii.Model.Card;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Event.EventType;
import ar.edu.utn.frc.tup.lciii.Model.Event.MoneyStrategy;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeckTest {

    List<Card> cards;
    Deck deck;
    Event event;



    @BeforeEach
    void setUp() {
        cards= new ArrayList<>();
        event = new Event(EventType.MONEY, new MoneyStrategy());

        Card card1 = Mockito.mock(Card.class);
        cards.add(new Card(CardType.LUCK, event,"Carta 1", null, null,false));
        cards.add(new Card(CardType.LUCK, event,"Carta 2", null, null,true));
        cards.add(new Card(CardType.LUCK, event,"Carta 3", null, null,true));
        cards.add(new Card(CardType.DESTINY, event,"Carta 4", null, null,false));
        cards.add(new Card(CardType.DESTINY, event,"Carta 5", null, null,true));
        cards.add(new Card(CardType.DESTINY, event,"Carta 6", null, null,true));
        cards.add(new Card(CardType.DESTINY, event,"Carta 7", null, null,false));
        deck = new Deck(new ArrayList<>(cards));


    }
    @Test
    void DeckTest() throws NoSuchFieldException, IllegalAccessException {

        Field deckCardsField = Deck.class.getDeclaredField("cards");
        deckCardsField.setAccessible(true);
        List<Card> deckCards = (List<Card>) deckCardsField.get(deck);

        assertTrue (cards.containsAll(deckCards));
        assertEquals(cards,deckCards);

    }

    @Test
    void testTakeAndExecute() throws NoSuchFieldException, IllegalAccessException {
        cards.clear();

        Card card1 = new Card(CardType.LUCK, event,"Carta 1", null, null,false);
        Card spyCard1 = spy(card1);
        doNothing().when(spyCard1).execute(any(), any());

        Card card2 = new Card(CardType.DESTINY, event,"Carta 2", null, null,false);
        Card spyCard2 = spy(card2);
        doNothing().when(spyCard2).execute(any(), any());

        Card card3 = new Card(CardType.LUCK, event,"Carta 3", null, null,true);
        Card spyCard3 = spy(card3);
        doNothing().when(spyCard3).execute(any(), any());

        cards.add(spyCard1);
        cards.add(spyCard2);
        cards.add(spyCard3);


        Deck newDeck = new Deck(null);
        Field deckCardsField = Deck.class.getDeclaredField("cards");
        deckCardsField.setAccessible(true);

        deckCardsField.set(newDeck, cards);


        Board board = new Board();
        Player player = new HumanPlayer(new ArrayList<>(),false);
        Game game = new Game(board, new User("test", "test", "test"));


        newDeck.takeAndExecute(game,player,CardType.DESTINY);

        Mockito.verify(spyCard1,times(0)).execute(any(),any());
        Mockito.verify(spyCard2,times(1)).execute(any(),any());
        Mockito.verify(spyCard3,times(0)).execute(any(),any());

        assertNotEquals(((ArrayList<Card>)deckCardsField.get(newDeck)).get(1), spyCard2);
        assertEquals(((ArrayList<Card>)deckCardsField.get(newDeck)).get(2), spyCard2);
        assertEquals(((ArrayList<Card>)deckCardsField.get(newDeck)).get(1), spyCard3);

        newDeck.takeAndExecute(game,player,CardType.LUCK);
        assertTrue(player.getCards().isEmpty());
        newDeck.takeAndExecute(game,player,CardType.LUCK);

        assertEquals(player.getCards().get(0), spyCard3);



    }


//    @Test
//    void isEmpty() {
//        assertTrue(new DeckEntity(new ArrayList<>()).isEmpty());
//        assertTrue(new DeckEntity(null).isEmpty());
//        assertFalse(deck.isEmpty());
//    }
//
//    @Test
//    void testHasMore() {
//        assertFalse(new DeckEntity(new ArrayList<>()).hasMore(CardTypeEntity.LUCK));
//        assertFalse(new DeckEntity(null).hasMore(CardTypeEntity.DESTINY));
//        assertTrue(deck.hasMore(CardTypeEntity.LUCK));
//    }


}