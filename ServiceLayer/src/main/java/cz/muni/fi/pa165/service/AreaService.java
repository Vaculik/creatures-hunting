/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;


import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author zbora
 */

@Service
public interface AreaService { 
    void createArea(Area area);
    void deleteArea(Area area);
    void updateArea(Area area);
    Area getAreaById(Long id);
    Area getAreaByame(String name);
    List<Area> findAllAreas();    
    
    public Map<Area,Integer> getAreasWithAmountCreatures();
    
    /**
     * This method gets creatures of specific Area
     * @param ar Area from which creatures are taken
     * @return list of creatures which occur in specific Area
     */
    public List<Area> getCreaturesOfArea(Area ar);
    
    
    
}
