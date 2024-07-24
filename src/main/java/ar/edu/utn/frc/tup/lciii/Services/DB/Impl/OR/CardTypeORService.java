package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;
import ar.edu.utn.frc.tup.lciii.Entities.CardEntity;
import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Entities.UserEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.OR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardTypeORService implements OR<CardTypeEntity> {

    @Override
    public CardTypeEntity getById(Long id) {
        String query = "SELECT * FROM card_type WHERE card_type_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CardTypeEntity cardType = new CardTypeEntity();
                    cardType.setCardTypeId(rs.getLong("card_type_id"));
                    cardType.setDescription(rs.getString("description"));
                    return cardType;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CardTypeEntity> getAll() {
        List<CardTypeEntity> cardTypeEntityList = new ArrayList<>();
        String query = "SELECT * FROM card_type order by card_type_id";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                CardTypeEntity cardType = new CardTypeEntity();
                cardType.setCardTypeId(rs.getLong("card_type_id"));
                cardType.setDescription(rs.getString("description"));
                cardTypeEntityList.add(cardType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardTypeEntityList;
    }
}
