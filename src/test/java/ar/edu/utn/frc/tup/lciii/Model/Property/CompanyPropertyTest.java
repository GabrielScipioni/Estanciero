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

class CompanyPropertyTest {
    CompanyProperty companyProperty;
    CompanyProperty companyProperty1;
    CompanyProperty mortgagedCompanyProperty;
    RailwayProperty railWayProperty;

    Game game;
    Player humanPlayer;
    Player botPlayer;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {


        railWayProperty = new RailwayProperty(false,"test",100);
        companyProperty = new CompanyProperty(false,"test",100);
        companyProperty1 = new CompanyProperty(false,"test",100);
        mortgagedCompanyProperty = new CompanyProperty(false,"test",100);

        humanPlayer = new HumanPlayer(List.of(companyProperty,companyProperty1,railWayProperty),false);
        botPlayer = new HumanPlayer(new ArrayList<>(),false);

        game = new Game(new Board(),new User("test","test","test"));
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
    void companyProperty() {
        assertNotNull(companyProperty);
    }


    @Test
    void getRentValue() {
        assertEquals(0,companyProperty.getRentValue(game,humanPlayer));
        assertEquals(400, companyProperty.getRentValue(game,botPlayer));

    }

}