package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu;

import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.GameOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.EndGameOptionOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

import java.util.ArrayList;

public class JailPlayerGameMenu extends GameOption {


    public JailPlayerGameMenu(Game game, Player player) {
        super(0, "Jail", "Se encuentra en prisión. Seleccione una opcion", false, game);
        ArrayList<Option> subOptions = new ArrayList<>();
        subOptions.add(new PayFine(1, game, player));
        subOptions.add((new UseCard(2, game, player)));
        subOptions.add(new ThrowDice(3, game, player));
        subOptions.add(new EndGameOptionOption(4,game));
        subOptions.add(new ExitOption(5));
        super.setSubOptions(subOptions);

    }


    public void execute() {
        super.drawSubOptions();
    }

    private static class PayFine extends GameOption {
        Player player;

        public PayFine(Integer optionNumber, Game game, Player player) {
            super(optionNumber, "Pagar multa de 1000", "", true, game);
            this.player=player;
        }
        @Override
        public void execute()  {

            super.getGame().addEvent(player.getName() + " eligió: pagar multa");
            super.getGame().getBank().chargePlayer(player,1000);
            if(!player.getBankrupted()){
                super.getGame().getDice().throwDice(super.getGame(),player);
                Integer amount = super.getGame().getDice().getTotalValue();
                super.getGame().getBoard().moveSteps(super.getGame(), player, amount);
            }

        }
    }

    private static class UseCard extends GameOption {
        private final Player player ;

        public UseCard(Integer optionNumber, Game game, Player player) {
            super(optionNumber, "Usar carta", "Seleccione una carta para usar", true, game);
            this.player=player;


        }
        @Override
        public void execute() {

            if(player.getCards().isEmpty()){
                new UserInteraction().message("No tiene cartas para elegir");
                return;
            }
            updateSubOptions();
            super.getGame().addEvent(player.getName() + " eligió: usar carta");
           super.drawSubOptions();

        }
        @Override
        public void updateSubOptions(){
            ArrayList<Option> subOptions = new ArrayList<>();
            for (int i = 0; i<player.getCards().size(); i++){
                subOptions.add(new CardOption(i+1,super.getGame(),player, player.getCards().get(i)));
            }
            subOptions.add(new ExitOption(player.getCards().size()+1));
            super.setSubOptions(subOptions);
        }
    }

    private static class CardOption extends GameOption {
        private final Player player ;
        private final Card card;

        public CardOption(Integer optionNumber, Game game, Player player, Card card) {
            super(optionNumber, card.getMessage(), "", true, game);
            this.player=player;
            this.card = card;
        }
        @Override
        public void execute() {
            super.getGame().addEvent(player.getName() + " eligió usará la carta"+ card.getMessage());
            player.useCard(super.getGame(), card);

        }
    }

    public static class ThrowDice extends GameOption {
        Player player;
        public ThrowDice(Integer optionNumber, Game game, Player player) {
            super(optionNumber, "Tirar dado", "", true, game);
            this.player = player;
        }
        @Override
        public void execute() {
            super.getGame().getDice().throwDice(super.getGame(),player);
            if(super.getGame().getDice().checkDouble()){
                super.getGame().addEvent(player.getName()+ " sale de la carcel con doble!");
                super.getGame().getBoard().moveSteps(super.getGame(),player, super.getGame().getDice().getTotalValue());
            }

        }
    }



}

