package ar.edu.utn.frc.tup.lciii.Model.Menu.Option;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;

import ar.edu.utn.frc.tup.lciii.Services.ui.UiService;
import lombok.Getter;

@Getter
public abstract class GameOption extends Option {
    private Game game;

    public GameOption(int option, String optionName, String title, boolean isExitOption, Game game) {
        super(option, optionName, title, isExitOption);
        this.game = game;
    }


    @Override
    public void printHeader() {
        UiService.getInstance().printGameState(game);
    }

    public void updateSubOptions(){}
}
