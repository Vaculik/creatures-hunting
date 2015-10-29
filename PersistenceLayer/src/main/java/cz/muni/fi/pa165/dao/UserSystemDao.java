package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.UserSystem;

public interface UserSystemDao {
    public UserSystem getById(Long id);
    public UserSystem getByName(String name);
    public List<UserSystem> findAll();
    public void create(UserSystem user);
    public void update(UserSystem user);
    public void remove(UserSystem user);
}
