package ar.edu.utn.frc.tup.lciii.Model.Menu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ExitOptionTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @Test
    void gettersTest() {
        Option option = new ExitOption(0);


        assertEquals("", option.getTitle());
        assertEquals("Salir", option.getOptionName());
        assertEquals(0, option.getOptionNumber());
        assertNull(option.getSubOptions());
        assertNull(option.getMenuOption(0));
        assertTrue(option.isExitOption());


    }
    @Test
    void executeTest() {
        Option option = new ExitOption(0);
        assertDoesNotThrow(option::execute);


    }


    private String getOutput() {
        return testOut.toString();
    }


    }



