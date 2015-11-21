package cz.muni.fi.pa165.facadeImpl;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by vaculik on 20.11.15.
 */
@Service
@Transactional
public class CreatureFacadeImpl implements CreatureFacade {

    @Autowired
    private CreatureService creatureService;

    @Autowired
    private EntityMapper entityMapper;


    @Override
    public CreatureDTO getCreatureById(Long id) {
        return entityMapper.map(creatureService.getCreatureById(id), CreatureDTO.class);
    }

    @Override
    public CreatureDTO getCreatureByName(String name) {
        return entityMapper.map(creatureService.getCreatureByName(name), CreatureDTO.class);
    }

    @Override
    public Long createCreature(CreatureDTO creatureDTO) {
        Creature newCreature = entityMapper.map(creatureDTO, Creature.class);
        creatureService.createCreature(newCreature);
        return newCreature.getId();
    }

    @Override
    public void deleteCreature(CreatureDTO creatureDTO) {
        creatureService.deleteCreature(entityMapper.map(creatureDTO, Creature.class));
    }

    @Override
    public List<CreatureDTO> getAllCreatures() {
        return entityMapper.map(creatureService.getAllCreatures(), CreatureDTO.class);
    }

    @Override
    public List<CreatureDTO> getCreaturesOfType(CreatureType type) {
        return entityMapper.map(creatureService.getAllCreatures(), CreatureDTO.class);
    }

    @Override
    public List<CreatureDTO> getCreaturesWithMaxHeight() {
        return entityMapper.map(creatureService.getAllCreatures(), CreatureDTO.class);
    }

    @Override
    public List<CreatureDTO> getCreaturesWithMaxWeight() {
        return entityMapper.map(creatureService.getAllCreatures(), CreatureDTO.class);
    }
}
