package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu;



import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PlayerPlayMenuTest {

    private Game game;
    private Player player;
    private PlayerPlayMenu playerPlayMenu;
    private Bank bank;
    private Board board;
    Property terrainProperty;
    PropertySquare propertySquare;

    @BeforeEach
    public void setup() {
        game = mock(Game.class);
        player = mock(Player.class);

        bank = mock(Bank.class);
        board = mock(Board.class);


        Integer [] rentByPrice = new Integer[5];
        rentByPrice[0] = 400;
        rentByPrice[1] = 2000;
        rentByPrice[2] = 5750;
        rentByPrice[3] = 17000;
        rentByPrice[4] = 21000;
        terrainProperty = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);
        Property railWayProperty = new RailwayProperty(false, "Tren", 1000);
        propertySquare = new PropertySquare(terrainProperty);
        when(game.getBoard()).thenReturn(board);
        when(game.getBank()).thenReturn(bank);

        when(board.getSquarePlayerStanding(any())).thenReturn(propertySquare);

        when(game.getBoard().getSquarePlayerStanding(any())).thenReturn(propertySquare);

        playerPlayMenu = new PlayerPlayMenu(game, player);
    }

    @Test
    public void testBuyPropertyOption() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        PropertySquare square = mock(PropertySquare.class);
        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBoard().getSquarePlayerStanding(player)).thenReturn(square);
        when(square.getProperty()).thenReturn(property);
        when(property.getName()).thenReturn("Property 1");
        when(property.getPrice()).thenReturn(1000);
        when(property.getOwner(game)).thenReturn(null);
        when(player.getBalance()).thenReturn(1500);





        Constructor<?> constructor = PlayerPlayMenu.class.getDeclaredClasses()[0].getDeclaredConstructor(Integer.class, Game.class, Player.class);
        constructor.setAccessible(true);
        Object buyPropertyOption = constructor.newInstance(1, game, player);
        game.setBank(new Bank());

        Method executeMethod = buyPropertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(buyPropertyOption);

        verify(bank).buyProperty(player, property);
    }

    @Test
    public void testBuyPropertyOptionInsufficientFunds() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        PropertySquare square = mock(PropertySquare.class);
        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBoard().getSquarePlayerStanding(player)).thenReturn(square);
        when(square.getProperty()).thenReturn(property);
        when(property.getName()).thenReturn("Property 1");
        when(property.getPrice()).thenReturn(1000);
        when(property.getOwner(game)).thenReturn(null);
        when(player.getBalance()).thenReturn(20);

        Constructor<?> constructor = PlayerPlayMenu.class.getDeclaredClasses()[0].getDeclaredConstructor(Integer.class, Game.class, Player.class);
        constructor.setAccessible(true);
        Object buyPropertyOption = constructor.newInstance(1, game, player);

        Method executeMethod = buyPropertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(buyPropertyOption);


        verify(bank, never()).buyProperty(player, property);
    }

    @Test
    public void testBuyPropertyOptionPropertyNotAvailable() throws Exception {

        PropertySquare square = mock(PropertySquare.class);
        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBoard().getSquarePlayerStanding(player)).thenReturn(square);
        when(square.getProperty()).thenReturn(property);
        when(property.getName()).thenReturn("Property 1");
        when(property.getPrice()).thenReturn(1000);
        when(property.getOwner(game)).thenReturn(null);
        when(player.getBalance()).thenReturn(20);

        when(game.getBoard().getSquarePlayerStanding(player)).thenReturn(square);
        when(((PropertySquare) square).getProperty()).thenReturn(null);

        Constructor<?> constructor = PlayerPlayMenu.class.getDeclaredClasses()[0].getDeclaredConstructor(Integer.class, Game.class, Player.class);
        constructor.setAccessible(true);
        Object buyPropertyOption = constructor.newInstance(1, game, player);

        Method executeMethod = buyPropertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(buyPropertyOption);

        verify(bank, never()).buyProperty(player, null);
    }

    @Test
    public void testBuyPlayerPropertiesOption() throws Exception {
        Player otherPlayer = mock(Player.class);
        Property property = mock(Property.class);
        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(otherPlayer);
        PlayerIterator playerIterator = mock(PlayerIterator.class);
        when(playerIterator.getPlayersListTest()).thenReturn(players);
        when (game.getPlayersIterator()).thenReturn(playerIterator);
        when(game.getPlayersIterator().getPlayersListTest()).thenReturn(players);
        when(otherPlayer.getProperties()).thenReturn(List.of(property));
        when(property.getName()).thenReturn("Property 1");





        Constructor<?> constructor = PlayerPlayMenu.class.getDeclaredClasses()[1].getDeclaredConstructor(PlayerPlayMenu.class, Integer.class, Game.class, Player.class);
        constructor.setAccessible(true);
        Object buyPlayerPropertiesOption = constructor.newInstance(null,1, game, player);
        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(in);

        Method executeMethod = buyPlayerPropertiesOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(buyPlayerPropertiesOption);

//        PlayerPlayMenu.BuyPlayerPropertiesOption buyPlayerPropertiesOption = playerPlayMenu.new BuyPlayerPropertiesOption(3, game, player);
//        buyPlayerPropertiesOption.execute();

//        assertEquals(2, buyPlayerPropertiesOption.getSubOptions().size());
//        assertTrue(buyPlayerPropertiesOption.getSubOptions().get(0) instanceof PlayerPlayMenu.BuyOtherPropertyOption);
    }

    @Test
    public void testBuyOtherPropertyOption() throws Exception {
        Property property = mock(Property.class);
        Player owner = mock(Player.class);

        when(property.getOwner(game)).thenReturn(owner);
        when(property.getName()).thenReturn("Property 1");
        Class[] classes= PlayerPlayMenu.class.getDeclaredClasses();
        Constructor<?> constructor = PlayerPlayMenu.class.getDeclaredClasses()[2].getDeclaredConstructor(PlayerPlayMenu.class, Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object buyOtherPropertyOption = constructor.newInstance(null,1, game, player, terrainProperty);
        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(in);



        Method executeMethod = buyOtherPropertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(buyOtherPropertyOption);

//        PlayerPlayMenu.BuyOtherPropertyOption buyOtherPropertyOption = playerPlayMenu.new BuyOtherPropertyOption(1, game, player, property);
//        buyOtherPropertyOption.execute();

//        verify(property).getOwner(game);
    }
}

//
//Integer [] rentByPrice = new Integer[5];
//rentByPrice[0] = 400;
//rentByPrice[1] = 2000;
//rentByPrice[2] = 5750;
//rentByPrice[3] = 17000;
//rentByPrice[4] = 21000;
//Property terrainProperty = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);
//Property railWayProperty = new RailwayProperty(false, "Tren", 1000);
//PropertySquare propertySquare = new PropertySquare(terrainProperty);
//PropertySquare railwayPropertySquare = new PropertySquare(railWayProperty);
//NormalSquare jailSquare = new NormalSquare(new Event(EventType.JAIL, new JailStrategy()), "Comisaria", null,null );
//NormalSquare exitSquare = new NormalSquare(new Event(EventType.MONEY, new MoneyStrategy()),"Salida",null,500);
//        exitSquare.setEventWhenPassing(new Event(EventType.MONEY, new MoneyStrategy()));
//        exitSquare.setAmount(5000);
//        exitSquare.setEvent(new Event(EventType.MONEY, new MoneyStrategy()));
//NormalSquare parkingSquare= new NormalSquare(null, "Parking", null,null );
//
//
//
//
//Player humanPlayer = new HumanPlayer(new ArrayList<>(List.of(railWayProperty)) ,false);
//        humanPlayer.setName("Pepe");
//        humanPlayer.setBalance(3000);
//
//Player botPlayer = new ConservativeBot(new ArrayList<>(List.of(railWayProperty)),false);
//        botPlayer.setName("BotJuan");
//        botPlayer.setBalance(200);
//
//Player botPlayer1 = new ConservativeBot(List.of(new CompanyProperty(false, "railway", 100)),true);
//        botPlayer1.setName("BotJuan");
//        botPlayer1.setBalance(200);
//
//Player botPlayer3 = new ConservativeBot(new ArrayList<>(),false);
//        botPlayer3.setName("BotJuan");
//        botPlayer3.setBalance(200);
//
//
//        propertySquare.addPlayer(humanPlayer);
//List<Square> squares = new ArrayList<>();
//
//        squares.add(propertySquare);
//        squares.add(parkingSquare);
//        squares.add(railwayPropertySquare);
//        squares.add(jailSquare);
//        squares.add(exitSquare);
//
//
//
//
//
//Board board = new Board();
//        board.setSquares(squares);
//
////new MainMenu().execute();
//Game game = new Game(board, new User("test", "test", "test"));
//
//
//
//        humanPlayer.getCards().add(new Card(CardType.LUCK, new Event(EventType.GET_OUT_OF_JAIL, new GetOutOfJailStrategy()),"get out of jail card",null,null,false));
//
//        game.setPlayersIterator(new PlayerIterator(List.of(humanPlayer,botPlayer, botPlayer1, botPlayer3)));
//
//
//        game.addEvent("Player1 hace algo");
//        game.addEvent("Player2 hace algo");
//        game.addEvent("Player3 hace algo");
//        game.addEvent("Player4 hace algo");
//        game.addEvent("Player5 hace algo");
//        game.addEvent("Player6 hace algo");
//        game.addEvent("Player7 hace algo");
//        game.addEvent("Player8 hace algo");
//        game.addEvent("Player9 hace algo");
//        game.addEvent("Player10 hace algo");
//        game.addEvent("Player11 hace algo");
//        game.addEvent("Player12 hace algo");
//        game.addEvent("Player13 hace algo");
//        game.addEvent("Player14 hace algo");
//        game.addEvent("Player15 hace algoasdasd asdasdasd asdasda asdasdasdasda asdasdasdasdasdasdasdasdasdad asdasdasdasdasd asdasdddddddddddaaaaaassss asdasdad asdasdasdasd asdasdasd");
//
//
//
//
//        humanPlayer.play(game);
//
//        UiService.getInstance().printGameState(game);