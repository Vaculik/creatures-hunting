package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface represents service layer operations on objects of the class
 * Creature.
 *
 * @author Karel Vaculik
 */
@Service
public interface CreatureService {

    /**
     * Find a Creature entity by id.
     *
     * @param id id of the Creature
     * @return the found Creature or null if doesn't exist
     */
    Creature getCreatureById(Long id);

    /**
     * Find a Creature entity by name.
     *
     * @param name name of the Creature
     * @return the found Creature or null if doesn't exist
     */
    Creature getCreatureByName(String name);

    /**
     * Create the Creature entity.
     *
     * @param creature the Creature to be created
     */
    void createCreature(Creature creature);

    /**
     * Delete the Creature entity.
     *
     * @param creature the Creature to be deleted
     */
    void deleteCreature(Creature creature);

    /**
     * Update the Creature entity.
     *
     * @param creature the Creature to be updated
     */
    void updateCreature(Creature creature);

    /**
     * Find all Creature entities.
     *
     * @return list of the results
     */
    List<Creature> findAllCreatures();

    /**
     * Find all Creature entities of a given type.
     *
     * @param type the type
     * @return list of the results
     */
    List<Creature> getCreaturesOfType(CreatureType type);

    /**
     * Find all Creature entities with the biggest height.
     *
     * @return list of the results
     */
    List<Creature> getCreaturesWithMaxHeight();

    /**
     * Find all Creature entities with the biggest weight.
     *
     * @return list of the results
     */
    List<Creature> getCreaturesWithMaxWeight();

    /**
     * Find all Creature entities which are not in any area.
     *
     * @return list of the results
     */
    List<Creature> getCreaturesInNoArea();
}
