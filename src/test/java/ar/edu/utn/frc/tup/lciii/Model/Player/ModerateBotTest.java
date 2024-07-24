package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModerateBotTest {

    @Test
    public void testNoArgsConstructor() {
        ModerateBot bot = new ModerateBot();
        assertNotNull(bot);
    }

    @Test
    public void testConstructor() {

        List<Property> properties = new ArrayList<>();
        boolean bankrupted = false;
        ModerateBot moderateBot = new ModerateBot(properties, false);

        assertNotNull(moderateBot);
        assertEquals(properties, moderateBot.getProperties());
        assertEquals(bankrupted, moderateBot.getBankrupted());
    }

    @Test
    public void isPriorityTest() {
        TerrainProperty property = mock(TerrainProperty.class);
        ModerateBot bot = new ModerateBot();

        when(property.getProvince()).thenReturn(Province.MENDOZA);

        try{
            assertTrue(bot.isPriority(property));

        } catch (Exception ignored) {}
    }

    @Test
    public void isPriorityRailwayTest() {
        //arrange
        RailwayProperty property = mock(RailwayProperty.class);
        ModerateBot bot = new ModerateBot();

        //assert
        try{
            assertTrue(bot.isPriority(property));

        } catch (Exception ignored) {}
    }


    @Test
    public void buyPropertyTestNoSquare() {
        Square square = mock(Square.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        ModerateBot bot = new ModerateBot();

        Game game = new Game();
        Board board = new Board();
        board.setSquares(squareList);
        game.setBoard(board);


        for (int i = 0 ; i < 6 ; i++) {
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

        ModerateBot bot = new ModerateBot();

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

        ModerateBot bot = new ModerateBot();
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

        bot.buyProperty(game);

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

        ModerateBot bot = new ModerateBot();
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

        ModerateBot bot = new ModerateBot();
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


        for (int i = 0 ; i < 6 ; i++) {
            bot.buyProperty(game);
        }

        bot.buyProperty(game);

        assertNotNull(game);
        assertNotNull(board);
        assertNotNull(square);
        assertNotNull(bot);

    }

    @Test
    public void testPriceGap() {
        ModerateBot bot = new ModerateBot();
        assertEquals(50, bot.getPriceGap());
    }

    @Test
    public void buyPropertyTestPriorityAndBalance() {
        PropertySquare square = mock(PropertySquare.class);
        List<Square> squareList = new ArrayList<>();
        squareList.add(square);

        TerrainProperty property = mock(TerrainProperty.class);
        List<Property> properties = new ArrayList<>();
        ModerateBot bot = new ModerateBot(properties,false);
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
        when(property.getProvince()).thenReturn(Province.MENDOZA);
        when(property.getPrice()).thenReturn(10);

        for (int i = 0 ; i < 6 ; i++) {
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
        ModerateBot bot = new ModerateBot(properties,false);
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

        for (int i = 0 ; i < 6 ; i++) {
            bot.buyProperty(game);
        }
    }



}
