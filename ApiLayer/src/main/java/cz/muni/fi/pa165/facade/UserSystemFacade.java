package cz.muni.fi.pa165.facade;

import java.util.List;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;

public interface UserSystemFacade {

    /**
     * This method creates a user.
     *
     * @param user UserSystemDTO to be created
     */
    void createUser(UserSystemDTO user);

    /**
     * This method deletes a user.
     *
     * @param user user to be deleted
     */
    void deleteUser(UserSystemDTO user);

    /**
     * This method updates a user.
     *
     * @param userUpdate the user
     */
    void updateUser(UserSystemUpdateDTO userUpdate);

    /**
     * Find all Users.
     *
     * @return list of the results
     */
    List<UserSystemDTO> getAllUsers();

    /**
     * Find all users of given type.
     *
     * @param type the type
     * @return list of the results
     */
    List<UserSystemDTO> getUsersOfType(UserType type);

    /**
     * Find all users of given sex.
     *
     * @param sex the type
     * @return list of the results
     */
    List<UserSystemDTO> getUsersOfSex(SexType sex);

    /**
     * Find a User by id.
     *
     * @param id id of the UserSystem
     * @return the found UserSystemDTO or null if doesn't exist
     */
    UserSystemDTO getUserById(Long id);

    /**
     * Find a User by user name.
     *
     * @param userName the user name of the UserSystem
     * @return the found UserSystemDTO or null if doesn't exist
     */
    UserSystemDTO getUserByUserName(String userName);

    /**
     * Authenticate a user with given user login information in userSystemLoginDTO.
     *
     * @param userSystemLoginDTO the userSystemLoginDTO
     * @return the UserSystemVerifiedDTO or null, if authentication failed
     */
    UserSystemVerifiedDTO login(UserSystemLoginDTO userSystemLoginDTO);

    /**
     * Change the password of the user.
     *
     * @param userSystemChangePasswordDTO the UserSystemChangePasswordDTO
     * @return true if the change was successful, otherwise return false
     */
    boolean changePassword(UserSystemChangePasswordDTO userSystemChangePasswordDTO);

    /**
     * Promote a user to admin.
     *
     * @param userSystemDTO the user
     */
    void promoteToAdmin(UserSystemDTO userSystemDTO);

    /**
     * Degrade a user from admin to user.
     *
     * @param userSystemDTO the user
     */
    void degradeToUser(UserSystemDTO userSystemDTO);
}