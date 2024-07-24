package ar.edu.utn.frc.tup.lciii.Services;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.AggressiveBot;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankTest {
    @Mock
    private Player payer;

    @Mock
    private Player beneficiary;

    @Mock
    private Property property;
    @Mock
    private Game game;

    @Mock
    private UserInteraction ui;

    private Bank bank;

    @InjectMocks
    private Bank bs;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bank = new Bank();
        bank.ui = ui;
    }

    @Test
    public void testNoArgsConstructor() {
        Bank bank = new Bank();
        assertNotNull(bank, "El bank no puede ser null desp de crearlo.");
    }

    @Test
    public void testMakePlayerPayToOtherPlayer() {
        when(payer.getBalance()).thenReturn(1000);
        when(payer.getBankrupted()).thenReturn(false);

        bank.makePlayerPayToOtherPlayer(game, beneficiary, 500, payer);

        verify(payer).setBalance(500);
        verify(beneficiary).setBalance(500);
    }

    @Test
    public void testMakePlayerPayToOtherPlayerNotEnoughMoney() {
        when(payer.getBalance()).thenReturn(100);
        when(payer.getBankrupted()).thenReturn(false);

        bank.makePlayerPayToOtherPlayer(game, beneficiary, 500, payer);


        verify(payer).findWayToMakeMoney(game, 500);
        verify(payer).setBalance(0);
        verify(payer, times(2)).setBankrupted(true);
    }

    @Test
    public void testTransferProperty() {
        when(payer.ownsProperty(property)).thenReturn(true);

        bank.transferProperty(payer, beneficiary, property);

        verify(payer).removeProperty(property);
        verify(beneficiary).addProperty(property);
    }

    @Test
    public void testTransferPropertyNotOwned() {
        when(payer.ownsProperty(property)).thenReturn(false);

        bank.transferProperty(payer, beneficiary, property);

        verify(ui).message("Cant transfer a property that you dont own!");
    }

    @Test
    public void testTransferPropertyWithPayment() {
        when(payer.ownsProperty(property)).thenReturn(true);
        when(payer.getBalance()).thenReturn(1000);

        assertTrue(bank.transferPropertyWithPayment(game, beneficiary, 500, property, payer));

        verify(payer).removeProperty(property);
        verify(beneficiary).addProperty(property);
        verify(payer).setBalance(500);
        verify(beneficiary).setBalance(500);
    }

    @Test
    public void testTransferPropertyWithPaymentNotEnoughMoney() {
        when(payer.ownsProperty(property)).thenReturn(true);
        when(payer.getBalance()).thenReturn(100);

        assertFalse(bank.transferPropertyWithPayment(game, beneficiary, 500, property, payer));

        verify(ui).message("Not enough Cash!!");
    }

    @Test
    public void testMortgageProperty() {
        TerrainProperty terrainProperty = new TerrainProperty(false, "Propiedad de prueba",
                1000, Zone.NORTE, Province.SALTA, new Integer[]{100, 200, 300, 400, 500}, 100, 0);
        when(payer.ownsProperty(terrainProperty)).thenReturn(true);
        when(payer.getBalance()).thenReturn(1000);

        bank.mortgageProperty(terrainProperty, payer);

        verify(payer).setBalance(950);
        assertTrue(terrainProperty.isMortgaged());
    }

    @Test
    public void testMortgagePropertyAlreadyMortgaged() {
        when(property.isMortgaged()).thenReturn(true);
        when(payer.ownsProperty(property)).thenReturn(true);

        bank.mortgageProperty(property, payer);

        verify(ui).message("That property is already mortgaged!");
    }

    @Test
    public void testMortgagePropertyNotOwned() {
        when(property.isMortgaged()).thenReturn(false);
        when(payer.ownsProperty(property)).thenReturn(false);

        bank.mortgageProperty(property, payer);

        verify(ui).message("That's not the owner of that property!");
    }

    @Test
    public void testPayOffMortgage() {
        when(property.isMortgaged()).thenReturn(true);
        when(payer.ownsProperty(property)).thenReturn(true);
        when(payer.getBalance()).thenReturn(1000);
        when(property.getMorgageValue()).thenReturn(900);

        bank.payOffMortgage(property, payer);

        verify(payer).setBalance(100);
        verify(property).setMortgaged(false);  // Verificar que se llamó a setMortgaged(false)
        verify(ui).message(payer.getName() + "successfully payed " + property.getName() + " mortgage! " +
                "\nNow the property is fully back");
    }

    @Test
    public void testPayOffMortgageNotMortgaged() {
        when(property.isMortgaged()).thenReturn(false);

        bank.payOffMortgage(property, payer);

        verify(ui).message("That property is not mortgaged!");
    }

    @Test
    public void testPayOffMortgageNotOwned() {
        when(property.isMortgaged()).thenReturn(true);
        when(payer.ownsProperty(property)).thenReturn(false);

        bank.payOffMortgage(property, payer);

        verify(ui).message("Thats not the owner of that property!");
    }

    @Test
    public void testAuctionProperty() {
        List<Player> players = new ArrayList<>();
        players.add(payer);
        players.add(beneficiary);
        when(payer.ownsProperty(property)).thenReturn(false);
        when(beneficiary.ownsProperty(property)).thenReturn(true);

        bank.auctionProperty(game, property, players);
        verify(game, times(4)).addEvent(any());

    }

    // Métodos privados

    @Test
    public void testTransaction() {
        when(payer.getBalance()).thenReturn(1000);

        assertTrue(bank.chargePlayer(payer, 500));
        verify(payer).setBalance(500);

        assertFalse(bank.chargePlayer(payer, 2000));
        verify(payer, times(1)).setBalance(anyInt());
    }

    @Test
    public void testFindBidderPlayers() throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(payer);
        players.add(beneficiary);
        when(payer.ownsProperty(property)).thenReturn(false);
        when(beneficiary.ownsProperty(property)).thenReturn(true);

        Method method = Bank.class.getDeclaredMethod("findBidderPlayers", Property.class, List.class);
        method.setAccessible(true);

        List<Player> bidders = (List<Player>) method.invoke(bank, property, players);

        assertEquals(1, bidders.size());
        assertTrue(bidders.contains(payer));
    }

    @Test
    public void testFindOwner() throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(payer);
        players.add(beneficiary);
        when(payer.ownsProperty(property)).thenReturn(false);
        when(beneficiary.ownsProperty(property)).thenReturn(true);

        Method method = Bank.class.getDeclaredMethod("findOwner", Property.class, List.class);
        method.setAccessible(true);

        Player owner = (Player) method.invoke(bank, property, players);

        assertEquals(beneficiary, owner);
    }


    @Test
    public void testCollectBids() throws Exception {
        List<Player> bidders = new ArrayList<>();
        bidders.add(payer);
        when(payer.makeBid(property, 500)).thenReturn(600);

        Method method = Bank.class.getDeclaredMethod("collectBids",Game.class, Property.class, List.class);
        method.setAccessible(true);

        Map<Player, Integer> bids = (Map<Player, Integer>) method.invoke(bank,game, property, bidders);

        assertEquals(0, bids.size());
        assertEquals(null, bids.get(payer));
    }

    @Test
    public void testProcessBids() throws Exception {
        when(payer.getName()).thenReturn("John Doe");
        when(payer.getBalance()).thenReturn(1000);
        when(bank.transferPropertyWithPayment(game, payer, 600, property, beneficiary)).thenReturn(true);

        assertEquals("John Doe", payer.getName());
        assertEquals(1000, payer.getBalance().intValue());
        assertFalse(bank.transferPropertyWithPayment(game, payer, 600, property, beneficiary));

        Map<Player, Integer> bids = new HashMap<>();
        bids.put(payer, 600);

        Method method = Bank.class.getDeclaredMethod("processBids",Game.class, Player.class, Property.class, Map.class);
        method.setAccessible(true);

        method.invoke( bank,game, beneficiary, property, bids);

        verify(game).addEvent(any());
    }

    @Test
    public void findOwnerNullTest() throws Exception {
        when(payer.ownsProperty(property)).thenReturn(false);
        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(payer);

        Method method = Bank.class.getDeclaredMethod("findOwner", Property.class, List.class);
        method.setAccessible(true);

        Player owner = (Player) method.invoke(bank, property, listPlayer);

        assertNull(owner);
    }

    @Test
    public void sellRailwayToBankTest() {
        // Arrange
        Property property = new RailwayProperty();
        property.setMortgaged(false);
        property.setPrice(100);

        List<Property> properties = new ArrayList<>();
        properties.add(property);

        when(payer.getProperties()).thenReturn(properties);

        // Act
        bank.sellPropertyToBank(property, payer);

        // Assert
        verify(payer).removeProperty(property);
        verify(ui, never()).message("Cannot sell a mortgaged property to the bank!");
    }

    @Test
    public void sellMortgagedPropertyToBankTest() {
        // caso de la prop hipotecada Arrange->Act->Assert
        Property property = new RailwayProperty();
        property.setMortgaged(true);
        property.setPrice(200);

        List<Property> properties = new ArrayList<>();
        properties.add(property);

        Player player = Mockito.mock(Player.class);
        player.setProperties(properties);

        bank.sellPropertyToBank(property, player);

        Mockito.verify(ui).message("Cannot sell a mortgaged property to the bank!");
    }

    @Test
    public void sellTerrainWithUpgradesToBankTest() {
        TerrainProperty property = new TerrainProperty();
        property.setMortgaged(false);
        property.setPrice(300);
        property.setUpgradeLevel(1);

        List<Property> properties = new ArrayList<>();
        properties.add(property);

        Player player = Mockito.mock(Player.class);
        player.setProperties(properties);

        bank.sellPropertyToBank(property, player);

        Mockito.verify(ui).message("Sell the upgrades first!");
    }

    @Test
    public void sellTerrainPropertyWithUpgradeLevelZeroToBankTest() {
        TerrainProperty property = new TerrainProperty();
        property.setMortgaged(true);
        property.setPrice(300);
        property.setUpgradeLevel(0);

        List<Property> properties = new ArrayList<>();
        properties.add(property);

        Player player = Mockito.mock(Player.class);
        player.setProperties(properties);

        bank.sellPropertyToBank(property, player);

        Mockito.verify(ui).message("Cannot sell a mortgaged property to the bank!");
    }

    @Test
    public void sellUpgradesTerrainWithNoUpgradesTest() {
        TerrainProperty property = new TerrainProperty();
        property.setUpgradeLevel(0);

        Player player = Mockito.mock(Player.class);

        bank.sellUpgrades(property, player);

        Mockito.verify(ui).message("Terrain has no upgrades!");
        Mockito.verify(player, Mockito.never()).removeProperty(property);
        Mockito.verify(player, Mockito.never()).setProperties(Mockito.any());
        Mockito.verify(player, Mockito.never()).getProperties();
        Mockito.verify(player, Mockito.never()).addProperty(property);
    }

    @Test
    public void sellUpgradesTerrainWithUpgradesTest() {
        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setUpgradeLevel(1);
        terrainProperty.setUpgradePrice(100);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(100);

        Player player = Mockito.mock(Player.class);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        Mockito.when(player.getProperties()).thenReturn(properties);

        bank.sellUpgrades(terrainProperty, player);

        Mockito.verify(player).getBalance();
        Mockito.verify(player).setBalance(Mockito.anyInt());
    }

    @Test
    public void getHousesTest() {
        Bank bank = new Bank();

        assertEquals(44, bank.getHouses());
    }

    @Test
    void setHousesTest() {
        Bank bank = new Bank();
        bank.setHouses(7);

        assertEquals(7, bank.getHouses());
    }

//    @Test
//    public void upgradeTerrainNoHousesLeftTest() {
//        TerrainProperty terrainProperty = new TerrainProperty();
//        terrainProperty.setUpgradeLevel(0);
//        Player player = mock(Player.class);
//        Bank bank = new Bank();
//        bank.setHouses(0);
//
//        bank.upgradeTerrain(game, player, terrainProperty);
//
//        assertEquals(0, bank.getHouses());
//    }
//
//    @Test
//    public void upgradeTerrainWithHousesAndNotEnoughMoneyTest() {
//        TerrainProperty terrainProperty = new TerrainProperty();
//        terrainProperty.setPrice(100);
//        terrainProperty.setMortgaged(false);
//        terrainProperty.setUpgradePrice(10);
//
//        Player player = mock(Player.class);
//        player.setBalance(500);
//        Bank bank = new Bank();
//        bank.setHouses(1);
//        terrainProperty.setUpgradeLevel(0);
//
//        bank.upgradeTerrain(game, player, terrainProperty);
//
//        assertEquals(1, bank.getHouses());
//    }
//
//    @Test
//    public void upgradeTerrainWithHousesTest() {
//        TerrainProperty terrainProperty = new TerrainProperty();
//        terrainProperty.setPrice(100);
//        terrainProperty.setMortgaged(false);
//        terrainProperty.setUpgradePrice(10);
//        terrainProperty.setUpgradeLevel(0);
//
//        List<Property> properties = new ArrayList<>();
//        properties.add(terrainProperty);
//
//        Player player = new AggressiveBot();
//        player.setBalance(50);
//        player.setName("Agu");
//        player.setProperties(properties);
//
//        assertEquals(44, bank.getHouses());
//        bank.upgradeTerrain(game, player, terrainProperty);
//        assertEquals(43, bank.getHouses());
//    }

    @Test
    public void handleBankruptcyTest() {
        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setName("Casa Verde");
        terrainProperty.setUpgradeLevel(2);
        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);

        Player player = new AggressiveBot();
        player.setName("Agu");
        player.setProperties(properties);

        assertEquals(1, player.getProperties().size());
        assertTrue(player.getProperties().contains(terrainProperty));

        bank.handleBankruptcy(player);

        assertEquals(0, player.getProperties().size());
        assertFalse(player.getProperties().contains(terrainProperty));
        assertTrue(player.getBankrupted());
    }

    @Test
    public void handleBankruptcyNoTerrainTest() {
        RailwayProperty terrainProperty = new RailwayProperty();
        terrainProperty.setName("Casa Verde");
        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);

        Player player = new AggressiveBot();
        player.setName("Agu");
        player.setProperties(properties);

        assertEquals(1, player.getProperties().size());
        assertTrue(player.getProperties().contains(terrainProperty));

        bank.handleBankruptcy(player);

        assertEquals(0, player.getProperties().size());
        assertFalse(player.getProperties().contains(terrainProperty));
        assertTrue(player.getBankrupted());
    }

    @Test
    public void payToBankTest() {
        Player player = new AggressiveBot();
        player.setBalance(100);
        player.setBankrupted(false);

        bank.payToBank(game, 20, player);

        assertEquals(80, player.getBalance());
    }

    @Test
    public void payToBankBankruptTest() {
        Player player = mock(Player.class);
        player.setBalance(100);
        player.setBankrupted(false);

        // aca simulamos el metodo findWayToMakeMoney
        doAnswer(invocation -> {
            player.setBalance(110); // decimos que cuando hace el metodo aumenta la plata en 150
            return null; // y devolvemos null xq es void ese metod.
        }).when(player).findWayToMakeMoney(game, 150);

        bank.payToBank(game, 150, player);
        assertFalse(player.getBankrupted());
    }

    @Test
    public void buyPropertyTest() {
        Player player = new AggressiveBot();
        player.setBalance(100);
        player.setBankrupted(false);
        player.setName("Agu");
        player.setProperties(new ArrayList<>());

        Property prop = new RailwayProperty();
        prop.setPrice(50);
        prop.setName("Tren San Martin");

        bank.buyProperty(player, prop);

        assertEquals(50, player.getBalance());
    }

    @Test
    public void buyPropertyNoMoneyTest() {
        Player player = new AggressiveBot();
        player.setBalance(10);
        player.setBankrupted(false);
        player.setName("Agu");
        player.setProperties(new ArrayList<>());

        Property prop = new RailwayProperty();
        prop.setPrice(50);
        prop.setName("Tren San Martin");

        bank.buyProperty(player, prop);

        assertEquals(10, player.getBalance());
    }

    @Test
    public void testMortgagePropertyNotEnoughMoney() {
        TerrainProperty terrainProperty = new TerrainProperty(false, "Propiedad de prueba",
                1000, Zone.NORTE, Province.SALTA, new Integer[]{100, 200, 300, 400, 500}, 100, 0);
        when(payer.ownsProperty(terrainProperty)).thenReturn(true);
        when(payer.getBalance()).thenReturn(10);

        bank.mortgageProperty(terrainProperty, payer);

        assertFalse(terrainProperty.isMortgaged());
    }

    @Test
    public void testPayOffMortgageBroke() {
        when(property.isMortgaged()).thenReturn(true);
        when(payer.ownsProperty(property)).thenReturn(true);
        when(payer.getBalance()).thenReturn(7);
        when(property.getMorgageValue()).thenReturn(900);

        bank.payOffMortgage(property, payer);

        assertEquals(7, payer.getBalance());
    }

    @Test
    public void testProcessBidsIterate() throws Exception {
        when(payer.getName()).thenReturn("John Doe");
        when(payer.getBalance()).thenReturn(0);

        when(bank.transferPropertyWithPayment(game, payer, 600, property, beneficiary)).thenReturn(true);

        assertEquals("John Doe", payer.getName());
        assertEquals(0, payer.getBalance().intValue());
        assertFalse(bank.transferPropertyWithPayment(game, payer, 600, property, beneficiary));

        Map<Player, Integer> bids = new HashMap<>();

        Player player1 = mock(Player.class);
        bids.put(payer, 600);
        bids.put(beneficiary, 500);
        bids.put(player1, -1);

        Method method = Bank.class.getDeclaredMethod("processBids",Game.class, Player.class, Property.class, Map.class);
        method.setAccessible(true);

        method.invoke(bank,game, beneficiary, property, bids);
    }

    @Test
    public void testProcessBidsWin() throws Exception {
        Player owner = new AggressiveBot();
        owner.setName("A");
        owner.setBankrupted(false);
        owner.setBalance(10);


        RailwayProperty tren = new RailwayProperty();
        tren.setMortgaged(false);
        tren.setName("601");
        tren.setPrice(10);

        List<Property> properties = new ArrayList<>();
        properties.add(tren);

        Player player1 = new AggressiveBot();
        player1.setName("B");
        player1.setProperties(properties);
        player1.setBalance(100);

        Player player2 = new AggressiveBot();
        player2.setName("B");
        player2.setProperties(properties);
        player2.setBalance(100);

        Player player3 = new AggressiveBot();
        player3.setName("B");
        player3.setProperties(properties);
        player3.setBalance(100);

        owner.setProperties(properties);

        Map<Player, Integer> map = new HashMap<>();
        map.put(player1, 12);
        map.put(player2, 15);
        map.put(player3, 11);

        Bank bank = mock(Bank.class);
        bank.ui = ui;
        when(bank.transferPropertyWithPayment(game, player2,15,tren, owner)).thenReturn(true);

        Method method = Bank.class.getDeclaredMethod("processBids",Game.class, Player.class, Property.class, Map.class);
        method.setAccessible(true);

        method.invoke(bank,game, owner, tren, map);

        assertEquals(10, owner.getBalance());
    }

    @Test
    public void testCollectBidsBidding() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        RailwayProperty tren = new RailwayProperty();
        tren.setPrice(10);
        tren.setName("A");
        tren.setMortgaged(false);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        when(player1.makeBid(tren,tren.getPrice()/2)).thenReturn(50);
        when(player2.makeBid(tren,50)).thenReturn(55);
        when(player1.makeBid(tren, 55)).thenReturn(60);
        when(player2.makeBid(tren, 60)).thenReturn(65);
        when(player1.makeBid(tren, 65)).thenReturn(0);
        when(player2.makeBid(tren, 65)).thenReturn(0);

        Method method = Bank.class.getDeclaredMethod("collectBids", Game.class, Property.class, List.class);
        method.setAccessible(true);

        Bank bank = mock(Bank.class);
        bank.ui = ui;

        method.invoke(bank,game, tren, playerList);

        assertFalse(tren.isMortgaged());
    }

    @Test
    public void testMortgagePropertyWithUpgrades() {
        TerrainProperty terrainProperty = new TerrainProperty(false, "Propiedad de prueba",
                1000, Zone.NORTE, Province.SALTA, new Integer[]{100, 200, 300, 400, 500}, 100, 3);
        when(payer.ownsProperty(terrainProperty)).thenReturn(true);
        when(payer.getBalance()).thenReturn(1000);

        bank.mortgageProperty(terrainProperty, payer);

        assertTrue(terrainProperty.isMortgaged());
    }

    @Test
    public void testMortgagePropertyNotTerrain() {
        RailwayProperty railwayProperty = new RailwayProperty();
        railwayProperty.setMortgaged(false);
        railwayProperty.setName("Tren prueba");
        railwayProperty.setPrice(7);

        when(payer.ownsProperty(railwayProperty)).thenReturn(true);
        when(payer.getBalance()).thenReturn(1000);

        bank.mortgageProperty(railwayProperty, payer);

        assertTrue(railwayProperty.isMortgaged());
    }

    @Test
    public void provinceRequierementTest_CaseTwo_PartOne() throws Exception {
        Bank bank = new Bank();
        TerrainProperty terrain = new TerrainProperty();
        terrain.setProvince(Province.TUCUMAN);

        Method privateMethod = Bank.class.getDeclaredMethod("provinceRequierement", TerrainProperty.class);
        privateMethod.setAccessible(true);

        Integer result =  (Integer) privateMethod.invoke(bank, terrain);
        assertEquals(2, result);
    }

    @Test
    public void provinceRequierementTest_CaseTwo_PartTwo() throws Exception {
        Bank bank = new Bank();
        TerrainProperty terrain = new TerrainProperty();
        terrain.setProvince(Province.RIO_NEGRO);

        Method privateMethod = Bank.class.getDeclaredMethod("provinceRequierement", TerrainProperty.class);
        privateMethod.setAccessible(true);

        Integer result =  (Integer) privateMethod.invoke(bank, terrain);
        assertEquals(2, result);
    }

    @Test
    public void provinceRequierementTest_CaseThree() throws Exception {
        Bank bank = new Bank();
        TerrainProperty terrain = new TerrainProperty();
        terrain.setProvince(Province.CORDOBA);

        Method privateMethod = Bank.class.getDeclaredMethod("provinceRequierement", TerrainProperty.class);
        privateMethod.setAccessible(true);

        Integer result =  (Integer) privateMethod.invoke(bank, terrain);
        assertEquals(3, result);
    }

//    @Test
//    public void upgradeTerrainMaximumUpgradesTest() {
//        TerrainProperty terrainProperty = new TerrainProperty();
//        terrainProperty.setPrice(100);
//        terrainProperty.setMortgaged(false);
//        terrainProperty.setUpgradePrice(10);
//        terrainProperty.setUpgradeLevel(5);
//
//        List<Property> properties = new ArrayList<>();
//        properties.add(terrainProperty);
//
//        Player player = new AggressiveBot();
//        player.setBalance(50);
//        player.setName("Agu");
//        player.setProperties(properties);
//
//        assertEquals(44, bank.getHouses());
//        bank.upgradeTerrain(game, player, terrainProperty);
//        assertEquals(43, bank.getHouses());
//    }

    @Test
    public void canUpgradePropertyTest_True() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.TUCUMAN);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Tucuman Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(0);
        terrainProperty2.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = mock(Player.class);
        when(player.getProperties()).thenReturn(properties);

        Bank bank = new Bank();



        assertTrue(bank.canUpgradeProperty(player, terrainProperty));

    }

    @Test
    public void canUpgradePropertyTest_False() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.CORDOBA);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Cordoba Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(0);
        terrainProperty2.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = mock(Player.class);
        when(player.getProperties()).thenReturn(properties);

        Bank bank = new Bank();



        assertFalse(bank.canUpgradeProperty(player, terrainProperty));

    }

    @Test
    public void canUpgradePropertyTest_PlayerWithNoTerrains() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();

        Player player = mock(Player.class);
        when(player.getProperties()).thenReturn(properties);

        Bank bank = new Bank();

        assertFalse(bank.canUpgradeProperty(player, terrainProperty));

    }

    @Test
    public void upgradeTerrainTest_WithCheck() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.CORDOBA);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Cordoba Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(0);
        terrainProperty2.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = new AggressiveBot();
        player.setBalance(50);
        player.setName("Agu");
        player.setProperties(properties);

        bank.upgradeTerrain(game, player, terrainProperty);
        assertEquals(43, bank.getHouses());
    }

    @Test
    public void upgradeTerrainTest_WithNoCheck() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);

        Player player = new AggressiveBot();
        player.setBalance(50);
        player.setName("Agu");
        player.setProperties(properties);

        bank.upgradeTerrain(game, player, terrainProperty);
        assertEquals(43, bank.getHouses());
    }

    @Test
    public void upgradeTerrainTest_WithCheck_MaxUpgrades() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.CORDOBA);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Cordoba Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(0);
        terrainProperty2.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = new AggressiveBot();
        player.setBalance(50);
        player.setName("Agu");
        player.setProperties(properties);

        bank.upgradeTerrain(game, player, terrainProperty);
        assertEquals(43, bank.getHouses());
    }

    @Test
    public void upgradeTerrainTest_WithCheck_NoHouses() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.CORDOBA);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Cordoba Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(0);
        terrainProperty2.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = new AggressiveBot();
        player.setBalance(50);
        player.setName("Agu");
        player.setProperties(properties);

        Bank bank = new Bank();
        bank.setHouses(0);

        bank.upgradeTerrain(game, player, terrainProperty);
        assertEquals(0, bank.getHouses());
    }

    @Test
    public void upgradeTerrainTest_WithCheck_NoMoney() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.CORDOBA);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Cordoba Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(0);
        terrainProperty2.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = new AggressiveBot();
        player.setBalance(0);
        player.setName("Agu");
        player.setProperties(properties);

        Bank bank = new Bank();

        bank.upgradeTerrain(game, player, terrainProperty);
        assertEquals(0, player.getBalance());
    }

    @Test
    public void upgradeTerrainTest_WithCheck_HighUpgradeLevel() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(6);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.CORDOBA);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Cordoba Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(6);
        terrainProperty2.setRentByPrice(precios);

        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = new AggressiveBot();
        player.setBalance(0);
        player.setName("Agu");
        player.setProperties(properties);

        Bank bank = new Bank();

        bank.upgradeTerrain(game, player, terrainProperty);
        assertEquals(0, player.getBalance());
    }

    @Test
    public void upgradeTerrainTest_TrueCheck() {
        Integer[] precios = {1,1,1,1,1};

        TerrainProperty terrainProperty = new TerrainProperty();
        terrainProperty.setProvince(Province.TUCUMAN);
        terrainProperty.setMortgaged(false);
        terrainProperty.setPrice(7);
        terrainProperty.setName("Tucuman Norte");
        terrainProperty.setZone(Zone.NORTE);
        terrainProperty.setUpgradePrice(1);
        terrainProperty.setUpgradeLevel(0);
        terrainProperty.setRentByPrice(precios);


        TerrainProperty terrainProperty2 = new TerrainProperty();
        terrainProperty2.setProvince(Province.TUCUMAN);
        terrainProperty2.setMortgaged(false);
        terrainProperty2.setPrice(7);
        terrainProperty2.setName("Cordoba Sur");
        terrainProperty2.setZone(Zone.SUR);
        terrainProperty2.setUpgradePrice(1);
        terrainProperty2.setUpgradeLevel(0);
        terrainProperty2.setRentByPrice(precios);


        List<Property> properties = new ArrayList<>();
        properties.add(terrainProperty);
        properties.add(terrainProperty2);

        Player player = new AggressiveBot();
        player.setBalance(50);
        player.setName("Agu");
        player.setProperties(properties);

        bank.upgradeTerrain(game, player, terrainProperty);
        assertEquals(43, bank.getHouses());
    }
}