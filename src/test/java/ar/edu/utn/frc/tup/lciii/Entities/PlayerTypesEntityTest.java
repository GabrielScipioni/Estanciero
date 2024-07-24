package ar.edu.utn.frc.tup.lciii.Entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTypesEntityTest {

    private PlayerTypesEntity playerType1;
    private PlayerTypesEntity playerType2;

    @BeforeEach
    public void setUp() {
        playerType1 = new PlayerTypesEntity(1L, "Regular");
        playerType2 = new PlayerTypesEntity();
        playerType2.setPlayerTypeId(2L);
        playerType2.setDescription("VIP");
    }

    @Test
    public void testGetPlayerTypeId() {
        assertEquals(1L, playerType1.getPlayerTypeId());
        assertEquals(2L, playerType2.getPlayerTypeId());
    }

    @Test
    public void testSetPlayerTypeId() {
        playerType1.setPlayerTypeId(3L);
        assertEquals(3L, playerType1.getPlayerTypeId());
    }

    @Test
    public void testGetTypePlayer() {
        assertEquals("Regular", playerType1.getDescription());
        assertEquals("VIP", playerType2.getDescription());
    }

    @Test
    public void testSetTypePlayer() {
        playerType1.setDescription("Advanced");
        assertEquals("Advanced", playerType1.getDescription());
    }

    @Test
    public void testEquals() {
        PlayerTypesEntity playerType3 = playerType1;

        assertEquals(playerType1, playerType3);
        assertNotEquals(playerType1, playerType2);
    }
}
