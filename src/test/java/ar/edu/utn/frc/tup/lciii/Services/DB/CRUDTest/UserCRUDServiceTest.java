package ar.edu.utn.frc.tup.lciii.Services.DB.CRUDTest;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.UserEntity;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.UserCRUDService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UserCRUDServiceTest {

    @InjectMocks
    private UserCRUDService userCRUDService;

    @Mock
    private ConnexionDB connexionDB;

    @Mock
    private Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() throws Exception {
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("user_id")).thenReturn(1L);
        when(resultSet.getString("username")).thenReturn("testuser");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("name")).thenReturn("test@example.com");

        UserEntity user = userCRUDService.getById(1L);

        Assertions.assertNotNull(user);
        assertEquals(1L, user.getUserId().longValue());
        Assertions.assertEquals("testuser", user.getUsername());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertEquals("test@example.com", user.getName());
    }




    @Test
    void testGetByUserNameAndPass_Success() throws SQLException {
        // Arrange
        String userName = "testuser";
        String password = "password123";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUserName(userName);
        expectedUser.setPassWord(password);
        expectedUser.setName("Test User");

        // Set up mock behavior
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true); // Simulate that a result was found
        when(resultSet.getLong("user_id")).thenReturn(expectedUser.getId());
        when(resultSet.getString("username")).thenReturn(expectedUser.getUserName());
        when(resultSet.getString("password")).thenReturn(expectedUser.getPassWord());
        when(resultSet.getString("name")).thenReturn(expectedUser.getName());

        // Act
        User actualUser = userCRUDService.getByUserNameAndPass(userName, password);

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUserName(), actualUser.getUserName());
        assertEquals(expectedUser.getPassWord(), actualUser.getPassWord());
        assertEquals(expectedUser.getName(), actualUser.getName());
    }

    @Test
    void testGetByUserNameAndPass_NoUserFound() throws SQLException {
        // Arrange
        String userName = "testuser";
        String password = "password123";

        // Set up mock behavior
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false); // Simulate that no result was found

        // Act
        User actualUser = userCRUDService.getByUserNameAndPass(userName, password);

        // Assert
        assertNull(actualUser);
    }



    @Test
    void testGetByUserName_Success() throws SQLException {
        // Arrange
        String userName = "testuser";

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUserName(userName);

        expectedUser.setName("Test User");

        // Set up mock behavior
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true); // Simulate that a result was found
        when(resultSet.getLong("user_id")).thenReturn(expectedUser.getId());
        when(resultSet.getString("username")).thenReturn(expectedUser.getUserName());

        when(resultSet.getString("name")).thenReturn(expectedUser.getName());

        // Act
        User actualUser = userCRUDService.getByUserName(userName);

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUserName(), actualUser.getUserName());
        assertEquals(expectedUser.getPassWord(), actualUser.getPassWord());
        assertEquals(expectedUser.getName(), actualUser.getName());
    }

    @Test
    void testGetByUserName_NoUserFound() throws SQLException {
        // Arrange
        String userName = "testuser";

        // Set up mock behavior
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false); // Simulate that no result was found

        // Act
        User actualUser = userCRUDService.getByUserName(userName);

        // Assert
        assertNull(actualUser);
    }

    @Test
    void testCreate() throws SQLException {
        // Crear un UserEntity de prueba
        User user = new User();
        user.setUserName("testuser");
        user.setPassWord("testpassword");
        user.setName("test@test.com");

        // Configurar mocks
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simular que la inserción afecta 1 fila

        // Configurar ResultSet para simular la recuperación del ID generado
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simula que hay una fila en el ResultSet
        when(resultSet.getLong(1)).thenReturn(1L); // El ID generado es 1L

        // Llamar al método create
        User createdUser = userCRUDService.create(user);

        // Verificar la interacción con los mocks
        verify(connection).prepareStatement("INSERT INTO users (username, password, name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        verify(preparedStatement).setString(1, "testuser");
        verify(preparedStatement).setString(2, "testpassword");
        verify(preparedStatement).setString(3, "test@test.com");
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).getGeneratedKeys();
        verify(resultSet).next();


        // Verificar que el usuario creado tiene el ID generado
        assertNotNull(createdUser);

        assertEquals("testuser", createdUser.getUserName());
        assertEquals("testpassword", createdUser.getPassWord());
        assertEquals("test@test.com", createdUser.getName());
    }




}

