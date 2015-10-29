package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Weapon;
import java.util.List;
/**
 * This class represents an interface of Data Access Object of Weapon Entity. 
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
public interface WeaponDao {
    /**
     * Find weapon by it's id.
     * @param id is unique LongInt identifier of weapon (db primary key)
     * @return weapon with specified id
     */
    public Weapon getById(Long id);
    
    /**
     * Find weapon by it's name.
     * @param name is unique String identifier of weapon
     * @return weapon with specified name
     */
    public Weapon getByName(String name);
    
    /**
     * Get list of all weapons in db
     * @return list of all weapons in db
     */
    public List<Weapon> findAll();
    
    /**
     * Create (persist) weapon to db. All attributes of weapon must be set.
     * @param weapon object to be persisted.
     */
    public void create(Weapon weapon);
    
    /**
     * Update (merge) weapon in db
     * @param weapon new state of weapon to be saved
     */
    public void update(Weapon weapon);
    
    /**
     * Delete weapon from db
     * @param weapon weapon to be deleted
     */
    public void delete(Weapon weapon);
}
