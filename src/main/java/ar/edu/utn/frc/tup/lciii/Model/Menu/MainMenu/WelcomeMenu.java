package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;

import java.util.ArrayList;

public class WelcomeMenu extends MenuOption {

    public WelcomeMenu() {
        super(0, "", "Bienvenido, Ingrese una opción", false);
        ArrayList<Option> options = new ArrayList<Option>();
        options.add(new LoginMenu(1));
        options.add(new RegisterMenu(2));
        options.add(new ExitOption(3));
        super.setSubOptions(options);

    }

    public void execute(){
        super.drawSubOptions();
    }




}




