package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.RailwayProperty;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testNoArgsConstructor() {
        Player player = new AggressiveBot();
        assertNotNull(player, "El jugador no puede ser null desp de crearlo.");
    }

    @Test
    public void playerTest() {
        

        Player aggressiveBot = new AggressiveBot(new ArrayList<>(), false);

        assertNotNull(aggressiveBot);

        List<Property> properties = new ArrayList<>();
        aggressiveBot.setBankrupted(true);
        aggressiveBot.setProperties(properties);
        assertTrue(aggressiveBot.getBankrupted());
        assertEquals(properties, aggressiveBot.getProperties());

    }

    @Test
    public void ownsPropertyTest() {

        List<Property> properties = new ArrayList<>();
        RailwayProperty railway = new RailwayProperty(false, "Tren Test", 100);
        properties.add(railway);
        Player player = new AggressiveBot(properties,false);

        assertTrue(player.ownsProperty(railway));
    }

    @Test
    public void removePropertyTest() {
        List<Property> properties = new ArrayList<>();
        RailwayProperty railway = new RailwayProperty(false, "Tren Test", 100);
        properties.add(railway);
        Player player = new AggressiveBot(properties,false);

        assertTrue(properties.contains(railway));

        player.removeProperty(railway);

        assertFalse(properties.contains(railway));
    }

    @Test
    public void addPropertyTest() {
        List<Property> properties = new ArrayList<>();
        RailwayProperty railway = new RailwayProperty(false, "Tren Test", 100);

        Player player = new AggressiveBot(properties,false);

        assertFalse(properties.contains(railway));

        player.addProperty(railway);

        assertTrue(properties.contains(railway));
    }

    @Test
    public void getCardsTest() {
        Card card = new Card();
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        Player player = new AggressiveBot();

        player.setCards(cards);

        assertEquals(player.getCards(), cards);
    }

    @Test
    public void setCardsTest() {
        Card card = new Card();
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        Player player = new AggressiveBot();

        player.setCards(cards);

        assertEquals(player.getCards(), cards);
    }

    @Test
    public void getBalanceTest() {
        Player player = new AggressiveBot();
        player.setBalance(1337);

        assertEquals(player.getBalance(), 1337);
    }

    @Test
    public void setBalanceTest() {
        Player player = new AggressiveBot();
        player.setBalance(1337);

        assertEquals(player.getBalance(), 1337);
    }

    @Test
    public void getNameTest() {
        Player player = new AggressiveBot();
        player.setName("Agus");

        assertEquals(player.getName(), "Agus");
    }

    @Test
    public void setNameTest() {
        Player player = new AggressiveBot();
        player.setName("Agus");

        assertEquals(player.getName(), "Agus");
    }
}
