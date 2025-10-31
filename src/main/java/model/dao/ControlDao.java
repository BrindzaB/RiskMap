package model.dao;

import model.Control;

import java.util.List;


public interface ControlDao {
    public void insert(Control control);
    public void update(Control control);
    public void deleteByID(int id);
    public Control findById(int id);
    public List<Control> findAll();

}
