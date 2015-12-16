package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entity.UserSystem;

/**
 * This class represents an interface for UserSystem Data Access Object. It
 * contains several basic methods.
 *
 * @author Jakub Miculka
 */
public interface UserSystemDao {

    /**
     * This method finds user by its id
     *
     * @param id specific number (identification) which is unique for each user
     * @return id - identification number
     */
    UserSystem getById(Long id);

    /**
     * This method finds user by its name
     *
     * @param name string-name of specific user
     * @return user with specific name
     */
    UserSystem getByName(String name);

    /**
     * This method finds all users in a database
     *
     * @return list of all users in a database
     */
    List<UserSystem> findAll();

    /**
     * This method creates a user in a database
     *
     * @param user UserSystem to be created
     */
    void create(UserSystem user);

    /**
     * This method updates a user in a database
     *
     * @param user UserSystem to be updated
     */
    void update(UserSystem user);

    /**
     * This method deletes a user in a database
     *
     * @param user UserSystem to be deleted
     */
    void delete(UserSystem user);
}
