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
    public void createUser(UserSystem user);
    
    /**
     * This method deletes a user.
     *
     * @param user user to be deleted
     */
    public void deleteUser(UserSystem user);
    
    /**
     * This method updates a user.
     *
     * @param user UserSystem to be updated
     */
    public void updateUser(UserSystem user);
    
    /**
     * Find all Users.
     *
     * @return list of the results
     */
    public List<UserSystem> getAllUsers();

    /**
     * Find all users of given type.
     *
     * @param type the type
     * @return list of the results
     */
    public List<UserSystem> getUsersOfType(UserType type);
    
    /**
     * Find all users of given sex.
     *
     * @param sex the type
     * @return list of the results
     */
    public List<UserSystem> getUsersOfSex(SexType sex);
    
    /**
     * Find a User by id.
     *
     * @param id id of the UserSystem
     * @return the found UserSystem or null if doesn't exist
     */
    public UserSystem getUserById(Long id);

    /**
     * Find a User by name.
     *
     * @param name name of the UserSystem
     * @return the found UserSystemDTO or null if doesn't exist
     */
    public UserSystem getUserByName(String name);
}
