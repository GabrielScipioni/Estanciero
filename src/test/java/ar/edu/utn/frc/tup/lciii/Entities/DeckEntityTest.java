package ar.edu.utn.frc.tup.lciii.Entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;

public class DeckEntityTest {

    @Test
    public void testGetOrderCardsById() {
        // Arrange
        DeckEntity deck = new DeckEntity();
        deck.setOrderByIdPlayer("5-3-2-6");

        // Act
        String orderCardsById = deck.getOrderCardsByIdString();

        // Assert
        assertEquals("5-3-2-6", orderCardsById);
    }

    @Test
    public void testSetOrderByIdPlayer() {
        // Arrange
        DeckEntity deck = new DeckEntity();

        // Act
        deck.setOrderByIdPlayer("5-3-2-6");

        // Assert
        assertEquals(Arrays.asList("5", "3", "2", "6"), deck.getOrderCardsById());
    }

}