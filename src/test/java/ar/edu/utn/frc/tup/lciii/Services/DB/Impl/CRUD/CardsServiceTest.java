package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardsServiceTest {

    @InjectMocks
    private CardsService cardsService;

    @Mock
    private ConnexionDB connexionDB;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connexionDB.connect()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    void testGetAll() throws SQLException {
        String query = "SELECT card_id, ct.description as cardDescription, ct2.description cardToPickUpDescription , e.description as eventDescription, c.message, c.amount,  c.salvageable_by_player,\n" +
                "s.square_id as squareId,\n" +
                "st.description as squareTypeDescription, s.name_square as nameSquare, p.description as squareProvinceDescription,\n" +
                "z.description as squareZoneDescription, s.upgradeprice as squareUpgradePrice, s.amount as squareAmount, e2.description as squareEventDescription, e3.description as squareventWhenPassingDescription,\n" +
                "s.price as squarePrice, ct3.description as squareCardTypeToPickUpDescription, s.rentByUpgrade \n" +
                "FROM public.card c\n" +
                "left join card_type ct on ct.card_type_id = c.card_type_id \n" +
                "left join square s on s.square_id =c.square_id \n" +
                "left join square_type st on st.square_type_id =s.square_type_id \n" +
                "left join \"zone\" z on z.zone_id =s.zone_id \n" +
                "left join events e2 on e2.event_id=s.event_id \n" +
                "left join events e3 on e3.event_id=s.event_when_passing_id  \n" +
                "left join card_type ct2 on ct2.card_type_id =s.card_type_to_pick_id \n" +
                "left join province p on p.province_id =s.province_id \n" +
                "left join events e on e.event_id = c.event_id \n" +
                "left join card_type ct3 on ct3.card_type_id =c.card_type_to_pick_up_id \n" +
                "order by c.card_id";

        when(statement.executeQuery(query)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getInt("card_id")).thenReturn(1);
        when(resultSet.getString("cardDescription")).thenReturn("DESTINY");
        when(resultSet.getString("eventDescription")).thenReturn("MONEY");
        when(resultSet.getString("message")).thenReturn("Test Message");
        when(resultSet.getInt("amount")).thenReturn(100);
        when(resultSet.getBoolean("salvageable_by_player")).thenReturn(true);

        List<Card> cards = cardsService.getAll();

        assertNotNull(cards);
        assertEquals(1, cards.size());
        assertEquals("Test Message", cards.get(0).getMessage());
        assertEquals(100, cards.get(0).getAmount());
    }
}
