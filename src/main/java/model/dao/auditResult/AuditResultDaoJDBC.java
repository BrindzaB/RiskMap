package model.dao.auditResult;

import model.AuditResult;
import model.Result;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuditResultDaoJDBC implements AuditResultDao {

    private Connection conn;

    public AuditResultDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(AuditResult auditResult) {
        String sql = "INSERT INTO audit_results (controlId, date, result) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, auditResult.getControlId());
            st.setDate(2, Date.valueOf(auditResult.getDate()));
            st.setString(3, auditResult.getResult().name());
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    auditResult.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting new audit result", e);
        }

    }

    @Override
    public List<AuditResult> findByControlId(int controlId) {
        String sql = "SELECT * FROM audit_results WHERE controlId = ? ORDER BY date DESC";
        List<AuditResult> auditResults = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, controlId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                AuditResult auditResult = instatiateAuditResult(rs);
                auditResults.add(auditResult);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching audit result", e);
        }

        return auditResults;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM audit_results WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting audit result", e);
        }
    }

    private AuditResult instatiateAuditResult(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int controlId = rs.getInt("controlId");
        LocalDate date = rs.getDate("date").toLocalDate();
        Result result = Result.valueOf(rs.getString("result"));

        return new AuditResult(id, controlId, date, result);
    }

}
