package ar.edu.utn.frc.tup.lciii.Model.Property;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;

/**
 * modela una propiedad de Terreno
 */
public class TerrainProperty extends Property {
    private Zone zone;
    private Province province;
    private Integer[] rentByPrice;
    private Integer upgradePrice;
    private Integer upgradeLevel;

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Integer[] getRentByPrice() {
        return rentByPrice;
    }

    public void setRentByPrice(Integer[] rentByPrice) {
        this.rentByPrice = rentByPrice;
    }

    public Integer getUpgradePrice() {
        return upgradePrice;
    }

    public void setUpgradePrice(Integer upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public Integer getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(Integer upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public TerrainProperty() {}

    public TerrainProperty(Boolean mortgaged, String name, Integer price, Zone zone, Province province,
                           Integer[] rentByPrice, Integer upgradePrice, Integer upgradeLevel) {
        super(mortgaged, name, price);
        this.zone = zone;
        this.province = province;
        this.rentByPrice = rentByPrice;
        this.upgradePrice = upgradePrice;
        this.upgradeLevel = upgradeLevel;
    }




    public Integer getRentValue(Game game, Player player) {
        //si es posible cobrar, se cobra segun upgrade level, si no, se cobra 0
       if (super.canChargeRent(game,player)){
           //se cobra el alquiler segun el upgrade level
           return  rentByPrice[upgradeLevel];
       }else{
           return 0;
       }

    }
}
