package ar.edu.utn.frc.tup.lciii.Services.DB.Or;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Entities.ZoneEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.ZoneORService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ZoneORServiceTest {

    private static ZoneORService zoneORService;


    @BeforeAll
    static void setUp() {
        zoneORService = new ZoneORService();
        ConnexionDB.getInstance().deleteInstance();
    }
    @Test
    void testGetByIdNORTE() {
        ZoneEntity retrievedZone = zoneORService.getById(1L);

        assertNotNull(retrievedZone);
        assertEquals("NORTE", retrievedZone.getDescription());
    }


    @Test
    void testGetAll() {

        List<ZoneEntity> zoneEntityList = zoneORService.getAll();

        // Verify that the list of games is not empty and contains the created game
        assertNotNull(zoneEntityList);
        assertFalse(zoneEntityList.isEmpty());
        assertEquals(3,zoneEntityList.size());
        assertEquals(1L,zoneEntityList.get(0).getZoneId());
        assertEquals("NORTE",zoneEntityList.get(0).getDescription());
        assertEquals(2L,zoneEntityList.get(1).getZoneId());
        assertEquals("CENTRO",zoneEntityList.get(1).getDescription());
        assertEquals(3L,zoneEntityList.get(2).getZoneId());
        assertEquals("SUR",zoneEntityList.get(2).getDescription());
    }

}
