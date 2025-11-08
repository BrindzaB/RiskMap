package service;

import model.Control;
import model.dao.auditResult.AuditResultDao;
import model.dao.control.ControlDao;

import java.util.List;
import java.util.function.Predicate;

public class ControlService {
    private final ControlDao controlDao;
    private final AuditResultDao auditResultDao;

    public ControlService(ControlDao controlDao, AuditResultDao auditResultDao) {
        this.controlDao = controlDao;
        this.auditResultDao = auditResultDao;
    }

    public List<Control> getControlsByCondition(Predicate<Control> condition) {
        return controlDao.findAll().stream().filter(condition).toList();
    }

    public List<Control> getAllControls() {
        return controlDao.findAll();
    }

    public Control getControlById(int id) {
        return controlDao.findById(id);
    }

    public void addControl(Control control) {
        controlDao.insert(control);
    }

    public void updateControl(Control control) {
        controlDao.update(control);
    }

    public void deleteControl(int id) {
        controlDao.deleteByID(id);
    }


}
