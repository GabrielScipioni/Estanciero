package ar.edu.utn.frc.tup.lciii.Services.DB.Or;

import ar.edu.utn.frc.tup.lciii.Entities.SquareEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.SquareORService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SquareORServiceTest {

    private static SquareORService squareORService;

    @BeforeAll
    static void setUp() {
        squareORService = new SquareORService();
    }

    @Test
    void testGetById() {
        SquareEntity retrievedSquare = squareORService.getById(1L);

        assertNotNull(retrievedSquare);
        assertEquals(1L, retrievedSquare.getSquareId());
        assertEquals(1L, retrievedSquare.getSquareTypeId());
        assertEquals("Salida", retrievedSquare.getNameSquare());
        assertEquals(0L,retrievedSquare.getProvinceId());
        assertEquals(0L,retrievedSquare.getZoneId());
        assertEquals(0,retrievedSquare.getUpgradePrice());
        assertEquals(5000, retrievedSquare.getAmount());
        assertEquals(0L,retrievedSquare.getEventId());
        assertEquals(0, retrievedSquare.getPrice());
        assertEquals(0L,retrievedSquare.getCardToPickId());
    }

    @Test
    void testGetAll() {
        List<SquareEntity> squareEntityList = squareORService.getAll();

        assertNotNull(squareEntityList);
        assertFalse(squareEntityList.isEmpty());
        assertEquals(42, squareEntityList.size());

        SquareEntity retrievedSquare = squareEntityList.get(0);
        assertEquals(1L, retrievedSquare.getSquareId());
        assertEquals(1L, retrievedSquare.getSquareTypeId());
        assertEquals("Salida", retrievedSquare.getNameSquare());
        assertEquals(0L,retrievedSquare.getProvinceId());
        assertEquals(0L,retrievedSquare.getZoneId());
        assertEquals(0,retrievedSquare.getUpgradePrice());
        assertEquals(5000, retrievedSquare.getAmount());
        assertEquals(0L,retrievedSquare.getEventId());
        assertEquals(0, retrievedSquare.getPrice());
        assertEquals(0L,retrievedSquare.getCardToPickId());

        SquareEntity retrievedSquare2 = squareEntityList.get(15);
        assertEquals(16L, retrievedSquare2.getSquareId());
        assertEquals(1L, retrievedSquare2.getSquareTypeId());
        assertEquals("SUERTE (!)", retrievedSquare2.getNameSquare());
        assertEquals(0L,retrievedSquare2.getProvinceId());
        assertEquals(0L,retrievedSquare2.getZoneId());
        assertEquals(0,retrievedSquare2.getUpgradePrice());
        assertEquals(0, retrievedSquare2.getAmount());
        assertEquals(0L,retrievedSquare2.getEventId());
        assertEquals(0, retrievedSquare2.getPrice());
        assertEquals(2L,retrievedSquare2.getCardToPickId());
    }
}