package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ar.edu.utn.frc.tup.lciii.Model.Property.Province;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

import java.util.*;

/**
 * El bot player usa un template method para las estrategias (cada bot sabe que propiedades preferir, etc)
 *
 * Clase que representa a cualquier tipo Bot, de esta clase se heredaran comportamientos y propiedades que tendran cualquier Bot
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AggressiveBot.class, name = "AgressiveBot"),
        @JsonSubTypes.Type(value = ConservativeBot.class, name = "ConservativeBot"),
        @JsonSubTypes.Type(value = ModerateBot.class, name = "ModerateBot")
})
public abstract class BotPlayer extends Player {

    public BotPlayer() {}

    private List<Province> preferenceProvinces;

    public BotPlayer(List<Property> properties, Boolean bankrupted,List<Province>preferenceProvinces) {
        super(properties, bankrupted);
        this.preferenceProvinces = preferenceProvinces;
    }

    public Boolean isPriorityProperty(Property property) {
        if (property instanceof TerrainProperty) {
            return preferenceProvinces.contains(((TerrainProperty) property).getProvince());
        }
        return false;
    }

    public List<Province> getPreferenceProvinces() {
        return preferenceProvinces;
    }

    public abstract Boolean isPriority(Property property);
    public abstract Integer getPriceGap();
    public abstract void buyProperty(Game game);

    @Override
    public void play(Game game){
        buyProperty(game); //Al caer en PropertySquare: Compra si puede o si quiere
        raiseMortgage(game);
        boolean x = true;
        while (x)
        {
            //Ciclo para que el bot upgradee mientras cumpla las condiciones
            x = upgradeTerrainProperty(game);
        };
    }

    @Override
    public boolean sellPropertyToPlayerInner(Game game, Property property, Player buyer, Integer price)
    {
        int propertyPrice = property.getPrice(); //Precio de la property
        int maxPrice = (propertyPrice * getPriceGap()) / 100;  //El precio maximo que puede pagar por ella

        if (price <= maxPrice && buyer.getBalance() >= price )//El precio recibido no supera nuestro maximo
            // Y podemos pagar el precio
        {
            return true;
        }
        return false;
    }

    public void findWayToMakeMoney(Game game, Integer moneyToPay) {
        int control;
        int dineroGenerado = 0;

        while (dineroGenerado < moneyToPay) {
            control = this.sellUpgrades(game);
            if (control != 0) {
                dineroGenerado += control;
            } else {
                break;
            }
        }
        while (dineroGenerado < moneyToPay) {
            control = this.mortgageProperty(game);
            if (control != 0) {
                dineroGenerado += control;
            } else {
                break;
            }
        }
        while (dineroGenerado < moneyToPay) {
            control = this.offerAuction(game);
            if (control != 0) {
                dineroGenerado += control;
            } else {
                break;
            }
        }
        while (dineroGenerado < moneyToPay) {
            control = this.sellProperty(game);
            if (control != 0) {
                dineroGenerado += control;
            } else {
                break;
            }
        }
        this.setBalance(getBalance()+dineroGenerado);
    }

    public Integer makeBid(Property auctionProperty, Integer currentHighestBid) {
        //Sumar entre 300 a 500
        if (currentHighestBid < this.getBalance()) //Si podemos pagar la subasta actual
        {
            if(isPriority(auctionProperty))//Es prioridad (Se va a comprar)
            {
                if (currentHighestBid<auctionProperty.getPrice()
                        && auctionProperty.getPrice() < this.getBalance())
                //El precio de la apuesta es menor al precio de compra de la propiedad
                //Y
                //Podemos pagar ese precio original
                {
                    return auctionProperty.getPrice();//Enviamos nuestra oferta
                }
            }
        }
        return 0; //No podemos pagar la subasta actual-->No podemos sumar mas
    }


    public Integer sellProperty(Game game) {
        Random random = new Random();

        if (!getProperties().isEmpty())
        {   // El player tiene propiedades

            // Lista de propiedades del player que no son prioridad
            List<Property> noPriorities = getNopriorities(this.getProperties());


            if (!noPriorities.isEmpty()) { //El player tenia propiedades no prioritarias

                int index = random.nextInt(noPriorities.size());
                // Vendemos de la lista de no prioridades si no está vacía
                game.getBank().sellPropertyToBank(noPriorities.get(index), this);
                return noPriorities.get(index).getPrice() / 2;
            } else{ // Si tenemos solo propiedades prioridades

                //Vendemos directo de la lista general (con las prioridades)
                int index = random.nextInt(this.getProperties().size()); //Random

                Property propertyToSell = this.getProperties().get(index); //La propiedad a vender

                game.getBank().sellPropertyToBank(propertyToSell, this);//La vendemos
                return propertyToSell.getPrice(); // Retornas el valor vendido de la propiedad
            }
        }
        return 0; //El player no tiene propiedades
    }

    public List<Property> getNopriorities(List<Property> properties) {
        List<Property> noPriorities = new ArrayList<>();
        for (Property property : properties) {
            if (!isPriority(property)) {
                noPriorities.add(property);
            }
        }
        return noPriorities;
    }

    public boolean upgradeTerrainProperty(Game game) {
        //to do:
        // para upgradear se necesita:
        // tener todas las zonas de una provincia (provincia completa)
        // tener la plata
        // no podes mejorar una zona hipotecada (supongo q lo revisa el banco)
        //to do: este bot mejorara solo si la mejora cuesta hasta un 30%
        List<Property> properties = this.getProperties();
        Integer upgradeprice;
        Integer priceGap;
        List<Province> repeatedProvinces =
                findCompletedProvinces(properties);
        Integer condicionalPrice;
        if (!repeatedProvinces.isEmpty()) // Tiene provincias completas
        {
            for (Property property : properties)//Por cada propiedad
            {
                if (property instanceof TerrainProperty && //Es provincia, esta completa y no esta hipotecada
                        repeatedProvinces.contains(((TerrainProperty) property).getProvince())
                && !property.isMortgaged() && ((TerrainProperty) property).getUpgradeLevel()<6)
                {
                    upgradeprice = ((TerrainProperty) property).getUpgradePrice(); //Calculo de la mejora
                    //Ejemplo: si el gap es 50%,
                    //simulamos que tiene 500 y paga 180 por el upgrade
                    //320 > 50% de 500 (250), asi q se puede permitir gastar 180 con 500

                    int balance = this.getBalance();
                    int nuevo_balance = balance - upgradeprice;
                    priceGap = (balance * getPriceGap()) / 100;
                    if (balance >= upgradeprice) //Se puede pagar el upgrade
                    {
                        if (nuevo_balance >= priceGap) //No sobrepase el gap
                        //No esta hipotecada, se puede pagar el upgrade
                        {
                            //Se hace el upgrade
                            game.getBank().upgradeTerrain(game,this, (TerrainProperty) property);
                            return true;
                        }
                        else if (this instanceof ModerateBot //Es moderate,
                                && checkAllPropertiesOwnership(game)) // Se vendieron +75% de propiedades
                        {
                            //Se hace el upgrade
                            game.getBank().upgradeTerrain(game,this, (TerrainProperty) property);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean checkAllPropertiesOwnership(Game game) {
        int contador = 0;
        List<Square> squares = game.getBoard().getSquares();
        int total = 0;
        for (Square square : squares) {
            if (square instanceof PropertySquare) {
                total++; //Contador de propiedades que hay (para el porcentaje)
                //La propiedad tiene dueño => Fue vendida
                if (((PropertySquare) square).getProperty().getOwner(game) != null) {

                    contador++;//Sumo las propiedades vendidas
                }
            }
        }
        if ((contador / total) * 100 > 75) { //Se hayan vendido + 75% de propiedades
            return true;
        }
        return false;
    }

    public Integer mortgageProperty(Game game) {
        //to do: hipotecar
        // preferentemente, que la propiedad no sea isPreference
        //to do: hipotecar
        // preferentemente, que la propiedad no sea isPreference
        // No puedo hipotecar algo que tiene upgrades
        List<Property> properties = this.getProperties();
        List<Property> mortgageableProperties = new ArrayList<>();
        List<Property> mortgageablePropertiesNoPriorities = new ArrayList<>();

        if (!properties.isEmpty()) //Tiene propiedades
        {
            //Obtener lista de propiedades Hipotecables:
            mortgageableProperties = getMortgageableProperties(properties);

            if (!mortgageableProperties.isEmpty()) //Tenemos propiedades hipotecables
            {
                //Quitar los prioritarios de las propiedades a hipotecar
                mortgageablePropertiesNoPriorities = removePriorityProperties(mortgageableProperties); //Lista solo con NO prioridades (hipotecables)

                Property propertyToMorgage = null; //Instancia a devolver

                if (!mortgageablePropertiesNoPriorities.isEmpty()) //Lista solo con NO prioridades
                {
                    propertyToMorgage =
                            obtainBiggestMortgage(mortgageablePropertiesNoPriorities);//Obtenemos el mas grande de los que quedaron en la lista de NO prioridades
                } else //Todas eran prioritarias
                {
                    propertyToMorgage =
                            obtainBiggestMortgage(mortgageableProperties); //obtenemos la mejor de la lista general DE HIPOTECABLES
                }
                //Hacer el hipotecado de la property con mayor valor hipotecario:
                game.getBank().mortgageProperty(propertyToMorgage, this);
                //Se calcula la cantidad de dinero generada para retornar en findway:
                int mortgageValue = propertyToMorgage.getMorgageValue();
                int valor = mortgageValue - (mortgageValue / 10);
                return valor;
            }
        }
        return 0;
    }
    /**
     * Filtra las propiedades hipotecables según las condiciones especificadas:
     * - La propiedad no está hipotecada.
     * - Si es una propiedad de tipo TerrainProperty, no tiene mejoras (upgradeLevel == 0).
     *
     * @param properties la lista de propiedades a analizar
     * @return una lista de propiedades que cumplen las condiciones de hipotecabilidad
     */
    public List<Property> getMortgageableProperties(List<Property> properties) {
        List<Property> mortgageableProperties = new ArrayList<>();
        // Filtrar propiedades que no están hipotecadas y, si son de tipo TerrainProperty, no tienen mejoras
        for (Property property : properties)
        {
            if (!property.isMortgaged())  // La propiedad no está hipotecada
            {
                //Preguntar si tiene chacras:
                if (property instanceof TerrainProperty) // Si es tipo terrain
                {
                    if (((TerrainProperty) property).getUpgradeLevel() >0)
                    {
                        //Tiene mejoras, no agrego la propiedad a la lista de "hipotecables"
                    }
                    else
                    {
                        mortgageableProperties.add(property); //No tiene mejoras
                    }
                }
                else
                {
                    mortgageableProperties.add(property); //No era Terrain--> No tiene chacras
                }
            }
        }
        return mortgageableProperties;
    }
    /**
     * Filtra las propiedades prioritarias de una lista de propiedades hipotecables.
     *
     * @param mortgageableProperties la lista de propiedades hipotecables
     * @return una lista de propiedades hipotecables sin las propiedades prioritarias
     */
    public List<Property> removePriorityProperties(List<Property> mortgageableProperties) {
        List<Property> filteredProperties = new ArrayList<>(mortgageableProperties);

        // Filtrar y eliminar propiedades prioritarias
        for (Property property : mortgageableProperties) {
            if (isPriority(property)) { // Es prioridad
                filteredProperties.remove(property); // Lo quito
            }
        }
        return filteredProperties;
    }
    public Property obtainBiggestMortgage(List<Property> properties) {
        int mayor = 0;
        boolean x = true;
        Property propertyToMorgage = null;
        for (Property propertyMayor : properties) {
            if (x) {
                mayor = propertyMayor.getMorgageValue();//1er mayor
                propertyToMorgage = propertyMayor;
            } else {
                if (propertyMayor.getMorgageValue() > mayor)//Los otros valores
                {
                    mayor = propertyMayor.getMorgageValue();
                    propertyToMorgage = propertyMayor;
                }
            }
            x = false;
        }
        return propertyToMorgage;
    }
    public boolean raiseMortgage(Game game) {
        List<Property> properties = this.getProperties();
        //List<Property> properties_auxiliar = this.getProperties();
        //Para q no apunten al mismo objeto, creo una "copia" de la lista
        List<Property> propertiesToDismorgage= new ArrayList<>(properties);

        Property propertyDismorgaged = null;

        if (!properties.isEmpty())
        {
            //Filtramos de la lista de propiedades
            //las propiedades no hipotecadas
            for (Property property : properties) {
                if (!property.isMortgaged()) {
                    propertiesToDismorgage.remove(property);
                }
            }
            if (!propertiesToDismorgage.isEmpty())
            {
                List<Property> propertiesToDismorgageAuxiliar = new ArrayList<>(propertiesToDismorgage);

                for (Property property : propertiesToDismorgage) { //Por cada propiedad hipotecable
                    if (!propertiesToDismorgageAuxiliar.isEmpty()) //Chequeo auxiliar no vacia
                    {
                        propertyDismorgaged =
                                property;//Se obtiene el hipotecable
                        game.getBank().payOffMortgage(propertyDismorgaged, this);//Se hipoteca
                        propertiesToDismorgageAuxiliar.remove(propertyDismorgaged);//Se quita 1 del auxiliar
                    }
                }
                return true;
            }
        }
        return false;
    }
    public Property obtainBiggestMorgageAffordable(List<Property> properties)
    {
        int mayor = 0;
        boolean x= true;
        Property propertyToMorgage = null;
        for (Property propertyMayor :properties)
        {
            if (x)
            {
                mayor = propertyMayor.getMorgageValue();//1er mayor
                propertyToMorgage = propertyMayor;
            }
            else
            {
                if (propertyMayor.getMorgageValue() > mayor &&
                        this.getBalance() >=mayor)
                {
                    mayor = propertyMayor.getMorgageValue();
                    propertyToMorgage = propertyMayor;
                }
            }
            x = false;
        }
        return propertyToMorgage;
    }
    public Integer offerAuction(Game game) {

        Random random = new Random();
        List<Property> noPriorities = getNopriorities(this.getProperties());
        List<Player> players = new ArrayList<>();
        if (!getProperties().isEmpty()) //Tengo propiedades
        {
            if (!noPriorities.isEmpty())//Vendo una random de las que no son prioridad
            {
                int index = random.nextInt(noPriorities.size()); //random max size de la list (-1)
                //Se vende:
                players = getOtherPlayersOnBoard(game);
                //Aramamos la subasta/auction con la propiedad random y los players ofertantes

                return game.getBank().auctionProperty(game, this.getProperties().get(index), players);
            } else { //Vendo una random de las que tengo (son prioridades)
                int index = random.nextInt(this.getProperties().size());//random max size de la list (-1)
                //Se vende:
                players = getOtherPlayersOnBoard(game);
                //Aramamos la subasta/auction con la propiedad random y los players ofertantes
                //Obtenemos el dinero generado de la subasta
                return game.getBank().auctionProperty(game, this.getProperties().get(index), players);
            }
        }
        return 0;
    }
    /**
     * Obtiene una lista de jugadores presentes en todas las casillas del tablero,
     * excluyendo al jugador actual.
     *
     * @return una lista de jugadores que están presentes en las casillas del tablero, excluyendo al jugador actual
     */
    public List<Player> getOtherPlayersOnBoard(Game game) {
        List<Player> players = new ArrayList<>();

        // Recorremos todas las casillas del tablero
        for (Square square : game.getBoard().getSquares()) {
            // Verificamos si la casilla tiene jugadores encima
            if (!square.getPlayers().isEmpty()) {
                // Recorremos los jugadores de la casilla
                for (Player player : square.getPlayers()) {
                    // Excluimos al jugador actual
                    if (!player.equals(this)) {
                        players.add(player); // Agregamos el jugador a la lista de jugadores
                    }
                }
            }
        }

        return players;
    }
    public Integer sellUpgrades(Game game)
    {
        List<TerrainProperty> terrainProperties = new ArrayList<>();
        Random random = new Random();

        //Obtiene las provincias del player y los guarda en una lista
        terrainProperties = getPlayerTerrainProperties(this);

        //Filtra la lista de terrenos
        terrainProperties = filterTerrainProperties(terrainProperties);

        //No quedó vacia la lista-->Tenemos terrenos sin hipotecas y upgradeados
        if (!terrainProperties.isEmpty())
        {
            int index = random.nextInt(terrainProperties.size());
            //Se vende el upgrade y obtenemos el dinero generado a travez del banco
            return game.getBank().sellUpgrades(terrainProperties.get(index), this);
        }
        return 0;
    }

    public List<TerrainProperty> filterTerrainProperties(List<TerrainProperty> terrainProperties) {
        List<TerrainProperty> filteredProperties = new ArrayList<>(terrainProperties);

        for (TerrainProperty terrain : new ArrayList<>(filteredProperties)) {
            // Saca los terrenos hipotecados
            if (terrain.isMortgaged()) {
                filteredProperties.remove(terrain);
            }
            // Saca los terrenos sin mejoras
            if (terrain.getUpgradeLevel().equals(0)) {
                filteredProperties.remove(terrain);
            }
        }

        return filteredProperties;
    }
    public List<TerrainProperty> getPlayerTerrainProperties(Player player) {
        List<TerrainProperty> terrainProperties = new ArrayList<>();

        // Obtiene las provincias del jugador y las guarda en una lista
        for (Property property : player.getProperties()) {
            if (property instanceof TerrainProperty) {
                terrainProperties.add((TerrainProperty) property);
            }
        }
        return terrainProperties;
    }

    public List<Province> findCompletedProvinces(List<Property> properties) {
        // Cuenta las ocurrencias de cada provincia
        Map<Province, Integer> provinceCount = countProvinceOccurrences(properties);
        // Encuentra las provincias que se repiten al menos 3 veces
        // y el caso especial para Tucumán y Río Negro al menos 2 veces
        return findRepeatedProvinces(provinceCount);
    }
    public Map<Province, Integer> countProvinceOccurrences(List<Property> properties) {
        Map<Province, Integer> provinceCount = new HashMap<>();

        // Cuenta las ocurrencias de cada provincia
        for (Property property : properties) {
            if (property instanceof TerrainProperty) {
                Province province = ((TerrainProperty) property).getProvince();
                provinceCount.put(province, provinceCount.getOrDefault(province, 0) + 1);
            }
        }
        return provinceCount;
    }
    public List<Province> findRepeatedProvinces(Map<Province, Integer> provinceCount) {
        List<Province> repeatedProvinces = new ArrayList<>();

        for (Map.Entry<Province, Integer> entry : provinceCount.entrySet()) {
            Province province = entry.getKey();
            int count = entry.getValue();

            if (count >= 3 || (count == 2 &&
                    (province == Province.TUCUMAN || province == Province.RIO_NEGRO))) {
                repeatedProvinces.add(province);
            }
        }
        return repeatedProvinces;
    }

    //public abstract void tradeRequest(); //AGUS : Este seria "buyOtherPlayerProperty()"
}
