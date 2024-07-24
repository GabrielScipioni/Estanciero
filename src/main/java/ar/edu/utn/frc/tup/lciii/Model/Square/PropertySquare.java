package ar.edu.utn.frc.tup.lciii.Model.Square;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

/**
 *  modela un casillero de propiedad
 */
@Getter
public class PropertySquare extends Square {
    private Property property;

    public PropertySquare(Property property) {
        this.property = property;
    }
    public PropertySquare(Long id, Property property) {
        super(id);
        this.property = property;
    }
    public PropertySquare() {

    }

    public void execute (Game game, Player player) {
        Integer rent = property.getRentValue(game, player);

        if (rent != 0){
            game.getBank().makePlayerPayToOtherPlayer(game, property.getOwner(game), rent, player);
        }

    }

    @Override
    public void executeWhenPassing(Game game, Player player) {
        return;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return this.property.getName();
    }



}
