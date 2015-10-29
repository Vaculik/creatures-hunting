package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.UserSystem;

/**
 * This class represents an interface for UserSystem Data Access Object. It contains several basic methods.
 *
 * @author Jakub Miculka
 */
public interface UserSystemDao {

    public UserSystem getById(Long id);

    public UserSystem getByName(String name);

    public List<UserSystem> findAll();

    public void create(UserSystem user);

    public void update(UserSystem user);

    public void delete(UserSystem user);
}
