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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ManagePropertiesOptionTest {
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


    }

    @Test
    public void testPropertyOption() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        PropertySquare square = mock(PropertySquare.class);
        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBoard().getSquarePlayerStanding(player)).thenReturn(square);
        when(square.getProperty()).thenReturn(property);
        when(property.getName()).thenReturn("Property 1");
        when(property.getOwner(game)).thenReturn(null);

        ByteArrayInputStream in = new ByteArrayInputStream("7\n".getBytes());
        System.setIn(in);



        Constructor<?> constructor = PropertyOption.class.getDeclaredConstructor(Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object propertyOption = constructor.newInstance(1, game, player, property);

        Method executeMethod = propertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(propertyOption);

//        verify(bankService).buyProperty(player, property);
    }

    @Test
    public void testUpgradeProperty() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        TerrainProperty property = mock(TerrainProperty.class);
        RailwayProperty railwayProperty = mock(RailwayProperty.class);

        when(game.getBank()).thenReturn(bank);

        Constructor<?> constructor = UpgradeProperty.class.getDeclaredConstructor( Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object upgradePropertyOption = constructor.newInstance(1, game, player, property);

        Method executeMethod = upgradePropertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(upgradePropertyOption);

        verify(bank, times(1)).upgradeTerrain(any(), any(), any());

        upgradePropertyOption = constructor.newInstance(1, game, player, railwayProperty);

         executeMethod = upgradePropertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(upgradePropertyOption);

        verify(bank, times(1)).upgradeTerrain(any(), any(), any());


    }

    @Test
    public void testSellPropertyUpgrades() throws Exception {

        TerrainProperty property = mock(TerrainProperty.class);
        RailwayProperty railwayProperty = mock(RailwayProperty.class);
        when(game.getBank()).thenReturn(bank);

        Constructor<?> constructor = SellPropertyUpgrades.class.getDeclaredConstructor( Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object sellPropertyUpgrades = constructor.newInstance(1, game, player, property);

        Method executeMethod = sellPropertyUpgrades.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(sellPropertyUpgrades);

        verify(bank, times(1)).sellUpgrades(any(), any());

        sellPropertyUpgrades = constructor.newInstance(1, game, player, railwayProperty);

        executeMethod = sellPropertyUpgrades.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(sellPropertyUpgrades);

        verify(bank, times(1)).sellUpgrades(any(), any());
    }

    @Test
    public void testMortgageProperty() throws Exception {

        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBank()).thenReturn(bank);
        Class[] classes = ManagePropertiesOption.class.getDeclaredClasses();

        Constructor<?> constructor = MortgageProperty.class.getDeclaredConstructor(Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object mortgageProperty = constructor.newInstance(1, game, player, property);

        Method executeMethod = mortgageProperty.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(mortgageProperty);

        verify(bank, times(1)).mortgageProperty(any(), any());
    }
    @Test
    public void testPayffMortgageProperty() throws Exception {

        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBank()).thenReturn(bank);


        Constructor<?> constructor = PayOffMortgage.class.getDeclaredConstructor( Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object mortgageProperty = constructor.newInstance(1, game, player, property);

        Method executeMethod = mortgageProperty.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(mortgageProperty);

        verify(bank, times(1)).payOffMortgage(any(), any());
    }

    @Test
    public void testSellProperty() throws Exception {

        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBank()).thenReturn(bank);

        Constructor<?> constructor = SellProperty.class.getDeclaredConstructor( Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object sellPropertyOption = constructor.newInstance(1, game, player, property);

        Method executeMethod = sellPropertyOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(sellPropertyOption);

        verify(bank, times(1)).sellPropertyToBank(any(), any());
    }

    @Test
    public void testStartAuctiony() throws Exception {

        TerrainProperty property = mock(TerrainProperty.class);

        when(game.getBank()).thenReturn(bank);
        Class[] classes = ManagePropertiesOption.class.getDeclaredClasses();

        Constructor<?> constructor = StartAuction.class.getDeclaredConstructor( Integer.class, Game.class, Player.class, Property.class);
        constructor.setAccessible(true);
        Object startAuctionOption = constructor.newInstance(1, game, player, property);

        Method executeMethod = startAuctionOption.getClass().getDeclaredMethod("execute");
        executeMethod.setAccessible(true);
        executeMethod.invoke(startAuctionOption);

        verify(bank, times(1)).auctionProperty(any(), any(), any());
    }




}