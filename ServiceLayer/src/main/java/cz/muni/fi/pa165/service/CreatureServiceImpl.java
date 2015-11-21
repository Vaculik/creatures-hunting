package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CreatureDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vaculik on 20.11.15.
 */
@Service
public class CreatureServiceImpl implements CreatureService {

    @Autowired
    private CreatureDao creatureDao;

    @Override
    public Creature getCreatureById(Long id) {
        return creatureDao.getById(id);
    }

    @Override
    public Creature getCreatureByName(String name) {
        return creatureDao.getByName(name);
    }

    @Override
    public void createCreature(Creature creature) {
        creatureDao.create(creature);
    }

    @Override
    public void deleteCreature(Creature creature) {
        creatureDao.delete(creature);
    }

    @Override
    public void updateCreature(Creature creature) {
        creatureDao.update(creature);
    }

    @Override
    public List<Creature> getAllCreatures() {
        return creatureDao.findAll();
    }

    @Override
    public List<Creature> getCreaturesOfType(CreatureType type) {
        List<Creature> results = new LinkedList<>();
        List<Creature> creatures = creatureDao.findAll();

        for (Creature creature : creatures) {
           if (creature.getType() == type) {
               results.add(creature);
           }
        }
        return results;
    }

    @Override
    public List<Creature> getCreaturesWithMaxHeight() {
        List<Creature> results = new LinkedList<>();
        List<Creature> creatures = creatureDao.findAll();
        Integer maxHeight = null;
        Integer height;

        for (Creature creature : creatures) {
            height = creature.getHeight();
            if (height == null) {
                continue;
            }
            if (maxHeight == null || height > maxHeight) {
                maxHeight = height;
                results.clear();
                results.add(creature);
            } else if (height == maxHeight) {
                results.add(creature);
            }
        }
        return results;
    }

    @Override
    public List<Creature> getCreaturesWithMaxWeight() {
        List<Creature> results = new LinkedList<>();
        List<Creature> creatures = creatureDao.findAll();
        Integer maxWeight = null;
        Integer weight;

        for (Creature creature : creatures) {
            weight = creature.getWeight();
            if (weight == null) {
                continue;
            }
            if (maxWeight == null || weight > maxWeight) {
                maxWeight = weight;
                results.clear();
                results.add(creature);
            } else if (weight == maxWeight) {
                results.add(creature);
            }
        }
        return results;
    }
}
