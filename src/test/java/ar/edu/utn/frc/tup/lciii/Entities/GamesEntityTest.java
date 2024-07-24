package ar.edu.utn.frc.tup.lciii.Entities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GamesEntityTest {

    private GamesEntity gamesEntity;

    @BeforeEach
    public void setUp() {
        gamesEntity = new GamesEntity();
        gamesEntity.setGameId(1L);
        gamesEntity.setDateSetup(LocalDateTime.now());
        gamesEntity.setFinished(false);
        gamesEntity.setOrderByIdPlayer("5-3-2-6");
    }

    @Test
    public void testGetOrderByIdPlayerAsString() {
        String expected = "5-3-2-6";
        assertEquals(expected, gamesEntity.getOrderByIdPlayerAsString());
    }

    @Test
    public void testSetOrderByIdPlayer() {
        String orderByIdPlayer = "7-4-1";
        gamesEntity.setOrderByIdPlayer(orderByIdPlayer);
        List<String> expected = Arrays.asList("7", "4", "1");
        assertEquals(expected, gamesEntity.getOrderByIdPlayer());
    }

    @Test
    public void testConstructorAndGetters() {
        assertNotNull(gamesEntity);
        assertEquals(1L, gamesEntity.getGameId());
        assertFalse(gamesEntity.getFinished());
        assertEquals(Arrays.asList("5", "3", "2", "6"), gamesEntity.getOrderByIdPlayer());
    }

    @Test
    public void testToString() {
        String expectedToString = "GamesEntity(gameId=1, dateSetup=" + gamesEntity.getDateSetup() +
                ", finished=false, orderByIdPlayer=[5, 3, 2, 6])";
        assertEquals(expectedToString, gamesEntity.toString());
    }
}
