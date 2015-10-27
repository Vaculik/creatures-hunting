package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.User;

public interface UserDao {
    public User getById(Long id);
    public User getByName(String name);
    public List<User> findAll();
    public void create(User user);
    public void update(User user);
    public void remove(User user);
}
