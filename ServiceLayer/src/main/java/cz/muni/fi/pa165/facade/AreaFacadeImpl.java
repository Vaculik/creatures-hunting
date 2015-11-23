/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.util.EntityMapper;
import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author Martin Zboril
 */
public class AreaFacadeImpl implements AreaFacade{

    @Autowired
    private AreaService areaService;

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public Long createArea(AreaDTO area) {
        Area ar = entityMapper.map(area, Area.class);
        areaService.createArea(ar);
        return ar.getId();
    }

    @Override
    public void deleteArea(AreaDTO area) {
        areaService.deleteArea(entityMapper.map(area, Area.class));
    }

    @Override
    public List<AreaDTO> getAllAreas() {
        return entityMapper.map(areaService.findAllAreas(), AreaDTO.class);
    }

    @Override
    public List<String> getAreasNames() {
        List<String> names = new ArrayList<>();
        for(AreaDTO area : entityMapper.map(areaService.findAllAreas(), AreaDTO.class)){
            names.add(area.getName());
        }
        return names;
    }

    @Override
    public List<AreaDTO> getAreasWithNoCreature() {
        return entityMapper.map(areaService.getAreasWithNoCreature(), AreaDTO.class);
    }

    @Override
    public List<AreaDTO> getAreasWithAnyCreature() {
        return entityMapper.map(areaService.getAreasWithAnyCreature(), AreaDTO.class);
    }

    @Override
    public List<AreaDTO> getAreasMostCreatures() {
        return entityMapper.map(areaService.getAreasMostCreatures(), AreaDTO.class);
    }

    @Override
    public List<AreaDTO> getAreasFewestCreatures() {
        return entityMapper.map(areaService.getAreasFewestCreatures(), AreaDTO.class);
    }

    @Override
    public int getCreaturesAmount(AreaDTO ar) {
        return entityMapper.map(areaService.getAreaById(ar.getId()), AreaDTO.class).getCreatures().size();
    }

    @Override
    public AreaDTO getById(Long id) {
        return entityMapper.map(areaService.getAreaById(id), AreaDTO.class);
    }

    @Override
    public AreaDTO getByName(String name) {
        return entityMapper.map(areaService.getAreaByName(name), AreaDTO.class);
    }

    @Override
    public void addCreature(AreaDTO area, CreatureDTO creature) {
        Area ar = entityMapper.map(area, Area.class);
        Creature cr = entityMapper.map(creature, Creature.class);
        ar.addCreature(cr);
        areaService.updateArea(ar);        
    }

    @Override
    public Long updateArea(AreaDTO area) {
        Area ar = entityMapper.map(area, Area.class);
        areaService.updateArea(ar);
        return ar.getId();
    }

    @Override
    public void removeCreature(AreaDTO area, CreatureDTO creature) {
        Area ar = entityMapper.map(area, Area.class);
        Creature cr = entityMapper.map(creature, Creature.class);
        ar.removeCreature(cr);
        areaService.updateArea(ar);     
    }

    @Override
    public boolean containAreaCreature(AreaDTO area, CreatureDTO creature) {
        Area ar = entityMapper.map(area, Area.class);
        Creature cr = entityMapper.map(creature, Creature.class);
        if(ar.getCreatures().contains(cr)){
            return true;
        }
        return false;
    }

    @Override
    public List<CreatureDTO> getCreaturesOfAreaById(Long id) {
        return entityMapper.map(areaService.getAreaById(id).getCreatures(), CreatureDTO.class);
    }

    @Override
    public boolean moveCreature(CreatureDTO creature, AreaDTO fromArea, AreaDTO toArea) {
        Area ar = entityMapper.map(fromArea, Area.class);
        Area ar2 = entityMapper.map(toArea, Area.class);
        Creature cr = entityMapper.map(creature, Creature.class);
        return areaService.moveCreature(cr, ar, ar2);
    }   
   
}












