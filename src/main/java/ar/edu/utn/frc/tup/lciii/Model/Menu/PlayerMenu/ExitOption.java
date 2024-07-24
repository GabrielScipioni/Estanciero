package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.GameOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;

public class ExitOption extends GameOption {
    public ExitOption(int option) {
        super(option, "Salir", "", true, null);
    }
    @Override
    public void execute() {
        return;
    }

}
