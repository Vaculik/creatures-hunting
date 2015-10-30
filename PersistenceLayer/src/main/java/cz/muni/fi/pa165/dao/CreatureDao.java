package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Creature;

import java.util.List;

/**
 * The interface represents basic operations on Creature entity.
 *
 * @author Karel Vaculik
 */
public interface CreatureDao {

    /**
     * Find a Creature entity by id.
     *
     * @param id    id of a Creature entity
     * @return      the found Creature entity or null if the entity does not exist
     * @throws IllegalArgumentException if the id argument is null
     */
    Creature getById(Long id);

    /**
     * Find a Creature entity by name.
     *
     * @param name  name of a Creature entity
     * @return      the found Creature entity or null if the entity does not exist
     */
    Creature getByName(String name);

    /**
     * Create the Creature entity.
     *
     * @param creature  Creature entity
     * @throws IllegalArgumentException if the Creature argument is null
     */
    void create(Creature creature);

    /**
     * Remove the Creature entity.
     *
     * @param creature  Creature entity
     * @throws IllegalArgumentException if the Creature argument is null
     */
    void delete(Creature creature);

    /**
     * Update the Creature entity.
     *
     * @param creature  Creature entity
     * @throws IllegalArgumentException if the Creature argument is null
     */
    void update(Creature creature);

    /**
     * Find all Creature entities.
     *
     * @return list of the results
     */
    List<Creature> findAll();
}
