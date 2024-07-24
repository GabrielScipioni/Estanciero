package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.LoadGameMenu.LoadGameMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.NewGameMenu.StartNewGameMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.User;

import java.util.ArrayList;

public class StartMenu extends MenuOption {
    User user;
    public StartMenu(User user) {
        super(0, "", "Seleccione una opción:", false);
        ArrayList<Option> options = new ArrayList<Option>();
        options.add(new StartNewGameMenu(1, user));
        options.add(new LoadGameMenu(2, user));
        options.add(new Rules(3));
        options.add(new ExitOption(4));
        super.setSubOptions(options);

        this.user = user;

    }
    public void execute() {
        super.drawSubOptions();

    }


}
