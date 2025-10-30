package model.dao;

import model.Control;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class ControlDaoJDBC implements ControlDao {

    private Connection conn;

    public ControlDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Control control) {
        String sql = "INSERT INTO controls (name, description, impact, likelihood, owner) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, control.getName());
            st.setString(2, control.getDescription());
            st.setInt(3, control.getImpact());
            st.setInt(4, control.getLikelihood());
            st.setInt(5, control.getOwner().getId());
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    control.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting control", e);
        }
    }

    @Override
    public void update(Control control) {

    }

    @Override
    public void deleteByID(UUID id) {

    }

    @Override
    public Control findById(UUID id) {
        return null;
    }

    @Override
    public List<Control> findAll() {
        return List.of();
    }
}
