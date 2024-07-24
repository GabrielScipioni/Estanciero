package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.AggressiveBot;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.RailwayProperty;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PayPerUpgradeStrategyTest {

    @Test
    public void testExecuteNoTerrainProps() {
        // Arrange
        Game game = new Game();
        Bank bank = new Bank();
        Player player = new AggressiveBot();
        player.setBalance(500);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        int amount = 500; // $500 x casa
        RailwayProperty property = new RailwayProperty();
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        player.setProperties(properties);

        PayPerUpgradeStrategy strategy = new PayPerUpgradeStrategy();

        // Act
        strategy.execute(game, player, square, amount, cardType);

        // Assert
        assertEquals(500,player.getBalance());
    }

    @Test
    public void testExecuteNoUpgrades() {
        // Arrange
        Game game = new Game();
        Bank bank = new Bank();
        Player player = new AggressiveBot();
        player.setBalance(500);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        int amount = 500; // $500 x casa
        TerrainProperty property = new TerrainProperty();
        property.setUpgradeLevel(0);
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        player.setProperties(properties);

        PayPerUpgradeStrategy strategy = new PayPerUpgradeStrategy();

        // Act
        strategy.execute(game, player, square, amount, cardType);

        // Assert
        assertEquals(500,player.getBalance());
    }

    @Test
    public void testExecuteHotelUpgrades() {
        // Arrange
        Game game = new Game();
        Bank bank = new Bank();
        Player player = new AggressiveBot();
        player.setBalance(2500);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        player.setName("Aguu");
        int amount = 500; // $500 x casa
        TerrainProperty property = new TerrainProperty();
        property.setUpgradeLevel(5);
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        player.setProperties(properties);

        PayPerUpgradeStrategy strategy = new PayPerUpgradeStrategy();

        // Act
        strategy.execute(game, player, square, amount, cardType);

        // Assert
        assertEquals(0,player.getBalance());
    }

    @Test
    public void testExecuteHousesUpgrades() {
        // Arrange
        Game game = new Game();
        Bank bank = new Bank();
        Player player = new AggressiveBot();
        player.setBalance(500);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        player.setName("Aguu");
        int amount = 500; // $500 x casa
        TerrainProperty property = new TerrainProperty();
        property.setUpgradeLevel(3);
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        player.setProperties(properties);

        PayPerUpgradeStrategy strategy = new PayPerUpgradeStrategy();

        // Act
        strategy.execute(game, player, square, amount, cardType);

        // Assert
        assertEquals(0,player.getBalance());
    }

    @Test
    public void testExecuteHousesAndHotelUpgrades() {
        // Arrange
        Game game = new Game();
        Bank bank = new Bank();
        Player player = new AggressiveBot();
        player.setBalance(3000);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        player.setName("Aguu");
        int amount = 500; // $500 x casa
        TerrainProperty property = new TerrainProperty();
        property.setUpgradeLevel(3);
        TerrainProperty propertyHotel = new TerrainProperty();
        propertyHotel.setUpgradeLevel(5);
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        properties.add(propertyHotel);
        player.setProperties(properties);

        PayPerUpgradeStrategy strategy = new PayPerUpgradeStrategy();

        // Act
        strategy.execute(game, player, square, amount, cardType);

        // Assert
        assertEquals(0,player.getBalance());
    }

    @Test
    public void testExecuteTerrainImposibleUpgrades() {
        // Arrange
        Game game = new Game();
        Bank bank = new Bank();
        Player player = new AggressiveBot();
        player.setBalance(500);
        Square square = mock(Square.class);
        CardType cardType = CardType.LUCK;
        player.setName("Aguu");
        int amount = 500; // $500 x casa
        TerrainProperty property = new TerrainProperty();
        property.setUpgradeLevel(6);
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        player.setProperties(properties);

        PayPerUpgradeStrategy strategy = new PayPerUpgradeStrategy();

        // Act
        strategy.execute(game, player, square, amount, cardType);

        // Assert
        assertEquals(500,player.getBalance());
    }
}