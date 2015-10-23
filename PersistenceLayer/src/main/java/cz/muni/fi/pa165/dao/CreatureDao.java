package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Creature;

import java.util.List;

/**
 * Created by vaculik on 23.10.15.
 */
public interface CreatureDao {

    Creature findById(Long id);

    Creature findByName(String name);

    void create(Creature creature);

    void delete(Creature creature);

    void update(Creature creature);

    List<Creature> findAll();
}
