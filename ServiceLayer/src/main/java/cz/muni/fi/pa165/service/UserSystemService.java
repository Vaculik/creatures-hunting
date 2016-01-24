package cz.muni.fi.pa165.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;

@Service
public interface UserSystemService {

    /**
     * This method creates a user.
     *
     * @param user UserSystem to be created
     */
    void createUser(UserSystem user);

    /**
     * This method deletes a user.
     *
     * @param user user to be deleted
     */
    void deleteUser(UserSystem user);

    /**
     * This method updates a user.
     *
     * @param user UserSystem to be updated
     */
    void updateUser(UserSystem user);

    /**
     * Find all Users.
     *
     * @return list of the results
     */
    List<UserSystem> getAllUsers();

    /**
     * Find all users of given type.
     *
     * @param type the type
     * @return list of the results
     */
    List<UserSystem> getUsersOfType(UserType type);

    /**
     * Find all users of given sex.
     *
     * @param sex the type
     * @return list of the results
     */
    List<UserSystem> getUsersOfSex(SexType sex);

    /**
     * Find a User by id.
     *
     * @param id id of the UserSystem
     * @return the found UserSystem or null if doesn't exist
     */
    UserSystem getUserById(Long id);

    /**
     * Find a User by name.
     *
     * @param name name of the UserSystem
     * @return the found UserSystemDTO or null if doesn't exist
     */
    UserSystem getUserByUserName(String name);

    /**
     * Authenticate a user with given login name and password.
     *
     * @param loginName the login name
     * @param password the password
     * @return the authenticated UserSystem or null, if authentication failed
     */
    UserSystem login(String loginName, String password);

    /**
     * Change a password of a user.
     *
     * @param user the user
     * @param originPassword the original password
     * @param newPassword the new password
     * @return true if the change was successful, otherwise return false
     */
    boolean changePassword(UserSystem user, String originPassword, String newPassword);
}
