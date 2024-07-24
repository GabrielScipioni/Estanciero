package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import ar.edu.utn.frc.tup.lciii.Model.Property.Province;
import ar.edu.utn.frc.tup.lciii.Model.Property.RailwayProperty;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

import java.util.List;

import java.util.*;

public class ModerateBot extends BotPlayer {
    int counter = 0;

    public ModerateBot(List<Property> properties, Boolean bankrupted) {
        super(properties, bankrupted, List.of(Province.MENDOZA, Province.SANTA_FE, Province.TUCUMAN));
    }
    public ModerateBot() {
        super(new ArrayList<>(),false, List.of(Province.MENDOZA, Province.SANTA_FE, Province.TUCUMAN));
    }

    @Override
    public Boolean isPriority(Property property) {
        if (property instanceof RailwayProperty) {
            return true;
        }
        return super.isPriorityProperty(property);
    }

    /**
     * Provincias prioritarias:
     * Formosa, Río Negro y Salta.
     * <p>
     * Prioriza: propiedades de bajo costo.
     * Comprará fuera de las provincias de preferencia, 1 de cada
     * 5 propiedades que compre.
     */
    @Override
    public void buyProperty(Game game) {
        // darle logica para comprar propiedades, respetando los perfiles
        // Las condiciones para que la compre son
        // cayo en una casilla de propiedad
        // no tiene la propiedad
        // Tiene la plata
        // isPriority O contador 1 de cada 3
        List<Square> squares = game.getBoard().getSquares();
        Player player = this;
        for (Square square : squares) {
            if (square.isPlayerInSquare(player) && square instanceof PropertySquare) {
                Property property = ((PropertySquare) square).getProperty();
                if (property.getOwner(game) == null) { //No tiene dueño
                    if (isPriority(property) && getBalance() > property.getPrice()) {
                        game.getBank().buyProperty(player, property);
                        break;
                    } else if (!isPriority(property) && counter == 3
                            && getBalance() > property.getPrice()) {
                        game.getBank().buyProperty(player, property);
                        counter = 0;
                        break;
                    }
                }
            }
        }
        if (counter != 3) {
            counter++;
        }
    }

//    @Override
//    public void tradeRequest() {
//        //Si llegamos con el tiempo:
//        //TODO:realizar la logica de Tradeo
//    }
    @JsonIgnore
    @Override
    public Integer getPriceGap() {
        return 50;
    }


}
