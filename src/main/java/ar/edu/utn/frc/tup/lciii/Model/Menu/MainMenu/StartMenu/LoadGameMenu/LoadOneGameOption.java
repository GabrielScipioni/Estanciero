package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.LoadGameMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

public class LoadOneGameOption extends MenuOption {
    Game game;
    public LoadOneGameOption(int optionNumber, Game game) {
        super(optionNumber, game.getDate()+ " - " + (game.isFinishGame()?"Finalizado":"Activo"), "", false);
        this.game=game;
    }
    public void execute() {
         game.resumeGame();
          }

}
