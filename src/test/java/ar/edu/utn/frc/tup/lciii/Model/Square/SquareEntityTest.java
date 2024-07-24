package ar.edu.utn.frc.tup.lciii.Model.Square;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.AggressiveBot;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;

import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.Province;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Model.Property.Zone;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SquareEntityTest {

    @Test
    public void testAddPlayer() {
        Game game = new Game(null, new User("test", "test", "test"));
        Player player1 = new HumanPlayer(new ArrayList<Property>(), false);
        Player player2 = new AggressiveBot(new ArrayList<Property>(), false);
        Integer [] rentByPrice = new Integer[5];
        rentByPrice[0] = 400;
        rentByPrice[1] = 2000;
        rentByPrice[2] = 5750;
        rentByPrice[3] = 17000;
        rentByPrice[4] = 21000;

        Property property = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);

        Square square = new PropertySquare(property);
        square.addPlayer(player1);
        square.addPlayer(player2);

        List<Player> playersOnSquare = square.getPlayers();

        assertEquals(2, playersOnSquare.size());
        assertEquals(player1, playersOnSquare.get(0));
        assertEquals(player2, playersOnSquare.get(1));


    }
    @Test
    public void testTakePlayer() {
        Game game = new Game(null, new User("test", "test", "test"));
        Player player1 = new HumanPlayer(new ArrayList<Property>(), false);
        Player player2 = new AggressiveBot(new ArrayList<Property>(), false);
        Integer [] rentByPrice = new Integer[5];
        rentByPrice[0] = 400;
        rentByPrice[1] = 2000;
        rentByPrice[2] = 5750;
        rentByPrice[3] = 17000;
        rentByPrice[4] = 21000;
        player1.setId(1);
        player2.setId(2);

        Property property = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);

        Square square = new PropertySquare(property);
        square.addPlayer(player1);
        square.addPlayer(player2);

        assertEquals(player1, square.takePlayerOut(player1));
        assertNull(square.takePlayerOut(player1));
        assertFalse(square.isPlayerInSquare(player1));

        square.takePlayerOut(player2);
        assertNull(square.takePlayerOut(player1));
        assertFalse(square.isPlayerInSquare(player1));



    }

    @Test
    public void testGetName() {
        Integer [] rentByPrice = new Integer[5];
        rentByPrice[0] = 400;
        rentByPrice[1] = 2000;
        rentByPrice[2] = 5750;
        rentByPrice[3] = 17000;
        rentByPrice[4] = 21000;

        Property property = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);

        Square square = new PropertySquare(property);
        square.setId(1L);
        square.setPlayersOnSquare(new ArrayList<>());
        assertNotNull(square.getName());
    }
}
