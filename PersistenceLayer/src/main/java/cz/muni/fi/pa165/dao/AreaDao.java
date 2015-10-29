/*

* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class represents an interface for Area Data Access Object implementation.
 * It contains several basic methods.
 * @author Martin Zboril
 */
@Transactional
public interface AreaDao {
    /**
     * This method creates an area in a database
     * @param area Area to be created
     */
    public void create(Area area);
    
    /**
     * This method deletes an area in a database
     * @param area Area to be deleted
     */
    public void delete(Area area);
    
    /**
     * This method updates an area in a database
     * @param area Area to be updated
     */
    public void update(Area area);
    
    /**
     * This method finds all areas in a database
     * @return list of all areas in a database
     */
    public List<Area> findAll();  
    
    /**
     * This method finds area by its id
     * @param id specific number (identification) which is unique for each area
     * @return id - identification number
     */
    public Area getById(Long id);
    
    /**
     * This method finds area by its name
     * @param name string-name of specific area
     * @return area with specific name
     */
    public Area getByName(String name);          
}
