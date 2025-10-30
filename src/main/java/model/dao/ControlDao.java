package model.dao;

import model.Control;

import java.util.List;
import java.util.UUID;

public interface ControlDao {
    public void insert(Control control);
    public void update(Control control);
    public void deleteByID(UUID id);
    public Control findById(UUID id);
    public List<Control> findAll();

}
