package ar.edu.utn.frc.tup.lciii.Model.Property;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import lombok.NoArgsConstructor;

/**
 * modela una propiedad de tipo compania
 */
@NoArgsConstructor
public class CompanyProperty extends Property {


    public CompanyProperty(Boolean mortgaged, String name, Integer price) {
        super(mortgaged, name, price);
    }

    @Override
    public Integer getRentValue(Game game, Player player) {
        //si no se puede cobrar, se cobra 0
        if (!super.canChargeRent(game,player)){
            return  0;
        }
        Player owner = super.getOwner(game);

        int multiplier = 0; //en este punto el owner tiene this en sus properties, por lo que se va a sumar al menos 1, asi que arrancamos en 0
        for(Property property : owner.getProperties()){

             if(property instanceof CompanyProperty
                && !property.isMortgaged()){
                    multiplier++;
            }
        }
        game.getDice().throwDice();
        return game.getDice().getTotalValue()*multiplier*100;
    }

}
