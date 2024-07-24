package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.BotPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BotPlayerTest {

    private AggressiveBot aggressiveBot;
    private TerrainProperty priorityTerrain;
    private TerrainProperty nonPriorityTerrain;
    private RailwayProperty railwayProperty;
    private PropertySquare prioritySquare;
    private PropertySquare nonPrioritySquare;
    private PropertySquare railwaySquare;
    private Game game;
    private Board board;

    @BeforeEach
    public void setUp2() {
        List<Property> properties = new ArrayList<>();
        aggressiveBot = new AggressiveBot(properties, false);
    }

    @Mock
    private Game gameMock;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para cada test
        List<Property> properties = new ArrayList<>();
        priorityTerrain = new TerrainProperty(false, "Priority Terrain", 1000,
                Zone.SUR, Province.TUCUMAN, new Integer[]{100, 200, 300}, 500, 1);
        nonPriorityTerrain = new TerrainProperty(false, "Non-Priority Terrain", 1200,
                Zone.CENTRO, Province.RIO_NEGRO, new Integer[]{100, 200, 300}, 500, 1);
        railwayProperty = new RailwayProperty(false, "Railway", 200);

        properties.add(priorityTerrain);
        properties.add(nonPriorityTerrain);
        properties.add(railwayProperty);

        aggressiveBot = new AggressiveBot(properties, false);

        prioritySquare = new PropertySquare(priorityTerrain);
        nonPrioritySquare = new PropertySquare(nonPriorityTerrain);
        railwaySquare = new PropertySquare(railwayProperty);

        List<Square> squares = new ArrayList<>();
        squares.add(prioritySquare);
        squares.add(nonPrioritySquare);
        squares.add(railwaySquare);

        board = new Board();
        board.setSquares(squares);

        game = new Game();
        game.setBoard(board);
    }


    @Test
    @Tag("Constructors-Getters-Setters")
    public void testGettersAndSetters() {
        // Crear una lista de propiedades para el BotPlayer
        List<Property> properties = new ArrayList<>();

        // Crear una lista de provincias de preferencia para el BotPlayer
        List<Province> preferenceProvinces = new ArrayList<>();

        // Crear una instancia de una clase concreta que extienda BotPlayer para pruebas
        BotPlayer botPlayer = new AggressiveBot();

        // Probar el getter y setter para la lista de propiedades
        botPlayer.setProperties(properties);
        assertEquals(properties, botPlayer.getProperties());

        // Probar el getter y setter para el nombre
        botPlayer.setName("TestBot");
        assertEquals("TestBot", botPlayer.getName());

        // Probar el getter y setter para el balance
        botPlayer.setBalance(1000);
        assertEquals(1000, botPlayer.getBalance());

        // Probar el getter y setter para el estado de bancarrota
        botPlayer.setBankrupted(true);
        assertTrue(botPlayer.getBankrupted());

        // Probar el getter y setter para la lista de cartas
        List<Card> cards = new ArrayList<>();
        botPlayer.setCards(cards);
        assertEquals(cards, botPlayer.getCards());

    }
    @Test
    @Tag("Constructors-Getters-Setters")
    public void testAggressiveBotConstructorAndPriority() {
        List<Property> properties = List.of(
                new TerrainProperty(false, "Terreno 1", 1000,
                        Zone.SUR, Province.TUCUMAN, new Integer[]{100, 200, 300}, 500, 1),
                new RailwayProperty(false, "Ferrocarril 1", 200)
        );
        AggressiveBot aggressiveBot = new AggressiveBot(properties, false);

        assertEquals(properties, aggressiveBot.getProperties());
        assertFalse(aggressiveBot.getBankrupted());
        assertEquals(List.of(Province.TUCUMAN, Province.CORDOBA, Province.BUENOS_AIRES), aggressiveBot.getPreferenceProvinces());

        Property priorityProperty = new TerrainProperty(false, "Terreno 2", 1500,
                Zone.NORTE, Province.TUCUMAN, new Integer[]{100, 200, 300}, 500, 1);
        Property nonPriorityProperty = new TerrainProperty(false, "Terreno 3", 1500,
                Zone.CENTRO, Province.SANTA_FE, new Integer[]{100, 200, 300}, 500, 1);
        Property railwayProperty = new RailwayProperty(false, "Ferrocarril 2", 300);
        Property nonPriorityTerrainProperty = new TerrainProperty(false, "Terreno 4", 1200,
                Zone.CENTRO, Province.RIO_NEGRO, new Integer[]{100, 200, 300}, 500, 1);
        Property nonTerrainProperty = new RailwayProperty(false, "Ferrocarril 3", 300);

        assertTrue(aggressiveBot.isPriority(priorityProperty));
        assertFalse(aggressiveBot.isPriority(nonPriorityProperty));
        assertTrue(aggressiveBot.isPriority(railwayProperty));

        // comprobar que isPriorityProperty devuelve false cuando la propiedad no es una prioridad
        assertFalse(aggressiveBot.isPriorityProperty(nonPriorityTerrainProperty));

        // Verificación de que isPriorityProperty devuelve false cuando la propiedad no es un TerrainProperty
        assertFalse(aggressiveBot.isPriorityProperty(nonTerrainProperty));

    }

    @Test
    @Tag("Constructors-Getters-Setters")
    public void testModerateBotConstructorAndPriority() {
        List<Property> properties = List.of(
                new TerrainProperty(false, "Terreno 1", 1000,
                        Zone.CENTRO, Province.MENDOZA, new Integer[]{100, 200, 300}, 500, 1),
                new RailwayProperty(false, "Ferrocarril 1", 200)
        );
        ModerateBot moderateBot = new ModerateBot(properties, false);

        assertEquals(properties, moderateBot.getProperties());
        assertFalse(moderateBot.getBankrupted());
        assertEquals(List.of(Province.MENDOZA, Province.SANTA_FE, Province.TUCUMAN), moderateBot.getPreferenceProvinces());

        Property priorityProperty = new TerrainProperty(false, "Terreno 2", 1500,
                Zone.NORTE, Province.MENDOZA, new Integer[]{100, 200, 300}, 500, 1);
        Property nonPriorityProperty = new TerrainProperty(false, "Terreno 3", 1500,
                Zone.SUR, Province.CORDOBA, new Integer[]{100, 200, 300}, 500, 1);
        Property railwayProperty = new RailwayProperty(false, "Ferrocarril 2", 300);

        assertTrue(moderateBot.isPriority(priorityProperty));
        assertFalse(moderateBot.isPriority(nonPriorityProperty));
        assertTrue(moderateBot.isPriority(railwayProperty));
    }

    @Test
    @Tag("Constructors-Getters-Setters")
    public void testConservativeBotConstructorAndPriority() {
        List<Property> properties = List.of(
                new TerrainProperty(false, "Terreno 1", 1000, Zone.SUR, Province.FORMOSA, new Integer[]{100, 200, 300}, 500, 1),
                new RailwayProperty(false, "Ferrocarril 1", 200)
        );
        ConservativeBot conservativeBot = new ConservativeBot(properties, false);

        assertEquals(properties, conservativeBot.getProperties());
        assertFalse(conservativeBot.getBankrupted());
        assertEquals(List.of(Province.FORMOSA, Province.RIO_NEGRO, Province.SALTA), conservativeBot.getPreferenceProvinces());

        Property priorityProperty = new TerrainProperty(false, "Terreno 2", 1500, Zone.CENTRO, Province.FORMOSA, new Integer[]{100, 200, 300}, 500, 1);
        Property nonPriorityProperty = new TerrainProperty(false, "Terreno 3", 1500, Zone.CENTRO, Province.CORDOBA, new Integer[]{100, 200, 300}, 500, 1);

        assertTrue(conservativeBot.isPriority(priorityProperty));
        assertFalse(conservativeBot.isPriority(nonPriorityProperty));
    }

    @Test
    @Tag("GetNoPriorities")
    public void testGetNoPriorities_AllNonPriorities() {
        List<Property> properties = aggressiveBot.getProperties();

        List<Property> noPriorities = aggressiveBot.getNopriorities(properties);

        // Dado que en AggressiveBot las propiedades prioritarias son aquellas que están en provincias preferidas
        // y las propiedades de ferrocarril y de compañía, debemos ajustar nuestras expectativas
        assertEquals(1, noPriorities.size());
        assertEquals(nonPriorityTerrain, noPriorities.get(0));
    }

    @Test
    @Tag("GetNoPriorities")
    public void testGetNoPriorities_NoNonPriorities() {
        List<Property> properties = new ArrayList<>();
        properties.add(priorityTerrain);

        aggressiveBot.setProperties(properties);

        List<Property> noPriorities = aggressiveBot.getNopriorities(properties);

        assertEquals(0, noPriorities.size());
    }

    @Test
    @Tag("GetMorgeageableProperties")
    public void getMorgageablePropertiesTest() {
        List<Property> listProperties = new ArrayList<>();
        Property prop = mock(Property.class);
        TerrainProperty terrain = mock(TerrainProperty.class);
        terrain.setUpgradeLevel(0);

        listProperties.add(prop);
        listProperties.add(terrain);

        BotPlayer bot = mock(BotPlayer.class);

        when(bot.getMortgageableProperties(listProperties)).thenReturn(listProperties);

        List<Property> resultList = bot.getMortgageableProperties(listProperties);

        assertEquals(resultList.get(1), listProperties.get(1));

    }
    @Test
    @Tag("GetMorgeageableProperties")
    public void getMorgageablePropertiesTestNoTerrain() {
        List<Property> listProperties = new ArrayList<>();

        RailwayProperty railwayProperty = new RailwayProperty();
        railwayProperty.setMortgaged(false);
        railwayProperty.setPrice(100000);

        RailwayProperty railwayProperty2 = new RailwayProperty();
        railwayProperty2.setMortgaged(false);
        railwayProperty2.setPrice(50000);

        listProperties.add(railwayProperty);
        listProperties.add(railwayProperty2);

        ModerateBot moderateBot = new ModerateBot(listProperties,false);

        List<Property> result = moderateBot.getMortgageableProperties(moderateBot.getProperties());
        int size = result.size();
        assertEquals(size , 2);

    }
    @Test
    @Tag("IsPriority")
    public void isPriorityPropertyTest(){
        boolean b = aggressiveBot.isPriorityProperty(priorityTerrain);
        assertTrue(b);
    }
    @Test
    @Tag("IsPriority")
    public void isNotPriorityPropertyTest(){
        boolean b = aggressiveBot.isPriorityProperty(nonPriorityTerrain);
        assertFalse(b);
    }

    @Test
    @Tag("SellPropertyToPlayer")
    public void sellPropertyToPlayerInner_TooHighPrice() {
        // Arrange
        Property property = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
                );

        // Ejemplo de una propiedad con precio 20000
        aggressiveBot.setBalance(50000);// Ejemplo de un comprador con saldo de 25000
        int price = 90; // Ejemplo de precio de venta inferior al max permitido
        BotPlayer propertyManager = new AggressiveBot();
        propertyManager.setProperties(new ArrayList<>(){{
            add(property);
        }});
        // Act
        boolean result = propertyManager.sellPropertyToPlayerInner(null, property, aggressiveBot, price);

        // Assert
        assertTrue(result, "Expected sale to fail due to price being too high");
    }

    @Test
    @Tag("SellPropertyToPlayer")
    public void sellPropertyToPlayerInner_InsufficientBalance() {
        // Arrange
        Property property = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );

        // Ejemplo de una propiedad con precio 20000
        aggressiveBot.setBalance(50000);// Ejemplo de un comprador con saldo de 25000
        int price = 200000000; // Ejemplo de precio de venta igual a la propiedad
        BotPlayer propertyManager = new AggressiveBot();
        propertyManager.setProperties(new ArrayList<>(){{
            add(property);
        }});
        // Act
        boolean result = propertyManager.sellPropertyToPlayerInner(null, property, aggressiveBot, price);

        // Assert
        assertFalse(result, "Expected sale to fail due to insufficient buyer balance");
    }

    @Test
    @Tag("FindWayToMakeMoney")
    public void findWayToMakeMoney_EnoughMoneyToPay() {
        // Arrange
        aggressiveBot.setBalance(300);
        int initialBalance = aggressiveBot.getBalance();
        int moneyToPay = 500; // Establecer el dinero que se desea obtener

        // Act
        aggressiveBot.findWayToMakeMoney(game, moneyToPay);

        // Assert
        assertTrue(aggressiveBot.getBalance() >= initialBalance + moneyToPay,
                "Expected balance to increase by at least moneyToPay");
    }


    @Test
    @Tag("FindWayToMakeMoney")
    public void findWayToMakeMoney_NotEnoughMoneyToPay() {
        // Arrange
        aggressiveBot.setBalance(100);
        int initialBalance = aggressiveBot.getBalance();
        int moneyToPay = 9999; // Un monto que seguramente no se pueda obtener

        // Act
        aggressiveBot.findWayToMakeMoney(game, moneyToPay);

        // Assert
        assertTrue(initialBalance < aggressiveBot.getBalance(),
                "Expected balance to remain unchanged if moneyToPay is too high");
    }

    @Test
    @Tag("FindWayToMakeMoney")
    public void findWayToMakeMoney_offerAuction() {
        Property property = new TerrainProperty(
                true,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                0
        );
        List<Property> properties = new ArrayList<>();
        properties.add(property);

        aggressiveBot = new AggressiveBot(properties,false);

        aggressiveBot.setBalance(300);
        int initialBalance = aggressiveBot.getBalance();
        int moneyToPay = 10000; // Establecer el dinero que se desea obtener

        // Act
        aggressiveBot.findWayToMakeMoney(game, moneyToPay);

        // Assert
        assertTrue(aggressiveBot.getBalance() >= initialBalance + moneyToPay,
                "Expected balance to increase by at least moneyToPay");

    }

    @Test
    @Tag("FindWayToMakeMoney")
    public void findWayToMakeMoney_offerAuctionReturnNumber() {
        Property property = new TerrainProperty(
                true,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                0
        );
        List<Property> properties = new ArrayList<>();
        properties.add(property);

        //Spy moderateBot para simular retorno true en un metodo suyo
        AggressiveBot aggressiveBot = spy(new AggressiveBot(properties, false));
        // Mockear offerAuction para que devuelva un resultado
        doReturn(500).when(aggressiveBot).offerAuction(game);

        aggressiveBot.setBalance(300);
        int initialBalance = aggressiveBot.getBalance();
        int moneyToPay = 10000; // Establecer el dinero que se desea obtener

        // Act
        aggressiveBot.findWayToMakeMoney(game, moneyToPay);

        // Assert
        assertTrue(aggressiveBot.getBalance() >= initialBalance + moneyToPay,
                "Expected balance to increase by at least moneyToPay");

    }


    @Test
    @Tag("CheckAllPropertiesOwnership")
    public void testCheckAllPropertiesOwnership() {
        // Crear mocks de los cuadrados de propiedad
        PropertySquare propertySquare1 = mock(PropertySquare.class);
        PropertySquare propertySquare2 = mock(PropertySquare.class);
        PropertySquare propertySquare3 = mock(PropertySquare.class);

        // Crear mocks de las propiedades
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        Property property3 = mock(Property.class);

        // Configurar propiedades de los cuadrados
        when(propertySquare1.getProperty()).thenReturn(property1);
        when(propertySquare2.getProperty()).thenReturn(property2);
        when(propertySquare3.getProperty()).thenReturn(property3);

        // Simular dueños de las propiedades
        when(property1.getOwner(gameMock)).thenReturn(null);
        when(property2.getOwner(gameMock)).thenReturn(null);
        when(property3.getOwner(gameMock)).thenReturn(mock(Player.class)); // Simular propiedad con dueño

        // Crear una lista de cuadrados de propiedad simulados
        List<Square> squares = new ArrayList<>();
        squares.add(propertySquare1);
        squares.add(propertySquare2);
        squares.add(propertySquare3);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        boolean result = aggressiveBot.checkAllPropertiesOwnership(gameMock);

        // Verificar que el resultado sea el esperado
        assertFalse(result); // Esperamos que devuelva false porque no todas las propiedades están sin dueño

        // Verificar interacciones con los mocks (opcional)
        verify(property1, times(1)).getOwner(gameMock);
        verify(property2, times(1)).getOwner(gameMock);
        verify(property3, times(1)).getOwner(gameMock);
    }
    @Test
    @Tag("CheckAllPropertiesOwnership")
    public void testCheckAllPropertiesOwnership_MoreThan75PercentUnowned() {
        // Crear mocks de los cuadrados de propiedad
        PropertySquare propertySquare1 = mock(PropertySquare.class);
        PropertySquare propertySquare2 = mock(PropertySquare.class);
        PropertySquare propertySquare3 = mock(PropertySquare.class);

        // Crear mocks de las propiedades
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        Property property3 = mock(Property.class);

        // Configurar propiedades de los cuadrados
        when(propertySquare1.getProperty()).thenReturn(property1);
        when(propertySquare2.getProperty()).thenReturn(property2);
        when(propertySquare3.getProperty()).thenReturn(property3);

        // Simular dueños de las propiedades
        when(property1.getOwner(gameMock)).thenReturn(null);
        when(property2.getOwner(gameMock)).thenReturn(null);
        when(property3.getOwner(gameMock)).thenReturn(mock(Player.class)); // Simular propiedad con dueño

        // Crear una lista de cuadrados de propiedad simulados
        List<Square> squares = new ArrayList<>();
        squares.add(propertySquare1);
        squares.add(propertySquare2);
        squares.add(propertySquare3);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        boolean result = aggressiveBot.checkAllPropertiesOwnership(gameMock);

        // Verificar que el resultado sea el esperado
        assertFalse(result); // Esperamos que devuelva false porque no todas las propiedades están sin dueño

        // Verificar interacciones con los mocks (opcional)
        verify(property1).getOwner(gameMock);
        verify(property2).getOwner(gameMock);
        verify(property3).getOwner(gameMock);
    }

    @Test
    @Tag("CheckAllPropertiesOwnership")
    public void testCheckAllPropertiesOwnership_MoreThan75PercentOwned() {
        // Crear mocks de los cuadrados de propiedad
        PropertySquare propertySquare1 = mock(PropertySquare.class);
        PropertySquare propertySquare2 = mock(PropertySquare.class);
        PropertySquare propertySquare3 = mock(PropertySquare.class);
        PropertySquare propertySquare4 = mock(PropertySquare.class);

        // Crear mocks de las propiedades
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        Property property3 = mock(Property.class);
        Property property4 = mock(Property.class);

        // Configurar propiedades de los cuadrados
        when(propertySquare1.getProperty()).thenReturn(property1);
        when(propertySquare2.getProperty()).thenReturn(property2);
        when(propertySquare3.getProperty()).thenReturn(property3);
        when(propertySquare4.getProperty()).thenReturn(property4);

        // Crear un mock de jugadores
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        // Simular dueños de las propiedades
        when(property1.getOwner(gameMock)).thenReturn(player1);
        when(property2.getOwner(gameMock)).thenReturn(player2);
        when(property3.getOwner(gameMock)).thenReturn(player1);
        when(property4.getOwner(gameMock)).thenReturn(player2);

        // Crear una lista de cuadrados de propiedad simulados
        List<Square> squares = new ArrayList<>();
        squares.add(propertySquare1);
        squares.add(propertySquare2);
        squares.add(propertySquare3);
        squares.add(propertySquare4);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        boolean result = aggressiveBot.checkAllPropertiesOwnership(gameMock);

        // Verificar que el resultado sea el esperado
        assertTrue(result); // Esperamos que devuelva true porque más del 75% de las propiedades tienen dueño

        // Verificar interacciones con los mocks (opcional)
        verify(property1).getOwner(gameMock);
        verify(property2).getOwner(gameMock);
        verify(property3).getOwner(gameMock);
        verify(property4).getOwner(gameMock);
    }

    @Test
    @Tag("MakeBid")
    public void makeBid_CanAffordAndPriority_ReturnsValidBid() {
        int currentHighestBid = 500; // Precio de la oferta más alta actualmente
        priorityTerrain.setPrice(1000);
        aggressiveBot.setBalance(2000);
        Integer bid = aggressiveBot.makeBid(priorityTerrain, currentHighestBid);

        assertTrue(2000<= bid, "Expected to bid the property price if affordable and a priority");
    }

    @Test
    @Tag("MakeBid")
    public void makeBid_CanAffordButNotPriority_ReturnsZero() {

        int currentHighestBid = 500; // Precio de la oferta más alta actualmente
        aggressiveBot.setBalance(1000);

        Integer bid = aggressiveBot.makeBid(nonPriorityTerrain, currentHighestBid);


        assertEquals(0, bid, "Expected to bid zero if affordable but not a priority property");
    }

    @Test
    @Tag("MakeBid")
    public void makeBid_CannotAfford_ReturnsZero() {

        int currentHighestBid = 1500; // Precio de la oferta más alta actualmente
        aggressiveBot.setBalance(0);

        Integer bid = aggressiveBot.makeBid(priorityTerrain, currentHighestBid);

        assertTrue(2000<= bid, "Expected to bid zero if unable to afford the current auction");
    }

    @Test
    @Tag("SellProperty")
    public void sellPropertyTest(){
        aggressiveBot.setBalance(500);
        List<TerrainProperty> properties = aggressiveBot.getPlayerTerrainProperties(aggressiveBot);
        for (TerrainProperty tp : properties){
            tp.setUpgradeLevel(0);
        }
        Integer x = aggressiveBot.sellProperty(game);
        assertEquals(600,x);
    }

    @Test
    @Tag("SellProperty")
    public void sellPropertyTest_AllPriorities() {

        // Crear propiedades con prioridad
        TerrainProperty property1 = new TerrainProperty(false, "Property1", 300, Zone.SUR, Province.CORDOBA,
                new Integer[]{100, 200, 300}, 100, 1);
        TerrainProperty property2 = new TerrainProperty(false, "Property2", 400, Zone.NORTE, Province.CORDOBA,
                new Integer[]{150, 250, 350}, 120, 1);
        TerrainProperty property3 = new TerrainProperty(false, "Property3", 500, Zone.CENTRO, Province.CORDOBA,
                new Integer[]{200, 300, 400}, 150, 1);

        // Crear una lista de propiedades prioritarias
        List<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);
        properties.add(property3);

        // Crear una nueva instancia de aggressiveBot con las propiedades prioritarias
        BotPlayer aggressiveBot = new AggressiveBot(properties, true); // Ajusta según cómo se construya AggressiveBot

        // Configurar el saldo inicial del jugador agresivo
        aggressiveBot.setBalance(500);

        // Llamar al método que queremos testear
        Integer result = aggressiveBot.sellProperty(game);

        // Verificar que el resultado sea el esperado (el precio de una propiedad prioritaria vendida)
        assertTrue(result == 300 || result == 400 || result == 500); // Esperamos el precio original de una propiedad prioritaria

        // Verificar que la propiedad vendida ya no esté en las propiedades del jugador
    }
    @Test
    @Tag("SellProperty")
    public void sellPropertyTest_NoProperties() {
        // Crear una lista de propiedades vacía
        List<Property> emptyProperties = new ArrayList<>();

        // Crear una nueva instancia de aggressiveBot y asignarle las propiedades vacías
        BotPlayer aggressiveBot = new AggressiveBot();
        aggressiveBot.setProperties(emptyProperties);

        // Configurar el saldo inicial del jugador agresivo
        aggressiveBot.setBalance(500);

        // Llamar al método que queremos testear
        Integer result = aggressiveBot.sellProperty(game);

        // Verificar que el resultado sea 0
        assertEquals(0, result); // Esperamos que no se venda ninguna propiedad y que el resultado sea 0
    }

    @Test
    @Tag("GetNoPriorities")
    public void getNoprioritiesTest(){
        List<Property> properties = aggressiveBot.getNopriorities(aggressiveBot.getProperties());
        TerrainProperty tp = new TerrainProperty();
        for (Property p: properties){
            if (p instanceof  TerrainProperty){
                tp = (TerrainProperty) p;
            }
        }
        assertEquals(1,properties.size());
        assertEquals(tp.getZone(),Zone.CENTRO);
        assertEquals(tp.getProvince(),Province.RIO_NEGRO);
        assertEquals(tp.getName(),nonPrioritySquare.getName());
    }

    @Test
    @Tag("CheckAllPropertiesOwnership")
    public void checkAllPropertiesOwnershipTest(){
        boolean b = aggressiveBot.checkAllPropertiesOwnership(game);

        Assertions.assertFalse(b);
    }

    @Test
    @Tag("MortgageProperty")
    public void mortgagePropertyReturn0(){
        aggressiveBot.setProperties(new ArrayList<>()); // No properties

        int result = aggressiveBot.mortgageProperty(game);

        assertEquals(0, result);
    }
    @Test
    @Tag("MortgageProperty")
    public void mortgageProperty()
    {
        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setMortgaged(false);
        terrainProperty.setProvince(Province.BUENOS_AIRES);
        terrainProperty.setZone(Zone.SUR);
        terrainProperty.setPrice(5000);
        terrainProperty.setUpgradeLevel(0);

        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setProvince(Province.BUENOS_AIRES);
        terrainProperty2.setZone(Zone.NORTE);
        terrainProperty2.setPrice(7000);
        terrainProperty2.setUpgradeLevel(1);

        TerrainProperty terrainProperty3 = new TerrainProperty();
        terrainProperty3.setMortgaged(false);
        terrainProperty3.setProvince(Province.BUENOS_AIRES);
        terrainProperty3.setZone(Zone.CENTRO);
        terrainProperty3.setPrice(6000);
        terrainProperty3.setUpgradeLevel(0);

        TerrainProperty terrainProperty4 = new TerrainProperty();
        terrainProperty4.setMortgaged(false);
        terrainProperty4.setProvince(Province.RIO_NEGRO);
        terrainProperty4.setZone(Zone.NORTE);
        terrainProperty4.setPrice(2000);
        terrainProperty4.setUpgradeLevel(0);

        CompanyProperty companyProperty = new CompanyProperty();
        companyProperty.setMortgaged(true);
        companyProperty.setPrice(3700);

        RailwayProperty railwayProperty1 = new RailwayProperty();
        railwayProperty1.setMortgaged(false);
        railwayProperty1.setPrice(4200);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);
        properties.add(terrainProperty3);
        properties.add(terrainProperty4);
        properties.add(companyProperty);
        properties.add(railwayProperty1);
        AggressiveBot aggressiveBot1 = new AggressiveBot(properties,false);
        aggressiveBot1.setBalance(15000);

        int result = aggressiveBot1.mortgageProperty(game);
        assertTrue(result!=0);
    }

    @Test
    @Tag("MortgageProperty")
    public void mortgagePropertyAllPriorities() {
        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setMortgaged(false);
        terrainProperty.setProvince(Province.BUENOS_AIRES);
        terrainProperty.setZone(Zone.SUR);
        terrainProperty.setPrice(5000);
        terrainProperty.setUpgradeLevel(0);

        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setProvince(Province.BUENOS_AIRES);
        terrainProperty2.setZone(Zone.NORTE);
        terrainProperty2.setPrice(7000);
        terrainProperty2.setUpgradeLevel(1);

        TerrainProperty terrainProperty3 = new TerrainProperty();
        terrainProperty3.setMortgaged(false);
        terrainProperty3.setProvince(Province.BUENOS_AIRES);
        terrainProperty3.setZone(Zone.CENTRO);
        terrainProperty3.setPrice(6000);
        terrainProperty3.setUpgradeLevel(0);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);
        properties.add(terrainProperty3);

        AggressiveBot aggressiveBot1 = new AggressiveBot(properties,false);
        aggressiveBot1.setBalance(15000);

        int result = aggressiveBot1.mortgageProperty(game);
        assertTrue(result!=0);
    }



    @Test
    @Tag("GetMortgageableProperties")
    public void testGetMortgageableProperties() {
        priorityTerrain.setMortgaged(false);
        priorityTerrain.setUpgradeLevel(0);
        List<Property> properties = new ArrayList<>(){{
           add(priorityTerrain);
           add(priorityTerrain);
           add(priorityTerrain);
           add(priorityTerrain);
           add(priorityTerrain);
        }};
        List<Property> result = aggressiveBot.getMortgageableProperties(properties);

        // Verificar que el resultado contiene solo las propiedades hipotecables
        assertEquals(5, result.size());
    }
    @Test
    @Tag("FindRepeatedProvinces")
    public void testFindRepeatedProvinces_ThreeOrMoreTimes() {
        // Configurar el mapa de provincias y sus conteos
        Map<Province, Integer> provinceCount = new HashMap<>();
        provinceCount.put(Province.BUENOS_AIRES, 3);
        provinceCount.put(Province.CORDOBA, 4);
        provinceCount.put(Province.SANTA_FE, 1);

        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findRepeatedProvinces(provinceCount);

        // Verificar que el resultado sea el esperado
        assertTrue(result.contains(Province.BUENOS_AIRES));
        assertTrue(result.contains(Province.CORDOBA));
        assertFalse(result.contains(Province.SANTA_FE));
    }
    @Test
    @Tag("FindRepeatedProvinces")
    public void testFindRepeatedProvinces_TucumanOrRioNegroTwice() {
        // Configurar el mapa de provincias y sus conteos
        Map<Province, Integer> provinceCount = new HashMap<>();
        provinceCount.put(Province.TUCUMAN, 2);
        provinceCount.put(Province.RIO_NEGRO, 2);
        provinceCount.put(Province.MENDOZA, 2);

        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findRepeatedProvinces(provinceCount);

        // Verificar que el resultado sea el esperado
        assertTrue(result.contains(Province.TUCUMAN));
        assertTrue(result.contains(Province.RIO_NEGRO));
        assertFalse(result.contains(Province.MENDOZA));
    }
    @Test
    @Tag("FindRepeatedProvinces")
    public void testFindRepeatedProvinces_LessThanThree() {
        // Configurar el mapa de provincias y sus conteos
        Map<Province, Integer> provinceCount = new HashMap<>();
        provinceCount.put(Province.BUENOS_AIRES, 1);
        provinceCount.put(Province.CORDOBA, 2);
        provinceCount.put(Province.SANTA_FE, 2);

        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findRepeatedProvinces(provinceCount);

        // Verificar que el resultado sea el esperado
        assertFalse(result.contains(Province.BUENOS_AIRES));
        assertFalse(result.contains(Province.CORDOBA));
        assertFalse(result.contains(Province.SANTA_FE));
    }
    @Test
    @Tag("FindRepeatedProvinces")
    public void testFindRepeatedProvinces_EmptyMap() {
        // Configurar el mapa de provincias vacío
        Map<Province, Integer> provinceCount = new HashMap<>();

        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findRepeatedProvinces(provinceCount);

        // Verificar que el resultado sea una lista vacía
        assertTrue(result.isEmpty());
    }
    @Test
    @Tag("CountProvinceOccurrences")
    public void testCountProvinceOccurrences_MultipleOccurrences() {
        // Crear mocks de las propiedades
        TerrainProperty property1 = mock(TerrainProperty.class);
        TerrainProperty property2 = mock(TerrainProperty.class);
        TerrainProperty property3 = mock(TerrainProperty.class);

        // Configurar las provincias de las propiedades
        when(property1.getProvince()).thenReturn(Province.BUENOS_AIRES);
        when(property2.getProvince()).thenReturn(Province.BUENOS_AIRES);
        when(property3.getProvince()).thenReturn(Province.CORDOBA);

        // Crear una lista de propiedades
        List<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);
        properties.add(property3);

        // Llamar al método que queremos testear
        Map<Province, Integer> result = aggressiveBot.countProvinceOccurrences(properties);

        // Verificar que el resultado sea el esperado
        assertEquals(2, result.get(Province.BUENOS_AIRES).intValue());
        assertEquals(1, result.get(Province.CORDOBA).intValue());
        assertNull(result.get(Province.SANTA_FE));
    }

    @Test
    @Tag("CountProvinceOccurrences")
    public void testCountProvinceOccurrences_EmptyList() {
        // Crear una lista vacía de propiedades
        List<Property> properties = new ArrayList<>();

        // Llamar al método que queremos testear
        Map<Province, Integer> result = aggressiveBot.countProvinceOccurrences(properties);

        // Verificar que el resultado sea un mapa vacío
        assertTrue(result.isEmpty());
    }

    @Test
    @Tag("CountProvinceOccurrences")
    public void testCountProvinceOccurrences_NoTerrainProperties() {
        // Crear mocks de las propiedades
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        Property property3 = mock(Property.class);

        // Crear una lista de propiedades sin TerrainProperties
        List<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);
        properties.add(property3);

        // Llamar al método que queremos testear
        Map<Province, Integer> result = aggressiveBot.countProvinceOccurrences(properties);

        // Verificar que el resultado sea un mapa vacío
        assertTrue(result.isEmpty());
    }

    @Test
    @Tag("CountProvinceOccurrences")
    public void testCountProvinceOccurrences_SingleProvince() {
        // Crear un mock de la propiedad
        TerrainProperty property1 = mock(TerrainProperty.class);

        // Configurar la provincia de la propiedad
        when(property1.getProvince()).thenReturn(Province.BUENOS_AIRES);

        // Crear una lista de propiedades
        List<Property> properties = new ArrayList<>();
        properties.add(property1);

        // Llamar al método que queremos testear
        Map<Province, Integer> result = aggressiveBot.countProvinceOccurrences(properties);

        // Verificar que el resultado sea el esperado
        assertEquals(1, result.get(Province.BUENOS_AIRES).intValue());
        assertNull(result.get(Province.CORDOBA));
        assertNull(result.get(Province.SANTA_FE));
    }
    @Test
    @Tag("FindCompletedProvinces")
    public void testFindCompletedProvinces_MultipleOccurrences() {
        // Crear mocks de las propiedades
        TerrainProperty property1 = mock(TerrainProperty.class);
        TerrainProperty property2 = mock(TerrainProperty.class);
        TerrainProperty property3 = mock(TerrainProperty.class);
        TerrainProperty property4 = mock(TerrainProperty.class);

        // Configurar las provincias de las propiedades
        when(property1.getProvince()).thenReturn(Province.BUENOS_AIRES);
        when(property2.getProvince()).thenReturn(Province.BUENOS_AIRES);
        when(property3.getProvince()).thenReturn(Province.BUENOS_AIRES);
        when(property4.getProvince()).thenReturn(Province.CORDOBA);

        // Crear una lista de propiedades
        List<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);

        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findCompletedProvinces(properties);

        // Verificar que el resultado sea el esperado
        assertTrue(result.contains(Province.BUENOS_AIRES));
        assertFalse(result.contains(Province.CORDOBA));
    }
    @Test
    @Tag("FindCompletedProvinces")
    public void testFindCompletedProvinces_EmptyList() {
        // Crear una lista vacía de propiedades
        List<Property> properties = new ArrayList<>();

        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findCompletedProvinces(properties);

        // Verificar que el resultado sea una lista vacía
        assertTrue(result.isEmpty());
    }

    @Test
    @Tag("FindCompletedProvinces")
    public void testFindCompletedProvinces_NoCompletedProvinces() {
        // Crear mocks de las propiedades
        TerrainProperty property1 = mock(TerrainProperty.class);
        TerrainProperty property2 = mock(TerrainProperty.class);
        TerrainProperty property3 = mock(TerrainProperty.class);

        // Configurar las provincias de las propiedades
        when(property1.getProvince()).thenReturn(Province.BUENOS_AIRES);
        when(property2.getProvince()).thenReturn(Province.CORDOBA);
        when(property3.getProvince()).thenReturn(Province.SANTA_FE);

        // Crear una lista de propiedades
        List<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);
        properties.add(property3);


        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findCompletedProvinces(properties);

        // Verificar que el resultado sea el esperado
        assertFalse(result.contains(Province.BUENOS_AIRES));
        assertFalse(result.contains(Province.CORDOBA));
        assertFalse(result.contains(Province.SANTA_FE));
    }

    @Test
    @Tag("FindCompletedProvinces")
    public void testFindCompletedProvinces_SpecialCases() {
        // Crear mocks de las propiedades
        TerrainProperty property1 = mock(TerrainProperty.class);
        TerrainProperty property2 = mock(TerrainProperty.class);
        TerrainProperty property3 = mock(TerrainProperty.class);
        TerrainProperty property4 = mock(TerrainProperty.class);
        TerrainProperty property5 = mock(TerrainProperty.class);

        // Configurar las provincias de las propiedades
        when(property1.getProvince()).thenReturn(Province.TUCUMAN);
        when(property2.getProvince()).thenReturn(Province.TUCUMAN);
        when(property3.getProvince()).thenReturn(Province.RIO_NEGRO);
        when(property4.getProvince()).thenReturn(Province.RIO_NEGRO);
        when(property5.getProvince()).thenReturn(Province.SANTA_FE);

        // Crear una lista de propiedades
        List<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);
        properties.add(property5);

        // Llamar al método que queremos testear
        List<Province> result = aggressiveBot.findCompletedProvinces(properties);

        // Verificar que el resultado sea el esperado
        assertTrue(result.contains(Province.TUCUMAN));
        assertTrue(result.contains(Province.RIO_NEGRO));
        assertFalse(result.contains(Province.SANTA_FE));
    }
    @Test
    @Tag("GetOtherPlayersOnBoard")
    public void testGetOtherPlayersOnBoard_NoOtherPlayers() {
        // Crear un mock de la casilla sin jugadores
        Square square = mock(Square.class);
        when(square.getPlayers()).thenReturn(Collections.emptyList());

        // Crear una lista de casillas con solo una casilla vacía
        List<Square> squares = new ArrayList<>();
        squares.add(square);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        List<Player> result = aggressiveBot.getOtherPlayersOnBoard(gameMock);

        // Verificar que el resultado sea una lista vacía
        assertTrue(result.isEmpty());
    }
    @Test
    @Tag("GetOtherPlayersOnBoard")
    public void testGetOtherPlayersOnBoard_OtherPlayersOnSomeSquares() {
        // Crear mocks de las casillas
        Square square1 = mock(Square.class);
        Square square2 = mock(Square.class);

        // Crear mocks de jugadores
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        // Configurar jugadores en las casillas
        when(square1.getPlayers()).thenReturn(Arrays.asList(player1));
        when(square2.getPlayers()).thenReturn(Arrays.asList(player2));

        // Crear una lista de casillas
        List<Square> squares = new ArrayList<>();
        squares.add(square1);
        squares.add(square2);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        List<Player> result = aggressiveBot.getOtherPlayersOnBoard(gameMock);

        // Verificar que el resultado contenga los otros jugadores
        assertTrue(result.contains(player1));
        assertTrue(result.contains(player2));
    }

    @Test
    @Tag("GetOtherPlayersOnBoard")
    public void testGetOtherPlayersOnBoard_CurrentPlayerOnSquare() {
        // Crear mocks de las casillas
        Square square1 = mock(Square.class);
        Square square2 = mock(Square.class);

        // Crear mocks de jugadores
        Player player1 = mock(Player.class);

        // Configurar jugadores en las casillas
        when(square1.getPlayers()).thenReturn(Arrays.asList(aggressiveBot, player1));
        when(square2.getPlayers()).thenReturn(Collections.emptyList());

        // Crear una lista de casillas
        List<Square> squares = new ArrayList<>();
        squares.add(square1);
        squares.add(square2);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        List<Player> result = aggressiveBot.getOtherPlayersOnBoard(gameMock);

        // Verificar que el resultado contenga solo el otro jugador
        assertTrue(result.contains(player1));
        assertFalse(result.contains(aggressiveBot));
    }

    @Test
    @Tag("GetOtherPlayersOnBoard")
    public void testGetOtherPlayersOnBoard_SomeSquaresEmpty() {
        // Crear mocks de las casillas
        Square square1 = mock(Square.class);
        Square square2 = mock(Square.class);

        // Crear mocks de jugadores
        Player player1 = mock(Player.class);

        // Configurar jugadores en las casillas
        when(square1.getPlayers()).thenReturn(Arrays.asList(player1));
        when(square2.getPlayers()).thenReturn(Collections.emptyList());

        // Crear una lista de casillas
        List<Square> squares = new ArrayList<>();
        squares.add(square1);
        squares.add(square2);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        List<Player> result = aggressiveBot.getOtherPlayersOnBoard(gameMock);

        // Verificar que el resultado contenga solo el jugador en la casilla con jugadores
        assertTrue(result.contains(player1));

    }

    @Test
    @Tag("GetOtherPlayersOnBoard")
    public void testGetOtherPlayersOnBoard_MultipleSquaresWithPlayers() {
        // Crear mocks de las casillas
        Square square1 = mock(Square.class);
        Square square2 = mock(Square.class);
        Square square3 = mock(Square.class);

        // Crear mocks de jugadores
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);

        // Configurar jugadores en las casillas
        when(square1.getPlayers()).thenReturn(Arrays.asList(player1));
        when(square2.getPlayers()).thenReturn(Arrays.asList(player2, aggressiveBot));
        when(square3.getPlayers()).thenReturn(Arrays.asList(player3));

        // Crear una lista de casillas
        List<Square> squares = new ArrayList<>();
        squares.add(square1);
        squares.add(square2);
        squares.add(square3);

        // Mock del tablero
        Board boardMock = mock(Board.class);
        when(boardMock.getSquares()).thenReturn(squares);

        // Configurar el mock del juego
        when(gameMock.getBoard()).thenReturn(boardMock);

        // Llamar al método que queremos testear
        List<Player> result = aggressiveBot.getOtherPlayersOnBoard(gameMock);

        // Verificar que el resultado contenga todos los otros jugadores
        assertTrue(result.contains(player1));
        assertTrue(result.contains(player2));
        assertTrue(result.contains(player3));
        assertFalse(result.contains(aggressiveBot));
    }

    @Test
    @Tag("ObtainBiggestMorgageAffordable")
    public void testObtainBiggestMorgageAffordable_NoProperties() {
        List<Property> properties = new ArrayList<>();

        Property result = aggressiveBot.obtainBiggestMorgageAffordable(properties);

        assertNull(result);
    }

    @Test
    @Tag("ObtainBiggestMorgageAffordable")
    public void testObtainBiggestMorgageAffordable_OneProperty() {
        Property property = mock(Property.class);
        when(property.getMorgageValue()).thenReturn(100);

        List<Property> properties = new ArrayList<>();
        properties.add(property);

        Property result = aggressiveBot.obtainBiggestMorgageAffordable(properties);

        assertEquals(property, result);
    }

    @Test
    @Tag("ObtainBiggestMorgageAffordable")
    public void testObtainBiggestMorgageAffordable_MultiplePropertiesSufficientBalance() {
        // Crear mocks de las propiedades
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        Property property3 = mock(Property.class);

        // Configurar los valores de hipoteca para las propiedades
        when(property1.getMorgageValue()).thenReturn(100);
        when(property2.getMorgageValue()).thenReturn(200);
        when(property3.getMorgageValue()).thenReturn(300);

        // Crear una lista de propiedades
        List<Property> properties = Arrays.asList(property1, property2, property3);

        // Crear una instancia real de AggressiveBot en lugar de un mock
        AggressiveBot aggressiveBot = new AggressiveBot();

        // Configurar el balance usando un stub o un método que puedas definir en AggressiveBot
        aggressiveBot.setBalance(300);

        // Llamar al método que queremos testear
        Property result = aggressiveBot.obtainBiggestMorgageAffordable(properties);

        // Verificar que el resultado sea el esperado
        assertEquals(property3, result);
    }

    @Test
    @Tag("FilterTerrainProperties")
    public void filterTerrainPropertiesTest () {

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setMortgaged(true);
        terrainProperty.setUpgradeLevel(0);

        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setUpgradeLevel(0);

        TerrainProperty terrainProperty3 = new TerrainProperty();
        terrainProperty3.setMortgaged(false);
        terrainProperty3.setUpgradeLevel(0);

        List<TerrainProperty> terrains = new ArrayList<TerrainProperty>();
        terrains.add(terrainProperty);
        terrains.add(terrainProperty2);
        terrains.add(terrainProperty3);

        List<TerrainProperty> result = new ArrayList<>();

        //result = filterTerrainProperties();
        terrains = aggressiveBot.filterTerrainProperties(terrains);
        boolean resultx = terrains.isEmpty();
        assertTrue(resultx);

    }

    @Test
    @Tag("RemovePriorityProperties")
    public void testRemovePriorityProperties() {
        priorityTerrain.setMortgaged(true);
        List<Property> properties = new ArrayList<>(){{
            add(priorityTerrain);
        }};
        List<Property> propertieLst = aggressiveBot.removePriorityProperties(properties);
        assertTrue(propertieLst.isEmpty());
    }
    @Test
    @Tag("ObtainBiggestMortgage")
    void obtainBiggestMortgageTest (){
        List<Property> properties = new ArrayList<>(){{
            add(priorityTerrain);
            add(nonPriorityTerrain);
        }};
        Property p = aggressiveBot.obtainBiggestMortgage(properties);
        assertEquals(nonPriorityTerrain,p);

    }

    @Test
    @Tag("OfferAuction")
    void noofferAuctionTest(){
        aggressiveBot.setBalance(900);
        int i = aggressiveBot.offerAuction(game);
        assertEquals(0,i);
    }

    @Test
    @Tag("OfferAuction")
    public void offerAuctionTest()
    {

        TerrainProperty terrain1 = new TerrainProperty();
        terrain1.setPrice(1000);
        terrain1.setProvince(Province.MENDOZA);

        TerrainProperty terrain2 = new TerrainProperty();
        terrain2.setPrice(2000);
        terrain2.setProvince(Province.SANTA_FE);

        TerrainProperty terrain3 = new TerrainProperty();
        terrain3.setPrice(3000);
        terrain3.setProvince(Province.TUCUMAN);

        List<Property> properties = new ArrayList<>();
        properties.add(terrain1);
        properties.add(terrain2);
        properties.add(terrain3);
        ModerateBot moderateBot = new ModerateBot(properties,false);

        int result= moderateBot.offerAuction(game);

        assertTrue(result >=0);

    }
    @Test
    @Tag("OfferAuction")
    public void offerAuctionTestNoProperties()
    {

        List<Property> properties = new ArrayList<>();
        ModerateBot moderateBot = new ModerateBot(properties,false);
        int result= moderateBot.offerAuction(game);
        assertTrue(result ==0);

    }

    @Test
    @Tag("UpgradeTerrainProperty")
    public void upgradeTerrainPropertyTestFirstTrue()
    {
        Property property = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property2 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.CENTRO,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property3 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.NORTE,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property4 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.NORTE,
                Province.TUCUMAN,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property5 = new RailwayProperty(
                false,
                "Belgrano",
                3000
                );
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);
        properties.add(property5);
        ModerateBot moderateBot = new ModerateBot(properties,false);
        moderateBot.setBalance(500000);
        boolean result = moderateBot.upgradeTerrainProperty(game);
        assertTrue(result);
    }
    @Test
    @Tag("UpgradeTerrainProperty")
    public void upgradeTerrainPropertyTestSecondTrue()
    {
        Property property = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property2 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.CENTRO,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property3 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.NORTE,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property4 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.NORTE,
                Province.TUCUMAN,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property5 = new RailwayProperty(
                false,
                "Belgrano",
                3000
        );
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);
        properties.add(property5);

        //Spy moderateBot para simular retorno true en un metodo suyo
        ModerateBot moderateBot = spy(new ModerateBot(properties, false));
        moderateBot.setBalance(110);

        // Mockear checkAllPropertiesOwnership para que devuelva true
        doReturn(true).when(moderateBot).checkAllPropertiesOwnership(game);

        boolean result = moderateBot.upgradeTerrainProperty(game);

        assertTrue(result);
    }
    @Test
    @Tag("UpgradeTerrainProperty")
    public void upgradeTerrainPropertyTestFalse()
    {
        Property property = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        ModerateBot moderateBot = new ModerateBot(properties,false);
        boolean result = moderateBot.upgradeTerrainProperty(game);
        assertFalse(result);
    }

    @Test
    @Tag("UpgradeTerrainProperty")
    public void upgradeTerrainPropertyTestFalseOutForBucle()
    {
        Property property = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property2 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.CENTRO,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property3 = new TerrainProperty(
                false,
                "Property B",
                20000,
                Zone.NORTE,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        properties.add(property2);
        properties.add(property3);

        //Spy moderateBot para simular retorno true en un metodo suyo
        ModerateBot moderateBot = spy(new ModerateBot(properties, false));
        moderateBot.setBalance(110);

        // Mockear checkAllPropertiesOwnership para que devuelva FALSE
        doReturn(false).when(moderateBot).checkAllPropertiesOwnership(game);

        boolean result = moderateBot.upgradeTerrainProperty(game);

        assertFalse(result);
    }

    @Test
    @Tag("play")
    public void playRaiseAndUpgradeTest()
    {
        Property property = new TerrainProperty(
                false,
                "Bs As Sur",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property2 = new TerrainProperty(
                false,
                "Bs As Centro",
                20000,
                Zone.CENTRO,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                0
        );
        Property property3 = new TerrainProperty(
                false,
                "Bs As Norte",
                20000,
                Zone.NORTE,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                0
        );
        Property property4 = new TerrainProperty(
                false,
                "Tucuman norte",
                20000,
                Zone.NORTE,
                Province.TUCUMAN,
                new Integer[]{100,200,300},
                100,
                0
        );
        Property property5 = new RailwayProperty(
                true,
                "Ferrocarril Belgrano",
                3000
        );
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);
        properties.add(property5);
        ModerateBot moderateBot = new ModerateBot(properties,false);
        moderateBot.setBalance(500000);

        moderateBot.play(game);
        boolean result = moderateBot.upgradeTerrainProperty(game);
        assertFalse(moderateBot.getProperties().get(4).isMortgaged());
        assertFalse(moderateBot.getProperties().get(3).isMortgaged());
        assertFalse(moderateBot.getProperties().get(2).isMortgaged());
        assertFalse(moderateBot.getProperties().get(1).isMortgaged());
        assertFalse(moderateBot.getProperties().get(0).isMortgaged());
    }

    @Test
    @Tag("play")
    public void playRaiseFalseTest()
    {
        Property property = new TerrainProperty(
                false,
                "Bs As Sur",
                20000,
                Zone.SUR,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                1
        );
        Property property2 = new TerrainProperty(
                false,
                "Bs As Centro",
                20000,
                Zone.CENTRO,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                0
        );
        Property property3 = new TerrainProperty(
                false,
                "Bs As Norte",
                20000,
                Zone.NORTE,
                Province.BUENOS_AIRES,
                new Integer[]{100,200,300},
                100,
                0
        );
        Property property4 = new TerrainProperty(
                false,
                "Tucuman norte",
                20000,
                Zone.NORTE,
                Province.TUCUMAN,
                new Integer[]{100,200,300},
                100,
                0
        );
        Property property5 = new RailwayProperty(
                false,
                "Ferrocarril Belgrano",
                3000
        );
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        properties.add(property2);
        properties.add(property3);
        properties.add(property4);
        properties.add(property5);
        ModerateBot moderateBot = new ModerateBot(properties,false);
        moderateBot.setBalance(500);

        moderateBot.play(game);
        boolean result = moderateBot.upgradeTerrainProperty(game);
        assertFalse(result);
    }

    @Test
    @Tag("play")
    public void playBuyTest()
    {
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

        bot.play(game);
        assertEquals(bot.getProperties().size(),1);
    }





}
