package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR;

import ar.edu.utn.frc.tup.lciii.Entities.CardTypeEntity;
import ar.edu.utn.frc.tup.lciii.Entities.ProvinceEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.OR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvinceORService implements OR<ProvinceEntity> {
    @Override
    public ProvinceEntity getById(Long id) {
        String query = "SELECT * FROM province WHERE province_id = ?";
        try (Connection conn = connexionDB.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ProvinceEntity province = new ProvinceEntity();
                    province.setProvinceId(rs.getLong("province_id"));
                    province.setDescription(rs.getString("description"));
                    return province;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProvinceEntity> getAll() {
        List<ProvinceEntity> provinceEntityList = new ArrayList<>();
        String query = "SELECT * FROM province order by province_id";
        try (Connection conn = connexionDB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ProvinceEntity province = new ProvinceEntity();
                province.setProvinceId(rs.getLong("province_id"));
                province.setDescription(rs.getString("description"));
                provinceEntityList.add(province);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinceEntityList;
    }
}
