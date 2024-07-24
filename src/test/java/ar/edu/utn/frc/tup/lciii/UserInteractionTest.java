package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInteractionTest {
    /*
     * AGUS: Fijensen q se crean los Mocks necesarios para el testeo de la class
     *
     * esta el Mock de la class PrintStream, esta clase a lo resumen hace
     * lo de mostrar el texto en la consola, tiene los metodos print() y println()
     * si se fijan esos metodos llaman a uno que es newLine() o write(), el chiste de
     * usar el mock de PrintStream es interceptar el llamado de estos metodos
     * lo interceptas usando el objeto out que es instancia de esta class
     * esta clase entera se puede testear en su mayoria de metodos al interceptar el llamado
     * de PrintStream a uno de estos metodos con el contendio de el mensaje a testear
     * hay que pensarlo como si fuera una especie de asserttrue ponele de lo que hacemos en otras class
     *
     * nada eso, igual pueden saltearse eso porque total es la unica clase que se testea de esta forma
     * las otras podemos testearlas con los assert y los mock o spy
     *
     * IMPORTANTE: notese que cuando se testea un metodo que tiene adentro tiene un
     * "scanner.nextLine()" el scanner lo que hace es leer los datos ingresados hasta que
     * se encuentra con una secuencia de escape, basicamente esta esperando un ENTER osea el "\n"
     * cuando lo encuentra el scanner termina el metodo de next line y te retorna lo que escribiste
     * SI NO SE PONE LA SECUENCIA DE ESCAPE EL METODO NUNCA TERMINA, esperando por siempre
     * que se ponga "el enter" al input (que es un string predefinido)
     */
    @Mock
    private PrintStream out;
    @Mock
    private Container<String> container;

    @InjectMocks
    private UserInteraction ui;

    @BeforeEach
    public void setUp() {
        System.setOut(out);
    }

    @Test
    public void testMessage() {
        String message = "Hello, World!";
        ui.message(message);
        verify(out).println(message);
    }

    @Test
    public void testShowRolls() {
        Integer[] rolls = {3, 3}; //rolls de prueba

        ui.showRolls(rolls);

        verify(out).println("You roll [3] and [3]!");
    }

    @Test
    public void testAsk() {
        // simular input
        String simulatedInput = "Racing\n"; // leer introduccion para saber por que el "\n"
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());

        System.setIn(inputStream); // le ponemos al System.in el input simulado

        UserInteraction userInteraction = new UserInteraction(); //nuevo ui (importante)

        String result = userInteraction.ask("Cual es el peor equipo de futbol de cordoba?");

        assertEquals("Racing", result);
    }

    @Test
    public void testAskValidation() {
        //entrada valida
        String simulatedInput = "y\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        UserInteraction userInteraction = new UserInteraction();
        String result = userInteraction.askValidation("test","[yYnN]");
        assertEquals("y",result);

        //ingresar algo invalido y desp algo valido
        simulatedInput = "x\ny\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.askValidation("test", "[yYnN]");
        assertEquals("y", result);
    }

    @Test
    public void testAskYesNo() {
        // input "y"
        String simulatedInput = "y\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        UserInteraction userInteraction = new UserInteraction();
        boolean result = userInteraction.askYesNo("test");
        assertTrue(result);

        // input "n"
        simulatedInput = "n\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.askYesNo("test");
        assertFalse(result);

        // input "x" --> input "y"
        simulatedInput = "x\ny\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.askYesNo("test");
        assertTrue(result);
    }

    @Test
    public void testAskInt() {
        // "int"
        String simulatedInput = "7\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        UserInteraction userInteraction = new UserInteraction();
        Integer result = userInteraction.askInt("random");
        assertEquals(7,result);

        // "no int" --> "int"
        simulatedInput = "Labo\n3\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.askInt("question");
        assertEquals(3, result);
    }

    @Test
    public void testShowList() {
        //list w/ items
        List<String> list = new ArrayList<>();
        list.add("Argentina");
        list.add("France");

        ui.showList(list);
        verify(out).println("* Argentina");
        verify(out).println("* France");

        //empty list
        list.clear();

        ui.showList(list);
        verify(out).println("No items currently!");
    }

    @Test
    public void askValidSelection() {
        List<String> list = new ArrayList<>();
        list.add("Argentina");
        list.add("France");

        // input "1"
        String simulatedInput = "1\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        UserInteraction userInteraction = new UserInteraction();
        Integer result = userInteraction.askValidSelection(list,"Quien esta primero?");
        assertEquals(0,result); //Argentina

        // input "3" (no valido) ---> input "2" (valido)
        simulatedInput = "3\n2\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.askValidSelection(list, "Quién esta segundo?");
        assertEquals(1, result); // France

        // input "0" (fuera de rango) ---> input "2"
        simulatedInput = "0\n2\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.askValidSelection(list, "Quién esta segundo?");
        assertEquals(1, result); // France
    }

    @Test
    public void testShowListWithSelection() {
        List<String> list = new ArrayList<>();
        list.add("Argentina");
        list.add("France");

        // input "1"
        String simulatedInput = "1\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        UserInteraction userInteraction = new UserInteraction();
        Integer result = userInteraction.showListWithSelection(list);
        assertEquals(0,result);
        verify(out).println("1. Argentina");
        verify(out).println("2. France");

        // input "3" (invalid) --> input "2" (valid)
        simulatedInput = "3\n2\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.showListWithSelection(list);
        assertEquals(1, result);
        verify(out).println("Invalid selection. Please choose a number between 1 and " + list.size() + ".");
    }

    @Test
    public void testShowNestedList() {
        List<String> materias = new ArrayList<>();
        materias.add("Laboratorio III");
        materias.add("Programacion III");
        List<Container<String>> nest = List.of(container);
        when(container.getList()).thenReturn(materias);
        when(container.getIdentifier()).thenReturn("Materias");

        //input "1" (valid)
        String simulatedInput = "1\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        UserInteraction userInteraction = new UserInteraction();
        Integer result = userInteraction.showNestedList(nest);

        assertEquals(0, result);
        verify(out).println("1. Materias");
        verify(out).println("* Laboratorio III");
        verify(out).println("* Programacion III");

        //input "3" ---> input "1"
        simulatedInput = "3\n1\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        userInteraction = new UserInteraction();
        result = userInteraction.showNestedList(nest);

        assertEquals(0, result);
        verify(out, times(2)).println("1. Materias");
        verify(out, times(2)).println("* Laboratorio III");
        verify(out, times(2)).println("* Programacion III");
        verify(out).println("Invalid selection. Please choose a number between 1 and " + nest.size() + ".");
    }

    @Test
    public void testClose() {
        // Llamar al método close() de UserInteraction para cerrar el Scanner
        ui.close();

        // Intentar leer algo del Scanner cerrado debería lanzar una excepción
        assertThrows(Exception.class, () -> ui.ask("el scanner ya no funca"));
    }
}