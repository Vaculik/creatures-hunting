/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;


import cz.muni.fi.pa165.dao.AreaDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Martin Zboril
 */
@Service
public class AreaServiceImpl implements AreaService{

    @Autowired
   private AreaDao areaDao;
    
    @Override
    public void createArea(Area area) {
        areaDao.create(area);
    }

    @Override
    public void deleteArea(Area area) {
        areaDao.delete(area);
    }

    @Override
    public void updateArea(Area area) {
        areaDao.update(area);
    }

    @Override
    public Area getAreaById(Long id) {
        return areaDao.getById(id);
    }

    @Override
    public Area getAreaByame(String name) {
        return areaDao.getByName(name);
    }

    @Override
    public List<Area> findAllAreas() {
        return areaDao.findAll();
    }

    @Override
    public Map<Area, Integer> getAreasWithAmountCreatures() {
        Map<Area, Integer> result = new HashMap<Area, Integer>();
       
        for(Area ar : areaDao.findAll()){
            result.put(ar, new Integer(ar.getCreatures().size()));
        }                
        return result;
    }        
    
    @Override
    public boolean moveCreature(Creature cr, Area fromAr, Area toAr){
        if(cr == null || fromAr == null || toAr == null){
            throw new IllegalArgumentException("Creature or one of the Areas is null");
        }
        if(!fromAr.getCreatures().contains(cr)){
            throw new AreaServiceException("The area does not contain the creature");
        }
        
        fromAr.removeCreature(cr);
        toAr.addCreature(cr);     
        return true;
    }

    @Override
    public Set<Creature> commonCreatures(Area ar1, Area ar2) {
        if(ar1 == null || ar2 == null){
            throw new IllegalArgumentException("One of the Areas is null");
        }
        Set<Creature> result = new HashSet<Creature>();
        
        for(Creature cr : ar1.getCreatures()){
            if(ar2.getCreatures().contains(cr)){
                result.add(cr);
            }
        }
        return result;
    }
    
    
}
