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
 * @author Martin Zboril
 */
@Transactional
public interface AreaDao {
    /**
     * This method creates an area in a database
     * @param ar Area to be created
     */
    public void create(Area ar);
    
    /**
     * This method deletes an area in a database
     * @param ar Area to be deleted
     */
    public void delete(Area ar);
    
    /**
     * This method updates an area in a database
     * @param ar Area to be updated
     */
    public void update(Area ar);
    
    /**
     * This method finds all areas in a database
     * @return list of all areas in a database
     */
    public List<Area> findAll();
    
    /**
     * This method retrieves names of all areas
     * @return list of names of all areas in a database
     */
    public List<String> retrieveAreasNames();
    
    /**
     * This method retrieves areas with no creature in
     * @return list of all areas in a database where is no creature
     */
    public List<Area> retrieveAreasWithNoCreature();
    
    /**
     * This method retrieves areas with any creature in
     * @return list of all areas in a database where is any creature
     */
    public List<Area> retrieveAreasWithAnyCreature();
    
    /**
     * This method retrieves areas where is the highest amount of creatures
     * @return list of areas in a database where is the highest number of creatures
     */
    public List<Area> retrieveAreasMostCreatures();
    
    /**
     * This method retrieves areas where is the lowest amount of creatures
     * @return list of areas in a database where is the lowest number of creatures
     */
    public List<Area> retrieveAreasFewestCreatures();
    
    /**
     * This method retrieves an amount of creatures of specific area
     * @param ar Area of which we want the amount of creature
     * @return an amount of creatures of specific area
     */
    public int retrieveCreaturesAmount(Area ar);    
    
    /**
     * This method finds area by its id
     * @param id specific number (identification) which is unique for each area
     * @return id - identification number
     */
    public Area findById(Long id);
    
    /**
     * This method finds area by its name
     * @param name string-name of specific area
     * @return area with specific name
     */
    public Area findByName(String name);
    
    /**
     * This method adds a creature to an area
     * @param ar an area where to add
     * @param cr a creature which to add
     */
    public void addCreature(Area ar, Creature cr);
    
    /**
     * This method removes a creature from an area
     * @param ar an area where to remove from
     * @param cr a creature which to remove
     * @return removing was successful
     */
    public boolean removeCreature(Area ar, Creature cr);
             
    /**
     * This method checks if an area contains a creature
     * @param ar an area where to check it
     * @param cr a creature which to check
     * @return 
     */
    public boolean containAreaCreature(Area ar, Creature cr);

//Aby nebylo creature ve vice Area          
}
