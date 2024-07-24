package ar.edu.utn.frc.tup.lciii.Model.Property;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TerrainPropertyTest {

    TerrainProperty property;
    Player humanPlayer;
    Player botPlayer;
    Game game;
    @BeforeEach
    void setUp() {

        Integer [] rentByPrice = new Integer[5];
        rentByPrice[0] = 400;
        rentByPrice[1] = 2000;
        rentByPrice[2] = 5750;
        rentByPrice[3] = 17000;
        rentByPrice[4] = 21000;

        property = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,2);

        game = new Game(new Board(),new User("test","test","test"));
        humanPlayer = new HumanPlayer(List.of(property),false);
        botPlayer = new HumanPlayer(new ArrayList<>(),false);

        game.setPlayersIterator(new PlayerIterator(List.of(humanPlayer,botPlayer)));



    }

    @Test
    public void testNoArgsConstructor() {
        TerrainProperty prop = new TerrainProperty();
        assertNotNull(prop, "no puede ser null desp de crearlo.");
    }

    @Test
    void gettersSetters() {
        property.setZone(Zone.SUR);
        assertEquals(property.getZone(), Zone.SUR);



        property.setProvince(Province.CORDOBA);
        assertEquals(property.getProvince(), Province.CORDOBA);

        Integer [] rentByPrice = new Integer[1];
        rentByPrice[0] = 1;


        property.setRentByPrice(rentByPrice);
        assertEquals(property.getRentByPrice(), rentByPrice);

        property.setUpgradePrice(100);
        assertEquals(property.getUpgradePrice(), 100);

        property.setUpgradeLevel(2);
        assertEquals(property.getUpgradeLevel(), 2);


    }



    @Test
    void getRentValue() {
        assertEquals(0, property.getRentValue(game,humanPlayer));
        assertEquals(5750, property.getRentValue(game,botPlayer));
    }
}