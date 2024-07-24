package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu;

import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.UserCRUDService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginMenuTest {

    @Mock
    private UserCRUDService userCRUDServiceMock;

    @Mock
    private UserInteraction userInteractionMock;

    @Mock
    private PrintStream out;

    @InjectMocks
    private LoginMenu loginMenu;

    @BeforeEach
    public void setUp() {
        System.setOut(out);
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
        // Configura UserCRUDService singleton para devolver el mock en lugar de la instancia real
        UserCRUDService.setInstance(userCRUDServiceMock);
    }

    @Test
    public void LoginConstructorTest() {
        LoginMenu login = new LoginMenu(1);
    }

    @Test
    public void GetSetUiTest() {
        UserInteraction ui = new UserInteraction();
        LoginMenu login = new LoginMenu(1);

        login.setUserInteraction(ui);

        assertEquals(ui, login.getUserInteraction());
    }

    /*
    @Test
    public void testExecute_UserNotFound() {
        // Arrange
        userInteractionMock = new UserInteraction();
        String simulatedInput = "nonexistentUser\n"; // leer introduccion para saber por que el "\n"
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());

        System.setIn(inputStream);

        simulatedInput = "wrongPassword\n"; // leer introduccion para saber por que el "\n"
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());

        System.setIn(inputStream);

        loginMenu = new LoginMenu(1);
        loginMenu.setUserInteraction(userInteractionMock);

        // Simula el comportamiento de UserCRUDService

        user
        when(userCRUDServiceMock.getByUserNameAndPass(anyString(), anyString())).thenReturn(null);

        // Act
        loginMenu.execute();

        // Assert
        verify(userInteractionMock).message("Usuario y/o contraseñas incorrectas");
        verify(userInteractionMock).sleep(500);
    }*/
}