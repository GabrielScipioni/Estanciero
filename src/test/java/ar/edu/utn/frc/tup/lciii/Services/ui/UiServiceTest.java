package ar.edu.utn.frc.tup.lciii.Services.ui;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.ModerateBot;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.Property.RailwayProperty;
import ar.edu.utn.frc.tup.lciii.Model.User;
import net.bytebuddy.matcher.StringMatcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UiServiceTest {
    Game game;
    HumanPlayer humanPlayer;
    ModerateBot botPlayer;
    ModerateBot botPlayer1;
    ModerateBot botPlayer3;
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @BeforeEach
    void setUp(){
         game = new Game(new Board(), new User("test", "test", "test"));
         humanPlayer = new HumanPlayer(List.of(new RailwayProperty(false, "railway", 100)),false);
        humanPlayer.setName("Pepe");
        humanPlayer.setBalance(200);
        humanPlayer.getCards().add(new Card(CardType.LUCK, null,"",null,null,false));

        botPlayer = new ModerateBot(List.of(new RailwayProperty(false, "railway", 100)),true);
        botPlayer.setName("BotJuan");
        botPlayer.setBalance(200);

        botPlayer1 = new ModerateBot(List.of(new RailwayProperty(false, "railway", 100)),false);
        botPlayer1.setName("BotJuan");
        botPlayer1.setBalance(200);

        botPlayer3 = new ModerateBot(List.of(new RailwayProperty(false, "railway", 100)),false);
        botPlayer3.setName("BotJuan");
        botPlayer3.setBalance(200);
        game.setPlayersIterator(new PlayerIterator(List.of(humanPlayer,botPlayer, botPlayer3, botPlayer1)));

        game.addEvent("Player1 hace algo");
        game.addEvent("Player2 hace algo");
        game.addEvent("Player3 hace algo");
        game.addEvent("Player4 hace algo");
        game.addEvent("Player5 hace algo");
        game.addEvent("Player6 hace algo");
        game.addEvent("Player7 hace algo");
        game.addEvent("Player8 hace algo");
        game.addEvent("Player9 hace algo");
        game.addEvent("Player10 hace algo");
        game.addEvent("Player11 hace algo");
        game.addEvent("Player12 hace algo");
        game.addEvent("Player13 hace algo");
        game.addEvent("Player14 hace algo");
        game.addEvent("Player15 hace algoasdasd asdasdasd asdasda asdasdasdasda asdasdasdasdasdasdasdasdasdad asdasdasdasdasd asdasdddddddddddaaaaaassss asdasdad asdasdasdasd asdasdasd");

    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
    @Test
    void printGameState() {


        UiService.getInstance().printGameState(game);

    }

    @Test
    void testPrintBanner() {
        UiService.getInstance().printBanner();
        assertTrue(getOutput().contains("$$"));

    }


    @Test
    void testPrintGameState1() {
        UiService.getInstance().printGameState(game);
        String output = getOutput();
        assertTrue(output.contains("Pepe"));
        assertTrue(output.contains("Player1"));

        assertTrue(output.contains("Dinero: 200"));

        assertTrue(output.contains(ANSI.RED));
        assertTrue(output.contains(ANSI.GREEN));
        assertTrue(output.contains(ANSI.PURPLE));


    }

    @Test
    void testPrintGameState2() {
    }

    @Test
    void printBannerAndOptions() {
    }

    @Test
    void printBanner() {
    }

    private String getOutput() {
        return testOut.toString();
    }
}