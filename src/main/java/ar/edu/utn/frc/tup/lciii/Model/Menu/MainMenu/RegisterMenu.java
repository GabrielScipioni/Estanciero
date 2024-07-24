package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.UserCRUDService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

public class RegisterMenu extends MenuOption {

    public RegisterMenu(int optionNumbre) {
        super(optionNumbre, "Registrarse", "Ingrese los datos:", false);


    }

    public void execute() {
        String name = new UserInteraction().ask("Nombre: ");
        String userName = new UserInteraction().ask("Usuario: ");
        String pass = new UserInteraction().ask("Password: ");



        User user = UserCRUDService.getInstance().create(new User(userName,pass,name));

        new UserInteraction().message("Se creó el usuario " + user.getName());

    }
}
