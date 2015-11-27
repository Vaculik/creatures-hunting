package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.enums.CreatureType;

import java.util.List;

/**
 * The interface represents operations on a transfer object of the class
 * CreatureDTO.
 *
 * @author Karel Vaculik
 */
public interface CreatureFacade {

    /**
     * Find a CreatureDTO by id.
     *
     * @param id id of the CreatureDTO
     * @return the found CreatureDTO or null if doesn't exist
     */
    public CreatureDTO getCreatureById(Long id);

    /**
     * Find a CreatureDTO by name.
     *
     * @param name name of the CreatureDTO
     * @return the found CreatureDTO or null if doesn't exist
     */
    public CreatureDTO getCreatureByName(String name);

    /**
     * Create the CreatureDTO.
     *
     * @param creatureDTO the CreatureDTO to be created
     * @return id of the created CreatureDTO
     */
    public Long createCreature(CreatureDTO creatureDTO);

    /**
     * Delete the CreatureDTO.
     *
     * @param creatureDTO the CreatureDTO to be deleted
     */
    public void deleteCreature(CreatureDTO creatureDTO);

    /**
     * Find all CreatureDTOs.
     *
     * @return list of the results
     */
    public List<CreatureDTO> getAllCreatures();

    /**
     * Find all CreatureDTOs of a given type.
     *
     * @param type the type
     * @return list of the results
     */
    public List<CreatureDTO> getCreaturesOfType(CreatureType type);

    /**
     * Find all CreatureDTOs with the biggest height.
     *
     * @return list of the results
     */
    public List<CreatureDTO> getCreaturesWithMaxHeight();

    /**
     * Find all CreatureDTOs with the biggest weight.
     *
     * @return list of the results
     */
    public List<CreatureDTO> getCreaturesWithMaxWeight();
}
