package ar.edu.utn.frc.tup.lciii.Services.DB.Or;

import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.CardTypeORService;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardTypeORServiceTest {

    private static CardTypeORService cardTypeORService;


    @BeforeAll
    static void setUp() {
        cardTypeORService = new CardTypeORService();
    }

    @Test
    void testGetByIdLUCK() {

        CardTypeEntity retrievedCardType = cardTypeORService.getById(2L);

        assertNotNull(retrievedCardType);
        assertEquals("LUCK", retrievedCardType.getDescription());
    }
    @Test
    void testGetByIdDESTINY() {

        CardTypeEntity retrievedCardType = cardTypeORService.getById(1L);

        assertNotNull(retrievedCardType);
        assertEquals("DESTINY", retrievedCardType.getDescription());
    }

    @Test
    void testGetAll() {

        List<CardTypeEntity> cardTypeEntityList = cardTypeORService.getAll();

        // Verify that the list of games is not empty and contains the created game
        assertNotNull(cardTypeEntityList);
        assertFalse(cardTypeEntityList.isEmpty());
        assertEquals(2,cardTypeEntityList.size());
        assertEquals(1L,cardTypeEntityList.get(0).getCardTypeId());
        assertEquals(2L,cardTypeEntityList.get(1).getCardTypeId());
    }
}
