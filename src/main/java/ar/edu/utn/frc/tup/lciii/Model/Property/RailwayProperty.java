package ar.edu.utn.frc.tup.lciii.Model.Property;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;

/**
 * modela una propiedad de tipo compania
 */
public class RailwayProperty extends Property {

    public RailwayProperty() {
        super();
    }

    public RailwayProperty(boolean mortgaged, String name, Integer price) {
        super(mortgaged, name, price);

    }

//    Teniendo 1 Ferrocarril.......500
//    Teniendo 2 Ferrocarril.......1000
//    Teniendo 3 Ferrocarril.......2000
//    Teniendo 4 Ferrocarril.......4000
    @Override
    public Integer getRentValue(Game game, Player player) {
        //si no se puede cobrar, se cobra 0
        if (!super.canChargeRent(game,player)){
            return  0;
        }
        Player owner = super.getOwner(game);

        int multiplier = 0; //en este punto el owner tiene this en sus properties, por lo que se va a sumar al menos 1, asi que arrancamos en 0
        for(Property property : owner.getProperties()){

             if(property instanceof RailwayProperty
                && !property.isMortgaged()){
                    multiplier++;
            }
        }
        return (int) (500 * Math.pow(2,(multiplier-1)));
    }

}
