package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.AreaDTO;
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
    CreatureDTO getCreatureById(Long id);

    /**
     * Find a CreatureDTO by name.
     *
     * @param name name of the CreatureDTO
     * @return the found CreatureDTO or null if doesn't exist
     */
    CreatureDTO getCreatureByName(String name);

    /**
     * Create the CreatureDTO.
     *
     * @param creatureDTO the CreatureDTO to be created
     * @return id of the created CreatureDTO
     */
    Long createCreature(CreatureDTO creatureDTO);

    /**
     * Delete the CreatureDTO.
     *
     * @param creatureDTO the CreatureDTO to be deleted
     */
    void deleteCreature(CreatureDTO creatureDTO);

    /**
     * Update the CreatureDTO.
     *
     * @param creatureDTO the CreatureDTO to be updated
     */
    void updateCreature(CreatureDTO creatureDTO);

    /**
     * Find all CreatureDTOs.
     *
     * @return list of the results
     */
    List<CreatureDTO> getAllCreatures();

    /**
     * Find all CreatureDTOs of a given type.
     *
     * @param type the type
     * @return list of the results
     */
    List<CreatureDTO> getCreaturesOfType(CreatureType type);

    /**
     * Find all CreatureDTOs with the biggest height.
     *
     * @return list of the results
     */
    List<CreatureDTO> getCreaturesWithMaxHeight();

    /**
     * Find all CreatureDTOs with the biggest weight.
     *
     * @return list of the results
     */
    List<CreatureDTO> getCreaturesWithMaxWeight();

    /**
     * Find all CreatureDTOs which are not in any area.
     *
     * @return list of the results
     */
    List<CreatureDTO> getCreaturesInNoArea();

    /**
     * Get an AreaDTO of a creature specified by the id.
     *
     * @param creatureId the id of the creature
     * @return the AreaDTO
     */
    AreaDTO getAreaOfCreature(Long creatureId);

    /**
     * Remove an area of a creature specified by the id.
     *
     * @param creatureId the id of the creature
     */
    void removeAreaOfCreature(Long creatureId);
}
