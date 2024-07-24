package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import ar.edu.utn.frc.tup.lciii.Model.Property.Province;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

public class ConservativeBot extends BotPlayer {
    private Integer priceGap;
    public ConservativeBot(List<Property> properties, Boolean bankrupted) {
        super(properties, bankrupted, List.of(Province.FORMOSA, Province.RIO_NEGRO, Province.SALTA));
    }
    public ConservativeBot() {
        super(new ArrayList<>(), false, List.of(Province.FORMOSA, Province.RIO_NEGRO, Province.SALTA));
    }


    @Override
    public Boolean isPriority(Property property) {
        return super.isPriorityProperty(property);
    }

    private int counter =0;
    /**
     * Provincias prioritarias: Mendoza, Santa
     * Fe y Tucuman
     * buscará comprar la serie de Ferrocarriles
     * comprará fuera de las provincias de preferencia, 1 de cada
     * 3 propiedades que compre
     */
    @Override
    public void buyProperty(Game game) {
        //TODO darle logica para comprar propiedades, respetando los perfiles
        // Las condiciones para que la compre son
        // cayo en una casilla de propiedad
        // no tiene la propiedad
        // Tiene la plata
        // isPriority O contador 1 de cada 5
        List<Square> squares = game.getBoard().getSquares();
        Player player = this;
        for (Square square : squares) {
            if (square.isPlayerInSquare(player) && square instanceof PropertySquare) {
                Property property = ((PropertySquare) square).getProperty();
                if (property.getOwner(game) == null ) {
                    if (isPriority(property) && getBalance() > property.getPrice()) {
                        game.getBank().buyProperty(player, property);
                        break;
                    } else if (counter == 5 && getBalance() > property.getPrice()) {
                        game.getBank().buyProperty(player, property);
                        counter = 0;
                        break;
                    }
                }
            }
        }
        if (counter != 5) {
            counter++;
        }

    }


//    @Override
//    public void tradeRequest() {
//        //Si llegamos con el tiempo:
//        //TODO:realizar la logica de Tradeo
//

    /**
     * no encuentro algo que muestre una preferencia de ventas
     * se seguira la de su perfil de compra
     * vendera propiedades que no prefiera o en defecto que solo tenga propiedades
     * preciadas, vendera la de menor costo que le permita costear lo que tenga que pagar
     *
     * @see #ConservativeBot#sellProperty
     */

    @JsonIgnore
    @Override
    public Integer getPriceGap() {
        return 30;
    }


}
