package ar.edu.utn.frc.tup.lciii.Services.DB.Or;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.CardEntity;
import ar.edu.utn.frc.tup.lciii.Entities.EventEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.CardORService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardORServiceTest {

    private static CardORService cardORService;

    @BeforeAll
    static void setUp() {
        cardORService = new CardORService();
    }

    @Test
    void testGetById() {
        // Insert expected data in the database if not already present

        // Assuming card_id = 1 exists with the following values based on your SQL inserts
        Long expectedCardId = 1L;
        Long expectedCardTypeId = 1L;
        Long expectedEventId = 1L;
        Long expectedSquareId = 0L;
        String expectedMessage = "Por venta de acciones cobre 1000.";
        Integer expectedAmount = 1000;
        Long expectedCardTypeToPickId = 0L; // Replace with actual value if not null
        Boolean expectedSalvageableByPlayer = false;

        CardEntity retrievedCard = cardORService.getById(expectedCardId);

        assertNotNull(retrievedCard);
        assertEquals(expectedCardId, retrievedCard.getCardId());
        assertEquals(expectedCardTypeId, retrievedCard.getCardTypeId());
        assertEquals(expectedEventId, retrievedCard.getEventId());
        assertEquals(expectedSquareId, retrievedCard.getSquareId());
        assertEquals(expectedMessage, retrievedCard.getMessage());
        assertEquals(expectedAmount, retrievedCard.getAmount());
        assertEquals(expectedCardTypeToPickId, retrievedCard.getCardTipeToPickId());
        assertEquals(expectedSalvageableByPlayer, retrievedCard.getSalvagebleByPlayer());
    }


    @Test
    void testGetAll() {

        List<CardEntity> cardEntityList = cardORService.getAll();

        // Verify that the list of games is not empty and contains the created game
        assertNotNull(cardEntityList);
        assertFalse(cardEntityList.isEmpty());
        assertEquals(32, cardEntityList.size());
    }
}
