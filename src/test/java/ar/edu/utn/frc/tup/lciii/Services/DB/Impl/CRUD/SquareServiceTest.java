package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Event.EventType;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.SquareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SquareServiceTest {

    @InjectMocks
    private SquareService squareService;

    @Mock
    private ConnexionDB connexionDB;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Array sqlArray;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(sqlArray.getArray()).thenReturn(new Long[]{1L, 2L, 3L});

    }

    @Test
    void testGetAll() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("squareId")).thenReturn(1L);
        when(resultSet.getString("squareTypeDescription")).thenReturn("NORMAL");
        when(resultSet.getString("nameSquare")).thenReturn("Test Square");
        when(resultSet.getString("squareProvinceDescription")).thenReturn("BUENOS_AIRES");
        when(resultSet.getString("squareZoneDescription")).thenReturn("NORTE");
        when(resultSet.getInt("squareUpgradePrice")).thenReturn(100);
        when(resultSet.getInt("squareAmount")).thenReturn(50);
        when(resultSet.getString("squareEventDescription")).thenReturn("MONEY");
        when(resultSet.getString("squareventWhenPassingDescription")).thenReturn("GO_TO_JAIL");
        when(resultSet.getInt("squarePrice")).thenReturn(200);
        when(resultSet.getString("squareCardTypeToPickUpDescription")).thenReturn("LUCK");
        when(resultSet.getArray("rentByUpgrade")).thenReturn(sqlArray);

        List<Square> squares = squareService.getAll();

        assertNotNull(squares);
        assertEquals(1, squares.size());
        Square square = squares.get(0);
        assertTrue(square instanceof NormalSquare);
    }

    @Test
    void testSquareFactoryNormalSquare() throws SQLException {
        Square square = squareService.squareFactory(
                1L, "NORMAL", "Test Square", Province.BUENOS_AIRES, Zone.NORTE,
                100, 50, EventType.MONEY, EventType.GO_TO_JAIL, 200, CardType.DESTINY, sqlArray
        );

        assertNotNull(square);
        assertTrue(square instanceof NormalSquare);
        NormalSquare normalSquare = (NormalSquare) square;
        assertEquals("Test Square", normalSquare.getName());
    }

    @Test
    void testConvertSQLArrayToIntegerArray() throws SQLException {
        when(sqlArray.getArray()).thenReturn(new Long[]{1L, 2L, 3L});
        Integer[] integers = SquareService.convertSQLArrayToIntegerArray(sqlArray);
        assertNotNull(integers);
        assertArrayEquals(new Integer[]{1, 2, 3}, integers);
    }

    @Test
    void testConvertSQLArrayToIntegerArrayWithIntegerValues() throws SQLException {
        when(sqlArray.getArray()).thenReturn(new Integer[]{1, 2, 3});
        Integer[] integers = SquareService.convertSQLArrayToIntegerArray(sqlArray);
        assertNotNull(integers);
        assertArrayEquals(new Integer[]{1, 2, 3}, integers);
    }

    @Test
    void testConvertSQLArrayToIntegerArrayWithNullArray() throws SQLException {
        Integer[] integers = SquareService.convertSQLArrayToIntegerArray(null);
        assertNull(integers);
    }

    @Test
    void testSquareFactoryPropertySquare() throws SQLException {
        Square square = squareService.squareFactory(
                1L, "TERRAIN_PROPERY_SQUARE", "Test Property Square", Province.BUENOS_AIRES, Zone.NORTE,
                100, 50, EventType.MONEY, EventType.GO_TO_JAIL, 200, CardType.LUCK, sqlArray
        );

        assertNotNull(square);
        assertTrue(square instanceof PropertySquare);
    }

    @Test
    void testSquareFactoryInvalidType() throws SQLException {
        Square square = squareService.squareFactory(
                1L, "INVALID_TYPE", "Test Square", Province.BUENOS_AIRES, Zone.NORTE,
                100, 50, EventType.MONEY, EventType.GO_TO_JAIL, 200, CardType.LUCK, sqlArray
        );

        assertNull(square);
    }
}
