package model.dao.auditResult;

import model.AuditResult;

import java.sql.Connection;
import java.util.List;

public class AuditResultDaoJDBC implements AuditResultDao {

    private Connection conn;

    public AuditResultDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(AuditResult auditResult) {

    }

    @Override
    public void update(AuditResult auditResult) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public AuditResult findById(int id) {
        return null;
    }

    @Override
    public List<AuditResult> findAll() {
        return List.of();
    }
}
