package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;

public class ExitOption extends MenuOption {
    public ExitOption(int option) {
        super(option, "Salir", "", true);
    }

    @Override
    public void execute() {

    }
}
