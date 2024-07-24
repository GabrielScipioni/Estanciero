package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR;

import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Entities.ZoneEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.OR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneORService implements OR<ZoneEntity> {

    @Override
    public ZoneEntity getById(Long id) {
        String query = "SELECT * FROM zone WHERE zone_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ZoneEntity zone = new ZoneEntity();
                    zone.setZoneId(rs.getLong("zone_id"));
                    zone.setDescription(rs.getString("description"));
                    return zone;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ZoneEntity> getAll() {
        List<ZoneEntity> zones = new ArrayList<>();
        String query = "SELECT * FROM zone order by 1";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ZoneEntity zoneEntity = new ZoneEntity();
                zoneEntity.setZoneId(rs.getLong("zone_id"));
                zoneEntity.setDescription(rs.getString("description"));
                zones.add(zoneEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zones;
    }
}
