package cz.muni.fi.pa165.facadeImpl;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.facade.CreatureFacade;

import java.util.List;

/**
 * Created by vaculik on 20.11.15.
 */
public class CreatureFacadeImpl implements CreatureFacade {


    @Override
    public CreatureDTO getCreatureById(Long id) {
        return null;
    }

    @Override
    public CreatureDTO getCreatureByName(String name) {
        return null;
    }

    @Override
    public Long createCreature(CreatureDTO creatureDTO) {
        return null;
    }

    @Override
    public void deleteCreature(CreatureDTO creatureDTO) {

    }

    @Override
    public Long updateCreature(CreatureDTO creatureDTO) {
        return null;
    }

    @Override
    public List<CreatureDTO> getAllCreatures() {
        return null;
    }

    @Override
    public List<CreatureDTO> getCreaturesOfType(CreatureType type) {
        return null;
    }

    @Override
    public List<CreatureDTO> getCreaturesWithMaxHeight() {
        return null;
    }

    @Override
    public List<CreatureDTO> getCreaturesWithMaxWeight() {
        return null;
    }
}
