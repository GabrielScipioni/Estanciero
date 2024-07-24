package ar.edu.utn.frc.tup.lciii.Services.DB.CRUDTest;


import ar.edu.utn.frc.tup.lciii.Entities.GamesEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;

import java.util.List;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class GameCRUDServiceTest {

    private static GameService gameCRUDService;
    private GamesEntity gamesEntity;
    private Game game;
    private String gameJSON;


    @BeforeAll
    static void setUp() {
        gameCRUDService = GameService.getInstance();
    }

    @BeforeEach
    void init() throws JsonProcessingException {
        gamesEntity = new GamesEntity();
        gamesEntity.setGameId(1L);
        gamesEntity.setDateSetup(LocalDateTime.now());
        gamesEntity.setFinished(false);
        gamesEntity.setOrderByIdPlayer("5-3-2-1");

    }

    @AfterEach
    void tearDown() {
//        if (gamesEntity != null && gamesEntity.getGameId() != null) {
//            gameCRUDService.delete(gamesEntity.getGameId());
//        }
    }

    @Test
    void testCreate() {
//        GamesEntity createdGame = gameCRUDService.create(game);
//
//        assertNotNull(createdGame.getGameId());
//
//        assertEquals(gamesEntity.getDateSetup(), createdGame.getDateSetup());
//        assertEquals(gamesEntity.getFinished(), createdGame.getFinished());
//        assertEquals(gamesEntity.getOrderByIdPlayer(), createdGame.getOrderByIdPlayer());
//    }


//    @Test
//    void testUpdate() {
//        // Create a new Game
//        GamesEntity createdGame = gameCRUDService.create(gamesEntity);
//
//        // Modify some data of the game
//        createdGame.setFinished(true);
//        createdGame.setOrderByIdPlayer("1-2-3-4");
//
//        // Update the game in the database
//        GamesEntity updatedGame = gameCRUDService.update(createdGame);
//
//        // Retrieve the updated game by ID
//        GamesEntity retrievedGame = gameCRUDService.getById(updatedGame.getGameId());
//
//        // Verify that the retrieved game has the updated data
//        assertEquals(updatedGame.getGameId(), retrievedGame.getGameId());
//        assertEquals(updatedGame.getFinished(), retrievedGame.getFinished());
//        assertEquals(updatedGame.getOrderByIdPlayer(), retrievedGame.getOrderByIdPlayer());
//    }
//
//    @Test
//    void testDelete() {
//        // Create a new Game
//        GamesEntity createdGame = gameCRUDService.create(gamesEntity);
//
//        // Delete the game by ID
//        gameCRUDService.delete(createdGame.getGameId());
//
//        // Try to retrieve the deleted game by ID
//        GamesEntity deletedGame = gameCRUDService.getById(createdGame.getGameId());
//
//        // Verify that the deleted game does not exist in the database
//        assertNull(deletedGame);
//    }
//
//
//    @Test
//    void testGetById() {
//        // Create a new Game
//        GamesEntity createdGame = gameCRUDService.create(gamesEntity);
//
//        // Retrieve the game by ID
//        GamesEntity retrievedGame = gameCRUDService.getById(createdGame.getGameId());
//
//        // Verify that the retrieved game matches the created game
//        assertNotNull(retrievedGame);
//        assertEquals(createdGame.getGameId(), retrievedGame.getGameId());
//        assertEquals(gamesEntity.getDateSetupAsString(), retrievedGame.getDateSetupAsString());
//        assertEquals(createdGame.getFinished(), retrievedGame.getFinished());
//        assertEquals(createdGame.getOrderByIdPlayer(), retrievedGame.getOrderByIdPlayer());
//    }
//
//    @Test
//    void testGetAll() {
//        // Create a new Game
//        GamesEntity createdGame = gameCRUDService.create(gamesEntity);
//
//        // Retrieve all games
//        List<GamesEntity> games = gameCRUDService.getAll();
//
//        // Verify that the list of games is not empty and contains the created game
//        assertNotNull(games);
//        assertFalse(games.isEmpty());
//
//        boolean found = false;
//        for (GamesEntity g : games) {
//            if (g.getGameId().equals(createdGame.getGameId())) {
//                found = true;
//                assertEquals(createdGame.getDateSetupAsString(), g.getDateSetupAsString());
//                assertEquals(createdGame.getFinished(), g.getFinished());
//                assertEquals(createdGame.getOrderByIdPlayer(), g.getOrderByIdPlayer());
//            }
//        }
//        assertTrue(found);
//    }
    }
}
