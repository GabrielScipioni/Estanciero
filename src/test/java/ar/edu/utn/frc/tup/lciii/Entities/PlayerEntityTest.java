package ar.edu.utn.frc.tup.lciii.Entities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerEntityTest {

    private PlayerEntity player1;
    @BeforeEach
    public void setUp() {
        player1 = new PlayerEntity(
                1L,
                3L ,
                2L,
                500,
                false,
                1L,
                1L
        );
    }


    @Test
    public void testSetterGetter() {
        player1.setPlayerId(2L);
        assertEquals(2L, player1.getPlayerId());
        player1.setGameid(2L);
        assertEquals(2L, player1.getGameid());
        player1.setPlayerTypeId(3L);
        assertEquals(3L, player1.getPlayerTypeId());
        player1.setUserId(4L);
        assertEquals(4L, player1.getUserId());
        player1.setBalance(200);
        assertEquals(200, player1.getBalance());
        player1.setBankrupted(true);
        assertTrue(player1.getBankrupted());
        player1.setSquarePosition(5L);
        assertEquals(5L, player1.getSquarePosition());
    }
}
