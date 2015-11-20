package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CreatureDTO;

import java.util.List;

/**
 * Created by vaculik on 20.11.15.
 */
public interface CreatureFacade {

    public CreatureDTO getCreatureById(Long id);

    public CreatureDTO getCreatureByName(String name);

    public Long createCreature(CreatureDTO creatureDTO);

    public void deleteCreature(CreatureDTO creatureDTO);

    public Long updateCreature(CreatureDTO creatureDTO);

    public List<CreatureDTO> getAllCreatures();
}
