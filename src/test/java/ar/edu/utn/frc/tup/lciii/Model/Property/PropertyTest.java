package ar.edu.utn.frc.tup.lciii.Model.Property;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Dice;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PropertyTest {
Property property;
Player humanPlayer;
Player botPlayer;
Game game;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        property  = new CompanyProperty(false,"test",100);
        game = new Game(new Board(),new User("test","test","test"));
        humanPlayer = new HumanPlayer(List.of(property),false);
        botPlayer = new HumanPlayer(new ArrayList<>(),false);

       game.setPlayersIterator(new PlayerIterator(List.of(humanPlayer,botPlayer)));

        Field gameDiceField = Game.class.getDeclaredField("dice");
        gameDiceField.setAccessible(true);

        Dice dice = new Dice();
        Dice diceMock = Mockito.spy(dice);
        diceMock.throwDice();



        when(diceMock.getTotalValue()).thenReturn(2);

        gameDiceField.set(game,diceMock);
}
    @Test
    void gettersSetters() {
        property.setMortgaged(true);
        assertTrue(property.isMortgaged());

        property.setName("name");
        assertSame(property.getName(), "name");

        property.setPrice(200);
        assertEquals(property.getPrice(),200);
        assertEquals(property.getMorgageValue(), 100);
    }


    @Test
    void canChargeRent() {
//
        assertTrue(property.canChargeRent(game, botPlayer));

        assertFalse(property.canChargeRent(game, humanPlayer));

        property.setMortgaged(true);
        assertFalse(property.canChargeRent(game, botPlayer));

        property.setMortgaged(false);
        humanPlayer.setProperties(new ArrayList<>());
        assertFalse(property.canChargeRent(game, botPlayer));

    }

    @Test
    void getOwner() {
        assertEquals(humanPlayer,property.getOwner(game));
        humanPlayer.setProperties(new ArrayList<>());
        assertNull(property.getOwner(game));
    }

    @Test
    public void testNoArgsConstructor() {
        Property prop = new RailwayProperty();
        assertNotNull(prop, "no puede ser null desp de crearlo.");
    }
}