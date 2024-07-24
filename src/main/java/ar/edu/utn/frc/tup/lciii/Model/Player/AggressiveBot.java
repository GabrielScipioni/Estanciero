package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class AggressiveBot extends BotPlayer {


    @JsonIgnore
    @Override
    public Integer getPriceGap() {
        return 100;
    }

    public AggressiveBot(List<Property> properties, Boolean bankrupted) {
        super(properties, bankrupted, List.of(Province.TUCUMAN, Province.CORDOBA, Province.BUENOS_AIRES));
    }
    public AggressiveBot() {
        super(new ArrayList<>(), false, List.of(Province.TUCUMAN, Province.CORDOBA, Province.BUENOS_AIRES));
    }

    @Override
    public Boolean isPriority(Property property) {
        if (property instanceof RailwayProperty || property instanceof CompanyProperty) {
            return true;
        }
        return super.isPriorityProperty(property);
    }

    /**
     * Provincias prioritarias: Tucuman, Córdoba y Buenos Aires.
     * Buscará comprar en Ferrocarriles y Companias.
     * No comprará fuera de las provincias preferidas a menos que:
     * - Ya se haya vendido al menos una propiedad en todas sus zonas de preferencia a otros jugadores, o
     * - El jugador haya completado sus zonas de preferencia.
     * En ese caso, comprará tantas propiedades como pueda.
     */
    @Override
    public void buyProperty(Game game)
    {
        //TO DO
        // Las condiciones para que la compre son
        // cayo en una casilla de propiedad
        // no tiene la propiedad
        // Tiene la plata
        // isPriority O:
        // Un jugador tiene almenos una de sus provincias (cualquier zona) O el agressive completó sus zonas
        List<Square> squares = game.getBoard().getSquares();
        Player player = this;

        for (Square square : squares)
        { //Recorremos todos los squares
            if (square.isPlayerInSquare(player) && square instanceof PropertySquare)
            {//El player esta en una propiedad
                Property property = ((PropertySquare) square).getProperty();
                List<Property> properties = this.getProperties();
                List<Province> repeatedProvinces =
                        findCompletedProvinces(properties);

                if (property.getOwner(game) == null
                        && getBalance() >= property.getPrice())
                {
                    //La propiedad tiene no dueño y se puede pagar

                    if (isPriority(property)) { //Es prioridad
                        game.getBank().buyProperty(player, property);
                        break;
                    }
                    else if(checkOwnershipOfPriorityProperty(game) || !repeatedProvinces.isEmpty()) {
                        //Si NO es prioridad, la comprara acorde a estas condiciones:
                        //Chequea que ningun player tenga una zona de su prioridad
                        //O que este bot tenga una provinca completa
                        game.getBank().buyProperty(player, property);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Integer makeBid(Property auctionProperty, Integer currentHighestBid) {
        if(isPriority(auctionProperty)) //check si es prioridad  (no chequea saldo, va a mentir)
        {
            Random random = new Random();
            Integer chance = random.nextInt(10);

            if(chance < 1) { //10% probabilidad de mentir
                return currentHighestBid + 2000;
            }
            else {
                return auctionProperty.getPrice() + 1000;
            }
        }
        return 0; //no ofrece si no le interesa
    }

    //Chequea que los players no tengan una property preferencia de este bot
    public boolean checkOwnershipOfPriorityProperty(Game game) {
        if (game !=null)
        {
            List<Square> squares = game.getBoard().getSquares();
            if (!squares.isEmpty())
            {
                for (Square square : squares) {
                    if (square instanceof PropertySquare) {
                        if ((((PropertySquare) square).getProperty().getOwner(game) != null
                                || (((PropertySquare) square).getProperty().getOwner(game) != this))
                                && isPriority(((PropertySquare) square).getProperty()))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}


