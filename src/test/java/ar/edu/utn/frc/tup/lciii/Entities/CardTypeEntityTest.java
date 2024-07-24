package ar.edu.utn.frc.tup.lciii.Entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CardTypeEntityTest {

    private CardTypeEntity cardTypeEntity;

    @BeforeEach
    public void setUp() {
        cardTypeEntity = new CardTypeEntity();
    }

    @Test
    public void testNoArgsConstructor() {
        assertNotNull(cardTypeEntity);
        assertNull(cardTypeEntity.getCardTypeId());
        assertNull(cardTypeEntity.getDescription());
    }

    @Test
    public void testAllArgsConstructor() {
        CardTypeEntity cardTypeEntity = new CardTypeEntity(1L, "Test Description");
        assertEquals(1L, cardTypeEntity.getCardTypeId());
        assertEquals("Test Description", cardTypeEntity.getDescription());
    }

    @Test
    public void testGettersAndSetters() {
        cardTypeEntity.setCardTypeId(2L);
        cardTypeEntity.setDescription("Updated Description");

        assertEquals(2L, cardTypeEntity.getCardTypeId());
        assertEquals("Updated Description", cardTypeEntity.getDescription());
    }


}
