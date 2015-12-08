/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * This class is an interface for Service Layer of Area object. This class
 * ensures the business logic.
 *
 * @author Martin Zboril
 */
@Service
public interface AreaService {

    /**
     * This method creates an area
     *
     * @param area Area to be created
     */
    void createArea(Area area);

    /**
     * This method deletes an area.
     *
     * @param area AreaDTO to be deleted
     */
    void deleteArea(Area area);

    /**
     * This method updates an area.
     *
     * @param area AreaDTO to be updated
     */
    void updateArea(Area area);

    /**
     * This method finds area by its id
     *
     * @param id specific number (identification) which is unique for each area
     * @return Area with specific id
     */
    Area getAreaById(Long id);

    /**
     * This method finds area by its name
     *
     * @param name string-name of specific area
     * @return area with specific name
     */
    Area getAreaByName(String name);

    /**
     * This method finds all areas in a database
     *
     * @return list of all areas in a database
     */
    List<Area> findAllAreas();

    /**
     * This method moves the creature from one area to another area
     *
     * @param cr creature to be moved
     * @param fromAr area to be moved from
     * @param toAr area to be moved to
     * @return True - moving was successful, False - was not
     */
    boolean moveCreature(Creature cr, Area fromAr, Area toAr);

    /**
     * This method gets areas with no creature in
     *
     * @return list of all areas in a database where is no creature
     */
    List<Area> getAreasWithNoCreature();

    /**
     * This method gets areas with any creature in
     *
     * @return list of all areas in a database where is any creature
     */
    List<Area> getAreasWithAnyCreature();

    /**
     * This method gets areas where is the highest amount of creatures
     *
     * @return list of areas in a database where is the highest number of
     * creatures
     */
    List<Area> getAreasMostCreatures();

    /**
     * This method gets areas where is the lowest amount of creatures
     *
     * @return list of areas in a database where is the lowest number of
     * creatures
     */
    List<Area> getAreasFewestCreatures();
}