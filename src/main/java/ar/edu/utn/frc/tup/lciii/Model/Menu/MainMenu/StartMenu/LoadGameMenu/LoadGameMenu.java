package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.LoadGameMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.GameService;

import java.util.ArrayList;
import java.util.List;

public class LoadGameMenu extends MenuOption {
    User user;
    public LoadGameMenu(int optionNumber,User user) {
        super(optionNumber, "Cargar juego", "Seleccione un juego", false);

        this.user = user;

    }
    public void execute() {
        updateSubOptions();
        super.drawSubOptions();
    }
    @Override
    public void updateSubOptions(){
        //logica para buscar los game
        List<Game> games = GameService.getInstance().getAllFromUser(user);

        ArrayList<Option> subOptions = new ArrayList<>();


        for (int i = 0; i<games.size(); i++){
            subOptions.add(new LoadOneGameOption(i+1,games.get(i)));
        }
        subOptions.add(new ExitOption(games.size()+1));
        super.setSubOptions(subOptions);
    }

}
