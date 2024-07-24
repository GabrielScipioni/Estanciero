package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.GamesEntity;
import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Game.GameDifficulty;
import ar.edu.utn.frc.tup.lciii.Model.Player.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.CardsService;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.SquareService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private ConnexionDB connexionDB;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private User mockUser;

    @Mock
    private SquareService squareService;

    @Mock
    private CardsService cardsService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connexionDB.connect()).thenReturn(connection);
    }

    @Test
    void testGetById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("game_id")).thenReturn(1L);
        when(resultSet.getTimestamp("datesetup")).thenReturn(Timestamp.valueOf("2023-07-02 10:10:10"));
        when(resultSet.getBoolean("finished")).thenReturn(false);
        when(resultSet.getString("order_by_id_player")).thenReturn("1,2,3");

        GamesEntity game = gameService.getById(1L);

        assertNotNull(game);
        assertEquals(1L, game.getGameId());
        assertEquals("2023-07-02T10:10:10", game.getDateSetup().toString());
        assertFalse(game.getFinished());
        assertEquals("1,2,3", game.getOrderByIdPlayerAsString());
    }

    @Test
    void testGetAll() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("game_id")).thenReturn(1L);
        when(resultSet.getTimestamp("datesetup")).thenReturn(Timestamp.valueOf("2023-07-02 10:10:10"));
        when(resultSet.getBoolean("finished")).thenReturn(false);
        when(resultSet.getString("order_by_id_player")).thenReturn("1,2,3");

        List<GamesEntity> games = gameService.getAll();

        assertNotNull(games);
        assertEquals(1, games.size());
        GamesEntity game = games.get(0);
        assertEquals(1L, game.getGameId());
        assertEquals("2023-07-02T10:10:10", game.getDateSetup().toString());
        assertFalse(game.getFinished());
        assertEquals("1,2,3", game.getOrderByIdPlayerAsString());
    }

    @Test
    void testGetAllFromUser() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("game")).thenReturn("{\"id\":1}");
        when(resultSet.getLong("game_id")).thenReturn(1L);
        when(resultSet.getDate("dateSetup")).thenReturn(Date.valueOf("2023-07-02"));

        when(mockUser.getId()).thenReturn(1L);

        List<Game> games = gameService.getAllFromUser(mockUser);

        assertNotNull(games);
        assertEquals(1, games.size());
        Game game = games.get(0);
        assertEquals(1L, game.getId());

    }

    @Test
    void testUpdate() throws SQLException {
        Game game = new Game();
        game.setId(1L);
        game.setDate(new java.util.Date(1232132132132L));
        game.setFinishGame(false);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);


        Game updatedGame = gameService.update(game);

        assertNotNull(updatedGame);
        assertEquals(1L, updatedGame.getId());
    }


    @Test
    void testSerializeGame() {
        Game game = new Game();
        String jsonString = gameService.serializeGame(game);

        assertNotNull(jsonString);
    }

    @Test
    void testDeSerializeGame() {
        String jsonString = "{\"id\":1}";
        Game game = gameService.deSerializeGame(jsonString);

        assertNotNull(game);
        assertEquals(1L, game.getId());
    }

    @Test
    void testGameFactory() {
        when(mockUser.getName()).thenReturn("Test User");

        Game easyGame = gameService.gameFactory(GameDifficulty.EASY, mockUser);
        assertNotNull(easyGame);

        Game mediumGame = gameService.gameFactory(GameDifficulty.MEDIUM, mockUser);
        assertNotNull(mediumGame);

        Game hardGame = gameService.gameFactory(GameDifficulty.HARD, mockUser);
        assertNotNull(hardGame);
    }
}
