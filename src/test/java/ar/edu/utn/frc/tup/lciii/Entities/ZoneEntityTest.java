package ar.edu.utn.frc.tup.lciii.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZoneEntityTest {

    private ZoneEntity zone;

    @BeforeEach
    public void setUp() {
        zone = new ZoneEntity();
        zone.setZoneId(1L);
        zone.setDescription("Test Zone");
    }

    @Test
    public void testZoneId() {
        assertEquals(1L, zone.getZoneId());
    }

    @Test
    public void testDescription() {
        assertEquals("Test Zone", zone.getDescription());
    }

    @Test
    public void testNoArgsConstructor() {
        ZoneEntity newZone = new ZoneEntity();
        assertEquals(null, newZone.getZoneId());
        assertEquals(null, newZone.getDescription());
    }

    @Test
    public void testAllArgsConstructor() {
        ZoneEntity newZone = new ZoneEntity(2L, "Another Zone");
        assertEquals(2L, newZone.getZoneId());
        assertEquals("Another Zone", newZone.getDescription());
    }

    @Test
    public void testSetter() {
        zone.setZoneId(3L);
        zone.setDescription("Updated Zone");
        assertEquals(3L, zone.getZoneId());
        assertEquals("Updated Zone", zone.getDescription());
    }

}
