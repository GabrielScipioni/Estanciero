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
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.CRUD;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SquareService implements CRUD<GamesEntity> {

    private ConnexionDB connexionDB = ConnexionDB.getInstance();
    public static SquareService instance;
    public static SquareService getInstance(){
        if (instance==null){
            instance= new SquareService();
        }
        return instance;
    }
    private SquareService(){

    }
//    @Override
//    public Square getById(Long id) {
//        String query = "SELECT * FROM  square WHERE square_id = ?";
//        try (Connection conn = connexionDB.connect();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setLong(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    Square square =  squareFactory();
//                    game.setGameId(rs.getLong("game_id"));
//                    game.setDateSetup(rs.getTimestamp("datesetup").toLocalDateTime());
//                    game.setFinished(rs.getBoolean("finished"));
//                    game.setOrderByIdPlayer(rs.getString("order_by_id_player"));
//                    return game;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    @Override
//    public List<GamesEntity> getAll() {
//        List<GamesEntity> games = new ArrayList<>();
//        String query = "SELECT * FROM game";
//        try (Connection conn = connexionDB.connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                GamesEntity game = new GamesEntity();
//                game.setGameId(rs.getLong("game_id"));
//                game.setDateSetup(rs.getTimestamp("datesetup").toLocalDateTime());
//                game.setFinished(rs.getBoolean("finished"));
//                game.setOrderByIdPlayer(rs.getString("order_by_id_player"));
//                games.add(game);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return games;
//    }
public List<Square> getAll() {
    List<Square> squares = new ArrayList<>();
    String query = "SELECT s.square_id as squareId,\n" +
            "st.description as squareTypeDescription, s.name_square as nameSquare, p.description as squareProvinceDescription,\n" +
            "z.description as squareZoneDescription, s.upgradeprice as squareUpgradePrice, s.amount as squareAmount, e2.description as squareEventDescription, e3.description as squareventWhenPassingDescription,\n" +
            "s.price as squarePrice, s.rentByUpgrade , ct2.description as squareCardTypeToPickUpDescription\n" +
            "\n" +
            "from square s\n" +
            "left join square_type st on st.square_type_id =s.square_type_id \n" +
            "left join \"zone\" z on z.zone_id =s.zone_id \n" +
            "left join events e2 on e2.event_id=s.event_id \n" +
            "left join events e3 on e3.event_id=s.event_when_passing_id  \n" +
            "left join card_type ct2 on ct2.card_type_id =s.card_type_to_pick_id \n" +
            "left join province p on p.province_id =s.province_id \n" +
            "order by 1 \n";
    try (Connection conn = connexionDB.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {

                Square square = SquareService.getInstance().squareFactory(
                        rs.getLong("squareId"),
                        rs.getString("squareTypeDescription"),
                        rs.getString("nameSquare"),
                        rs.getString("squareProvinceDescription")!=null ? Province.valueOf(rs.getString("squareProvinceDescription")):null,
                        rs.getString("squareZoneDescription")!=null?Zone.valueOf(rs.getString("squareZoneDescription")):null,
                        rs.getInt("squareUpgradePrice"),
                        rs.getInt("squareAmount"),
                        rs.getString("squareEventDescription")!=null?EventType.valueOf(rs.getString("squareEventDescription")):null,
                        rs.getString("squareventWhenPassingDescription")!=null?EventType.valueOf(rs.getString("squareventWhenPassingDescription")):null,
                        rs.getInt("squarePrice"),
                        rs.getString("squareCardTypeToPickUpDescription")!=null?CardType.valueOf(rs.getString("squareCardTypeToPickUpDescription")):null,
                        rs.getArray("rentByUpgrade")
                );
            squares.add(square);
            }



        } catch (SQLException ex) {
        throw new RuntimeException(ex);
    }

    return squares;
}


    public Square squareFactory(Long id,String squareDBKey, String name, Province province, Zone zone, Integer upgradePrice,Integer amount,
                              EventType eventExecution, EventType eventWhenPassing, Integer price, CardType cardTypeToPickUp, Array rentByUpgrade) throws SQLException {


        switch (squareDBKey){
            case "NORMAL":
                return new NormalSquare(id,EventService.getInstance().createEvent(eventExecution),
                        EventService.instance.createEvent(eventWhenPassing),name,amount,cardTypeToPickUp);

            case "TERRAIN_PROPERY_SQUARE":
                return new PropertySquare(id,new TerrainProperty(false,name,price,zone,province,convertSQLArrayToIntegerArray(rentByUpgrade) , upgradePrice, 0  ));
            case "COMPANY_PROPERY_SQUARE":
                return new PropertySquare(id, new CompanyProperty(false, name,price));
            case "RAILWAY_PROPERY_SQUARE":
                return new PropertySquare(id, new RailwayProperty(false, name, price));
            default:
                return null;
        }
    }


        public static Integer[] convertSQLArrayToIntegerArray(Array sqlArray) throws SQLException {
            if (sqlArray == null) {
                return null;
            }

            // Obtén el array de objetos desde el sqlArray
            Object[] objectArray = (Object[]) sqlArray.getArray();

            // Convierte el array de objetos a Integer[]
            Integer[] integerArray = new Integer[objectArray.length];
            for (int i = 0; i < objectArray.length; i++) {
                if (objectArray[i] instanceof Long) {
                    integerArray[i] = ((Long) objectArray[i]).intValue();
                } else if (objectArray[i] instanceof Integer) {
                    integerArray[i] = (Integer) objectArray[i];
                } else {
                    throw new SQLException("Tipo de datos inesperado en el array SQL");
                }
            }

            return integerArray;
        }



}
