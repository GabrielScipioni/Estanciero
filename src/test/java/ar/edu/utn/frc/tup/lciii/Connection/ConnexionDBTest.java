
package ar.edu.utn.frc.tup.lciii.Connection;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConnexionDBTest {


    private ConnexionDB connexionDB;

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private FileInputStream mockFileInputStream;

    @Mock
    private Properties mockProperties;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        when(mockProperties.getProperty(anyString())).thenReturn("aaa");

        connexionDB = spy(new ConnexionDB(mockFileInputStream, mockProperties));
        doReturn(mockConnection).when(connexionDB).createConnection(anyString(), anyString(), anyString());
    }

    @AfterEach
    void tearDown() throws SQLException, IOException {
        if (mockConnection != null) {
            mockConnection.close();
        }
        if (mockStatement != null) {
            mockStatement.close();
        }
        if (mockFileInputStream != null) {
            mockFileInputStream.close();
        }
        connexionDB.disconnect();
        connexionDB.setUpConfigurations();
        connexionDB.deleteInstance();



    }

    @Test
    void testConnect() throws SQLException {
        Connection connection = connexionDB.connect();
        assertNotNull(connection);
        verify(mockConnection, times(0)).isClosed();


        connection = connexionDB.connect();
        verify(mockConnection).isClosed();
    }

    @Test
    void testDisconnect() throws SQLException {
        connexionDB.disconnect();
        verify(mockConnection, times(0)).close();


        connexionDB.connect();
        connexionDB.disconnect();
        verify(mockConnection).close();
    }

    @Test
    void testInitializeDatabase() throws Exception {
        doReturn(1).when(mockStatement).executeUpdate(anyString());

        connexionDB.initializeDatabase();

        verify(mockStatement, atLeastOnce()).executeUpdate(anyString());
    }

    @Test
    void testSetUpConfigurations() throws IOException {

        connexionDB.setUpConfigurations();


        verify(mockProperties, times(2)).load(mockFileInputStream);

        doThrow(new IOException("")).when(mockProperties).load(mockFileInputStream);
        connexionDB.setUpConfigurations();
        verify(mockProperties, times(3)).load(mockFileInputStream);

    }

    @Test
    void testCreateConnection() throws SQLException {

        Connection mockConnection = mock(Connection.class);

        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(null, null, null)).thenReturn(mockConnection);


            Connection connection = connexionDB.createConnection(null, null, null);


            assertNotNull(connection);
            mockedDriverManager.verify(() -> DriverManager.getConnection(any(), any(), any()), times(1));
        }


    }

    @Test
    void testGetInstanceCreatesNewInstance() throws Exception {

        try (MockedConstruction<FileInputStream> mockedFileInputStream = Mockito.mockConstruction(FileInputStream.class);
             MockedConstruction<Properties> mockedProperties = Mockito.mockConstruction(Properties.class)) {
            ConnexionDB instance1 = ConnexionDB.getInstance();
            ConnexionDB instance2 = ConnexionDB.getInstance();
         assertNotNull(instance1);
            assertSame(instance1, instance2);
        }
    }

//    @Test
//    void testGetInstanceHandlesException() {
//
//        try (MockedConstruction<FileInputStream> mockedFileInputStream = Mockito.mockConstruction(FileInputStream.class, (mock, context) -> {
//            when(mock.read()).thenThrow(new IOException("sdf"));
//        });
//             MockedConstruction<Properties> mockedProperties = Mockito.mockConstruction(Properties.class)) {
//
//            ConnexionDB instance = ConnexionDB.getInstance();
//
//            assertNull(instance);
//        }
//    }
    }


