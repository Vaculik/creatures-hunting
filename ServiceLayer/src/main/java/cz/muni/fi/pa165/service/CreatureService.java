package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vaculik on 20.11.15.
 */
@Service
public interface CreatureService {

    /**
     * Find a Creature entity by id.
     *
     * @param id id of the Creature
     * @return the found Creature or null if doesn't exist
     */
    public Creature getCreatureById(Long id);

    /**
     * Find a Creature entity by name.
     *
     * @param name name of the Creature
     * @return the found Creature or null if doesn't exist
     */
    public Creature getCreatureByName(String name);

    /**
     * Create the Creature entity.
     *
     * @param creature the Creature to be created
     */
    public void createCreature(Creature creature);

    /**
     * Delete the Creature entity.
     *
     * @param creature the Creature to be deleted
     */
    public void deleteCreature(Creature creature);

    /**
     * Update the Creature entity.
     *
     * @param creature the Creature to be updated
     */
    public void updateCreature(Creature creature);

    /**
     * Find all Creature entities.
     *
     * @return list of the results
     */
    public List<Creature> getAllCreatures();

    /**
     * Find all Creature entities of a given type.
     *
     * @param type the type
     * @return list of the results
     */
    public List<Creature> getCreaturesOfType(CreatureType type);

    /**
     * Find all Creature entities with the biggest height.
     *
     * @return list of the results
     */
    public List<Creature> getCreaturesWithMaxHeight();

    /**
     * Find all Creature entities with the biggest weight.
     *
     * @return list of the results
     */
    public List<Creature> getCreaturesWithMaxWeight();
}
