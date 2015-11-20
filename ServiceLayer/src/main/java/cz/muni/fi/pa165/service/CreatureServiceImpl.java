package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;

import java.util.List;

/**
 * Created by vaculik on 20.11.15.
 */
public class CreatureServiceImpl implements CreatureService {

    @Override
    public Creature getCreatureById(Long id) {
        return null;
    }

    @Override
    public Creature getCreatureByName(String name) {
        return null;
    }

    @Override
    public Long createCreature(Creature creature) {
        return null;
    }

    @Override
    public void deleteCreature(Creature creature) {

    }

    @Override
    public Long updateCreature(Creature creature) {
        return null;
    }

    @Override
    public List<Creature> getAllCreatures() {
        return null;
    }

    @Override
    public List<Creature> getCreaturesOfType(CreatureType type) {
        return null;
    }

    @Override
    public List<Creature> getCreaturesWithMaxHeight() {
        return null;
    }

    @Override
    public List<Creature> getCreaturesWithMaxWeight() {
        return null;
    }
}
