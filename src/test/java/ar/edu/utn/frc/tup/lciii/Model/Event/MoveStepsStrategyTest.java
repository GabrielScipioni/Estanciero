package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MoveStepsStrategyTest {
    @Test
    public void test(){
        MoveStepsStrategy strategy = new MoveStepsStrategy();
        Board board = Mockito.spy(Board.class);

        Mockito.doNothing().when(board).moveSteps(any(),any(),any());

        Game game = new Game(board, new User("test","test", "test"));

        strategy.execute(game,new HumanPlayer(new ArrayList<>(),false),new NormalSquare(), null,null);


        verify(board,times(1)).moveSteps(any(),any(),any());
    }
}
