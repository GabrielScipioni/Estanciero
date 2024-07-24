package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.NewGameMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Game.GameDifficulty;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.GameService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class NewGameDifficultyOptionTest {

    @Mock
    private User user;

    @Mock
    private GameDifficulty difficulty;

    @Mock
    private GameService gameService;

    @Mock
    private Game game;

    @Mock
    private UserInteraction userInteraction;

    @InjectMocks
    private NewGameDifficultyOption newGameDifficultyOption;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        newGameDifficultyOption = new NewGameDifficultyOption(1, "Test Option", user, difficulty);
    }

//    @Test
//    void testExecute() {
//        // Mock behavior for GameService and UserInteraction
//        when(gameService.gameFactory(difficulty, user)).thenReturn(game);
//
//        // Execute the method under test
//        newGameDifficultyOption.execute();
//
//        // Verify interactions
//        verify(gameService).create(game, user);
//        verify(game).resumeGame();
//        verify(userInteraction).message("Se creo un juego" + difficulty);
//
//        // Optionally, verify other interactions or assertions based on your specific logic
//    }
}
