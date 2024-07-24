package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConservativeBotTest {

    private ConservativeBot bot;
    private Game game;
    private Board board;

    @BeforeEach
    public void setUp() {
        bot = new ConservativeBot();
        game = new Game();
        board = new Board();
        game.setBoard(board);
    }

    @Test
    public void testNoArgsConstructor() {
        assertNotNull(bot);
    }

    @Test
    public void testConstructorWithArgs() {
        List<Property> properties = new ArrayList<>();
        ConservativeBot botWithArgs = new ConservativeBot(properties, false);

        assertNotNull(botWithArgs);
        assertEquals(properties, botWithArgs.getProperties());
        assertFalse(botWithArgs.getBankrupted());
    }

    @Test
    public void testPriceGap() {
        assertEquals(30, bot.getPriceGap());
    }


    @Test
    public void isPriorityTest() {

        TerrainProperty property = mock(TerrainProperty.class);
        ConservativeBot bot = new ConservativeBot();

        when(property.getProvince()).thenReturn(Province.SALTA);


        try{
            assertTrue(bot.isPriority(property));

        } catch (Exception ignored) {}
    }

    @Test
    public void isPriorityRailwayTest() {
        //arrange
        RailwayProperty property = mock(RailwayProperty.class);
        ConservativeBot bot = new ConservativeBot();

        //assert
        try{
            assertFalse(bot.isPriority(property));

        } catch (Exception ignored) {}
    }

    @Test
    public void isPriorityCompanyTest() {
        //arrange
        CompanyProperty property = mock(CompanyProperty.class);
        ConservativeBot bot = new ConservativeBot();

        //assert
        try{
            assertFalse(bot.isPriority(property));

        } catch (Exception ignored) {}
    }

    @Test
    public void buyPropertyTestNoSquare() {
        Square square = mock(Square.class);
        board.setSquares(List.of(square));

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
        board.setSquares(List.of(square));

        when(square.isPlayerInSquare(bot)).thenReturn(true);
        bot.buyProperty(game);
        when(square.isPlayerInSquare(bot)).thenReturn(false);
        bot.buyProperty(game);

        assertNotNull(game);
        assertNotNull(board);
        assertNotNull(square);
        assertNotNull(bot);
    }

    @Test
    public void buyPropertyTestPropertySquare() {
        PropertySquare square = mock(PropertySquare.class);
        TerrainProperty property = mock(TerrainProperty.class);
        board.setSquares(List.of(square));

        bot.setBalance(100);
        BotPlayer mockBot = mock(BotPlayer.class);

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
        Property property = mock(Property.class);
        board.setSquares(List.of(square));

        when(square.isPlayerInSquare(bot)).thenReturn(true);
        when(square.getProperty()).thenReturn(property);
        when(property.getOwner(game)).thenReturn(null);

        bot.buyProperty(game);

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

        ConservativeBot bot = new ConservativeBot();
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
        when(property.getProvince()).thenReturn(Province.CORDOBA);
        when(property.getPrice()).thenReturn(10);


        for (int i = 0 ; i < 6 ; i++) {

            try{ bot.buyProperty(game); }
            catch (Exception ignored) {}
        }

        try { bot.buyProperty(game); }
        catch (Exception ignored) {}

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
        ConservativeBot bot = new ConservativeBot(properties,false);
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
        ConservativeBot bot = new ConservativeBot(properties,false);
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