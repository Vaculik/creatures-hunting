package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.util.EntityMapper;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class implements the AreaFacade interface.
 *
 * @author Martin Zboril
 */
@Service
@Transactional
public class AreaFacadeImpl implements AreaFacade {

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
    public AreaDTO getById(Long id) {
        return entityMapper.map(areaService.getAreaById(id), AreaDTO.class);
    }

    @Override
    public AreaDTO getByName(String name) {
        return entityMapper.map(areaService.getAreaByName(name), AreaDTO.class);
    }

    @Override
    public void addCreature(AreaDTO area, CreatureDTO creature){
         if(creature != null){
            if(!area.getCreatures().contains(creature)){
                area.addCreature(creature);
                System.out.println("ABAA");
                System.out.println(area.getCreatures());
                Area ar = entityMapper.map(area, Area.class);
                System.out.println(ar.getCreatures());
                areaService.updateArea(ar);
            }
         }            
        }
}
