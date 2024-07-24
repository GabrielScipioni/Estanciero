package ar.edu.utn.frc.tup.lciii.Model.Board;

import ar.edu.utn.frc.tup.lciii.Model.Event.*;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.AggressiveBot;
import ar.edu.utn.frc.tup.lciii.Model.Player.ConservativeBot;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BoardTest {
    private Player aggressiveBot ;
    private Player humanPlayer ;
    private List<Square> squares = new ArrayList<Square>();
    private Property terrainProperty;
    private Square propertySquare;
    private Square jailSquare;
    private NormalSquare exitSquare;
    private NormalSquare parkingSquare;
    private Board board;
    private Game game;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
    @BeforeEach
    public void setUp(){
        Integer [] rentByPrice = new Integer[5];
        rentByPrice[0] = 400;
        rentByPrice[1] = 2000;
        rentByPrice[2] = 5750;
        rentByPrice[3] = 17000;
        rentByPrice[4] = 21000;
        terrainProperty = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);
        propertySquare = new PropertySquare(terrainProperty);
        jailSquare = new NormalSquare(1L,new Event(EventType.JAIL, new JailStrategy()), "Comisaria", null,null );
        exitSquare = new NormalSquare(1L,new Event(EventType.MONEY, new MoneyStrategy()),"Salida",null,500);
        exitSquare.setEventWhenPassing(new Event(EventType.MONEY, new MoneyStrategy()));
        exitSquare.setEvent(new Event(EventType.MONEY, new MoneyStrategy()));
        parkingSquare= new NormalSquare(1L,null, "Parking", null,null );



        aggressiveBot = new AggressiveBot(new ArrayList<>(), false);
        humanPlayer = new HumanPlayer(new ArrayList<>(), false);
        humanPlayer.setBalance(200);
        aggressiveBot.setId(1);
        humanPlayer.setId(2);


        propertySquare.addPlayer(aggressiveBot);
        exitSquare.addPlayer(humanPlayer);

        squares.add(propertySquare);
        squares.add(parkingSquare);
        squares.add(jailSquare);
        squares.add(exitSquare);



        board = new Board();
        board.setSquares(squares);
        game = new Game(board, new User("test","test","test"));

    }
    @Test
    public void testSetGetSquares(){
        assertNotNull(board);

        assertEquals(board.getSquares(),squares);


    }
    @Test
    public void testGetIndexOfSquareStanding() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

       Method getIndexOfSquareStandingMethod = Board.class.getDeclaredMethod("getIndexOfSquareStanding", Player.class);
        getIndexOfSquareStandingMethod.setAccessible(true);
        Integer index = (Integer) getIndexOfSquareStandingMethod.invoke(board,humanPlayer);

        assertEquals(index, 3);

        index = (Integer) getIndexOfSquareStandingMethod.invoke(board,new ConservativeBot(new ArrayList<>(), true));

        assertNull(index);
        board.getSquares().clear();

        index = (Integer) getIndexOfSquareStandingMethod.invoke(board,new ConservativeBot(new ArrayList<>(), true));

        assertNull(index);

        board.setSquares(null);

        index = (Integer) getIndexOfSquareStandingMethod.invoke(board,new ConservativeBot(new ArrayList<>(), true));

        assertNull(index);

    }
    @Test
    public void testGetIndexOfFirstSquareWithStrategyType() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method getIndexOfFirstSquareWithStrategyTypeMethod = Board.class.getDeclaredMethod("getIndexOfFirstSquareWithStrategyType", EventType.class);
        getIndexOfFirstSquareWithStrategyTypeMethod.setAccessible(true);
        Integer index = (Integer) getIndexOfFirstSquareWithStrategyTypeMethod.invoke(board,EventType.MONEY);

        assertEquals(index, 3);

        index = (Integer) getIndexOfFirstSquareWithStrategyTypeMethod.invoke(board,EventType.MOOVE_X_STEPS);

        assertNull(index);


        board.setSquares(null);
        index = (Integer) getIndexOfFirstSquareWithStrategyTypeMethod.invoke(board,EventType.MONEY);

        assertNull(index);

        board.setSquares(new ArrayList<>());
        index = (Integer) getIndexOfFirstSquareWithStrategyTypeMethod.invoke(board,EventType.MONEY);

        assertNull(index);



    }
    @Test
    public void testBoard(){

        Board newBoard = new Board(board);

        assert(newBoard.getSquares().equals(board.getSquares()));



    }

    @Test
    public void testSendToJail(){

        board.sendToJail(game, humanPlayer);
        assertEquals(humanPlayer,jailSquare.getPlayers().get(0));
        assertTrue(board.isPlayerInJail(humanPlayer));
        assertTrue(exitSquare.getPlayers().isEmpty());
        board.setSquares(new ArrayList<>());

        board.sendToJail(game, humanPlayer);
        assertEquals(getOutput(), "Error: No jail on board"+System.lineSeparator());
      board.isPlayerInJail(humanPlayer);

    }


    @Test
    public void testMoveToSquare() {
        List<Square> beforeSquares = List.copyOf(board.getSquares());
        board.moveToSquare(game,humanPlayer,exitSquare);
        assertEquals(board.getSquares(), beforeSquares);

        board.moveToSquare(game, humanPlayer,parkingSquare);

        assertEquals(parkingSquare.getPlayers().get(0), humanPlayer);
        assertTrue(exitSquare.getPlayers().isEmpty());

        NormalSquare normalSquare = Mockito.spy(NormalSquare.class);
        PropertySquare propertySquare1 = Mockito.spy(PropertySquare.class);
        PropertySquare propertySquare2 = Mockito.spy(PropertySquare.class);
        Mockito.doNothing().when(normalSquare).execute(any(),any());
        Mockito.doNothing().when(normalSquare).executeWhenPassing(any(),any());
        Mockito.doNothing().when(propertySquare1).execute(any(),any());
        Mockito.doNothing().when(propertySquare1).executeWhenPassing(any(),any());
        Mockito.doNothing().when(propertySquare2).execute(any(),any());
        Mockito.doNothing().when(propertySquare2).executeWhenPassing(any(),any());

        normalSquare.addPlayer(aggressiveBot);
        propertySquare1.addPlayer(humanPlayer);
        squares.clear();

        squares.add(normalSquare);
        squares.add(propertySquare1);
        squares.add(propertySquare2);
        board.setSquares(squares);

        board.moveToSquare(game, humanPlayer,normalSquare);

        verify(normalSquare,times(1)).execute(any(),any());
        verify(normalSquare,times(0)).executeWhenPassing(any(),any());
        verify(propertySquare1,times(0)).execute(any(),any());
        verify(propertySquare1,times(0)).executeWhenPassing(any(),any());
        verify(propertySquare2,times(0)).execute(any(),any());
        verify(propertySquare2,times(1)).executeWhenPassing(any(),any());



    }

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    public void teleportTestNullSquare() {
        Game game = mock(Game.class);
        Player player = mock(Player.class);
        Square square = mock(Square.class);
        Board board = mock(Board.class);

        game.setBoard(board);

        when(game.getBoard()).thenReturn(board);
        when(game.getBoard().getSquarePlayerStanding(player)).thenReturn(null);

        board.teleport(game, player, square);
    }

    @Test
    public void teleportTestNullPosition() {
        Game game = new Game();
        Player player = mock(Player.class);
        Square square = mock(Square.class);
        Board board = new Board();

        game.setBoard(board);

        board.teleport(game, player, square);

        assertNull(board.getSquarePlayerStanding(player));
    }

    @Test
    public void teleportTestContainsPosition() {
        Game game = new Game();
        Player player = mock(Player.class);
        Square square = mock(Square.class);
        Board board = new Board();

        List<Square> squares = new ArrayList<>();
        when(square.isPlayerInSquare(player)).thenReturn(false);
        squares.add(square);
        board.setSquares(squares);
        game.setBoard(board);

        board.teleport(game, player, square);

        assertNull(board.getSquarePlayerStanding(player));
    }
}
