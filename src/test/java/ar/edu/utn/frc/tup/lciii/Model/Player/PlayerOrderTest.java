package ar.edu.utn.frc.tup.lciii.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerOrderTest {

    private Player player;
    private PlayerOrder playerOrder;

    @BeforeEach
    public void setUp() {
        player = new AggressiveBot();
        playerOrder = new PlayerOrder(player);
    }

    @Test
    public void testConstructorWithPlayer() {
        assertEquals(player, playerOrder.getPlayer());
        assertNull(playerOrder.getDicePoints());
    }

    @Test
    public void testGetterSetterPlayer() {
        Player newPlayer = new AggressiveBot();
        playerOrder.setPlayer(newPlayer);
        assertEquals(newPlayer, playerOrder.getPlayer());
    }

    @Test
    public void testGetterSetterDicePoints() {
        Integer dicePoints = 4;
        playerOrder.setDicePoints(dicePoints);
        assertEquals(dicePoints, playerOrder.getDicePoints());
    }

    @Test
    public void testConstructorWithPlayerAndDicePoints() {
        Integer dicePoints = 6;
        PlayerOrder playerOrderWithDice = new PlayerOrder(player, dicePoints);
        assertEquals(player, playerOrderWithDice.getPlayer());
        assertEquals(dicePoints, playerOrderWithDice.getDicePoints());
    }
}
