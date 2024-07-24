package ar.edu.utn.frc.tup.lciii.Model.Menu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MenuOptionTest {


    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }



    @Test
    void drawOptionTest() {
        Option option = new MenuOption(0, "Test", "Test", false) {
            @Override
            public void execute() {
                System.out.println("Execute");
            }
        };


        ArrayList<Option> options = new ArrayList<>();
        Option child = new MenuOption(0, "Hijo", "Hijo",true) {
            @Override
            public void execute() {
                return;
            }
        };

        options.add(child);

        option.setSubOptions(options);
        ByteArrayInputStream in = new ByteArrayInputStream("3\n0\n".getBytes());
        System.setIn(in);

        try {
            option.drawSubOptions();
        } catch (NoSuchElementException e) {
        }





    }
    private String getOutput() {
        return testOut.toString();
    }


    }



