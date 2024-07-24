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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RailwayPropertyTest {

    RailwayProperty railWayProperty;
    RailwayProperty railWayProperty1;
    RailwayProperty mortgagedRailwayProperty;



    CompanyProperty companyProperty;

    Game game;
    Player humanPlayer;
    Player botPlayer;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {


        railWayProperty = new RailwayProperty(false,"test",100);
        railWayProperty1 = new RailwayProperty(false,"test",100);
        mortgagedRailwayProperty = new RailwayProperty(true,"test",100);

        companyProperty = new CompanyProperty(false,"test",100);

        humanPlayer = new HumanPlayer(List.of(mortgagedRailwayProperty, railWayProperty1,railWayProperty),false);
        botPlayer = new HumanPlayer(new ArrayList<>(),false);

        game = new Game(new Board(),new User("test","test","test"));
        ArrayList<Player> players = new ArrayList<>();
        players.add(humanPlayer);
        players.add(botPlayer);

        game.setPlayersIterator(new PlayerIterator(List.of(humanPlayer,botPlayer)));


    }
    @Test
    void companyProperty() {
        assertNotNull(companyProperty);
    }


    @Test
    void getRentValue() {
        assertEquals(0,railWayProperty.getRentValue(game,humanPlayer));
        assertEquals(1000, railWayProperty.getRentValue(game,botPlayer));

    }

    @Test
    public void testNoArgsConstructor() {
        RailwayProperty prop = new RailwayProperty();
        assertNotNull(prop, "no puede ser null desp de crearlo.");
    }

}