package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.LoadGameMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class LoadOneGameOptionTest {

    private Game mockGame;
    private LoadOneGameOption loadOneGameOption;

    @BeforeEach
    void setUp() {
        // Crear un objeto mock de Game
        mockGame = Mockito.mock(Game.class);
        // Configurar el comportamiento del mock
        Mockito.when(mockGame.getDate()).thenReturn(new Date());
        Mockito.when(mockGame.isFinishGame()).thenReturn(false);
        // Inicializar el objeto LoadOneGameOption con el mock
        loadOneGameOption = new LoadOneGameOption(1, mockGame);
    }

    @Test
    void testConstructor() {
        // Verificar que el constructor inicializa los valores correctamente
        assertEquals(1, loadOneGameOption.getOptionNumber());
        assertTrue(loadOneGameOption.getOptionName().contains("Activo"));
        assertEquals("", loadOneGameOption.getTitle());

    }

    @Test
    void testExecute() {
        // Llamar al método execute
        loadOneGameOption.execute();
        // Verificar que se llamó al método resumeGame del objeto Game
        verify(mockGame).resumeGame();
    }
}
