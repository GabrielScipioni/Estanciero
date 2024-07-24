package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.GamesEntity;
import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Event.*;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.CRUD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventService implements CRUD<GamesEntity> {

    private ConnexionDB connexionDB = ConnexionDB.getInstance();
    public static EventService instance;
    public static EventService getInstance(){
        if (instance==null){
            instance= new EventService();
        }
        return instance;
    }
    private EventService(){

    }
   public Event createEvent(EventType eventType){
        if(eventType==null){
            return null;
        }
       switch (eventType){
           case MONEY:
               return new Event(eventType, new MoneyStrategy());
           case GO_TO_JAIL:
               return new Event(eventType, new GoToJailStrategy());
           case GET_OUT_OF_JAIL:
               return new Event(eventType, new GetOutOfJailStrategy());
           case MOOVE_TO_SQUARE:
               return new Event(eventType, new TeleportStrategy());
           case MOOVE_TO_SQUARE_AND_EARN_AT_EXIT:
               return new Event(eventType, new MoveToSquareStrategy());
           case CHARGE_ALL_PLAYERS:
               return new Event(eventType, new ChargeAllPlayersStrategy());
           case MOOVE_X_STEPS:
               return new Event(eventType, new MoveStepsStrategy());
           case PAY_PER_UPGRADE:
               return new Event(eventType, new PayPerUpgradeStrategy());
           case PICK_UP_CARD_TYPE:
               return new Event(eventType, new PickUpCardTypeStrategy());
           case PAY_AND_PICKUP_CARD_TYPE:
               return new Event(eventType, new PayOrPickupStrategy());
           case JAIL:
               return new Event(eventType, new JailStrategy());

           default:
               return null;
       }
   }

}
