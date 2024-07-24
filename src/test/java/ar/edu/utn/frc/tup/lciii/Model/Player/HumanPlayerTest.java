package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu.JailPlayerGameMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu.MakeMoneyOfPropertiesMenu;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu.PlayerPlayMenu;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Services.ui.UiService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HumanPlayerTest {
    @Mock
    private UserInteraction mockUserInteraction;

    @Mock
    private Game mockGame;
    @Mock
    private Board mockBoard;

    @Mock
    private Property mockProperty;

    @Mock
    private Player mockBuyer;

    private HumanPlayer humanPlayer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
        humanPlayer = new HumanPlayer(Collections.emptyList(), false, mockUserInteraction); // Inyecta el mock en el constructor

        when(mockGame.getBoard()).thenReturn(mockBoard);

    }
    @Test
    public void testConstructor() {

        List<Property> properties = new ArrayList<>();
        boolean bankrupted = false;
        HumanPlayer humanPlayer = new HumanPlayer(properties, false);

        assertNotNull(humanPlayer);
        assertEquals(properties, humanPlayer.getProperties());
        assertEquals(bankrupted, humanPlayer.getBankrupted());
    }


    @Test
    public void testFindWayToMakeMoney() {

        HumanPlayer humanPlayer = new HumanPlayer(new ArrayList<>(), false);

//        assertDoesNotThrow(humanPlayer::findWayToMakeMoney);
        //dasdslkdjfh
    }


    @Test
    public void testPlay() {
        HumanPlayer humanPlayer = new HumanPlayer( new ArrayList<>(), false);
        //TODO:assertDoesNotThrow(humanPlayer::play);
    }

    @Test
    void testMakeBid() {
            when(mockUserInteraction.askInt(anyString())).thenReturn(500);
            Integer bid = humanPlayer.makeBid(mockProperty, 400);

            assertEquals(500, bid);
            verify(mockUserInteraction).askInt("Ingrese su oferta");

    }
//    @Test
//    void testPlay_PlayerInJail() {
//        when(mockBoard.isPlayerInJail(humanPlayer)).thenReturn(true);
//
//        try (MockedStatic<JailPlayerGameMenu> mockedJailMenu = Mockito.mockStatic(JailPlayerGameMenu.class);
//             MockedStatic<PlayerPlayMenu> mockedPlayMenu = Mockito.mockStatic(PlayerPlayMenu.class)) {
//
//            JailPlayerGameMenu mockJailMenu = mock(JailPlayerGameMenu.class);
//            PlayerPlayMenu mockPlayerMenu = mock(PlayerPlayMenu.class);
//
//            mockedJailMenu.when(() -> new JailPlayerGameMenu(mockGame, humanPlayer)).thenReturn(mockJailMenu);
//            mockedPlayMenu.when(() -> new PlayerPlayMenu(mockGame, humanPlayer)).thenReturn(mockPlayerMenu);
//
//            // Act
//            humanPlayer.play(mockGame);
//
//            // Assert
//            verify(mockJailMenu).execute();
//            verify(mockPlayerMenu).execute();
//        }
//    }

    /*
    @Test
    public void testOfferAuction() {
        List<Property> properties = new ArrayList<>();
        HumanPlayer humanPlayer = new HumanPlayer(properties, false);
        Integer [] rentByPrice = new Integer[5];
        rentByPrice[0] = 400;
        rentByPrice[1] = 2000;
        rentByPrice[2] = 5750;
        rentByPrice[3] = 17000;
        rentByPrice[4] = 21000;

        Property property = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);


        try {
            humanPlayer.offerAuction(property);
        } catch (Exception e) {
            fail("Exepcion");
        }
    }
     */
}
