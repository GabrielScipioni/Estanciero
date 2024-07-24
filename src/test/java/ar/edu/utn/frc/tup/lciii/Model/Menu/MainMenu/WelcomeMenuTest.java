package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.LoadGameMenu.LoadGameMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.NewGameMenu.StartNewGameMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.StartMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class WelcomeMenuTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;
    User user;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        user= new User("test", "test", "test");
        user.setId(1L);
    }



    @Test
    void testWelcomeMenuExecute() {
        Option option = new WelcomeMenu();

        ByteArrayInputStream in = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(in);


        try {
            option.execute();
        } catch (NoSuchElementException ignored) { }


        assertTrue(testOut.toString().contains("1. Login"));
        assertTrue(testOut.toString().contains("2. Registrarse"));
        assertTrue(testOut.toString().contains("Bienvenido"));
        assertTrue(testOut.toString().contains("3. Salir"));
    }
    @Test
    void testLoginMenuExecute() {
        Option option = new LoginMenu(1);

        ByteArrayInputStream in = new ByteArrayInputStream("lucas\nlucas\n4\n".getBytes());
        System.setIn(in);


        try {
            option.execute();
        } catch (NoSuchElementException ignored) { }


        assertTrue(testOut.toString().contains("Usuario"));
        //assertTrue(testOut.toString().contains("Password"));

    }

    @Test
    void testRegisterMenuExecute() {
        Option option = new RegisterMenu(1);

        ByteArrayInputStream in = new ByteArrayInputStream("lucas\nlucas\nlucas\n4\n".getBytes());
        System.setIn(in);


        try {
            option.execute();
        } catch (NoSuchElementException ignored) { }


        assertTrue(testOut.toString().contains("Usuario"));

        assertTrue(testOut.toString().contains("Nombre"));

    }

    @Test
    void testStartMenuMenuExecute() {
        Option option = new StartMenu( user);

        ByteArrayInputStream in = new ByteArrayInputStream("lucas\nlucas\nlucas\n4\n".getBytes());
        System.setIn(in);


        try {
            option.execute();
        } catch (NoSuchElementException ignored) { }


        assertTrue(testOut.toString().contains("Seleccione"));
        assertTrue(testOut.toString().contains("Nuevo juego"));
        assertTrue(testOut.toString().contains("Cargar juego"));
    }

    @Test
    void testStartNewGameMenuExecute() {
        Option option = new StartNewGameMenu(1, user);

        ByteArrayInputStream in = new ByteArrayInputStream("4\n".getBytes());
        System.setIn(in);


        try {
            option.execute();
        } catch (NoSuchElementException ignored) { }


        assertTrue(testOut.toString().contains("Fácil"));
        assertTrue(testOut.toString().contains("Seleccione una dificultad"));
        assertTrue(testOut.toString().contains("Difícil"));
    }
    @Test
    void testLoadGameMenuExecute() {
        Option option = new LoadGameMenu(1, user);

        ByteArrayInputStream in = new ByteArrayInputStream("4\n".getBytes());
        System.setIn(in);


        try {
            option.execute();
        } catch (NoSuchElementException ignored) { }


        assertTrue(testOut.toString().contains("Seleccione un ju"));
    }


//    @Test
//    void testWelcomeMenuExecute() {
//        WelcomeMenu WelcomeMenu = new WelcomeMenu();
//
//        WelcomeMenu.execute();
//        assertEquals("Se creó un juego"+System.lineSeparator(), getOutput());
//
//    }
//    @Test
//    void testLoadStartedGameOptionExecute() {
//        GameMenu.LoadStartedGameOption loadStartedGameOption = new GameMenu.LoadStartedGameOption(1);
//
//
//        loadStartedGameOption.execute();
//        assertEquals("Se cargó un juego"+System.lineSeparator(), getOutput());
//
//    }
//
//    private String getOutput() {
//        return testOut.toString();
//    }

}

