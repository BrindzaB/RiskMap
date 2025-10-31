package model.dao.user;

import model.User;

import java.util.List;

public interface UserDao {
    public void insert(User user);
    public void update(User user);
    public void deleteById(int id);
    public User findById(int id);
    public List<User> findAll();
}
