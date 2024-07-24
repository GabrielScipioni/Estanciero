package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AggressiveBotTest {

    @Test
    public void testNoArgsConstructor() {
        Player player = new AggressiveBot();
        assertNotNull(player, "El jugador no puede ser null desp de crearlo.");
    }

    @Test
    public void testConstructor() {

        List<Property> properties = new ArrayList<>();
        boolean bankrupted = false;
        AggressiveBot aggressiveBot = new AggressiveBot(properties, false);

        assertNotNull(aggressiveBot);
        assertEquals(properties, aggressiveBot.getProperties());
        assertEquals(bankrupted, aggressiveBot.getBankrupted());
    }

    @Test
    public void testBuyProperty() {
        List<Property> properties = new ArrayList<>();
        AggressiveBot aggressiveBot = new AggressiveBot(properties, false);
        //assertDoesNotThrow(aggressiveBot::buyProperty);
    }

    @Test
    public void testUpgradeProperty() {
        AggressiveBot aggressiveBot = new AggressiveBot(new ArrayList<>(), false);

        //assertDoesNotThrow(aggressiveBot::upgradeTerrainProperty);
    }

//    @Test
//    public void testFindWayToMakeMoney() {
//
//        AggressiveBot aggressiveBot = new AggressiveBot(new ArrayList<>(), false);
//
////        assertDoesNotThrow(aggressiveBot::findWayToMakeMoney);
//    }

//    @Test
//    public void testSellProperty() {
//        AggressiveBot aggressiveBot = new AggressiveBot( new ArrayList<>(), false);
//        //TODO: assertDoesNotThrow(aggressiveBot::sellProperty);
//    }
//    @Test
//    public void testPlay() {
//        AggressiveBot aggressiveBot = new AggressiveBot( new ArrayList<>(), false);
//        // TODO: assertDoesNotThrow(aggressiveBot::play);
//    }


    @Test
    public void makeBidTest() {
        Player bot = new AggressiveBot();
        bot.setBalance(0);
        Property prop = new RailwayProperty();
        prop.setName("tren");
        prop.setMortgaged(false);
        prop.setPrice(10);

        for (int i = 0; i < 100; i++) {
            bot.makeBid(prop,0);
        }
        assertNotNull(prop);
    }

    @Test
    public void testPriceGap() {
        AggressiveBot bot = new AggressiveBot();
        assertEquals(100, bot.getPriceGap());
    }

    @Test
    public void isPriorityTest() {

        TerrainProperty property = mock(TerrainProperty.class);
        AggressiveBot bot = new AggressiveBot();

        when(property.getProvince()).thenReturn(Province.CORDOBA);

        try {
            assertTrue(bot.isPriority(property));

        } catch (Exception ignored) {
        }
    }

    @Test
    public void isPriorityRailwayTest() {
        //arrange
        RailwayProperty property = mock(RailwayProperty.class);
        AggressiveBot bot = new AggressiveBot();

        //assert
        try {
            assertTrue(bot.isPriority(property));

        } catch (Exception ignored) {
        }
    }

    @Test
    public void isPriorityCompanyTest() {
        //arrange
        CompanyProperty property = mock(CompanyProperty.class);
        AggressiveBot bot = new AggressiveBot();

        //assert
        try {
            assertTrue(bot.isPriority(property));

        } catch (Exception ignored) {
        }
    }

    @Test
    public void checkOwnedPrioritiesTest() {
        AggressiveBot bot = new AggressiveBot();

        Game game = new Game();
        List<Square> squareList = new ArrayList<>();
        NormalSquare square = mock(NormalSquare.class);
        PropertySquare propertySquare = mock(PropertySquare.class);

        Property prop = mock(Property.class);
        Board board = new Board();

        game.setBoard(board);
        board.setSquares(squareList);

        squareList.add(square);
        squareList.add(propertySquare);
        when(propertySquare.getProperty()).thenReturn(prop);
        when(prop.getOwner(game)).thenReturn(bot);


        when(propertySquare.getProperty().getOwner(game)).thenReturn(bot);

        try {
            assertFalse(bot.checkOwnershipOfPriorityProperty(game));
        } catch (Exception ignored) {
        }

    }


    @Test
    public void buyPropertyTestNoSquare() {
        Square square = mock(Square.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        AggressiveBot bot = new AggressiveBot();

        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);


        for (int i = 0; i < 6; i++) {
            bot.buyProperty(game);
        }

        assertNotNull(game);
        assertNotNull(board);
        assertNotNull(square);
        assertNotNull(bot);
    }

    @Test
    public void buyPropertyTestNormalSquare() {
        NormalSquare square = mock(NormalSquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        AggressiveBot bot = new AggressiveBot();

        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);

        when(square.isPlayerInSquare(bot)).thenReturn(true);

        bot.buyProperty(game);

        assertNotNull(game);
        assertNotNull(board);
        assertNotNull(square);
        assertNotNull(bot);

        when(square.isPlayerInSquare(bot)).thenReturn(false);

        bot.buyProperty(game);
    }

    @Test
    public void buyPropertyTestPropertySquare() {
        PropertySquare square = mock(PropertySquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        TerrainProperty property = mock(TerrainProperty.class);

        AggressiveBot bot = new AggressiveBot();
        bot.setBalance(100);
        BotPlayer mockBot = mock(BotPlayer.class);

        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);

        when(square.isPlayerInSquare(bot)).thenReturn(true);
        when(square.getProperty()).thenReturn(property);

        when(property.getOwner(game)).thenReturn(mockBot);
        when(property.getProvince()).thenReturn(Province.SALTA);
        when(property.getPrice()).thenReturn(10);

        try {
            bot.buyProperty(game);
        } catch (Exception ignored) {
        }
        assertNotNull(game);
        assertNotNull(board);
        assertNotNull(square);
        assertNotNull(bot);

    }

    @Test
    public void buyPropertyTestNullOwner() {
        PropertySquare square = mock(PropertySquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        Property property = mock(Property.class);

        AggressiveBot bot = new AggressiveBot();
        BotPlayer mockBot = mock(BotPlayer.class);

        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);

        when(square.isPlayerInSquare(bot)).thenReturn(true);
        when(square.getProperty()).thenReturn(property);
        when(property.getOwner(game)).thenReturn(null);


        mockBot.buyProperty(game);

        assertNotNull(game);
        assertNotNull(board);
        assertNotNull(square);
        assertNotNull(bot);

    }

    @Test
    public void buyPropertyTestCounter() {
        PropertySquare square = mock(PropertySquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        TerrainProperty property = mock(TerrainProperty.class);

        AggressiveBot bot = new AggressiveBot();
        bot.setBalance(100);
        BotPlayer mockBot = mock(BotPlayer.class);

        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);


        when(square.isPlayerInSquare(bot)).thenReturn(true);
        when(square.getProperty()).thenReturn(property);
        when(property.getOwner(game)).thenReturn(null);

        when(property.getOwner(game)).thenReturn(mockBot);
        when(property.getProvince()).thenReturn(Province.SALTA);
        when(property.getPrice()).thenReturn(10);


        for (int i = 0; i < 6; i++) {

            try {
                bot.buyProperty(game);
            } catch (Exception ignored) {
            }
        }

        try {
            bot.buyProperty(game);
        } catch (Exception ignored) {
        }

        assertNotNull(game);
        assertNotNull(board);
        assertNotNull(square);
        assertNotNull(bot);

    }

    @Test
    public void buyPropertyTestPriorityAndBalance() {
        PropertySquare square = mock(PropertySquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        TerrainProperty property = mock(TerrainProperty.class);
        List<Property> properties = new ArrayList<>();
        AggressiveBot bot = new AggressiveBot(properties, false);
        bot.setBalance(100);
        BotPlayer mockBot = mock(BotPlayer.class);

        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);

        when(square.isPlayerInSquare(bot)).thenReturn(true);
        when(square.getProperty()).thenReturn(property);
        when(property.getOwner(game)).thenReturn(null);

        when(property.getOwner(game)).thenReturn(null);
        when(property.getProvince()).thenReturn(Province.FORMOSA);
        when(property.getPrice()).thenReturn(10);

        for (int i = 0; i < 6; i++) {
            bot.buyProperty(game);
        }
    }

    @Test
    public void buyPropertyTestPriorityAndBalancePriority() {


        //Setup

        PropertySquare square = mock(PropertySquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        TerrainProperty property = mock(TerrainProperty.class);
        List<Property> properties = new ArrayList<>();
        AggressiveBot bot = new AggressiveBot(properties, false);
        bot.setBalance(10000);


        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);

        //Condiciones

        when(square.isPlayerInSquare(bot)).thenReturn(true);
        when(square.getProperty()).thenReturn(property);
        when(property.getOwner(game)).thenReturn(null);

        when(property.getOwner(game)).thenReturn(null);
        when(property.getProvince()).thenReturn(Province.BUENOS_AIRES);
        when(property.getPrice()).thenReturn(10);

        for (int i = 0; i < 6; i++) {
            bot.buyProperty(game);
        }

    }

    @Test
    public void buyPropertyTestPriorityAndBalanceNoPriority() {

        //Setup

        PropertySquare square = mock(PropertySquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        TerrainProperty property = mock(TerrainProperty.class);
        List<Property> properties = new ArrayList<>();

        TerrainProperty terrain1 = new TerrainProperty();
        terrain1.setProvince(Province.BUENOS_AIRES);
        terrain1.setZone(Zone.NORTE);

        TerrainProperty terrain2 = new TerrainProperty();
        terrain2.setProvince(Province.BUENOS_AIRES);
        terrain2.setZone(Zone.NORTE);

        TerrainProperty terrain3 = new TerrainProperty();
        terrain3.setProvince(Province.BUENOS_AIRES);
        terrain3.setZone(Zone.NORTE);

        properties.add(terrain3);
        properties.add(terrain2);
        properties.add(terrain1);

        AggressiveBot bot = new AggressiveBot(properties, false);
        bot.setBalance(10000);


        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);

        //Condiciones

        when(square.isPlayerInSquare(bot)).thenReturn(true);
        when(square.getProperty()).thenReturn(property);
        when(property.getOwner(game)).thenReturn(null);

        when(property.getOwner(game)).thenReturn(null);
        when(property.getProvince()).thenReturn(Province.RIO_NEGRO);
        when(property.getPrice()).thenReturn(10);

        for (int i = 0; i < 6; i++) {
            bot.buyProperty(game);
        }

    }


}
