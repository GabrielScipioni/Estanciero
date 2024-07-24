package ar.edu.utn.frc.tup.lciii.Entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardEntityTest {

    private CardEntity CardEntity;

    @BeforeEach
    public void setUp() {
        // Initialize a sample CardEntity instance before each test
        CardEntity = new CardEntity(1L, 2L, 3L, 4L, "Sample message", 100, 5L, true);
    }

    @Test
    public void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, CardEntity.getCardId());
        assertEquals(2L, CardEntity.getCardTypeId());
        assertEquals(3L, CardEntity.getEventId());
        assertEquals(4L, CardEntity.getSquareId());
        assertEquals("Sample message", CardEntity.getMessage());
        assertEquals(100, CardEntity.getAmount());
        assertEquals(5L, CardEntity.getCardTipeToPickId());
        assertTrue(CardEntity.getSalvagebleByPlayer());

        // Test setters
        CardEntity.setCardId(10L);
        assertEquals(10L, CardEntity.getCardId());

        CardEntity.setCardTypeId(20L);
        assertEquals(20L, CardEntity.getCardTypeId());

        CardEntity.setEventId(30L);
        assertEquals(30L, CardEntity.getEventId());

        CardEntity.setSquareId(40L);
        assertEquals(40L, CardEntity.getSquareId());

        CardEntity.setMessage("New message");
        assertEquals("New message", CardEntity.getMessage());

        CardEntity.setAmount(200);
        assertEquals(200, CardEntity.getAmount());

        CardEntity.setCardTipeToPickId(50L);
        assertEquals(50L, CardEntity.getCardTipeToPickId());

        CardEntity.setSalvagebleByPlayer(false);
        assertFalse(CardEntity.getSalvagebleByPlayer());
    }

    @Test
    public void testNoArgsConstructor() {
        // Test no-args constructor
        CardEntity newCardEntityTest = new CardEntity();
        assertNotNull(newCardEntityTest);
    }

    @Test
    public void testAllArgsConstructor() {
        // Test all-args constructor
        CardEntity newCardEntityTest = new CardEntity(1L, 2L, 3L, 4L, "Message", 50, 6L, false);
        assertNotNull(newCardEntityTest);
        assertEquals(1L, newCardEntityTest.getCardId());
        assertEquals(2L, newCardEntityTest.getCardTypeId());
        assertEquals(3L, newCardEntityTest.getEventId());
        assertEquals(4L, newCardEntityTest.getSquareId());
        assertEquals("Message", newCardEntityTest.getMessage());
        assertEquals(50, newCardEntityTest.getAmount());
        assertEquals(6L, newCardEntityTest.getCardTipeToPickId());
        assertFalse(newCardEntityTest.getSalvagebleByPlayer());
    }
}