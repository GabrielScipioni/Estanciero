package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.LoadGameMenu;

import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LoadGameMenuTest {

    @Test
    public void LoadGameMenuConstructorTest() {
        User user = mock(User.class);
        LoadGameMenu loadGame = new LoadGameMenu(1,user);
    }
}