package model.dao.auditResult;

import model.AuditResult;

import java.util.List;

public interface AuditResultDao {
    public void insert(AuditResult auditResult);
    public void update(AuditResult auditResult);
    public void deleteById(int id);
    public AuditResult findById(int id);
    public List<AuditResult> findAll();
}
