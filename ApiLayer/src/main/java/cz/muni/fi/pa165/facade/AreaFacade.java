package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.List;

/**
 * This class is an interface for Area Facade (Service layer). It creates a
 * facade for Creature hunting information system
 *
 * @author Martin Zboril
 */
public interface AreaFacade {

    /**
     * This method creates an area.
     *
     * @param area AreaDTO to be created
     * @return id of an area which was created
     */
    public Long createArea(AreaDTO area);

    /**
     * This method deletes an area.
     *
     * @param area AreaDTO to be deleted
     */
    public void deleteArea(AreaDTO area);

    /**
     * This method updates an area.
     *
     * @param area AreaDTO to be updated
     */
    public Long updateArea(AreaDTO area);

    /**
     * This method finds all areas in a database
     *
     * @return list of all areas in a database
     */
    public List<AreaDTO> getAllAreas();

    /**
     * This method gets names of all areas
     *
     * @return list of names of all areas in a database
     */
    public List<String> getAreasNames();

    /**
     * This method gets areas with no creature in
     *
     * @return list of all areas in a database where is no creature
     */
    public List<AreaDTO> getAreasWithNoCreature();

    /**
     * This method gets areas with any creature in
     *
     * @return list of all areas in a database where is any creature
     */
    public List<AreaDTO> getAreasWithAnyCreature();

    /**
     * This method gets areas where is the highest amount of creatures
     *
     * @return list of areas in a database where is the highest number of
     * creatures
     */
    public List<AreaDTO> getAreasMostCreatures();

    /**
     * This method gets areas where is the lowest amount of creatures
     *
     * @return list of areas in a database where is the lowest number of
     * creatures
     */
    public List<AreaDTO> getAreasFewestCreatures();

    /**
     * This method gets an amount of creatures of specific area
     *
     * @param ar Area of which we want the amount of creature
     * @return an amount of creatures of specific area
     */
    public int getCreaturesAmount(AreaDTO ar);

    /**
     * This method finds area by its id
     *
     * @param id specific number (identification) which is unique for each area
     * @return id - identification number
     */
    public AreaDTO getById(Long id);

    /**
     * This method finds area by its name
     *
     * @param name string-name of specific area
     * @return area with specific name
     */
    public AreaDTO getByName(String name);

    /**
     * This method adds a creature to an area
     *
     * @param area an area where to add
     * @param creature a creature which to add
     */
    public void addCreature(AreaDTO area, CreatureDTO creature);

    /**
     * This method removes a creature from an area
     *
     * @param area an area where to remove from
     * @param creature a creature which to remove
     * @return removing was successful
     */
    public void removeCreature(AreaDTO area, CreatureDTO creature);

    /**
     * This method checks if an area contains a creature
     *
     * @param area an area where to check it
     * @param creature a creature which to check
     * @return
     */
    public boolean containAreaCreature(AreaDTO area, CreatureDTO creature);

    /**
     * This method moves the creature from one area to another area
     *
     * @param creature creature to be moved
     * @param fromArea area to be moved from
     * @param toArea area to be moved to
     * @return moving was successful
     */
    public boolean moveCreature(CreatureDTO creature, AreaDTO fromArea, AreaDTO toArea);
}