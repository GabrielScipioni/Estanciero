package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.GameOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

import java.util.ArrayList;

class ManagePropertiesOption extends GameOption {
    private final Player player ;

    public ManagePropertiesOption(Integer optionNumber, Game game, Player player) {
        super(optionNumber, "Gestionar propiedades", "Seleccione una propiedad a gestionar", false, game);
        this.player=player;


    }
    @Override
    public void execute() {

        if(player.getProperties().isEmpty()){
            new UserInteraction().message("No tiene propiedades para gestionar");

            return;
        }
        updateSubOptions();

        super.drawSubOptions();

    }
    public void updateSubOptions(){
        ArrayList<Option> subOptions = new ArrayList<>();
        for (int i = 0; i<player.getProperties().size(); i++){
            subOptions.add(new PropertyOption(i+1,super.getGame(),player, player.getProperties().get(i)));
        }
        subOptions.add(new ExitOption(player.getProperties().size()+1));
        super.setSubOptions(subOptions);
    }
}

class PropertyOption extends GameOption {
    private final Player player ;
    private final Property property;

    public PropertyOption(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, property.getName(), property.getName(), false, game);
        this.player=player;
        this.property = property;


        //Mejorar
        //Vender Mejoras
        //Hipotecar
        //Levantar hipoteca
        //Vender al banco
        //Iniciar subasta
        //Volver
        ArrayList<Option> subOptions = new ArrayList<>();
        subOptions.add(new UpgradeProperty(1, game, player, property));
        subOptions.add((new SellPropertyUpgrades(2, game, player, property)));
        subOptions.add(new MortgageProperty(3, game, player, property));
        subOptions.add(new PayOffMortgage(4, game, player, property));
        subOptions.add(new SellProperty(5, game, player, property));
        subOptions.add(new StartAuction(6, game, player, property));
        subOptions.add(new ExitOption(7));
        updateTitle();

        super.setSubOptions(subOptions);
    }
    @Override
    public void execute() {
        updateTitle();
        super.drawSubOptions();


    }

    private void updateTitle() {
        String message = property.getName()+ " | Precio: "+property.getPrice()+ " | Hipotecada: "+ property.isMortgaged();
        if(property instanceof TerrainProperty){
            message+= (" | UpgradeLevel: "+ ((TerrainProperty)property).getUpgradeLevel());
        }

        super.setOptionName(message);
        super.setTitle(message);
    }
}

class UpgradeProperty extends GameOption{
    private final Player player ;
    private final Property property;

    public UpgradeProperty(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, "Mejorar", "", true, game);
        this.player = player;
        this.property = property;
    }

    @Override
    public void execute() {
        if (property instanceof TerrainProperty){
            super.getGame().getBank().upgradeTerrain(super.getGame(), player, (TerrainProperty) property);
        }else{
            new UserInteraction().message("No es posible comprar mejoras para ésta propiedad");
            new UserInteraction().sleep(500);
        }


    }
}

class SellPropertyUpgrades extends GameOption{
    private final Player player ;
    private final Property property;

    public SellPropertyUpgrades(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, "Vender mejoras", "", true, game);
        this.player = player;
        this.property = property;
    }

    @Override
    public void execute() {
        if (property instanceof TerrainProperty){
            super.getGame().getBank().sellUpgrades((TerrainProperty) property, player);
        }else{
            new UserInteraction().message("No es posible vender mejoras para ésta propiedad");
            new UserInteraction().sleep(500);
        }


    }
}

class MortgageProperty extends GameOption{
    private final Player player ;
    private final Property property;

    public MortgageProperty(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, "Hipotecar", "", true, game);
        this.player = player;
        this.property = property;
    }

    @Override
    public void execute() {
        super.getGame().getBank().mortgageProperty(property, player);
    }
}

class PayOffMortgage extends GameOption{
    private final Player player ;
    private final Property property;

    public PayOffMortgage(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, "Pagar hipoteca", "", true, game);
        this.player = player;
        this.property = property;
    }

    @Override
    public void execute() {
        super.getGame().getBank().payOffMortgage(property, player);
    }
}

class SellProperty extends GameOption{
    private final Player player ;
    private final Property property;

    public SellProperty(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, "Vender al banco", "", true, game);
        this.player = player;
        this.property = property;
    }

    @Override
    public void execute() {
        super.getGame().getBank().sellPropertyToBank(property, player);
    }
}

class StartAuction extends GameOption{
    private Player player ;
    private Property property;

    public StartAuction(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, "Subastar", "", true, game);
        this.player = player;
        this.property = property;
    }

    @Override
    public void execute() {
        super.getGame().getBank().auctionProperty(getGame(), property, super.getGame().getPlayersIterator().getPlayersListTest());
    }
}