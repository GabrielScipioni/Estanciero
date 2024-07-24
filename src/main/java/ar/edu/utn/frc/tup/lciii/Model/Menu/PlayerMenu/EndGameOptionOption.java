package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.GameOption;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

public class EndGameOptionOption extends GameOption {

    public EndGameOptionOption(int option, Game game) {
        super(option, "Finalizar partida", "Seguro desea finalizar partida?", false, game);
    }
    @Override
    public void execute() {
        boolean finishGame = new UserInteraction().askYesNo("Seguro que desea finalizar la partida?");
        if(finishGame){
            super.getGame().finishGame();
            super.setExitOption(true);
        }

    }

}
