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
import java.util.Map;

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
    public List<Area> getCreaturesOfArea(Area ar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public Map<Area, Integer> getAreasWithAmountCreatures() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
