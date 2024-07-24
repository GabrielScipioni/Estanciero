package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Event when a player must pay to the bank based on the number of upgrades on their properties.
 * This can represent scenarios like property repairs or taxes.
 *
 * @see "Your properties need to be repaired. Pay the bank 500 for each house and 2500 for each hotel."
 * @see "For seed purchase, pay the bank 800 for each house and 4000 for each hotel."
 */
public class PayPerUpgradeStrategy implements EventStrategy {

    @Override
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType) {

        List<TerrainProperty> playerTerrains = new ArrayList<>();

        Integer houseCounter = 0;
        Integer hotelCounter = 0;

        for (Property property : player.getProperties()) {
            if(property instanceof TerrainProperty) {
                playerTerrains.add((TerrainProperty) property);
            }
        }

        for (TerrainProperty terrain : playerTerrains) {

            if (terrain.getUpgradeLevel().equals(5)) {
                hotelCounter++;
            } else if (terrain.getUpgradeLevel() <= 4 && terrain.getUpgradeLevel() > 0) {
                houseCounter++;
            }
        }

        Integer houseTax = houseCounter * amount; // $500 OR $800
        Integer hotelTax = hotelCounter * amount * 5; // $2.500 OR $4.000

        game.getBank().payToBank(game, hotelTax + houseTax, player);
    }
}