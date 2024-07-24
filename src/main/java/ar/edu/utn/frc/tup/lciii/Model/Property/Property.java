package ar.edu.utn.frc.tup.lciii.Model.Property;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;
import java.util.Objects;

/**
 * modela cualquier tipo de propiedad que integrara cada casillero
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompanyProperty.class, name = "CompanyProperty"),
        @JsonSubTypes.Type(value = RailwayProperty.class, name = "RailwayProperty"),
        @JsonSubTypes.Type(value = TerrainProperty.class, name = "TerrainProperty")
})
public abstract class Property {
    private Boolean mortgaged;
    private String name;
    private Integer price;

    public Boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(Boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Property() {}

    public Property(Boolean mortgaged, String name, Integer price) {
        this.mortgaged = mortgaged;
        this.name = name;
        this.price = price;
    }

    @JsonIgnore
    public Integer getMorgageValue(){ //este es fijo, 1/2 del property price
       return this.price/2;
    }
    public abstract Integer getRentValue(Game game, Player player);//lo cambie a abstract

    /**
     * Indicates if rent can be charged to specific player
     * This could only happen if it's not mortgaged, has owner, and it's different to the player to charge
     *
     * @param game Game context
     * @param player player to charge rent
     *
     * @return boolean if rent can be charged to player
     */
    public boolean canChargeRent(Game game, Player player){

        if(this.isMortgaged()){ //si está hipotecada, no se cobra
            return false;
        }

        Player owner = this.getOwner(game);
        if(Objects.isNull(owner)){ //si la propiedad no tiene dueño, no se cobra
            return false;
        }
        if(Objects.equals(player,owner)){ //si el dueño es el jugador, no se cobra
            return false;
        }

        return true;
    }

    public Player getOwner(Game game){
        List<Player> players = game.getPlayersIterator().getPlayersListTest();

        for (Player player :players){
            if(player.getProperties().contains(this)){
                return player;
            }
        }
        return null;
    }



}
