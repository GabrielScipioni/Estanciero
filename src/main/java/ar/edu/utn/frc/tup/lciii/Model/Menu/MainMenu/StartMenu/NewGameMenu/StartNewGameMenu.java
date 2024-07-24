package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.NewGameMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Model.Game.GameDifficulty;

import java.util.ArrayList;

public class StartNewGameMenu extends MenuOption {
    User user;
    public StartNewGameMenu(int optionNumber,User user) {
        super(optionNumber, "Nuevo juego", "Seleccione una dificultad", false);
        ArrayList<Option> options = new ArrayList<Option>();
        options.add(new NewGameDifficultyOption(1, "Fácil",user, GameDifficulty.EASY));
        options.add(new NewGameDifficultyOption(2, "Media",user, GameDifficulty.MEDIUM));
        options.add(new NewGameDifficultyOption(3, "Difícil",user, GameDifficulty.HARD));

        options.add(new ExitOption(4));
        super.setSubOptions(options);
        this.user = user;
    }
    public void execute() {
        super.drawSubOptions();
    }

}
