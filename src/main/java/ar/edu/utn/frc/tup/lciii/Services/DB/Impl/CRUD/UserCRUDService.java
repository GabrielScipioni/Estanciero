package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.UserEntity;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.DB.CRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCRUDService implements CRUD<UserEntity> {
    private ConnexionDB connexionDB = ConnexionDB.getInstance();

    public static UserCRUDService instance;
    public static UserCRUDService getInstance(){
        if (instance==null){
            instance= new UserCRUDService();
        }
        return instance;
    }
    private UserCRUDService(){

    }

    public static void setInstance(UserCRUDService userCRUDService) {
        instance = userCRUDService;
    }


//    @Override
    public UserEntity getById(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserEntity user = new UserEntity();
                    user.setUserId(rs.getLong("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }






    public User getByUserNameAndPass(String userName, String pass) {
        String query = "SELECT * FROM users WHERE username = ? and password = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userName);
            stmt.setString(2, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("user_id"));
                    user.setUserName(rs.getString("username"));
                    user.setPassWord(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByUserName(String userName) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserName(rs.getString("username"));
                    user.setPassWord(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setId(rs.getLong("user_id"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }








//    @Override
    public User create(User user) {
        String query = "INSERT INTO users (username, password, name) VALUES (?, ?, ?)";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassWord());
            stmt.setString(3, user.getName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return user;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
