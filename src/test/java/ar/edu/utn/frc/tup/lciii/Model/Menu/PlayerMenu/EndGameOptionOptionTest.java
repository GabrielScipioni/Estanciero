package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.mockito.Mockito.*;

class EndGameOptionOptionTest {

    @Test
    void execute() {
        Game game = mock(Game.class);
        ByteArrayInputStream in = new ByteArrayInputStream("y\n".getBytes());
        System.setIn(in);

        new EndGameOptionOption(1,game).execute();

        verify(game).finishGame();

         in = new ByteArrayInputStream("n\n".getBytes());
        System.setIn(in);
        verifyNoMoreInteractions(game);
        new EndGameOptionOption(1,game).execute();
        verifyNoMoreInteractions(game);


    }
}