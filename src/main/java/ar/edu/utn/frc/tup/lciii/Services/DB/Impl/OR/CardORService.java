package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR;

import ar.edu.utn.frc.tup.lciii.Entities.CardEntity;
import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.OR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardORService implements OR<CardEntity> {

    @Override
    public CardEntity getById(Long id) {
        String query = "SELECT * FROM card WHERE card_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CardEntity card = new CardEntity();
                    card.setCardId(rs.getLong("card_id"));
                    card.setCardTypeId(rs.getLong("card_type_id"));
                    card.setEventId(rs.getLong("event_id"));
                    card.setSquareId(rs.getLong("square_id"));
                    card.setMessage(rs.getString("message"));
                    card.setAmount(rs.getInt("amount"));
                    card.setCardTipeToPickId(rs.getLong("card_type_to_pick_up_id"));
                    card.setSalvagebleByPlayer(rs.getBoolean("salvageable_by_player"));
                    return card;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<CardEntity> getAll() {
        List<CardEntity> cardEntityList = new ArrayList<>();
        String query = "SELECT * FROM card";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                CardEntity card = new CardEntity();
                card.setCardId(rs.getLong("card_id"));
                card.setCardTypeId(rs.getLong("card_type_id"));
                card.setEventId(rs.getLong("event_id"));
                card.setSquareId(rs.getLong("square_id"));
                card.setMessage(rs.getString("message"));
                card.setAmount(rs.getInt("amount"));
                card.setCardTipeToPickId(rs.getLong("card_type_to_pick_up_id"));
                card.setSalvagebleByPlayer(rs.getBoolean("salvageable_by_player"));
                cardEntityList.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardEntityList;
    }
}
