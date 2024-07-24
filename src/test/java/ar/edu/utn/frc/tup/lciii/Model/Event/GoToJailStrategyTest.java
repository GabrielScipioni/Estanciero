package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GoToJailStrategyTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void executeTest() {
        GoToJailStrategy strategy = new GoToJailStrategy();
       Board board = Mockito.spy(Board.class);

       Mockito.doNothing().when(board).sendToJail(any(),any());

       Game game = new Game(board, new User("test","test", "test"));

       strategy.execute(game,new HumanPlayer(new ArrayList<>(),false),null, null,null);


       verify(board,times(1)).sendToJail(any(),any());

    }
}