package ar.edu.utn.frc.tup.lciii.Services.DB.Or;

import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Entities.SquareTypeEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.CardTypeORService;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.SquareTypeORService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SquareTypeORServiceTest {

    private static SquareTypeORService squareTypeORService;


    @BeforeAll
    static void setUp() {
        squareTypeORService = new SquareTypeORService();
    }

    @Test
    void testGetById() {

        SquareTypeEntity retrievedSquareType = squareTypeORService.getById(1L);

        assertNotNull(retrievedSquareType);
        assertEquals("NORMAL", retrievedSquareType.getDescription());
    }


    @Test
    void testGetAll() {

        List<SquareTypeEntity> squareTypeEntityList = squareTypeORService.getAll();

        // Verify that the list of games is not empty and contains the created game
        assertNotNull(squareTypeEntityList);
        assertFalse(squareTypeEntityList.isEmpty());
        assertEquals(4,squareTypeEntityList.size());
        assertEquals(1L,squareTypeEntityList.get(0).getSquareTypeId());
        assertEquals("NORMAL",squareTypeEntityList.get(0).getDescription());
        assertEquals(2L,squareTypeEntityList.get(1).getSquareTypeId());
        assertEquals("TERRAIN_PROPERY_SQUARE",squareTypeEntityList.get(1).getDescription());
        assertEquals(3L,squareTypeEntityList.get(2).getSquareTypeId());
        assertEquals("COMPANY_PROPERY_SQUARE",squareTypeEntityList.get(2).getDescription());
        assertEquals(4L,squareTypeEntityList.get(3).getSquareTypeId());
        assertEquals("RAILWAY_PROPERY_SQUARE",squareTypeEntityList.get(3).getDescription());
    }
}
