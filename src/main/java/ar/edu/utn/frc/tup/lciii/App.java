package ar.edu.utn.frc.tup.lciii;
import ar.edu.utn.frc.tup.lciii.Entities.BoardEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.CardsService;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.SquareService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Event.*;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.ConservativeBot;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Model.User;

import java.util.ArrayList;
import java.util.List;
import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.MainMenu.WelcomeMenu;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.sql.*;
import java.util.Objects;

import static ar.edu.utn.frc.tup.lciii.Services.DB.OR.connexionDB;

/**
 * Hello to TPI Estanciero
 *
 */
public class App {

    /**
     * This is the main program
     */
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println("Hello, TPI Estanciero.");
        //new MainMenu().execute();
        new WelcomeMenu().execute();
//        List<Card> cards = CardsService.getInstance().getAll();
//        System.out.println(cards);
//
//        List<Square> squares = SquareService.getInstance().getAll();
//        System.out.println(squares);

//ConnexionDB.getInstance();
//        String query = "SELECT * FROM CardEntity";
//
//        try {
//            Connection conn = ConnexionDB.getInstance().connect();
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            System.out.println(rs);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        // PlayerPlayMenu.BuyOtherPropertyOption buyOtherPropertyOption = playerPlayMenu.new BuyOtherPropertyOption(1, game, player, property);
//        buyOtherPropertyOption.execute();

//        verify(property).getOwner(game);




//
//
//
//Integer [] rentByPrice = new Integer[5];
//rentByPrice[0] = 400;
//rentByPrice[1] = 2000;
//rentByPrice[2] = 5750;
//rentByPrice[3] = 17000;
//rentByPrice[4] = 21000;
//Property terrainProperty = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);
//Property railWayProperty = new RailwayProperty(false, "Tren", 1000);
//PropertySquare propertySquare = new PropertySquare(terrainProperty);
//PropertySquare railwayPropertySquare = new PropertySquare(railWayProperty);
//NormalSquare jailSquare = new NormalSquare(new Event(EventType.JAIL, new JailStrategy()), "Comisaria", null,null );
//NormalSquare exitSquare = new NormalSquare(new Event(EventType.MONEY, new MoneyStrategy()),"Salida",null,500);
//        exitSquare.setEventWhenPassing(new Event(EventType.MONEY, new MoneyStrategy()));
//        exitSquare.setAmount(5000);
//        exitSquare.setEvent(new Event(EventType.MONEY, new MoneyStrategy()));
//NormalSquare parkingSquare= new NormalSquare(null, "Parking", null,null );
//
//
//
//
//Player humanPlayer = new HumanPlayer(new ArrayList<>(List.of(railWayProperty)) ,false);
//        humanPlayer.setName("Pepe");
//        humanPlayer.setBalance(100);
//
//Player botPlayer = new ConservativeBot(new ArrayList<>(List.of(railWayProperty)),false);
//        botPlayer.setName("BotJuan");
//        botPlayer.setBalance(200);
//
//Player botPlayer1 = new ConservativeBot(List.of(new CompanyProperty(false, "railway", 100)),true);
//        botPlayer1.setName("BotJuan");
//        botPlayer1.setBalance(200);
//
//Player botPlayer3 = new ConservativeBot(new ArrayList<>(),false);
//        botPlayer3.setName("BotJuan");
//        botPlayer3.setBalance(200);
//
//
//        propertySquare.addPlayer(humanPlayer);
//List<Square> squares = new ArrayList<>();
//
//        squares.add(propertySquare);
//        squares.add(parkingSquare);
//        squares.add(railwayPropertySquare);
//        squares.add(jailSquare);
//        squares.add(exitSquare);
//
//
//
//
//
//Board board = new Board();
//        board.setSquares(squares);
//
////new MainMenu().execute();
//Game game = new Game(board, new User("test", "test", "test"));
//game.getDice().throwDice();
//
//
//        humanPlayer.getCards().add(new Card(CardType.LUCK, new Event(EventType.GET_OUT_OF_JAIL, new GetOutOfJailStrategy()),"get out of jail card",null,null,false));
//
//        game.setPlayersIterator(new PlayerIterator(List.of(humanPlayer, botPlayer, botPlayer1, botPlayer3)));
//
////        humanPlayer.setGame(game);
//
//        game.addEvent("Player1 hace algo");
//        game.addEvent("Player2 hace algo");
//        game.addEvent("Player3 hace algo");
//        game.addEvent("Player4 hace algo");
//        game.addEvent("Player5 hace algo");
//        game.addEvent("Player6 hace algo");
//        game.addEvent("Player7 hace algo");
//        game.addEvent("Player8 hace algo");
//        game.addEvent("Player9 hace algo");
//        game.addEvent("Player10 hace algo");
//        game.addEvent("Player11 hace algo");
//        game.addEvent("Player12 hace algo");
//        game.addEvent("Player13 hace algo");
//        game.addEvent("Player14 hace algo");
//        game.addEvent("Player15 hace algoasdasd asdasdasd asdasda asdasdasdasda asdasdasdasdasdasdasdasdasdad asdasdasdasdasd asdasdddddddddddaaaaaassss asdasdad asdasdasdasd asdasdasd");
//
//
//
//        ConnexionDB.getInstance();
//
//        ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//            mapper.disable(DeserializationFeature
//                    .FAIL_ON_UNKNOWN_PROPERTIES);
//        String json = mapper.writeValueAsString(game);
//
//        // print the JSON output
//        System.out.println(json);
//
//            Game obj = mapper.readValue(json, Game.class);
//
//
//
//
//
//        int i =0;
////
//        String query = "INSERT INTO game (game) VALUES (?)";
//        try (Connection conn = connexionDB.connect();
//             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1,json);
//
//            int affectedRows = stmt.executeUpdate();
//
//            if (affectedRows == 0) {
//                throw new SQLException("Creating board failed, no rows affected.");
//            }
//
//            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                   i = (int) generatedKeys.getLong(1);
//                } else {
//                    throw new SQLException("Creating board failed, no ID obtained.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//            List<String> strs = new ArrayList<>();
//            query = "SELECT * FROM game";
//            try (Connection conn = connexionDB.connect();
//                 Statement stmt = conn.createStatement();
//                 ResultSet rs = stmt.executeQuery(query)) {
//                    while (rs.next()) {
//                            strs.add(rs.getString("game"));
//
//                    }
//            } catch (SQLException e) {
//                    e.printStackTrace();
//            }
//
//            Game obj2 = mapper.readValue(strs.get(0), Game.class);
//

    }



    }





