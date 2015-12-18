package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaAddCreatureDTO;
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
    Long createArea(AreaDTO area);

    /**
     * This method deletes an area.
     *
     * @param area AreaDTO to be deleted
     */
    void deleteArea(AreaDTO area);

    /**
     * This method finds all areas in a database
     *
     * @return list of all areas in a database
     */
    List<AreaDTO> getAllAreas();

    /**
     * This method gets areas with no creature in
     *
     * @return list of all areas in a database where is no creature
     */
    List<AreaDTO> getAreasWithNoCreature();

    /**
     * This method gets areas with any creature in
     *
     * @return list of all areas in a database where is any creature
     */
    List<AreaDTO> getAreasWithAnyCreature();

    /**
     * This method gets areas where is the highest amount of creatures
     *
     * @return list of areas in a database where is the highest number of
     * creatures
     */
    List<AreaDTO> getAreasMostCreatures();

    /**
     * This method gets areas where is the lowest amount of creatures
     *
     * @return list of areas in a database where is the lowest number of
     * creatures
     */
    List<AreaDTO> getAreasFewestCreatures();

    /**
     * This method finds area by its id
     *
     * @param id specific number (identification) which is unique for each area
     * @return id - identification number
     */
    AreaDTO getById(Long id);

    /**
     * This method finds area by its name
     *
     * @param name string-name of specific area
     * @return area with specific name
     */
    AreaDTO getByName(String name);

    /**
     * This method adds a creature to an area
     *
     * @param areaAddCreatureDTO an area where to add
     */
    void addCreature(AreaAddCreatureDTO areaAddCreatureDTO);
}