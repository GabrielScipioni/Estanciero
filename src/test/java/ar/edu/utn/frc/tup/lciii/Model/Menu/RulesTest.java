package ar.edu.utn.frc.tup.lciii.Model.Menu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RulesTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }



    @Test
    void test() {
        Rules helpMenu= new Rules(0);
        assertEquals("Ayuda", helpMenu.getOptionName());
        assertEquals("Seleccione una opción", helpMenu.getTitle());
        assertEquals(0, helpMenu.getOptionNumber());

        try {
            helpMenu.execute();
        } catch (NoSuchElementException ignored) { }

        String aux="CONTENIDO:\n" +
                "\n" +
                "*Tablero con 42 casillas\n" +
                "*6 jugadores (peones)\n" +
                "*29 escrituras ( 22 Provincias, 4 Ferrocarriles, 3 Compañias)\n" +
                "*32  cartas (Suerte y Destino)\n" +
                "*32 chacras, 12 estancias\n" +
                "*2 dados\n" +
                "*billetes\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "OBJETIVO:\n" +
                "\n" +
                "El BANCO provee billetes a los jugadores\n" +
                "Se debe adquirir propiedades: \n" +
                "Provincias (mejorar con chacras y estancias),\n" +
                "Ferrocarriles y Compañías\n" +
                "\n" +
                "*Los jugadores pueden hacer transacciones entre sí\n" +
                "*El juego finaliza al quedar 1\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "INICIO DEL JUEGO:\n" +
                "*Se entrega en diferentes billetes la suma de $35.000 a cada jugador\n" +
                "*El resto queda en el Banco\n" +
                "\n" +
                "*Los turnos se determinan por valor más alto al lanzar los dados\n" +
                "\n" +
                "*Opcionalmente se puede iniciar el juego con valores monetarios mayores\n" +
                "E incluso la entrega inicial de Escrituras para \"acortar la duracion del juego\"\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "JUEGO / REGLAS GENERALES:\n" +
                "\n" +
                "*Si sale par igual (doble) en los dados (par de 2, par de 5)\n" +
                "el jugador tiene otro turno. Maximo 2 veces. 3era COMISARÍA\n" +
                "\n" +
                "*El jugador al llegar a una casilla con ESCRITURA\n" +
                "(Prov, Ferro, Comp) se puede adquirir por el VALOR EN TABLERO\n" +
                "(si no fueron adquiridos)\n" +
                "El BANCO entregará en tal caso la ESCRITURA\n" +
                "\n" +
                "*El jugador al llegar a una casilla propiedad\n" +
                "de otro jugador, tiene que PAGAR ALQUILER\n" +
                "de acuerdo a la ESCRITURA\n" +
                "\n" +
                "*Si el PROPIETARIO no reclama el alquiler antes\n" +
                "de que el siguiente jugador tire los dados\n" +
                "pierde la chance de cobrar el alquiler\n" +
                "\n" +
                "*La VENTA DE CHACRAS Y ESTANCIAS es exclusiva \n" +
                "entre BANCO y PROPIETARIO. \n" +
                "Se pueden negociar ESCRITURAS de propiedades\n" +
                "\n" +
                "*Llegar a SUERTE o DESTINO implica agarrar la carta\n" +
                "cumplir lo que indica y guardarla abajo del todo\n" +
                "\n" +
                "*El BANCO paga y cobra PREMIOS e IMPUESTOS/MULTAS\n" +
                "\n" +
                "*Si el jugador no reclama el PREMIO al BANCO\n" +
                "antes de que el siguiente jugador tire los dados\n" +
                "pierde el PREMIO\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "COMISARÍA (casilla):\n" +
                "\n" +
                "El jugador permanece en COMISARÍA:\n" +
                "\n" +
                "1) *Si llega a la casilla COMISARÍA\n" +
                "2) *Si llega a la casilla MARCHE PRESO\n" +
                "3) *Si una tarjeta (?|!) lo indica\n" +
                "4) *Si saca par igual (doble) por 3era vez en los dados\n" +
                "\n" +
                "*El preso debe pagar una multa al banco de 1.000 para continuar\n" +
                "\n" +
                "Formas de salir:\n" +
                "\n" +
                "1) *Tarjeta (?|!) indica libre salida\n" +
                "2) *Si saca par igual (doble) al tirar los dados nuevamente\n" +
                "3)  Pagar la multa\n" +
                "\n" +
                "El preso no se ve impedido de efectuar transacciones\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "DESCANSO (casilla):\n" +
                "\n" +
                "Al llegar a esta casilla:\n" +
                "\n" +
                "*El jugador para sus siguientes 2 turnos \n" +
                "puede elegir entre avanzar o permanecer en su lugar,\n" +
                "pero deberá hacerlo ANTES DE TIRAR los dados.\n" +
                "\n" +
                "*Si el jugador saca (doble) se vera forzado a avanzar.\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "LIBRE ESTACIONAMIENTO (casilla) :\n" +
                "\n" +
                "*No te podes quedar en esta casilla\n" +
                "(supongo que tiras de nuevo dados)\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "CHACRAS (casitas):\n" +
                "\n" +
                "Son propiedades adquiridas a travez del BANCO\n" +
                "y colocadas sobre los campos (PROVINCIAS).\n" +
                "El VALOR de las chacras esta indicado en las ESCRITURAS\n" +
                "\n" +
                "*Se debe tener una PROVINCIA completa para adquirirlas\n" +
                "*Aumentan el ALQUILER a favor del PROPIETARIO\n" +
                "\n" +
                "*No se pueden tener +4 chacras en un solo campo\n" +
                "\n" +
                "*Solo se permite una diferencia MAXIMA de 1 chacra\n" +
                "entre las casillas de la misma PROVINCIA\n" +
                "(Ejemplos validos: 3 3 3, 4 3 2,)\n" +
                "(Ejemplos no validos: 4 4 2, 4 4 1, 3 3 1, 4 2 1)\n" +
                "\n" +
                "Adicionalmente:\n" +
                "*Se pueden vender al banco c/u de las CHACRAS por la mitad de su precio original\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "ESTANCIAS (CASOTA OMG):\n" +
                "\n" +
                "Tambien son adquiridas en el BANCO\n" +
                "Solo van en PROVINCIAS\n" +
                "Su valor esta en la ESCRITURA\n" +
                "\n" +
                "Como indica la ESCRITURA, la estancia vale\n" +
                "$x (precio) mas 4 CHACRAS\n" +
                "\n" +
                "*Por lo tanto para adquirir una \n" +
                "o bien se entregan las 4 CHACRAS previamente adquiridas al BANCO\n" +
                "y se paga el $x (precio) restante\n" +
                "*o bien se paga $x +  lo que valen las CHACRAS\n" +
                "que hacen falta para llegar a 4 CHACRAS de una\n" +
                "*el hecho es que en una casilla como MAXIMO puede haber\n" +
                "o 4 CHACRAS o 1 ESTANCIA\n" +
                "\n" +
                "Adicionalmente:\n" +
                "*Se pueden vender al banco c/u de las ESTANCIAS por la mitad de su precio original\n" +
                "\n" +
                "--------------------------------------\n" +
                "\n" +
                "HIPOTECA :\n" +
                "\n" +
                "*No se pueden hipotecar CHACRAS ni ESTANCIAS\n" +
                "\n" +
                "*El BANCO otorga dinero al propietario que realiza la HIPOTECA\n" +
                "de acuerdo al \"VALOR HIPOTECARIO\" que se muestra en la ESCRITURA\n" +
                "(por lo general 50% del precio de compra del campo) - 10% de interés\n" +
                "que cobra el BANCO por adelantado\n" +
                "Ej: Propiedad 2000, Valor hipotecario 1000, El banco te manguea 100 (10%) de los 1000)\n" +
                "\n" +
                "\n";
        assertEquals(aux + System.lineSeparator(), testOut.toString());
    }



    private String getOutput() {
        return testOut.toString();
    }

    }



