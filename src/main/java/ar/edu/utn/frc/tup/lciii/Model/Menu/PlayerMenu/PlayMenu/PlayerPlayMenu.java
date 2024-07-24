package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.GameOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.EndGameOptionOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerPlayMenu extends GameOption {
    //comprar donde estar parado
    //propiedades
        //Mejorar
        //Vender Mejoras
        //Hipotecar
        //Levantar hipoteca
        //Vender al banco
        //Iniciar subasta
        //Volver
    //Comprar prop de otros players


    public PlayerPlayMenu(Game game, Player player) {

        super(0, "", "Seleccione una opcion", false, game);

        ArrayList<Option> subOptions = new ArrayList<>();
        subOptions.add(new BuyPropertyOption(1, game, player));
        subOptions.add((new ManagePropertiesOption(2, game, player)));
        subOptions.add(new BuyPlayerPropertiesOption(3, game, player));
        subOptions.add(new EndGameOptionOption(4,game));
        subOptions.add(new GameOption(5, "Terminar turno", "", true, null) {
            @Override
            public void execute() {

            }
        });

        super.setSubOptions(subOptions);
    }


    public void execute() {
        super.drawSubOptions();
    }

    private static class BuyPropertyOption extends GameOption {
        Player player;
        Square squareStanding;
        Property standingProperty;
        public BuyPropertyOption(Integer optionNumber, Game game, Player player) {
            super(optionNumber, "Comprar propiedad", "", false, game);

            this.player=player;

            squareStanding = super.getGame().getBoard().getSquarePlayerStanding(player);

            if(squareStanding instanceof PropertySquare) {
                standingProperty =  ((PropertySquare) squareStanding).getProperty();
            }else{
                standingProperty = null;
            }
            if (standingProperty!=null){
                super.setOptionName("Comprar propiedad + "+standingProperty.getName()+ "-Precio: "+standingProperty.getPrice());
            }else{
                super.setOptionName("Comprar propiedad: NO DISPONIBLE");
            }
        }
        @Override
        public void execute()  {

            if(squareStanding!=null && standingProperty!=null && standingProperty.getOwner(super.getGame())==null){

                if(standingProperty.getPrice()<= player.getBalance()){
                    super.getGame().getBank().buyProperty(player,standingProperty);

                }else{
                    new UserInteraction().message("No tiene suficiente dinero para comprar la propiedad");
                    new UserInteraction().sleep(500);

                }


            }else{
                new UserInteraction().message("No hay / No se puede comprar la propiedad");
                new UserInteraction().sleep(500);
            }



        }
    }
    private class BuyPlayerPropertiesOption extends GameOption {
        Player player;
        public BuyPlayerPropertiesOption(Integer optionNumber, Game game, Player player) {
            super(optionNumber, "Comprar propiedades de jugadores", "", false, game);
            this.player = player;
        }
        @Override
        public void execute()  {
            refreshPropertiesOptiones();
            super.drawSubOptions();
        }

        private void refreshPropertiesOptiones() {
            ArrayList<Property> otherPlayersOwnedProperties = new ArrayList<>();
            for(Player p:super.getGame().getPlayersIterator().getPlayersListTest()){
                if(!Objects.equals(p,player)){
                    otherPlayersOwnedProperties.addAll(p.getProperties());
                }
            }

            ArrayList<Option> subOptions = new ArrayList<>();
            for (int i = 0; i<otherPlayersOwnedProperties.size(); i++){
                subOptions.add(new BuyOtherPropertyOption(i+1,super.getGame(),player, otherPlayersOwnedProperties.get(i)));
            }
            subOptions.add(new ExitOption(otherPlayersOwnedProperties.size()+1));
            super.setSubOptions(subOptions);
        }

    }

    private class BuyOtherPropertyOption extends GameOption {
        private final Player player ;
        private final Property property;

        public BuyOtherPropertyOption(Integer optionNumber, Game game, Player player, Property property) {
            super(optionNumber, property.getName(), property.getName(), false, game);
            this.player=player;
            this.property = property;

        }
        @Override
        public void execute() {
            //Player owner = property.getOwner(super.getGame());
            //TODO: Hacer el uso correcto de sell property
            //owner.sellProperty();
            //new UserInteraction().message("Se quiso comprar una casa");

        }
    }



}

