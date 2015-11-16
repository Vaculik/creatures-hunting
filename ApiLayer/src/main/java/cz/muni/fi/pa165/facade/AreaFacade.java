/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade;


import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.List;

/**
 *
 * @author Martin Zboril
 */
public interface AreaFacade {
    
    Long createArea(AreaDTO area);
    Long deleteArea(Long areaId);
    Long updateArea(AreaDTO area);
    
    
    /**
     * This method finds all areas in a database
     * @return list of all areas in a database
     */
    public List<AreaDTO> getAllAreas();
    
    /**
     * This method gets names of all areas
     * @return list of names of all areas in a database
     */
    public List<String> getAreasNames();
    
    /**
     * This method gets areas with no creature in
     * @return list of all areas in a database where is no creature
     */
    public List<AreaDTO> getAreasWithNoCreature();
    
    /**
     * This method gets areas with any creature in
     * @return list of all areas in a database where is any creature
     */
    public List<AreaDTO> getAreasWithAnyCreature();
    
    /**
     * This method gets areas where is the highest amount of creatures
     * @return list of areas in a database where is the highest number of creatures
     */
    public List<AreaDTO> getAreasMostCreatures();
    
    /**
     * This method gets areas where is the lowest amount of creatures
     * @return list of areas in a database where is the lowest number of creatures
     */
    public List<AreaDTO> getAreasFewestCreatures();
    
    /**
     * This method gets creatures of specific Area
     * @param ar Area from which creatures are taken
     * @return list of creatures which occur in specific Area
     */
    public List<AreaDTO> getCreaturesOfArea(AreaDTO ar);
    
    /**
     * This method gets an amount of creatures of specific area
     * @param ar Area of which we want the amount of creature
     * @return an amount of creatures of specific area
     */
    public int getCreaturesAmount(AreaDTO ar);    
    
    /**
     * This method finds area by its id
     * @param id specific number (identification) which is unique for each area
     * @return id - identification number
     */
    public AreaDTO getById(Long id);
    
    /**
     * This method finds area by its name
     * @param name string-name of specific area
     * @return area with specific name
     */
    public AreaDTO getByName(String name);
    
    /**
     * This method adds a creature to an area
     * @param ar an area where to add
     * @param cr a creature which to add
     */
    public void addCreature(Long areaId, Long creatureId);
    
    /**
     * This method removes a creature from an area
     * @param ar an area where to remove from
     * @param cr a creature which to remove
     * @return removing was successful
     */
    public boolean removeCreature(Long areaId, Long creatureId);
             
    /**
     * This method checks if an area contains a creature
     * @param ar an area where to check it
     * @param cr a creature which to check
     * @return 
     */
    public boolean containAreaCreature(Long areaId, Long creatureId);

}
