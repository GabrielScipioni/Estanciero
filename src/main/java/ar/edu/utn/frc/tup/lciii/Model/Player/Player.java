package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a cualquier tipo Jugador, de esta clase se heredaran comportamientos y propiedades que tendran cualquier jugador
 package ar.edu.utn.frc.tup.lciii.Model.PlayerEntity;

 import ar.edu.utn.frc.tup.lciii.Model.CardEntity.CardEntity;
 import ar.edu.utn.frc.tup.lciii.Model.Property.Property;

 import java.util.ArrayList;
 import java.util.List;

 /**
 * Clase que representa a cualquier tipo Jugador, de esta clase se heredaran comportamientos y propiedades que tendran cualquier jugador
 */
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HumanPlayer.class, name = "HumanPlayer"),
        @JsonSubTypes.Type(value = BotPlayer.class, name = "BotPlayer")
})
public abstract class Player implements Serializable {
    private Integer id;
    private List<Property> properties = new ArrayList<>();
    private String name;
    private Integer balance = 35000;
    private Boolean bankrupted = false; //True: entro en banca rota, False: no esta en banca rota

    private List<Card> cards =new ArrayList<>() ;

    public Player() {

    }
    public Player(List<Property> properties, boolean bankrupted) {
        this.properties = properties;
        this.bankrupted = bankrupted;
    }



    //METHODS

    /**
     * Makes a bid for an auction.
     *
     * @param auctionProperty the property being actioned
     * @param currentHighestBid the current highest bid in the auction
     * @return the bid amount or 0 if the player does not wish to bid
     */
    public abstract Integer makeBid(Property auctionProperty, Integer currentHighestBid);
    //AGUS: ESTE ES EL METODO PARA HACER UNA APUESTA EN UNA SUBASTA, por el momento los bots veran q hacen y el player tamb
    public boolean ownsProperty(Property property) {
        return properties.contains(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void useCard(Game game,Card card){
        if(!cards.contains(card)){
            System.out.println("Player no tiene la carta");
        }
        cards.remove(card);
        game.getDeck().returnCardAndExecute(game, this,card);
    }



    public abstract void play(Game game);

    public abstract void findWayToMakeMoney(Game game, Integer moneyToPay); //Necesitamos esto para saber
    //Cuanto tiene que generar el bot con el metodo, asi se detiene al generar
    //dicha cantidad


    /*
    public abstract boolean sellPropertyToPlayer(Game game, Property property,
                                                 Player buyer, Integer price);

    public boolean sellPropertyToPlayer2(Game game, Property property,
                                     Player buyer, Integer price)
    {
        if (properties.contains(property))
        {
            return sellPropertyToPlayer(game,property,buyer,price);
        }
        return false;
    }
    */

    public abstract boolean sellPropertyToPlayerInner(Game game, Property property, Player buyer, Integer price);

    public boolean sellPropertyToPlayer(Game game, Property property, Player buyer, Integer price) {
        if (properties.contains(property) && sellPropertyToPlayerInner(game, property,buyer,price)) {
            return game.getBank().transferPropertyWithPayment(game, buyer,price,property, this);
        }
        return false;
    }

}