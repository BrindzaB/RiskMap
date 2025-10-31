package model.dao.control;

import model.Control;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "UPDATE controls SET name = ?, description = ?, impact = ?, likelihood = ?, owner = ?, WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, control.getName());
            st.setString(2, control.getDescription());
            st.setInt(3, control.getImpact());
            st.setInt(4, control.getLikelihood());
            st.setInt(5, control.getOwner().getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating control", e);
        }
    }

    @Override
    public void deleteByID(int id) {
        String sql = "DELETE FROM controls WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            int rows =  st.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No records found with id: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting control", e);
        }
    }

    @Override
    public Control findById(int id) {
        String sql = "SELECT controls.*, users.username, users.password FROM controls INNER JOIN users ON controls.owner = users.id WHERE controls.id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateControl(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding control with this id", e);
        }
    }

    @Override
    public List<Control> findAll() {
        String sql = "SELECT controls.*, users.username, users.password FROM controls INNER JOIN users ON controls.owner = users.id ORDER BY controls.id";

        List<Control> controls = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Control control = instantiateControl(rs);
                controls.add(control);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all controls", e);
        }

        return controls;
    }

    private Control instantiateControl(ResultSet rs) throws SQLException {
        int controlId = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        int impact = rs.getInt("impact");
        int likelihood = rs.getInt("likelihood");

        int userId = rs.getInt("owner");
        String username = rs.getString("username");
        String password = rs.getString("password");

        User owner = new User(userId, username, password);

        return new Control(controlId, name, description, impact, likelihood, owner);

    }

}
