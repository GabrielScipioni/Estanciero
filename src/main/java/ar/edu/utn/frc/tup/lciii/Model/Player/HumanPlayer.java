package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu.JailPlayerGameMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu.MakeMoneyOfPropertiesMenu;

import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu.PlayerPlayMenu;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Services.ui.UiService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
public class HumanPlayer extends Player {
    private UserInteraction ui = new UserInteraction();
    public HumanPlayer(List<Property> properties, Boolean bankrupted) {
        super(properties, bankrupted);
    }
    public HumanPlayer(List<Property> properties, Boolean bankrupted, UserInteraction ui) {
        super(properties, bankrupted);
        this.ui = ui;
    }


    @Override
    public Integer makeBid(Property auctionProperty, Integer currentHighestBid) {

        ui.message("Se subasta la propiedad "+ auctionProperty.getName()+" - Precio Mercado:" + auctionProperty.getPrice()+ " - Hipotecada:"+ auctionProperty.isMortgaged());

        int amount = ui.askInt("Ingrese su oferta");
        return amount;
    }

    @Override
    public void play(Game game){
        if(game.getBoard().isPlayerInJail(this)){
            new JailPlayerGameMenu(game,this).execute();
        }
        new PlayerPlayMenu(game,this).execute();

    }

    @Override
    public void findWayToMakeMoney(Game game, Integer moneyToPay) {
        new MakeMoneyOfPropertiesMenu(game,this,moneyToPay).execute();
    }


    @Override
    public boolean sellPropertyToPlayerInner(Game game, Property property, Player buyer, Integer price) {
        UiService.getInstance().printGameState(game);
        return new UserInteraction().askYesNo("El jugador "+ buyer.getName() + " le ofrece comprar la propiedad " + property.getName()+ " por "+ price+"\n ¿Acepta?");
    }

}
