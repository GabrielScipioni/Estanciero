package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR;

import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Entities.SquareTypeEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.OR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SquareTypeORService implements OR <SquareTypeEntity> {

    @Override
    public SquareTypeEntity getById(Long id) {
        String query = "SELECT * FROM square_type WHERE square_type_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SquareTypeEntity squareType = new SquareTypeEntity();
                    squareType.setSquareTypeId(rs.getLong("square_type_id"));
                    squareType.setDescription(rs.getString("description"));
                    return squareType;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SquareTypeEntity> getAll() {
        List<SquareTypeEntity> squareTypeEntitiesList = new ArrayList<>();
        String query = "SELECT * FROM square_type order by 1";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                SquareTypeEntity squareTypeEntity = new SquareTypeEntity();
                squareTypeEntity.setSquareTypeId(rs.getLong("square_type_id"));
                squareTypeEntity.setDescription(rs.getString("description"));
                squareTypeEntitiesList.add(squareTypeEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return squareTypeEntitiesList;
    }
}

