package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR;


import ar.edu.utn.frc.tup.lciii.Entities.SquareEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.OR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SquareORService implements OR<SquareEntity> {
    @Override
    public SquareEntity getById(Long id) {
        String query = "SELECT * FROM square WHERE square_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SquareEntity square = new SquareEntity();
                    square.setSquareId(rs.getLong("square_id"));
                    square.setSquareTypeId(rs.getLong("square_type_id"));
                    square.setNameSquare(rs.getString("name_square"));
                    square.setProvinceId(rs.getLong("province_id"));
                    square.setZoneId(rs.getLong("zone_id"));
                    square.setUpgradePrice(rs.getInt("upgradeprice"));
                    square.setAmount(rs.getInt("amount"));
                    square.setEventId(rs.getLong("event_id"));
                    square.setEventWhenPassingId(rs.getLong("event_when_passing_id"));
                    square.setPrice(rs.getInt("price"));
                    square.setCardToPickId(rs.getLong("card_type_to_pick_id"));
                    return square;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SquareEntity> getAll() {
        List<SquareEntity> squareEntityList = new ArrayList<>();
        String query = "SELECT * FROM square order by square_id";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                SquareEntity square = new SquareEntity();
                square.setSquareId(rs.getLong("square_id"));
                square.setSquareTypeId(rs.getLong("square_type_id"));
                square.setNameSquare(rs.getString("name_square"));
                square.setProvinceId(rs.getLong("province_id"));
                square.setZoneId(rs.getLong("zone_id"));
                square.setUpgradePrice(rs.getInt("upgradeprice"));
                square.setAmount(rs.getInt("amount"));
                square.setEventId(rs.getLong("event_id"));
                square.setEventWhenPassingId(rs.getLong("event_when_passing_id"));
                square.setPrice(rs.getInt("price"));
                square.setCardToPickId(rs.getLong("card_type_to_pick_id"));
                squareEntityList.add(square);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return squareEntityList;
    }
}
