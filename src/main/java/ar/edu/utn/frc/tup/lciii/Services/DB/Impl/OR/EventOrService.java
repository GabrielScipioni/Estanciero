package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR;

import ar.edu.utn.frc.tup.lciii.Entities.EventEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.OR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventOrService implements OR<EventEntity> {
    @Override
    public EventEntity getById(Long id) {
        String query = "SELECT * FROM Events WHERE event_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EventEntity event = new EventEntity();
                    event.setEventId(rs.getLong("event_id"));
                    event.setDescription(rs.getString("description"));
                    return event;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EventEntity> getAll() {
        List<EventEntity> eventEntityList = new ArrayList<>();
        String query = "SELECT * FROM Events order by event_id";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                EventEntity event = new EventEntity();
                event.setEventId(rs.getLong("event_id"));
                event.setDescription(rs.getString("description"));
                eventEntityList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventEntityList;
    }
}
