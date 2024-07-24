package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.GamesEntity;
import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Event.EventType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Game.GameDifficulty;
import ar.edu.utn.frc.tup.lciii.Model.Player.*;
import ar.edu.utn.frc.tup.lciii.Model.Property.Province;
import ar.edu.utn.frc.tup.lciii.Model.Property.Zone;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.CRUD;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardsService implements CRUD<GamesEntity> {

    private ConnexionDB connexionDB = ConnexionDB.getInstance();
    public static CardsService instance;
    public static CardsService getInstance(){
        if (instance==null){
            instance= new CardsService();
        }
        return instance;
    }
    private CardsService(){

    }

    public List<Card> getAll() {
        List<Card> cards = new ArrayList<>();
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
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Square cardSquare = null;
                if(rs.getString("squareId")!=null){
                    cardSquare = SquareService.getInstance().squareFactory(
                            rs.getLong("squareId"),
                            rs.getString("squareTypeDescription"),
                            rs.getString("nameSquare"),
                            rs.getString("squareProvinceDescription")!=null?Province.valueOf(rs.getString("squareProvinceDescription")):null,
                            rs.getString("squareZoneDescription")!=null?Zone.valueOf(rs.getString("squareZoneDescription")):null,
                            rs.getInt("squareUpgradePrice"),
                            rs.getInt("squareAmount"),
                            rs.getString("squareEventDescription")!=null?EventType.valueOf(rs.getString("squareEventDescription")):null,
                            rs.getString("squareventWhenPassingDescription")!=null?EventType.valueOf(rs.getString("squareventWhenPassingDescription")):null,
                            rs.getInt("squarePrice"),
                            rs.getString("squareCardTypeToPickUpDescription")!=null?CardType.valueOf(rs.getString("squareCardTypeToPickUpDescription")):null,
                            rs.getArray("rentByUpgrade")
                    );
                }


                Card card = cardFactory(
                        rs.getInt("card_id"),
                        CardType.valueOf(rs.getString("cardDescription")),
                        EventType.valueOf(rs.getString("eventDescription")),
                        cardSquare,
                        rs.getString("message"),
                        rs.getInt("amount"),
                        rs.getString("cardToPickUpDescription")!=null? CardType.valueOf(rs.getString("cardToPickUpDescription")):null,
                        rs.getBoolean("salvageable_by_player")
                );

                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }


    public Card cardFactory (Integer id,CardType cardType, EventType eventType, Square square, String messagge, Integer amount, CardType cardTypeToPîckUp, boolean salvagableByPlayer){
        Card card = new Card();
        card.setCardType(cardType);
        card.setEvent(EventService.getInstance().createEvent(eventType));
        card.setMessage(messagge);
        card.setSquare(square);
        card.setAmount(amount);
        card.setSavableByPlayer(salvagableByPlayer);

        return card;

    }


}
