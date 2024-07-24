package ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.StartMenu.NewGameMenu;


import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Game.GameDifficulty;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.MenuOption;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.GameService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

public class NewGameDifficultyOption extends MenuOption {
    User user;
    GameDifficulty difficulty;
    public NewGameDifficultyOption(int optionNumber, String name, User user, GameDifficulty difficulty) {
        super(optionNumber, name, "", false);
        this.user = user;
        this.difficulty = difficulty;
    }
    public void execute() {
        //TODO: gameService.createGameFromDificulty(user, dificulty)
        //TODO: game.resume()

        Game game = GameService.getInstance().gameFactory(difficulty,user);

        GameService.getInstance().create(game,user).resumeGame();


        new UserInteraction().message("Se creo un juego"+difficulty);

    }

}
