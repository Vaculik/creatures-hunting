package cz.muni.fi.pa165.facade;

import java.util.List;

import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;

public interface UserSystemFacade {

    /**
     * This method creates a user.
     *
     * @param user UserSystemDTO to be created
     */
    public void createUser(UserSystemDTO user);

    /**
     * This method deletes a user.
     *
     * @param user user to be deleted
     */
    public void deleteUser(UserSystemDTO user);

    /**
     * This method updates a user.
     *
     * @param user UserSystemDTO to be updated
     */
    public void updateUser(UserSystemDTO user);

    /**
     * Find all Users.
     *
     * @return list of the results
     */
    public List<UserSystemDTO> getAllUsers();

    /**
     * Find all users of given type.
     *
     * @param type the type
     * @return list of the results
     */
    public List<UserSystemDTO> getUsersOfType(UserType type);

    /**
     * Find all users of given sex.
     *
     * @param sex the type
     * @return list of the results
     */
    public List<UserSystemDTO> getUsersOfSex(SexType sex);

    /**
     * Find a User by id.
     *
     * @param id id of the UserSystem
     * @return the found UserSystemDTO or null if doesn't exist
     */
    public UserSystemDTO getUserById(Long id);

    /**
     * Find a User by name.
     *
     * @param name name of the UserSystem
     * @return the found UserSystemDTO or null if doesn't exist
     */
    public UserSystemDTO getUserByName(String name);
}