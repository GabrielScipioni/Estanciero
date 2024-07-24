package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.StartMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.UserCRUDService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

public class LoginMenu extends MenuOption {

    public LoginMenu(int optionNumber) {
        super(optionNumber, "Login", "Ingrese los datos:", false);
    }

    UserInteraction ui = new UserInteraction();

    public void execute() {
        String userName = ui.ask("Usuario: ");
        String pass = ui.ask("Password: ");

        User user = UserCRUDService.getInstance().getByUserNameAndPass(userName,pass);
        if (user!=null) {
            ui.message("Login exitoso! Bienvenido/a " +user.getName());
            ui.sleep(500);
            new StartMenu(user).execute();

        } else {
            ui.message("Usuario y/o contraseñas incorrectas");
            ui.sleep(500);
        }

    }

    // Getter y Setter para userInteraction
    public UserInteraction getUserInteraction() {
        return ui;
    }

    public void setUserInteraction(UserInteraction userInteraction) {
        this.ui = userInteraction;
    }


}
