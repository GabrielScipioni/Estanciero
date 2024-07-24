package ar.edu.utn.frc.tup.lciii.Services;


import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.Province;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

    UserInteraction ui = new UserInteraction();

    Integer houses = 44;

    public Bank() {}


    //TODO:(HIPOTECA) agregar que el valor de la prop no tenga un upgrade mayor a 0 (cuando sea provincia)

    /**
     * Transfers an amount of money from one player to another.
     * If the payer does not have enough money, they attempt to find a way to make money.
     * If the payer still cannot pay, they transfer their remaining balance to the beneficiary
     * and are marked as bankrupted, Ending their game.
     *
     * @param payer the player who pays
     * @param beneficiary the player who will receive the money
     * @param amount the money to transfer
     */
    public void makePlayerPayToOtherPlayer(Game game, Player beneficiary, Integer amount, Player payer) {
        game.addEvent(payer.getName()+ " debe pagar "+ amount+ " a "+ beneficiary.getName());
        if (chargePlayer(payer, amount)) {
            payPlayer(beneficiary, amount);

        } else {
            game.addEvent(payer.getName()+ " no tiene suficiente dinero!");
            payer.findWayToMakeMoney(game, amount);
            if (!payer.getBankrupted() && chargePlayer(payer, amount)) {
                payPlayer(beneficiary, amount);
            } else {
                // Route where player cant pay and bankrupts,
                // giving away his remaining cash and calling handleBankruptcy()
                payPlayer(beneficiary, payer.getBalance());
                payer.setBalance(0);
                payer.setBankrupted(true);
                game.addEvent(payer.getName() + " se declara en banca rota. Game over!");
                handleBankruptcy(payer);
            }
        }
    }

    /**
     * Transfers a property from one player to another.
     * Checks beforehand if the player owns the property he is trying to exchange.
     *
     * @param owner player transferring the property
     * @param buyer player receiving the property
     * @param property the property to be transferred
     */
    public void transferProperty(Player owner, Player buyer, Property property) {
        if (owner.ownsProperty(property)) {
            owner.removeProperty(property);
            buyer.addProperty(property);
        } else {
            ui.message("Cant transfer a property that you dont own!");
        }
    }

    /**
     * Transfers a property from one player to another with a sum of money
     * Checks beforehand if the player owns the property he is trying to exchange.
     *
     * @param game
     * @param buyer    player receiving the property
     * @param money    owners extra paying amount for the buyer
     * @param property the property to be transferred
     * @param owner    player transferring the property and paying
     */

    // todo: En findWayToMakeMoney, necesitamos que este metodo que se usa en
    //  el metodo de auctionProperty, devuelva un integer con el resultado final
    //  del auction, el cual por lo visto se hace toda la implementacion
    //  y finalizacion del mismo en BankService


    public Boolean transferPropertyWithPayment(Game game, Player buyer, Integer money, Property property, Player owner) {
        if (owner.ownsProperty(property) && chargePlayer(owner, money)) {
            transferProperty(owner, buyer, property);
            payPlayer(buyer, money);
            game.addEvent("El jugador "+ buyer.getName() + " compró la pripiedad "+ property.getName() + " a "+ owner.getName() + " por "+ money);
            return true;
        } else if (!owner.ownsProperty(property)) {
            return false;
        } else {
            ui.message("Not enough Cash!!");
            return false;
        }
    }

    /**
     * Mortgages a property for a player.
     * The player must pay a fee of 10% of the mortgage value to mortgage the property.
     * The property must not already be mortgaged and the player has to own it.
     *
     * @param property the property you want mortgaged
     * @param player the player mortgaging the property
     */
    public void mortgageProperty(Property property, Player player) { //Deberia devolver un integer para el findwaytomakemoney
        //Asi sabemos cuanto dinero genero el llamado de este metodo
        //Esa parte la tuvimos q hacer en los metodos del Player

        if (!property.isMortgaged() && player.ownsProperty(property)) {
            Integer mortgageValue = property.getMorgageValue();
            Integer fee = mortgageValue / 10;

            if (property instanceof TerrainProperty) {
                if (((TerrainProperty) property).getUpgradeLevel() > 0){
                    ui.message("Cant morgage a property with upgrades!\n" +
                            "Sell the upgrades first!");
                }
            }

            if (chargePlayer(player, fee)) {
                payPlayer(player, mortgageValue);
                property.setMortgaged(true);
            } else {
                ui.message("Not enough money to pay the fee!\n" +
                        "Bank wont morgage you if you dont pay the fee first!");
            }
        } else if (!player.ownsProperty(property)) {
            ui.message("That's not the owner of that property!");
        } else {
            ui.message("That property is already mortgaged!");
        }
    }

    /**
     * Pays off the mortgage of a property for a player.
     * The player must pay the mortgage value to release the property from mortgage.
     * Checks if the property is mortgaged and has the same owner as the player from the parameter
     *
     * @param property the property to pay off the mortgage
     * @param player the player paying off the mortgage
     */
    public void payOffMortgage(Property property, Player player) {
        if (property.isMortgaged()) {
            if (player.ownsProperty(property)) {
                Integer mortgageValue = property.getMorgageValue();

                if (chargePlayer(player, mortgageValue)) {
                    property.setMortgaged(false);
                    ui.message(player.getName() + "successfully payed " + property.getName() + " mortgage! " +
                            "\nNow the property is fully back");
                }
                ui.message("Not enough money to pay off the mortgage!");
            }
            ui.message("Thats not the owner of that property!");
        }
        ui.message("That property is not mortgaged!");
    }

    /**
     * Pays a player a specific amount of money.
     *
     * @param player the player to recive the money
     * @param amount how much money to give
     */
    public void payPlayer(Player player, Integer amount) {
        transaction(player, amount);
    }

    /**
     * Charges a player an amount of money
     *
     * @param player the player you'll charge
     * @param amount how much money to charge
     * @return true if the player can pay that amount
     */
    public Boolean chargePlayer(Player player, Integer amount) {
        return transaction(player, -amount);
    }

    /**
     * Starts an auction for a property among a list of players.
     *
     * @param game
     * @param property the property to be auctioned
     * @param players  the list of players participating in the auction
     * @return money obtained from the auction
     */
    public Integer auctionProperty(Game game, Property property, List<Player> players) {
        /*AGUS:
         * explicado paso a paso, primero separamos los jugadores que son los que participan de hacer ofertas en
         * una lista y desp el owner lo separamos de la lista que nos viene por parametro.
         */
        game.addEvent("Se inicia la subasta de la propiedad "+ property.getName());
        List<Player> bidders = findBidderPlayers(property, players);
        Player owner = findOwner(property, players);

        // llamamos a este metodo que maneja LA FASE DE APUESTAS.
        // termina cuando nadie mas levanta la apuesta y te trae cada player con su apuesta (UN MAPA)
        Map<Player, Integer> bids = collectBids(game, property, bidders);

        //y pasamos el map de apuestas, el owner y la propiedad al metodo para cerrar la subasta
        //aca se va a ver si el jugador lo puede pagar y si no se lo delega al que siga como apuesta mas grande y asi
        Integer revenue = processBids(game,owner, property, bids);

        return revenue;
    }

    /**
     * Finds the players that can participate in the auctoin
     *
     * @param property The property being auctioned.
     * @param players The list of all players in the game.
     * @return A list of players that can make a bid in the auction
     */
    private List<Player> findBidderPlayers(Property property, List<Player> players) {
        List<Player> eligiblePlayers = new ArrayList<>();
        for (Player player : players) {
            if (!player.ownsProperty(property)) {
                eligiblePlayers.add(player);
            }
        }
        return eligiblePlayers;
    }

    /**
     * Finds the owner of the specified property.
     *
     * @param property The property whose owner is to be found.
     * @param players The list of all players in the game.
     * @return The player who owns the property, or null if the property doesn't have an owner.
     */
    private Player findOwner(Property property, List<Player> players) {
        for (Player player : players) {
            if (player.ownsProperty(property)) {
                return player;
            }
        }
        return null;
    }


    /**
     * Creates a map of bids for the property being auctioned,
     *
     * @param game
     * @param property The property being auctioned.
     * @param bidders  The participants of the auction.
     * @return A map of players and their respective bids.
     */
    private Map<Player, Integer> collectBids(Game game, Property property, List<Player> bidders) {
        Map<Player, Integer> bids = new HashMap<>();
        boolean auctionEnded = false;
        Integer highestBid = property.getPrice()/2;


        game.addEvent("Se inicia la puja con: $" + highestBid);

        while (!auctionEnded) {
            boolean newBidMade = false;

            for (Player player : bidders) {
                Integer bid = player.makeBid(property, highestBid);
                game.addEvent(player.getName() + " bidded " + bid + " !");
                if (bid > highestBid) {
                    highestBid = bid;
                    game.addEvent("VENDIDA POR " + highestBid + " !!");

                    bids.put(player, bid);
                    newBidMade = true;
                }
            }
            if (!newBidMade) {
                game.addEvent("Si nadie mas hace una oferta... Se cerra la subista!");
                auctionEnded = true;
            }
        }

        return bids;
    }

    /**
     * Processes the collected bids and transfers the property to the highest valid bidder. (if there's any)
     *
     * @param owner The current owner of the property.
     * @param property The property being auctioned.
     * @param bids The map of players and their respective bids.
     * @return highest bid paid by a player, or 0 if the property remains unsold.
     */
    private Integer processBids(Game game,Player owner, Property property, Map<Player, Integer> bids) {
        Player highestBidder = null;
        int highestBid = 0;

        //AGUS: aca parece medio raro pero lo que hace es por cada conjundo KEY-VALUE iterar, es un clasico for.
        // osea tenemos un SET {Key, Value} para ir iterando
        for (Map.Entry<Player, Integer> entry : bids.entrySet()) {
            if (entry.getValue() > highestBid) {
                highestBid = entry.getValue();
                highestBidder = entry.getKey();
            }
        }

        while (highestBidder != null) {
            game.addEvent("El ganador es: " + highestBidder.getName() + " con una puja de $" + highestBid);
            if (transferPropertyWithPayment(game, highestBidder, highestBid, property, owner)) {
               game.addEvent("VEEEENDIDA!");
                return highestBid;
            } else {
                ui.message("Highest bidder " + highestBidder.getName() + " could not complete the transaction. Finding next highest bidder...");
                bids.remove(highestBidder);

                highestBid = 0;
                highestBidder = null;
                for (Map.Entry<Player, Integer> entry : bids.entrySet()) {
                    if (entry.getValue() > highestBid) {
                        highestBid = entry.getValue();
                        highestBidder = entry.getKey();
                    }
                }
            }
        }

        ui.message("No valid bids. Property remains unsold.");
        return 0;
    }

    /**
     * Manages money exchange in the game. Checks the player's balance after the transaction
     * and returns false if the player cannot pay it, without completing the transaction.
     *
     * @param player the player to pay or charge
     * @param amount the amount of money to add or subtract
     * @return true if the transaction occurred, false otherwise
     */
    private Boolean transaction(Player player, Integer amount) {
        int newBalance = player.getBalance() + amount;
        if (newBalance >= 0) {
            player.setBalance(newBalance);
            return true;
        }
        return false;
    }

    /**
     * Sells a property to the bank for half its purchase price.
     * The property must not be mortgaged.
     *
     * @param property the property to be sold
     * @param seller the player selling the property
     */

    //Capaz deberia devolver integer
    //Asi sabemos cuanto dinero genero el llamado de este metodo
    //Esa parte la tuvimos q hacer en los metodos del Player
    public void sellPropertyToBank(Property property, Player seller) {

        if(property instanceof TerrainProperty) {
            if (((TerrainProperty) property).getUpgradeLevel() > 0) {
                ui.message("Sell the upgrades first!");
                return;
            }
        }
        if(!property.isMortgaged()){
            seller.removeProperty(property);
            payPlayer(seller, property.getPrice()/2);
        } else {
            ui.message("Cannot sell a mortgaged property to the bank!");
        }
    }

    /**
     * Upgrades a terrain property for a player.
     * Reduces the number of available houses by 1 if the upgrade is successful.
     *
     * @param game the current game
     * @param player  the player upgrading the property
     * @param terrain the property to be upgraded
     */
    public void upgradeTerrain(Game game, Player player, TerrainProperty terrain) {
        Integer upgradeLevel = terrain.getUpgradeLevel();
        Integer upgradePrice = terrain.getUpgradePrice();

        if(upgradeLevel>=5){
            ui.message("No se puede agregar mejoras");
            ui.sleep(800);
        }


        if (!canUpgradeProperty(player, terrain)){
            ui.message("No se puede mejorar porque no se completo la Zona!!");
            ui.sleep(800);
        }


        if (houses > 0) {
            if (chargePlayer(player, upgradePrice)) {
                terrain.setUpgradeLevel(upgradeLevel + 1);
                houses -= 1;
                game.addEvent(player.getName() + " mejoró " + terrain.getName() + " al nivel " + terrain.getUpgradeLevel() + ". Mejoras restantes: " + houses);
            } else {
                ui.message("No tenés suficiente dinero!");
                ui.sleep(800);
            }
        } else {
            ui.message("No quedan mas mejoras!");
            ui.sleep(800);
        }
    }

    /**
     * Sells one level of upgrades for a terrain property.
     *
     * @param terrain the property with upgrades to be sold
     * @param seller the player selling the upgrades
     */
    public Integer sellUpgrades(TerrainProperty terrain, Player seller) {
        Integer upgradeLevel = terrain.getUpgradeLevel();
        if (upgradeLevel > 0) {
            terrain.setUpgradeLevel(upgradeLevel - 1);
            payPlayer(seller, terrain.getUpgradePrice()/2);
            houses += 1;
            return terrain.getUpgradePrice()/2;
        } else {
            ui.message("Terrain has no upgrades!");
            return 0;
        }
    }

    /**
     * Handles the bankruptcy of a player. Removes all properties and returns upgrades to the house pool.
     *
     * @param player the player going bankrupt
     */
    public void handleBankruptcy(Player player) {
        for (Property property : new ArrayList<>(player.getProperties())) {
            player.removeProperty(property);
            if (property instanceof TerrainProperty terrainProperty) {
                Integer upgrades = terrainProperty.getUpgradeLevel();
                houses += upgrades;
                terrainProperty.setUpgradeLevel(0);
            }
        }
        player.setBankrupted(true);
    }

    /**
     * Attempts to deduct a specified amount of money from the player's balance to pay to the bank.
     * If the player cannot afford the payment, they are prompted to find a way to make money. If they still
     * cannot pay after attempting to find a solution, the player is declared bankrupt, and all their
     * properties are forfeited.
     *
     * @param player The player who is attempting to pay the bank.
     * @param amount The amount of money to be paid to the bank.
     */
    public void payToBank(Game game, Integer amount, Player player) {
        if (!chargePlayer(player, amount)) {
            ui.message("Not enough money!");
            player.findWayToMakeMoney(game, amount);
            if(!chargePlayer(player, amount)) {
                ui.message(player.getName() + " is now bankrupt!");
                handleBankruptcy(player);
            }
        }
    }

    /**
     * Tries to purchase a property for the player.
     * If the player can't afford the property, a message is displayed.
     *
     * @param player The player who is buying the property.
     * @param property The property being purchased.
     */
    public void buyProperty(Player player, Property property) {
        if (!chargePlayer(player, property.getPrice())) {
            ui.message("Not enough money!");
        } else {
            player.addProperty(property);
            ui.message(player.getName() + " bought " + property.getName());
        }
    }

    public int getHouses() {
        return houses;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    private Integer provinceRequierement(TerrainProperty terrain) {
        if (terrain.getProvince().equals(Province.TUCUMAN) || terrain.getProvince().equals(Province.RIO_NEGRO)) {
            return 2;
        }
        return 3;
    }

    public boolean canUpgradeProperty(Player player, TerrainProperty terrain) {
        Integer requirement = provinceRequierement(terrain);

        long count = player.getProperties().stream()
                .filter(property -> property instanceof TerrainProperty)
                .map(property -> (TerrainProperty) property)
                .filter(property -> property.getProvince().equals(terrain.getProvince()))
                .count();

        return requirement.equals((int) count);
    }
}
