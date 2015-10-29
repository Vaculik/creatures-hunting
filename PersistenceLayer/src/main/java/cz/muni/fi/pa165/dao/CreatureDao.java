package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Creature;

import java.util.List;

/**
 * The interface represents basic operations on Creature entity.
 *
 * Created by vaculik on 23.10.15.
 */
public interface CreatureDao {

    Creature getById(Long id);

    Creature getByName(String name);

    void create(Creature creature);

    void delete(Creature creature);

    void update(Creature creature);

    List<Creature> findAll();
}
