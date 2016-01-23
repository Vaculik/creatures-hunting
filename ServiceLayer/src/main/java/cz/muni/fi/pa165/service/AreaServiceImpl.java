package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.AreaDao;
import java.util.List;

import cz.muni.fi.pa165.dao.CreatureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import java.util.ArrayList;

/**
 * This class implements AreaService interface.
 *
 * @author Martin Zboril
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private CreatureDao creatureDao;

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
    public Area getAreaByName(String name) {
        return areaDao.getByName(name);
    }

    @Override
    public List<Area> findAllAreas() {
        return areaDao.findAll();
    }

    @Override
    public List<Area> getAreasWithNoCreature() {
        List<Area> result = new ArrayList<>();

        for (Area ar : areaDao.findAll()) {
            if (ar.getCreatures().isEmpty()) {
                result.add(ar);
            }
        }
        return result;
    }

    @Override
    public List<Area> getAreasWithAnyCreature() {
        List<Area> result = new ArrayList<>();

        for (Area ar : areaDao.findAll()) {
            if (!ar.getCreatures().isEmpty()) {
                result.add(ar);                          }
        }
        return result;
    }

    @Override
    public List<Area> getAreasMostCreatures() {
        List<Area> result = new ArrayList<>();
        Integer tmpSize = Integer.MIN_VALUE;
        for (Area ar : areaDao.findAll()) {
            if (ar.getCreatures().size() == tmpSize) {
                result.add(ar);
            }
            if (ar.getCreatures().size() > tmpSize) {
                tmpSize = ar.getCreatures().size();
                result.clear();
                result.add(ar);
            }
        }
        return result;
    }

    @Override
    public List<Area> getAreasFewestCreatures() {
        List<Area> result = new ArrayList<>();
        Integer tmpSize = Integer.MAX_VALUE;
        for (Area ar : areaDao.findAll()) {
            if (ar.getCreatures().size() == tmpSize) {
                result.add(ar);
            }
            if (ar.getCreatures().size() < tmpSize) {
                tmpSize = ar.getCreatures().size();
                result.clear();
                result.add(ar);
            }
        }
        return result;
    }

    @Override
    public void addCreature(Long areaId, String creatureName) {
        Area area = areaDao.getById(areaId);
        Creature creature = creatureDao.getByName(creatureName);

        area.addCreature(creature);
        areaDao.update(area);
    }
}