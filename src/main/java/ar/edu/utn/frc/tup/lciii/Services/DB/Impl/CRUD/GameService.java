package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.GamesEntity;
import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Game.GameDifficulty;
import ar.edu.utn.frc.tup.lciii.Model.Player.*;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.CRUD;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameService implements CRUD<GamesEntity> {

    private ConnexionDB connexionDB = ConnexionDB.getInstance();
    public static GameService instance;
    public static GameService getInstance(){
        if (instance==null){
            instance= new GameService();
        }
        return instance;
    }
    private GameService(){

    }
//    @Override
    public GamesEntity getById(Long id) {
        String query = "SELECT * FROM game WHERE game_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    GamesEntity game = new GamesEntity();
                    game.setGameId(rs.getLong("game_id"));
                    game.setDateSetup(rs.getTimestamp("datesetup").toLocalDateTime());
                    game.setFinished(rs.getBoolean("finished"));
                    game.setOrderByIdPlayer(rs.getString("order_by_id_player"));
                    return game;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
    public List<GamesEntity> getAll() {
        List<GamesEntity> games = new ArrayList<>();
        String query = "SELECT * FROM game";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                GamesEntity game = new GamesEntity();
                game.setGameId(rs.getLong("game_id"));
                game.setDateSetup(rs.getTimestamp("datesetup").toLocalDateTime());
                game.setFinished(rs.getBoolean("finished"));
                game.setOrderByIdPlayer(rs.getString("order_by_id_player"));
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public List<Game> getAllFromUser(User user) {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM  game where user_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, user.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Game game = deSerializeGame(rs.getString("game"));
                    game.setId(rs.getLong("game_id"));
                    game.setDate(rs.getDate("dateSetup"));
                    games.add(game);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }


//    @Override
    public Game update(Game game) {
        String query = "UPDATE game SET  finished = ?, game  = ? WHERE game_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, game.isFinishGame());
            stmt.setString(2, serializeGame(game));
            stmt.setLong(3, game.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return game;
    }

//    @Override
    public Game create(Game game, User user) {

        String gameJson = serializeGame(game);
        if(gameJson==null){
            return null;
        }

        String query = "INSERT INTO game (datesetup, finished, user_id, game) VALUES (?, ?, ?, ?)";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            stmt.setBoolean(2, false);
            stmt.setLong(3,user.getId());
            stmt.setString(4, gameJson);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating game failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    game.setId(generatedKeys.getLong(1));
                    return game;
                } else {
                    throw new SQLException("Creating game failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String serializeGame(Game game){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(game);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Game deSerializeGame(String gameJson){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(gameJson, Game.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Game gameFactory(GameDifficulty difficulty, User user){
        switch (difficulty){
            case EASY:
                return createEasyGame(user);

            case MEDIUM:
                return createMediumGame(user);
            case HARD:
                return createHardGame(user);
            default:
                return null;
        }
    }

    private Game createGameWithPlayers(List<Player> players, User user){
        List<Square> squares = SquareService.getInstance().getAll();
        List<Card> cards = CardsService.getInstance().getAll();
        Board board = new Board();
        board.setSquares(squares);

        Player humanPlayer = new HumanPlayer();
        humanPlayer.setName(user.getName());
        players.add(humanPlayer);

        return new Game(board, user, players, cards);
    }



    private Game createHardGame(User user) {

        Player aggressiveBotPlayer = new AggressiveBot();
        aggressiveBotPlayer.setName("Bot Nato");
        aggressiveBotPlayer.setId(1);

        Player moderateBotPlayer = new ModerateBot();
        moderateBotPlayer.setName("Bot Lucas");
        moderateBotPlayer.setId(2);

        Player moderateBotPlayer2 = new ModerateBot();
        moderateBotPlayer2.setName("Bot Agus");
        moderateBotPlayer2.setId(3);


        Player conservativeBotPlayer = new ConservativeBot();
        conservativeBotPlayer.setName("Bot Mati");
        conservativeBotPlayer.setId(4);

        List<Player>players = new ArrayList<>();

        players.add(moderateBotPlayer2);
        players.add(moderateBotPlayer);
        players.add(conservativeBotPlayer);
        players.add(aggressiveBotPlayer);

        return createGameWithPlayers(players,user);



    }

    private Game createMediumGame(User user) {
        Player aggressiveBotPlayer = new AggressiveBot();
        aggressiveBotPlayer.setName("Bot Agus");
        aggressiveBotPlayer.setId(1);

        Player moderateBotPlayer = new ModerateBot();
        moderateBotPlayer.setName("Bot Lucas");
        moderateBotPlayer.setId(2);

        Player conservativeBotPlayer = new ConservativeBot();
        conservativeBotPlayer.setName("Bot Mati");
        moderateBotPlayer.setId(3);

        List<Player>players = new ArrayList<>();


        players.add(moderateBotPlayer);
        players.add(conservativeBotPlayer);
        players.add(aggressiveBotPlayer);

        return createGameWithPlayers(players,user);
    }

    private Game createEasyGame(User user) {


        Player moderateBotPlayer = new ModerateBot();
        moderateBotPlayer.setName("Bot Lucas");
        moderateBotPlayer.setId(1);

        Player conservativeBotPlayer = new ConservativeBot();
        conservativeBotPlayer.setName("Bot Agus");
        conservativeBotPlayer.setId(2);


        List<Player>players = new ArrayList<>();

        players.add(moderateBotPlayer);
        players.add(conservativeBotPlayer);

        return createGameWithPlayers(players,user);
    }

}
